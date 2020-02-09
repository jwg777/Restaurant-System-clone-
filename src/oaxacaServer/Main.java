package oaxacaServer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class Main extends Application {

	Server server = Server.getInstance();

	@Override
	public void start(Stage stage) {
		try {
			stage.setResizable(false);
			FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerView.fxml"));
			Parent parent = loader.load();
			Scene scene = new Scene(parent);
			stage.setTitle("Oaxaca Server");
			stage.setScene(scene);
			stage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
