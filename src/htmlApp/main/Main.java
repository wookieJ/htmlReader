package htmlApp.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage primaryStage) throws Exception
	{
		Parent parent = (Parent) FXMLLoader.load(getClass().getResource("/htmlApp/view/MainPane.fxml"));
		Scene scene = new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.getIcons().add(new Image("/htmlApp/icons/windowIcon.png"));
		primaryStage.setTitle("HTMLOpenApp");
		
		primaryStage.show();
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
}
