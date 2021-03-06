package views;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import backend.CustomerAccess;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * Controller for the customer view.
 *
 */
public class CustomerViewController {

  /** Object containing methods that interact with database. **/
  CustomerAccess customerData = new CustomerAccess();

  /** The button controller. */
  SceneController controller = SceneController.getInstance();

  /** The menu. */
  MenuMap menu = MenuMap.getInstance();

  private double total = 0.00;

  /**
   * A VBox containing the starters in the menu.
   */
  @FXML
  VBox vboxStarter = new VBox();

  @FXML
  TextArea textArea = new TextArea();

  @FXML
  private TextArea reviewBox;

  @FXML
  private TextField nameBox;

  @FXML
  private TextField ratingBox;

  @FXML
  private AnchorPane revScroll;

  /** The menu tab pane. */
  @FXML
  TabPane menuTabPane = new TabPane();

  /** The ordered list. */
  @FXML
  ListView<String> orderedList = new ListView<>();

  @FXML
  ListView<String> paymentList = new ListView<>();

  @FXML
  ListView<String> cOrderList = new ListView<>();

  @FXML
  Alert addAlert = new Alert(AlertType.INFORMATION);

  @FXML
  private TextField orderID;

  @FXML
  Label statusLabel = new Label();

  @FXML
  Label timeLabel = new Label();

  @FXML
  Button orderInfoButton = new Button();

  @FXML
  private Pane totalPane;

  @FXML
  private Pane ptotalPane;

  @FXML
  private Pane cTotalPane;

