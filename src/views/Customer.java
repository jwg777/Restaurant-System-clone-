package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Customer extends Application {

    @Override
    public void start(Stage stage) throws Exception {
      Parent root = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
    
      Scene scene = new Scene(root, 362, 213);
    
      stage.setTitle("Oaxaca Customer View");
      stage.setScene(scene);
      stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}