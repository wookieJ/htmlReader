package htmlApp.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebView;

public class WebPaneController implements Initializable
{
	@FXML
	private WebView webView;
	
	public WebView getWebView()
	{
		return webView;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
	}
}