  /**
   * Runs this method during scene start up.
   *
   * @throws Exception the exception
   */
  @FXML
  private void initialize() throws Exception {
    controller.setMenuListener(new MenuListener() {
      @Override
      public void onMenuChange() {
        try {
          reload();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }

  /**
   * When the 'Back to main menu' button is pressed, return to the main menu.
   *
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception {
    controller.startMain();
  }

  /**
   * When the reload button is pressed, refreshes the menu with any changes in the database.
   *
   * @throws Exception the exception
   */
  @FXML
  private void reload() throws Exception {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        createMenu(menu);
      }
    });
  }

  /**
   * sends order to server when order is pressed.
   *
   * @throws Exception the exception
   */
  @FXML
  private void sendOrder() throws Exception {
    System.out.println("Order Button Pressed");
    /*
     * needs to check if order is valid.
     */
    ObservableList<String> orders = orderedList.getItems();
    //controller.sendOrder(orders);
    /*
     * Needs a new class and methods to run the following. This is only temporary.
     */
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try (Socket s = new Socket("192.168.1.13", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            DataInputStream dIn = new DataInputStream(s.getInputStream())) {
          dout.writeUTF("CUSTOMER"); // tells server that you're a customer
          dout.flush();
          dout.writeUTF("ORDER " + orders.toString()); // tells server that you're giving a order.
          dout.flush();
          /*
           * If order success message is received.
           */
          if (dIn.readUTF().equals("OK")) {
            orderedList.getItems().clear();
            Alert alert = new Alert(AlertType.NONE, "Order has been placed.", ButtonType.OK);
            alert.show();
            if (alert.getResult() == ButtonType.OK) {
              dout.writeUTF("STOP"); // tells server that you have finished.
              dout.flush();
            }
          }
        } catch (IOException e) {
          Alert alert = new Alert(AlertType.ERROR,
              "Failed to make order, would you like to notify a staff member?", ButtonType.NO,
              ButtonType.YES);
          alert.show();
          if (alert.getResult() == ButtonType.YES) {
            /*
             * Notify staff.
             */
          }
          alert.close();
        }
      }
    });
  }

  /**
   * Submit review.
   *
   * @param event the event
   * @throws IOException
   */
  @FXML
  private void submitReview(ActionEvent event) throws IOException {
    // System.out.println("Thanks");
    // method to submitReview
    String rB = reviewBox.getText(), nB = nameBox.getText(), raB = ratingBox.getText();
    System.out.println(nB + ", " + raB + ", " + rB);
    reviewBox.clear();
    nameBox.clear();
    ratingBox.clear();

    File file = new File("Reviews");
    FileWriter fr = new FileWriter(file, true);
    fr.write("\n" + nB + ">" + raB + ">" + rB);
    fr.close();

    try {
      // Loading the "Thanks!" scene
      FXMLLoader fLoad = new FXMLLoader(getClass().getResource("ThanksReviewView.fxml"));
      Parent root = (Parent) fLoad.load();
      Stage stage = new Stage();
      stage.setTitle("Thanks!");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  @FXML
  private void payment(ActionEvent event) throws Exception {
    System.out.println("Payment Test");

    try {
      // Loading the "Thanks!" scene
      FXMLLoader fLoad = new FXMLLoader(getClass().getResource("PaymentView.fxml"));
      Parent root = (Parent) fLoad.load();
      Stage stage = new Stage();
      stage.setTitle("Payment");
      stage.setScene(new Scene(root));
      stage.show();

    } catch (Exception e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
  }

  @FXML
  private void clearOrder(ActionEvent event) throws Exception {
    System.out.println("Clear Test");

    orderedList.getItems().clear();
    paymentList.getItems().clear();

    this.total = 0.00;
    totalLabel();
  }

  /**
   * Adds items to the VBox, as well as buttons to add/remove the item from an order.
   *
   * @param consumables the consumables
   * @return the v box
   */
  private VBox createVBox(ObservableList<Consumable> consumables) {
    VBox vbox = new VBox();
    for (Consumable consumable : consumables) {
      HBox tempHBox = new HBox(); // Layout for one consumable of the list
      tempHBox.setPrefHeight(50);
      tempHBox.getChildren().add(initialiseGap());
      tempHBox.getChildren().add(initialiseLabel(consumable.getName(), 200, 50));
      tempHBox.getChildren().add(initialiseGap());
      String price = String.format("%.2f", consumable.getPrice()); // Always show 2 decimal Place
      tempHBox.getChildren().add(initialiseLabel(Character.toString((char) 163) + price, 70, 50));
      tempHBox.getChildren().add(initialiseGap());
      StackPane minusStackPane = initialiseButton("-");
      String tAllergens = "";
      int tCalories = consumable.getCalories();

      ((Button) minusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          if (orderedList.getItems().contains(consumable.getName())) {
            orderedList.getItems().remove(consumable.getName());
            paymentList.getItems().remove(consumable.getName());
            cOrderList.getItems().remove(consumable.getName());
            total -= Double.parseDouble(String.format("%.2f", consumable.getPrice()));
            totalLabel();
          } else {
            Alert noItemAlert =
                new Alert(AlertType.CONFIRMATION, "Item does not exist in current order");
            noItemAlert.showAndWait();
          }
        }
      });
      tempHBox.getChildren().add(minusStackPane); // Remove food Button
      StackPane plusStackPane = initialiseButton("+");
      ((Button) plusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          orderedList.getItems().add(consumable.getName());
          paymentList.getItems().add(consumable.getName());
          cOrderList.getItems().add(consumable.getName());
          total += Double.parseDouble(String.format("%.2f", consumable.getPrice()));
          totalLabel();
        }
      });
      tempHBox.getChildren().add(plusStackPane); // Add food Button
      StackPane infoStackPane = initialiseButton("i");
      ((Button) infoStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          // method to bring up allergies and calories info
          // addAlert.setContentText("Allergens: " + tAllergens + " Calories: " + tCalories);
          // addAlert.show();


          addAlert.setContentText("Allergens: " + tAllergens + "    Calories: " + tCalories);
          addAlert.show();
        }
      });
      tempHBox.getChildren().add(infoStackPane); // Add info button
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    scrollReviews();
    totalLabel();
    return vbox;
  }

  /**
   * Creates a new button.
   *
   * @param name the text in the button
   * @return the stack pane
   */
  private StackPane initialiseButton(String name) {
    StackPane sPane = new StackPane(); // Stack pane to centre button
    sPane.setPrefSize(50, 50);
    Button button = new Button(name); // Button to remove and add food to order list
    button.setPrefSize(40, 30);
    sPane.getChildren().add(button);
    return sPane;
  }

  /**
   * Initialises a gap between the other interface.
   *
   * @return the pane
   */

  private Pane initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(25, 50);
    return gap;
  }

  /**
   * Initialises a text label.
   *
   * @param name the text in the label
   * @param width the width of the label
   * @param height the height of the label
   * @return the label
   */

  private Label initialiseLabel(String name, double width, double height) {
    Label label = new Label(name);
    label.setPrefSize(width, height);
    return label;
  }

  /**
   * createMenu method to set menu depend of input value.
   *
   * @param menu list of String
   */

  public void createMenu(MenuMap menu) {
    menuTabPane.getTabs().clear();
    for (String string : menu.keyArray()) {
      menuTabPane.getTabs().add(createTab(string, menu.get(string)));
    }
  }

  /**
   * Creates the tab.
   *
   * @param name the name
   * @param list the list
   * @return the tab
   */
  private Tab createTab(String name, ObservableList<Consumable> list) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setPrefWidth(580);
    anchorPane.getChildren().add(createVBox(list));
    ScrollPane scrollPane = new ScrollPane(anchorPane);
    scrollPane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollPane);
    return tab;
  }

  /**
   * When get information button is pressed, will fill in status and last update time.
   *
   * @param event on being pressed action
   *
   */
  @FXML
  private void getOrderInfo(ActionEvent event) {
    /*
     * String orderNumber = orderID.getText(); String statusAndTime = ""; try { statusAndTime =
     * customerData.getStatusAndTime(orderNumber); String[] split = new String[2]; split =
     * statusAndTime.split(">"); if (split[0] == "" || split[1] == "") { Alert alert = new
     * Alert(AlertType.NONE, "Order does not exist", ButtonType.OK); alert.showAndWait(); } else {
     * statusLabel.setText(split[0]); timeLabel.setText(split[1]); } } catch (Exception e) { Alert
     * alert = new Alert(AlertType.NONE, "Order does not exist", ButtonType.OK);
     * alert.showAndWait(); statusLabel.setText(""); timeLabel.setText(""); orderID.setText(""); }
     */
  }

  /** This method is used to get the status and time for an order.
   * 
   */
  public void getOrderInfo() {

    String orderNumber = orderID.getText();
    String statusAndTime = "";
    try {
      statusAndTime = customerData.getStatusAndTime(orderNumber);
      String[] split = new String[2];
      split = statusAndTime.split(">");
      if (split[0] == "" || split[1] == "") {
        Alert alert = new Alert(AlertType.NONE, "Order does not exist", ButtonType.OK);
        alert.showAndWait();
      } else {
        statusLabel.setText(split[0]);
        timeLabel.setText(split[1]);
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.NONE, "Order does not exist", ButtonType.OK);
      alert.showAndWait();
      statusLabel.setText("");
      timeLabel.setText("");
      orderID.setText("");
    }
  }

  private void scrollReviews() {
    ArrayList<String> myRevs = customerData.getReviews();
    int location = 50;
    for (int i = 0; i < myRevs.size(); i++) {
      this.revScroll.getChildren().add(initialiseLabel(myRevs.get(i), 400, location));
      location += 80;
    }
  }

  private void totalLabel() {
    String sTotal = String.format("%.2f", this.total); // + "0";

    this.totalPane.getChildren().clear();
    this.totalPane.getChildren().add(initialiseLabel(sTotal, 100, 50));

    this.ptotalPane.getChildren().clear();
    this.ptotalPane.getChildren().add(initialiseLabel(sTotal, 100, 100));

    this.cTotalPane.getChildren().clear();
    this.cTotalPane.getChildren().add(initialiseLabel(sTotal, 100, 50));
  }

  /**
   * Method sends whatver is contained in relevent textbox to the database. This will be accessed by
   * the waiter. This method is to be called in the correct action button method when the button is
   * pressed.
   */

  @FXML
  public void contactWaiter() {
    String message = textArea.getText();
    message = "'" + message + "'";
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Notify Waiter");
    alert.setHeaderText(
        "You are confirming that you want to send- " + message + "- to the order to the Waiter.");

    Optional<ButtonType> result = alert.showAndWait();

    if (result.get() == ButtonType.OK) {
      Alert sendMessage = new Alert(AlertType.INFORMATION);
      sendMessage.setTitle("Notify Waiter");
      sendMessage.setHeaderText(null);
      sendMessage.setContentText("The message has been successfully sent.");
      sendMessage.showAndWait();
    }
    // will need to someohow contain order ID in the future so that the waiter can know which table
    // has sent the message.
    customerData.notifyWaiter(message);
    textArea.clear();
  }

  //Test

  @FXML
  private Pane scene1;

  @FXML
  private Pane scene2;

  @FXML
  private void scene1B(ActionEvent event) throws Exception {
    scene2.setVisible(true);
  }

  @FXML
  private void scene2B(ActionEvent event) throws Exception {
    scene2.setVisible(false);
  }

  @FXML
  private Pane paymentPane1;

  @FXML
  private Pane paymentPane2;

  @FXML
  private void cardButton(ActionEvent event) throws Exception {
    paymentPane1.setVisible(false);
  }

  @FXML
  private void back2PO(ActionEvent event) throws Exception {
    paymentPane1.setVisible(true);
  }

  @FXML
  private TextField cNumber, expMonth, expYear, CVV;

  private String cardNumber, expDate, cvvCode;

  @FXML
  private void confirmPurchase(ActionEvent event) throws Exception {
    this.cardNumber = cNumber.getText();
    this.expDate = expMonth.getText() + expYear.getText();
    this.cvvCode = CVV.getText();

    cNumber.clear();
    expMonth.clear();
    expYear.clear();
    CVV.clear();

    paymentPane1.setVisible(true);
  }


}
