package views;

import java.sql.SQLException;
import java.util.ArrayList;
import backend.KitchenAccess;
import backend.WaiterAccess;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import order.Order;
import order.OrderMap;

/**
 * The Class KitchenViewController.
 */
public class KitchenViewController {
  /**
   * Initialise the data of KicthenAccess.
   */
  KitchenAccess kitchenData = new KitchenAccess();
  /** The menu tab pane. */
  @FXML
  private TabPane kitchenOrders = new TabPane();

  /** The vbox new. */
  @FXML
  private VBox vboxNew = new VBox();
 
  /** Text area for notifications to the waiter **/ 
  @FXML
  TextArea notifyWaiter = new TextArea();
  
  /** The vbox in progress. */
  @FXML
  private VBox vboxInProgress = new VBox();

  /** The vbox completed. */
  @FXML
  private VBox vboxCompleted = new VBox();

  /** The listView to display waiter messages on the gui **/
  @FXML
  private ListView<String> messages = new ListView<>();
  
  /** The ordered list. */
  @FXML
  private ListView<?> orderedList;


  /** The button controller. */
  SceneController butController = SceneController.getInstance();

  /**
   * Initialise the OrderMap to get the order data.
   */
  OrderMap order = OrderMap.getInstance();
  /**
   * Initialise the NewOrderTab in the TabPane.
   */
  @FXML
  TabPane OrderTabPane = new TabPane();
  


  /**
   * Initialise the reload button to be pushed.
   * 
   * @throws Exception
   */
  @FXML
  private void initialize() throws Exception {
    newOrderReload();
  }

  /**
   * Reload button to be pushed for the newOrderTab.
   * 
   * @throws Exception
   */
  @FXML
  private void newOrderReload() throws Exception {
    order.clear();
    kitchenData.getOrders();
    kitchenData.getMenu();
    OrderTabPane.getTabs().clear();
    createOrders(order);
  }


  /**
   * When the 'Return to Main Menu button is pressed, return to the main menu.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception {
    butController.startMain();
  }

  /**
   * the createNewOrderVBox method to create the VBox for the New order tab.
   * 
   * @param list return consumable menu value.
   * @return the corresponding VBox value.
   * @throws SQLException thrown if SQL error occurs
   */
  private VBox createNewOrderVBox(ArrayList<Order> list) {
    VBox vbox = new VBox();
    for (Order order : list) {
      HBox tempHBox = new HBox(); // Layout for one consumable of the list
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel("#" + order.getOrderID(), 100, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", order.getTotalPrice()); // Always show 2 decimal Place
      tempHBox.getChildren().add(initialiseLabel(Character.toString((char) 163) + price, 100, 50));
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel(order.getTimeStamp(), 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      if (order.getStatus().equals("processing")) {
        tempHBox.getChildren().add(initialiseCheckButton("started", 16, -1));
      } else if (order.getStatus().equals("started")) {
        tempHBox.getChildren().add(initialiseCheckButton("completed", 16, -1));
      } else if (order.getStatus().equals("ready")) {
        tempHBox.getChildren().add(initialiseCheckButton("paid", 16, order.getCustID()));
      }
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
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

  /**
   * To initialiseGap for the kitchenView.java.
   * 
   * @return the Gap of the kitchen view.
   */
  private Node initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(25, 50);
    return gap;
  }

  /**
   * Initialise the CheckBox with it's name and font.
   * 
   * @param name return the string name
   * @param font return the size of the string font.
   * @return the button.
   * @throws SQLException thrown if SQL error occurs
   */
  private CheckBox initialiseCheckButton(String name, int font, int custID) {
    CheckBox check = new CheckBox(name); // Button to remove and add food to order list
    check.setPrefSize(150, 50);
    check.setFont(new Font(font));
    try {
      if (custID != -1 && kitchenData.getIfPaid(custID)) {
        check.fire();
      }
    } catch (SQLException e) {
      e.printStackTrace(); 
    }
    return check;
  }

  /**
   * Constructor to create the new order tab.
   * 
   * @param name the name of the tab.
   * @param list of the consumable.
   * @return the corresponding tab been created.
   */
  private Tab createNewOrderTab(String name, ArrayList<Order> list) {
    AnchorPane anchorpane = new AnchorPane();
    anchorpane.setPrefWidth(580);
    anchorpane.getChildren().add(createNewOrderVBox(list));
    ScrollPane scrollpane = new ScrollPane(anchorpane);
    scrollpane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollpane);
    return tab;
  }

  /**
   * Create the list of order for createNewOrderTab.
   * 
   * @param orders from orderMap.
   */
  public void createOrders(OrderMap orders) {
    for (String string : orders.keyArray()) {
      OrderTabPane.getTabs().add(createNewOrderTab(string, orders.get(string)));
    }
  }
  
  @FXML  
  public void sendMessage() {
    String word = notifyWaiter.getText();
    notifyWaiter.clear();
    System.out.println(word);
    
    Alert send = new Alert(AlertType.INFORMATION);
    send.setTitle("Notify the Waiter");
    send.setHeaderText(null);
    send.setContentText("Message has been sent.");
    send.showAndWait();
    //kitchenData.sendMessageWaiter(word);
  }
  
  @FXML  
  public void getMessages() {
    System.out.println("TEST");
    messages.getItems().clear();
    messages.getItems().add("Order 23 has been changed");
    messages.getItems().add("Table 21 wants extra suace");
    messages.getItems().add("Order 2 wants to thank the kitchen for excellent food");
    
    //ResultSet rs  = kitehcnData.returnWaiterMessages():
    /**
     while (rs.next()) {
       messages.getItems().add(rs.getString("message");
     }
    **/
    
  }
}
