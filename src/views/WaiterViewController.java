package views;

import java.util.ArrayList;
import java.util.Optional;
import backend.WaiterAccess;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import order.Order;
import order.OrderMap;


// TODO: Auto-generated Javadoc
/**
 * Controller for the waiter view..
 */

public class WaiterViewController {

  /** The waiter data. */
  WaiterAccess waiterData = new WaiterAccess();

  /** The button controller. */
  SceneController butController = SceneController.getInstance();

  /** The menu. */
  MenuMap menu = MenuMap.getInstance();

  /** The orders. */
  OrderMap orders = OrderMap.getInstance();

  /**
   * When the 'Return to Main Menu button is pressed, return to the main menu.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception {
    butController.startMain();
  }

  @FXML
  private TextField dishName;

  @FXML
  private TextField type;

  @FXML
  private TextField price1;

  @FXML
  private TextField price2;

  @FXML
  private TextField allergies1;

  @FXML
  private TextField allergies2;

  @FXML
  private TextField allergies3;

  @FXML
  private TextField allergies4;

  @FXML
  private TextField allergies5;

  @FXML
  private TextField calories;

  @FXML
  private Button addItem;


  /**
   * Declare the menuTabPane in the Tab.
   */
  @FXML
  TabPane menuTabPane = new TabPane();
  /**
   * Declare the orderTabPane in the Tab.
   */
  @FXML
  TabPane orderTabPane = new TabPane();

  @FXML
  Alert deleteAlert = new Alert(AlertType.NONE);

  @FXML
  Alert addAlert = new Alert(AlertType.NONE);

  @FXML
  ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

