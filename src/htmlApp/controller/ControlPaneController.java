package htmlApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class ControlPaneController implements Initializable
{
	@FXML
	private Button backButton;
	
	@FXML
	private Button forwardButton;
	
	@FXML
	private Button saveButton;

	@FXML
	private ToggleButton editButton;
		
	public Button getBackButton()
	{
		return backButton;
	}

	public Button getForwardButton()
	{
		return forwardButton;
	}

	public ToggleButton getEditButton()
	{
		return editButton;
	}
	
	public Button getSaveButton()
	{
		return saveButton;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
}