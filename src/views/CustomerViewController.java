package views;

import java.io.BufferedWriter;
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
import javax.xml.ws.Response;
import backend.CustomerAccess;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.Database;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  SceneController butController = SceneController.getInstance();

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
  private TextField tableNumber;

  
  @FXML
  private TextField ratingBox;

  @FXML
  private AnchorPane revScroll;

  /** The menu tab pane. */
  @FXML
  TabPane menuTabPane = new TabPane();

  @FXML
  TabPane mainTabPane = new TabPane();

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
  
  @FXML
  private Button starb1;

  @FXML
  private Button starb2;

  @FXML
  private Button starb3;

  @FXML
  private Button starb4;

  @FXML
  private Button starb5;

  @FXML
  private ImageView yellow1;

  @FXML
  private ImageView yellow2;

  @FXML
  private ImageView yellow3;

  @FXML
  private ImageView yellow4;

  @FXML
  private ImageView yellow5;

  /**
   * Runs this method during scene start up.
   * 
   * @throws Exception the exception
   */
  @FXML
  private void initialize() throws Exception {
    //mainTabPane.getStylesheets().add(getClass().getResource("Tabs.css").toExternalForm());
    //ratingBox.getStylesheets().add(getClass().getResource("textFields.css").toExternalForm());
    orderID.getStylesheets().add(getClass().getResource("textFields.css").toExternalForm());
    statusLabel.getStylesheets().add(getClass().getResource("label.css").toExternalForm());
    timeLabel.getStylesheets().add(getClass().getResource("label.css").toExternalForm());
    orderInfoButton.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
    reloadPush();
    stars();
  }

  /**
   * When the 'Back to main menu' button is pressed, return to the main menu.
   * 
   * @throws Exception the exception
   */
  @FXML
  private void returnPush() throws Exception {
    butController.startMain();
  }

  /**
   * When the reload button is pressed, refreshes the menu with any changes in the database.
   * 
   * @throws Exception the exception
   */
  @FXML
  private void reloadPush() throws Exception {
    menu.clear();
    customerData.getMenu();
    menuTabPane.getTabs().clear();
    createMenu(menu);
  }

  /**
   * sends order to server when order is pressed.
   * 
   * @throws Exception
   */
  @FXML
  private void sendOrder() throws Exception {
    int tablenum = Integer.parseInt(tableNumber.getText());
    tableNumber.clear();
    
    float price = (float)this.total;
    total = 0;
    customerData.placeOrder(orderedList, tablenum, price);
    orderedList.getItems().clear();
    
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
    String rB = reviewBox.getText(), nB = nameBox.getText(), raB = "5";
    System.out.println(nB + ", " + raB + ", " + rB);
    reviewBox.clear();
    nameBox.clear();

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
  private VBox createVBox(ArrayList<Consumable> consumables) {
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
      String tAllergens = consumable.getAllergen();
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
    button.getStylesheets().add(getClass().getResource("button.css").toExternalForm());
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
    label.getStylesheets().add(getClass().getResource("label.css").toExternalForm());
    return label;
  }

  /**
   * createMenu method to set menu depend of input value.
   * 
   * @param menu list of String
   */

  public void createMenu(MenuMap menu) {
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
  private Tab createTab(String name, ArrayList<Consumable> list) {
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
    String orderNumber = orderID.getText();
    String status = "";
    try {
      status = customerData.getStatusAndTime(orderNumber);
      
      if (status.equals("")) {
        Alert alert = new Alert(AlertType.NONE, "Order does not exist", ButtonType.OK);
        DialogPane dialog = alert.getDialogPane();
        dialog.getStylesheets().add(getClass().getResource("Alerts.css").toExternalForm());
        alert.showAndWait();
      } else {
        statusLabel.setText(status);
      }
    } catch (Exception e) {
      Alert alert = new Alert(AlertType.NONE, "Order does not exist", ButtonType.OK);
      DialogPane dialog = alert.getDialogPane();
      dialog.getStylesheets().add(getClass().getResource("Alerts.css").toExternalForm());
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

  // Test

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
    int validInput = 0; // Counts how many inputs are valid card details.
    if (cNumber.getLength() != 16) {
      cNumber.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
      throw new IllegalDetails("Card number is invalid");
    } else {
      cNumber.setStyle("-fx-border-width: 0 0 0 0;");
      validInput++;
    }
    if (expMonth.getLength() != 2 && expYear.getLength() != 2) {
      expMonth.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
      expYear.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
      throw new IllegalDetails("Expiry date is invalid");
    } else {
      expMonth.setStyle("-fx-border-width: 0 0 0 0;");
      expYear.setStyle("-fx-border-width: 0 0 0 0;");
      validInput++;
    }
    if (CVV.getLength() != 3) {
      CVV.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
      throw new IllegalDetails("Security code is invalid");
    } else {
      CVV.setStyle("-fx-border-width: 0 0 0 0;");
      validInput++;
    }

    if (validInput == 3) {
      //isPaid = true;
      // Set order to paid in the database.
    }

    cNumber.clear();
    expMonth.clear();
    expYear.clear();
    CVV.clear();

    paymentPane1.setVisible(true);
  }
  
  private void stars() {

    starb1.setOnMouseEntered(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(true);
      }
    });

    starb1.setOnMouseExited(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(false);
      }
    });

    starb2.setOnMouseEntered(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(true);
        yellow2.setVisible(true);
      }
    });

    starb2.setOnMouseExited(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(false);
        yellow2.setVisible(false);
      }
    });

    starb3.setOnMouseEntered(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
      }
    });

    starb3.setOnMouseExited(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(false);
        yellow2.setVisible(false);
        yellow3.setVisible(false);
      }
    });

    starb4.setOnMouseEntered(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
        yellow4.setVisible(true);
      }
    });

    starb4.setOnMouseExited(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(false);
        yellow2.setVisible(false);
        yellow3.setVisible(false);
        yellow4.setVisible(false);
      }
    });

    starb5.setOnMouseEntered(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
        yellow4.setVisible(true);
        yellow5.setVisible(true);
      }
    });

    starb5.setOnMouseExited(new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        yellow1.setVisible(false);
        yellow2.setVisible(false);
        yellow3.setVisible(false);
        yellow4.setVisible(false);
        yellow5.setVisible(false);
      }
    });
  }
  
  /*private String rating;
  
  private String getRating() {
    starb1.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        rating = "1";
        yellow1.setVisible(true);
      }
    });
    
    starb2.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        rating = "2";
        yellow1.setVisible(true);
        yellow2.setVisible(true);
      }
    });
    
    starb3.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        rating = "3";
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
      }
    });
    
    starb4.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        rating = "4";
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
        yellow4.setVisible(true);
      }
    });
    
    starb5.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        rating = "5";
        yellow1.setVisible(true);
        yellow2.setVisible(true);
        yellow3.setVisible(true);
        yellow4.setVisible(true);
        yellow5.setVisible(true);
      }
    });
    
    return rating; 
  }
  
  public String call() {
    return "hello";
  }
  */
  
  @FXML
  private void cashB(ActionEvent event) throws IOException {
    Alert alert = new Alert(AlertType.CONFIRMATION);
    alert.setTitle("Cash Payment");
    alert.setHeaderText("Someone is coming to take your cash");
    alert.showAndWait();
  }
}
