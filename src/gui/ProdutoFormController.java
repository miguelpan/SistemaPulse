package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import model.entities.Produto;
import model.services.ProdutoServices;

public class ProdutoFormController implements Initializable{
	
	private Produto entity;
	private ProdutoServices service;
	
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

	public void setProduto(Produto entity) {
		this.entity = entity;
	}
	public void setProdutoServices(ProdutoServices service) {
		this.service = service;
	}	
	
	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if (entity == null) {
			throw new IllegalAccessError("Entity null");
		}
		if (service == null) {
			throw new IllegalAccessError("Service null");
		}
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);			
			Utils.palcoAtual(event).close();
		}catch (DbException e) {
			Alerts.showAlert("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}

	}
	
	private Produto getFormData() {
		Produto obj = new Produto();
		
		obj.setId( Utils.tryParseToInt(txtid.getText()));
		obj.setName(txtname.getText());
		
		return obj;
	}
	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.palcoAtual(event).close();
	}	
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();
	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtid);
		Constraints.setTextFieldMaxLength(txtname, 30);
	}
	
	public void updateFormData() {
		if (entity == null) {
			throw new IllegalAccessError("Entity não instanciada corretamente");
		}
		txtid.setText(String.valueOf(entity.getId()));
		txtname.setText(entity.getName());
	}
}
