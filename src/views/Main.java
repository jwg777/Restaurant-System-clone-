package views;

import javafx.application.Application;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class Main extends Application {
	
  /** The controller. */
  static ButtonController controller = ButtonController.getInstance();

  /**
   * Start.
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
   * The main method.
   *
   * @param args the arguments
   */
  public static void main(String[] args) {
    launch(args);
  }
}
