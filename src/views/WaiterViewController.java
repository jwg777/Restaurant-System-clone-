package views;

import javafx.fxml.FXML;

public class WaiterViewController {
  
  ButtonController butController = ButtonController.getInstance();
  
  
  @FXML
  private void returnPush() throws Exception{
      butController.startMain();
  }
  
}
