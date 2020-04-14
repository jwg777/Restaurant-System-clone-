
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class IllegalDetails extends IllegalArgumentException {


  /**
   * 
   */
  private static final long serialVersionUID = -2196904846817836605L;

  /**
   * Exception constructor that will send an alert with a message.
   * 
   * @param message The message given to show in the alert
   */
  public IllegalDetails(String message) {
    Alert alert = new Alert(AlertType.CONFIRMATION, message);
    alert.setTitle("IllegalCardDetails");
    alert.showAndWait();
  }
}
