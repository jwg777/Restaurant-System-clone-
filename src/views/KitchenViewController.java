package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

/**
 * The Class KitchenViewController.
 */
public class KitchenViewController {

  /** The menu tab pane. */
  @FXML
  private TabPane menuTabPane;

  /** The vbox new. */
  @FXML
  private VBox vboxNew;

  /** The vbox in progress. */
  @FXML
  private VBox vboxInProgress;

  /** The vbox completed. */
  @FXML
  private VBox vboxCompleted;

  /** The ordered list. */
  @FXML
  private ListView<?> orderedList;

  /** The reload. */
  @FXML
  private Button reload;

  /** The button controller. */
  SceneController butController = SceneController.getInstance();

  /**
   * When the 'Return to Main Menu button is pressed, return to the main menu.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception {
    butController.startMain();
  }

}
