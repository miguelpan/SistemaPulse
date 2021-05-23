package gui;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Produto;
import model.services.ProdutoServices;

public class ProdutoListController implements Initializable{

	private ProdutoServices service;
	
	@FXML
	private TableView<Produto> tableViewProduto;
	
	@FXML
	private TableColumn<Produto, Integer> tableCollumnId;
	
	@FXML
	private TableColumn<Produto, String> tableCollumnName;
	
	@FXML
	private Button btnew;
	
	private ObservableList<Produto> obsList;
	
	@FXML
	public void onBtNewAction () {
		System.out.println("teste");
	}
	
	public void setProdutoService(ProdutoServices service ) {
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
	}

}
