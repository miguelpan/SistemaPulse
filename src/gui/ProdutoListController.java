package gui;

import java.net.URL;
import java.util.ResourceBundle;

import application.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Produto;

public class ProdutoListController implements Initializable{

	@FXML
	private TableView<Produto> tableViewProduto;
	
	@FXML
	private TableColumn<Produto, Integer> tableCollumnId;
	
	@FXML
	private TableColumn<Produto, String> tableCollumnName;
	
	@FXML
	private Button btnew;
	
	@FXML
	public void onBtNewAction () {
		System.out.println("teste");
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

}
