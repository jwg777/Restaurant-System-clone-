package views;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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

  /**
   * A VBox containing the starters in the menu.
   */
  @FXML
  VBox vboxStarter = new VBox();

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
  Alert addAlert = new Alert(AlertType.INFORMATION);

  /**
   * Runs this method during scene start up.
   * 
   * @throws Exception the exception
   */
  @FXML
  private void initialize() throws Exception {
    reloadPush();
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
   * @throws Exception the exception
   */
  @FXML
  private void sendOrder() throws Exception {
    /*
     * needs to check if order is valid.
     */
    ObservableList<String> orders = orderedList.getItems();
    /*
     * Needs a new class and methods to run the following. This is only temporary.
     */
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        try (Socket s = new Socket("192.168.1.13", 6666);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream())) {
          dout.writeUTF("CUSTOMER"); // tells server that you're a customer
          dout.flush();
          dout.writeUTF("ORDER " + orders.toString()); // tells server that you're giving a order.
          dout.flush();
          /*
           * If order success message is received.
           */
          if (true) {
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
  void submitReview(ActionEvent event) throws IOException {
    // System.out.println("Thanks");
    // method to submitReview
    String rB = reviewBox.getText(), nB = nameBox.getText(), raB = ratingBox.getText();
    System.out.println(nB +", "+ raB +", "+ rB);
    reviewBox.clear();
    nameBox.clear();
    ratingBox.clear();
    
    File file = new File("Reviews");
    FileWriter fr = new FileWriter(file, true);
    fr.write("\n"+ nB +">"+ raB +">"+ rB);
    fr.close();
    
    try {
      //Loading the "Thanks!" scene
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
      tempHBox.getChildren().add(initialiseLabel("£ " + price, 70, 50));
      tempHBox.getChildren().add(initialiseGap());
      StackPane minusStackPane = initialiseButton("-");
      String tAllergens = consumable.getAllergen();
      int tCalories = consumable.getCalories();

      ((Button) minusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          orderedList.getItems().remove(consumable.getName());
        }
      });
      tempHBox.getChildren().add(minusStackPane); // Remove food Button
      StackPane plusStackPane = initialiseButton("+");
      ((Button) plusStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          orderedList.getItems().add(consumable.getName());
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
  
  public void scrollReviews() {
    
    Database db1 = new Database();
       
    this.revScroll.getChildren().add(initialiseLabel(db1.getReviews(), 400, 50));
  }


}
