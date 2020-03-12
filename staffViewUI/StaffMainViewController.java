import java.io.IOException;
import java.util.HashMap;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class StaffMainViewController {

  @FXML
  private StackPane confirmationPane;

  @FXML
  private TextField tableField;

  @FXML
  private Button confirmButton;

  @FXML
  private AnchorPane processingPane;

  @FXML
  private AnchorPane newOrderPane;

  private Node frontPane;

  MenuMap menu = MenuMap.getInstance();

  HashMap<String, Button> buttons = new HashMap<>();

  @FXML
  private void initialize() throws IOException {
    newOrderPane.toFront();
    frontPane = newOrderPane;
    confirmationPane.toFront();


  }



  public void fade(Node node) {
    if (!frontPane.equals(node)) {
      FadeTransition ft = new FadeTransition();
      ft.setDuration(Duration.millis(250));
      ft.setFromValue(0);
      ft.setToValue(10);
      ft.setNode(node);
      node.toFront();
      ft.play();
      frontPane = node;
    }
  }


  @FXML
  private void newOrdersPressed() {
    fade(newOrderPane);
  }

  @FXML
  private void processingPressed() {
    fade(processingPane);
  }


  @FXML
  private void logoutPressed() {
    fade(confirmationPane);
  }

  @FXML
  private void tableNumConfirmed() {
    try {
      int tableNum = Integer.valueOf(tableField.getText());
      /*
       * Confirm table number with server. And get the customer ID.
       */
      confirmationPane.toBack();
    } catch (Exception e) {
      tableField.setText("");
    }
  }

  /*
   * Temp buttons for testing.
   */

  int i1 = 0;
  int i2 = 0;
  int i3 = 0;

  @FXML
  private void button1() {
    String type = "Category1";
    String name = "Consumable " + (i1++);
    menu.put(new Consumable(type, name, 10.10f, 100, true, "Ingredient1, " + i1));
  }

  @FXML
  private void button2() {
    String type = "Category2";
    String name = "Consumable " + (i2++);
    menu.put(new Consumable(type, name, 10.10f, 100, true, "Ingredient1, " + i2));
  }

  @FXML
  private void button3() {
    String type = "Category3";
    String name = "Consumable " + (i3++);
    menu.put(new Consumable(type, name, 10.10f, 100, true, "Ingredient1, " + i3));
  }

}


