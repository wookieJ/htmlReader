package htmlApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeView;

public class TreeViewPaneController implements Initializable
{
	@FXML
	private TreeView<String> treeView;
	
	public TreeView<String> getTreeView()
	{
		return treeView;
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		
	}
	
	public TreeViewPaneController()
	{
	}
}
