package views;

import java.util.ArrayList;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
  private void returnPush() throws Exception {
    butController.startMain();
  }

  /**
   * Initialise the reload push button.
   * 
   * @throws Exception when it's not recognise the reload push button.
   */
  @FXML
  public void initialize() throws Exception {
    reloadPush();
  }

  /**
   * Declare the main tab for the orders to orderTabPane.
   */

  @FXML
  TabPane orderTabPane = new TabPane();
  /**
   * Declare the HBox inside the VBox to be order Confirm.
   */
  @FXML
  HBox orderConfirm = new HBox();
  /**
   * object MenuMap declared.
   */
  MenuMap tempMap = MenuMap.getInstace();

  /**
   * reloadPush() methods to input the value when the reload button is pressed. this will create the
   * menu to test the functions.
   * 
   * @throws Exception if the error occurs.
   */
  @FXML
  private void reloadPush() throws Exception {
    tempMap.put("WAITING ORDERS", new Consumable("ReadyOrderTest", 10f));
    tempMap.put("PROCESSING ORDERS", new Consumable("Order_CancelTest", 10f));
    tempMap.put("READY ORDERS", new Consumable("Order_Confrim", 10f));
    orderTabPane.getTabs().clear();
    createMenu(tempMap);
  }

  /**
   * Set the VBox and its children to be initialise and set the function to confirm order. the
   * function is to give a confrim notification to the waiter.
   * 
   * @param consumables of consumable.
   * @return VBox of what has been set.
   */

  private VBox createVBox(ArrayList<Consumable> consumables) {
    VBox vbox = new VBox();
    for (Consumable consumable : consumables) {
      HBox tempHBox = new HBox(); // Layout for one consumable of the list
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
      tempHBox.getChildren().add(initialiseLabel("£ " + price, 150, 50));
      tempHBox.getChildren().add(initialiseGap());
      StackPane confirmStackPane = initialiseButton("Confirm");
      ((Button) confirmStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          Alert alert =
              new Alert(AlertType.CONFIRMATION, "order has been confirmed", ButtonType.OK);
          alert.setTitle("Notification");
          alert.show();
          if (alert.getResult() == ButtonType.OK) {
            alert.close();
          }
          vbox.getChildren().remove(tempHBox);
        }
      });
      tempHBox.getChildren().add(confirmStackPane); // Remove food Button
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
  }

  /**
   * set the StackPane and its internal button.
   * 
   * @param name of StackPane name.
   * @return stackPane initialise value.
   */

  private StackPane initialiseButton(String name) {
    StackPane stPane = new StackPane(); // Stack pane to centre button
    stPane.setPrefSize(100, 50);
    Button button = new Button(name); // Button to remove and add food to order list
    button.setPrefSize(50, 50);
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
   * @param menu param of menu created.
   */

  public void createMenu(MenuMap menu) {
    for (String string : menu.keyArray()) {
      orderTabPane.getTabs().add(createTab(string, menu.get(string)));
    }
  }

  /**
   * Create the tab for the tab specify the constructor.
   * 
   * @param name name of Tab.
   * @param list list of consumable.
   * @return the corresponding tab.
   */

  private Tab createTab(String name, ArrayList<Consumable> list) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setPrefWidth(580);
    anchorPane.getChildren().add(createVBox(list));
    ScrollPane scrollPane = new ScrollPane(anchorPane);
    scrollPane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollPane);
    return tab;
  }

}