  @FXML
  ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);

  boolean emptyTextField;

  /**
   * Method for when the delete button is pushed.
   * 
   * @param event for when button is pressed
   * @throws Exception thrown if javafx error occurs
   * 
   */
  @FXML
  private void deletePush(ActionEvent event) throws Exception {
    emptyTextField = false;
    checkNotEmpty(dishName);
    dishName.setText(limitChars(dishName.getText(), 100));
    deleteAlert.setContentText("Are you sure you want to delete this dish?");
    deleteAlert.setAlertType(AlertType.CONFIRMATION);
    deleteAlert.getButtonTypes().setAll(yesButton, noButton);
    deleteAlert.showAndWait().ifPresent(buttonType -> {
      if (buttonType == yesButton && !emptyTextField) {
        try {
          if (waiterData.checkKeyExists(dishName.getText())) {
            waiterData.deleteMenuItem(dishName.getText());
            Stage stage = (Stage) addItem.getScene().getWindow();
            stage.close();
          } else {
            deleteAlert = new Alert(AlertType.NONE);
            deleteAlert.setContentText("Dish does not exist or is empty");
            deleteAlert.setAlertType(AlertType.ERROR);
            deleteAlert.show();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });;
  }

  /**
   * Method for when add button is pushed.
   * 
   * @param event for when button is pressed
   * @throws Exception thrown if javafx error occurs
   * 
   */
  @FXML
  private void addPush(ActionEvent event) throws Exception {
    emptyTextField = false;
    dishName.setText(limitChars(dishName.getText(), 100));
    type.setText(limitChars(type.getText(), 50));
    price1.setText(limitChars(price1.getText(), 4));
    price2.setText(limitChars(price2.getText(), 2));
    allergies1.setText(limitChars(allergies1.getText(), 100));
    allergies2.setText(limitChars(allergies2.getText(), 100));
    allergies3.setText(limitChars(allergies3.getText(), 100));
    allergies4.setText(limitChars(allergies4.getText(), 100));
    allergies5.setText(limitChars(allergies5.getText(), 100));
    checkNotEmpty(price1);
    if (emptyTextField) {
      price1.setText("0");
    }
    checkNotEmpty(price2);
    if (emptyTextField) {
      price2.setText("00");
    }
    checkNotEmpty(dishName);
    checkNotEmpty(type);
    checkNotEmpty(calories);
    String strPrice = price1.getText() + "." + price2.getText();
    float floatPrice = Float.parseFloat(strPrice);
    String alls = allergies1.getText() + " / " + allergies2.getText() + " / " + allergies3.getText()
        + " / " + allergies4.getText() + " / " + allergies5.getText();
    if (!emptyTextField) {
      try {
        if (!waiterData.checkKeyExists(dishName.getText())) {
          waiterData.addMenuItem("", "'" + dishName.getText() + "', '" + floatPrice + "', '" + alls
              + "', '" + Integer.parseInt(calories.getText()) + "', '" + type.getText() + "'");

          Stage stage = (Stage) addItem.getScene().getWindow();
          stage.close();

        } else {
          addAlert.setContentText("Dish already exists");
          addAlert.setAlertType(AlertType.ERROR);
          addAlert.show();
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      addAlert.setContentText("Empty field exists");
      addAlert.setAlertType(AlertType.ERROR);
      addAlert.show();
    }
  }

  /**
   * reloadPush() methods to input the value when the reload button is pressed. this will create the
   * menu to test the functions.
   * 
   * @throws Exception if the error occurs.
   */
  @FXML
  public void menuReload() throws Exception {
    menu.clear();
    waiterData.getMenu();
    menuTabPane.getTabs().clear();
    createMenu(menu);
  }

  @FXML
  private void addDeletePush() throws Exception {
    AddDeleteViewController a = new AddDeleteViewController();
  }

  /**
   * order Reload method() to input the value when the button is pressed.
   * 
   * @throws Exception
   */
  @FXML
  private void orderReload() throws Exception {
    orders.clear();
    waiterData.viewOrders();

    orderTabPane.getTabs().clear();
    createOrders(orders);
  }

  /**
   * Displays the current menu in the GUI.
   * 
   * @param consumables the items on the menu
   * @return VBox of what has been set.
   */

  private VBox createMenuVBox(ArrayList<Consumable> consumables) {
    VBox vbox = new VBox();
    for (Consumable consumable : consumables) {
      HBox tempHBox = new HBox(); // Layout for one consumable of the list
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
      tempHBox.getChildren().add(initialiseLabel("� " + price, 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
  }


  /**
   * Displays the orders currently on the database.
   * 
   * @param orders the orders on the database
   * @return VBox of what has been set
   */
  private VBox createOrderVBox(ArrayList<Order> orders) {
    VBox vbox = new VBox();
    for (Order order : orders) {
      HBox tempHBox = new HBox();
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel("#" + order.getOrderID(), 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", order.getTotalPrice());
      tempHBox.getChildren().add(initialiseLabel("� " + price, 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      if (order.getStatus().equals("waiting")) {
        StackPane confirmStackPane = initialiseButton("Confirm", 12, 70);
        ((Button) confirmStackPane.getChildren().get(0))
            .setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                try {
                  confirmOrder();
                  vbox.getChildren().remove(tempHBox);
                } catch (Exception e) {
                  // TODO Auto-generated catch blocks
                  e.printStackTrace();
                }
              }
            });
        tempHBox.getChildren().add(confirmStackPane);
      } else if (order.getStatus().equals("processing")) {
        StackPane cancelStackPane = initialiseButton("Cancel", 12, 70);
        ((Button) cancelStackPane.getChildren().get(0))
            .setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                try {
                  cancelOrder();
                  vbox.getChildren().remove(tempHBox);
                  waiterData.removeOrder(order);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
        tempHBox.getChildren().add(cancelStackPane);
      } else if (order.getStatus().contentEquals("ready")) {
        StackPane confirmDeliveredStackPane = initialiseButton("Confirm", 12, 70);
        ((Button) confirmDeliveredStackPane.getChildren().get(0))
            .setOnAction(new EventHandler<ActionEvent>() {
              @Override
              public void handle(ActionEvent event) {
                try {
                  confirmDelivered();
                  vbox.getChildren().remove(tempHBox);
                  waiterData.removeOrder(order);
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            });
        tempHBox.getChildren().add(confirmDeliveredStackPane);
      }
      vbox.getChildren().add(tempHBox);

    }
    return vbox;
  }

  /**
   * Cancel order constructor to give a alert when user press the cancel button.
   *
   * @throws Exception
   */
  @FXML
  public void cancelOrder() throws Exception {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Cancel Order");
    alert.setHeaderText("Cancelling this order will remove it from the database.");
    alert.setContentText("Are you sure you want to cancel this order?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      // TODO remove order from database
      Alert cancelled = new Alert(AlertType.INFORMATION);
      cancelled.setTitle("Cancel Order");
      cancelled.setHeaderText(null);
      cancelled.setContentText("The order has been successfully cancelled.");
      cancelled.showAndWait();
    }
  }
  
  @FXML
  public void confirmDelivered() throws Exception {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirm Delivered");
    alert.setHeaderText("You are confirming that you have delivered the order to the customer.");
    alert.setContentText("Are you sure you want to confirm this order?");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      Alert confirmed = new Alert(AlertType.INFORMATION);
      confirmed.setTitle("Cancel Order");
      confirmed.setHeaderText(null);
      confirmed.setContentText("The order has been successfully confirmed as delivered.");
      confirmed.showAndWait();
    }
  }

  /**
   * confirm the order when the button is pressed. this will show the alert message to ensure the
   * order been confirmed.
   * 
   * @throws Exception
   */
  @FXML
  public void confirmOrder() throws Exception {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Confirm Order");
    alert.setHeaderText("Confirming this order will send order to the kitchen");
    alert.setContentText("order is confirming");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      Alert confirmed = new Alert(AlertType.INFORMATION);
      confirmed.setTitle("Confirm Order");
      confirmed.setHeaderText(null);
      confirmed.setContentText("The order has been confirmed.");
      confirmed.showAndWait();
    }
    alert.close();
  }

  /**
   * set the StackPane and its internal button.
   *
   * @param name of StackPane name.
   * @param font the font
   * @return stackPane initialise value.
   */

  private StackPane initialiseButton(String name, int font, int buttonWidth) {
    StackPane stPane = new StackPane(); // Stack pane to centre button
    stPane.setPrefSize(80, 50);
    Button button = new Button(name); // Button to remove and add food to order list
    button.setPrefSize(buttonWidth, 50);
    button.setFont(new Font(font));
    stPane.getChildren().add(button);

    return stPane;
  }

  /**
   * to set the Gap between label in HBox.
   * 
   * @return the pane initialised size.
   */

  private Pane initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(25, 50);
    return gap;
  }

  /**
   * to initialise the label's value.
   * 
   * @param name to be label name
   * @param width set the width of label
   * @param height set the height of label.
   * @return return the initialiseLabel of specific value.
   */

  private Label initialiseLabel(String name, double width, double height) {
    Label label = new Label(name);
    label.setPrefSize(width, height);
    return label;
  }

  /**
   * method to createMenu using for loop and it's string value.
   * 
   * @param menu map of dishes in the database
   */

  public void createMenu(MenuMap menu) {
    for (String string : menu.keyArray()) {
      menuTabPane.getTabs().add(createMenuTab(string, menu.get(string)));
    }
  }

  /**
   * method to create the list of orders using for loop and its status key.
   * 
   * @param orders map of orders in database
   */
  public void createOrders(OrderMap orders) {
    for (String string : orders.keyArray()) {
      orderTabPane.getTabs().add(createOrderTab(string, orders.get(string)));
    }
  }

  /**
   * Create the tab for the tab specify the constructor.
   * 
   * @param name name of Tab.
   * @param list list of consumable.
   * @return the corresponding tab.
   */

  private Tab createMenuTab(String name, ArrayList<Consumable> list) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setPrefWidth(580);
    anchorPane.getChildren().add(createMenuVBox(list));
    ScrollPane scrollPane = new ScrollPane(anchorPane);
    scrollPane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollPane);
    return tab;
  }

  /**
   * Create the order tab and set the tab.
   * 
   * @param name name of Tab
   * @param list list of consumable.
   * @return the corresponding tab.
   */
  private Tab createOrderTab(String name, ArrayList<Order> list) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setPrefWidth(580);
    anchorPane.getChildren().add(createOrderVBox(list));
    ScrollPane scrollPane = new ScrollPane(anchorPane);
    scrollPane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollPane);
    return tab;
  }

  /**
   * Method to limit characters for TextFields.
   * 
   * @param input the TextField text
   * @param limit the maximum number of characters for the TextFields
   * @return the substring if text is over the limit, if under return the input
   * 
   */
  private String limitChars(String input, int limit) {
    if (input.length() > limit) {
      return input.substring(0, limit);
    }
    return input;
  }

  /**
   * Method to check if TextField is empty.
   * 
   * @param tf the TextField to check if empty
   * 
   */
  private void checkNotEmpty(TextField tf) {
    if (tf.getText() == null || tf.getText().trim().isEmpty()) {
      tf.setBorder(new Border(
          new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));
      emptyTextField = true;
    }
  }

}
