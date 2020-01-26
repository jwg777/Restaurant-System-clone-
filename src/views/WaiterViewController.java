package views;

import javafx.fxml.FXML;

// TODO: Auto-generated Javadoc
/**
 * The Class WaiterViewController.
 */
public class WaiterViewController {
  
  /** The but controller. */
  ButtonController butController = ButtonController.getInstance();
  
  
  /**
   * Return push.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception{
      butController.startMain();
  }
  
}
