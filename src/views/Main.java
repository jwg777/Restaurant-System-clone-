package views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  static ButtonController controller = ButtonController.getInstance();

  @Override
  public void start(Stage stage) throws Exception {
	controller.setStage(stage);
	controller.startMain();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
