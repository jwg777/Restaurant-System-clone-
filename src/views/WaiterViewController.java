package views;

import javafx.fxml.FXML;

/**
 * Controller for the waiter view..
 */
public class WaiterViewController {
  
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
