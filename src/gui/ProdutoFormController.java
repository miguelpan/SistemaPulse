package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ProdutoFormController implements Initializable{
	
	@FXML
	private TextField txtid;
	
	@FXML
	private TextField txtname;
	
	@FXML
	private Label labelError;
	
	@FXML
	private Button btSave;
	
	@FXML
	private Button btCancel;
	
	@FXML
	public void onBtSaveAction() {
		System.out.println("Salvar");
	}
	
	@FXML
	public void onBtCancelAction() {
		System.out.println("Cancelar");
	}	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtid);
		Constraints.setTextFieldMaxLength(txtname, 30);
	}
}
