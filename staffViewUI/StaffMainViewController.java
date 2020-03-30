import java.io.IOException;
import java.util.HashMap;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import order.Order;
import order.OrderMap;

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

  OrderMap order = OrderMap.getInstance();

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

  /**
   * To initialiseGap for the kitchenView.java.
   * 
   * @return the Gap of the kitchen view.
   */
  private Node initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(15, 50);
    return gap;
  }

  /**
   * to InitialiseLabel in the kitchenView.
   * 
   * @param name name for the label.
   * @param width of the label.
   * @param height of the label.
   * @return the label that has been set.
   */
  private Node initialiseLabel(String name, int width, int height) {
    Label label = new Label(name);
    label.setPrefSize(width, height);
    return label;
  }

  @FXML
  private void processingPressed() {
    fade(processingPane);
    for (String string : order.keyArray()) {
      ObservableList<Order> observableList = order.get(string);
      VBox vbox = new VBox();
      for (Order order : observableList) {
        HBox tempHBox = new HBox();
        tempHBox.setPrefHeight(50);
        tempHBox.getChildren().add(initialiseGap());
        tempHBox.getChildren().add(initialiseLabel("#" + order.getOrderID(), 100, 50));
        tempHBox.getChildren().add(initialiseGap());
        String price = String.format("%.2f", order.getPrice()); // Always show 2 decimal Place
        tempHBox.getChildren()
            .add(initialiseLabel(Character.toString((char) 163) + price, 100, 50));
        tempHBox.getChildren().add(initialiseGap());
        tempHBox.getChildren().add(initialiseLabel(order.getTimeStamp(), 150, 50));
        tempHBox.getChildren().add(initialiseGap());
      }
    }
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


