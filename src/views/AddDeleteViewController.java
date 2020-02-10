package views;
/** This class is used as a controller for the Adding and Deleting of menu items.
 * 
 * @author : TeamProject 2020 group 22
 * 
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AddDeleteViewController {

  /** Constructor will set a new stage, allowing staff to fill in information.
   * 
   */
  public AddDeleteViewController() {
    try {
      FXMLLoader fLoad = new FXMLLoader(getClass().getResource("WaiterAddDelete.fxml"));
      Parent root = (Parent) fLoad.load();
      Stage stage = new Stage();
      stage.setTitle("Add/Delete items");
      stage.setScene(new Scene(root));
      stage.show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
  @FXML
  public void deletePush(ActionEvent event) throws Exception {
    System.out.println("delete");
  }
  
  @FXML
  public void addPush(ActionEvent event) throws Exception {
    System.out.println("add");
  }
  
}
