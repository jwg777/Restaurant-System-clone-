package views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class KitchenViewController {

  @FXML
  private TabPane menuTabPane;

  @FXML
  private VBox vboxNew;

  @FXML
  private VBox vboxInProgress;

  @FXML
  private VBox vboxCompleted;

  @FXML
  private ListView<?> orderedList;

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
  private void returnPush() throws Exception{
    butController.startMain();
  }
  
}
