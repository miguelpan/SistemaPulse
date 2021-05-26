package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Produto;
import model.exceptions.ValidationException;
import model.services.ProdutoServices;

public class ProdutoFormController implements Initializable{
	
	private Produto entity;
	private ProdutoServices service;
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();
	
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
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
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
			notifyDataChangeListeners();
			Utils.palcoAtual(event).close();
		}
		catch (ValidationException e) {
			setErroMessages(e.getErrors());
		}
		catch (DbException e) {
			Alerts.showAlert("Erro ao salvar o objeto", null, e.getMessage(), AlertType.ERROR);
		}

	}
	
	private void notifyDataChangeListeners() {
		for (DataChangeListener listeners : dataChangeListeners) {
			listeners.onDataChanged();
		}
		
	}
	private Produto getFormData() {
		Produto obj = new Produto();
		
		ValidationException exception = new  ValidationException("Erro de validação dos campos");
				
		obj.setId( Utils.tryParseToInt(txtid.getText()));
		if (txtname.getText() == null || txtname.getText().trim().equals("")) {
			exception.addError("name", "Campo em branco");
		}
		obj.setName(txtname.getText());
		
		if (exception.getErrors().size()>0) {
			throw exception;
		}
		
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
	
	private void setErroMessages(Map<String, String> errors){
		Set<String> fields = errors.keySet();
		if (fields.contains("name")) {
			labelError.setText(errors.get("name"));
		}
	}
}
