package gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MainViewController implements Initializable {
	
	@FXML
	private MenuItem menuItemProduto;
	
	@FXML
	private MenuItem menuItemFormaPag;
	
	@FXML
	private MenuItem menuItemAbout;	    
	
	@FXML
	public void onMenuItemProdutoAction() {
		System.out.println("menuItemProduto");
	}
	
	@FXML
	public void onMenuItemFormaPagAction() {
		System.out.println("menuItemFormaPag");
	}
	
	@FXML
	public void onMenuItemAboutAction() {
		System.out.println("menuItemAbout");
	}
		
	@Override
	public void initialize(URL uri, ResourceBundle rb) {
	}

}
