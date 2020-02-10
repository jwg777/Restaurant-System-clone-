package views;

import javafx.application.Application;

import javafx.stage.Stage;

/**
 * Main class to startup the application.
 */
public class Main extends Application {
	
  /** The button controller. */
  static SceneController controller = SceneController.getInstance();

  /**
   * Prepares the main menu view for startup.
   *
   * @param stage the stage
   * @throws Exception the exception
   */
  @Override
  public void start(Stage stage) throws Exception {
    stage.setResizable(false);
    controller.setStage(stage);
    controller.startMain();
  }

  /**
   * Launches the application.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
