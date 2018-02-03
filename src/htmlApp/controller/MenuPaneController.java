package htmlApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;

public class MenuPaneController implements Initializable
{
	@FXML
	private MenuItem openMenu;
	
	public MenuItem getOpenMenu()
	{
		return openMenu;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
}
