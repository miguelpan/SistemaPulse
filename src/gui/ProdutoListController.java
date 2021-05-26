package gui;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbIntegrityException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Produto;
import model.services.ProdutoServices;

public class ProdutoListController implements Initializable, DataChangeListener {

	private ProdutoServices service;

	@FXML
	private TableView<Produto> tableViewProduto;

	@FXML
	private TableColumn<Produto, Integer> tableCollumnId;

	@FXML
	private TableColumn<Produto, String> tableCollumnName;

	@FXML
	private TableColumn<Produto, Produto> tableColumnEdit;

	@FXML
	private TableColumn<Produto, Produto> tableColumnREMOVE;

	@FXML
	private Button btnew;

	private ObservableList<Produto> obsList;

	@FXML
	public void onBtNewAction(ActionEvent event) {
		Stage parentStage = Utils.palcoAtual(event);
		Produto obj = new Produto();
		createDialogForm(obj, "/gui/ProdutoForm.fxml", parentStage);
	}

	public void setProdutoService(ProdutoServices service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}

	private void initializeNodes() {
		tableCollumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableCollumnName.setCellValueFactory(new PropertyValueFactory<>("name"));

		Stage stage = (Stage) Main.getMainScene().getWindow();
		tableViewProduto.prefHeightProperty().bind(stage.heightProperty());
	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("service estava null");
		}
		List<Produto> list = service.findAll();
		obsList = FXCollections.observableArrayList(list);
		tableViewProduto.setItems(obsList);
		initEditButtons();
		initRemoveButtons();
	}

	private void createDialogForm(Produto obj, String absoluteName, Stage parenteStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ProdutoFormController controller = loader.getController();
			controller.setProduto(obj);
			controller.setProdutoServices(new ProdutoServices());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("NOVO PRODUTO");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parenteStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();
		} catch (IOException e) {
			Alerts.showAlert("IO Exception", "Erro loadView", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();

	}

	private void initEditButtons() {
		tableColumnEdit.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEdit.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ProdutoForm.fxml", Utils.palcoAtual(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Produto, Produto>() {
			private final Button button = new Button("Excluir");

			@Override
			protected void updateItem(Produto obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}

	private void removeEntity(Produto obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("cONFIRMAÇÃO", "Deseja apagar esse produto?");
		if (result.get()== ButtonType.OK) {
			if (service ==null) {
				throw new IllegalStateException("Service nulo");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch(DbIntegrityException e) {
				Alerts.showAlert("Erro ao remover o produto", null, e.getMessage(), AlertType.ERROR);
			}

		}
		
	}

}
