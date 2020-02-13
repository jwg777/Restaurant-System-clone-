package views;
/** This is the controller for the waiter view.
 * 
 * @author : TeamProject2020 group 22
 * 
 */

import java.util.ArrayList;
import backend.WaiterAccess;
import consumable.Consumable;
import consumable.MenuMap;
import database_cafe.DataInteract;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
import javafx.stage.Stage;

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

  /** Method to call the Controller to set up the stage.
   * 
   * @param event for when a button is clicked
   * @throws Exception thrown if an error occurs with javafx
   * 
   */
  @FXML
  private void addDeletePush(ActionEvent event) throws Exception {
    AddDeleteViewController addDel = new AddDeleteViewController();
  }

  /** A VBox containing the starters in the menu **/

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
  TabPane orderTabPane = new TabPane();

  @FXML
  HBox orderConfirm = new HBox();

  @FXML
  Alert deleteAlert = new Alert(AlertType.NONE);

  @FXML
  Alert addAlert = new Alert(AlertType.NONE);

  @FXML
  ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

  @FXML
  ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);

  MenuMap tempMap = MenuMap.getInstace();

  WaiterAccess waiterData = new WaiterAccess();

  boolean emptyTextField;

  /** Method for when the delete button is pushed.
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

  /** Method for when add button is pushed.
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

  
  
  @FXML
  private void reloadPush() throws Exception {
    tempMap.clear();
    waiterData.getMenu();
    System.out.println("check for the reload button");
    tempMap.put("WAITING ORDERS", new Consumable("Special test 1", 10f));
    tempMap.put("PROCESSING ORDERS", new Consumable("Starter test 1", 10f));
    tempMap.put("READY ORDERS", new Consumable("Main test 1", 10f));
    System.out.println("test reload button!");
    orderTabPane.getTabs().clear();
    createMenu(tempMap);

    
  }

  private VBox createVBox(ArrayList<Consumable> consumables) {
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
      StackPane confirmStackPane = initialiseButton("Confirm");
      ((Button) confirmStackPane.getChildren().get(0)).setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          vbox.getChildren().remove(tempHBox);
          // tempHBox.getChildren().remove();
        }
      });
      tempHBox.getChildren().add(confirmStackPane); // Remove food Button

      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
  }

  private StackPane initialiseButton(String name) {
    StackPane sPane = new StackPane(); // Stack pane to centre button
    sPane.setPrefSize(100, 50);
    Button button = new Button(name); // Button to remove and add food to order list
    button.setPrefSize(50, 50);
    sPane.getChildren().add(button);
    return sPane;
  }

  private Pane initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(25, 50);
    return gap;
  }

  private Label initialiseLabel(String name, double width, double height) {
    Label label = new Label(name);
    label.setPrefSize(width, height);
    return label;
  }

  public void createMenu(MenuMap menu) {
    for (String string : menu.keyArray()) {
      orderTabPane.getTabs().add(createTab(string, menu.get(string)));
    }
  }

  private Tab createTab(String name, ArrayList<Consumable> list) {
    AnchorPane anchorPane = new AnchorPane();
    anchorPane.setPrefWidth(580);
    anchorPane.getChildren().add(createVBox(list));
    ScrollPane scrollPane = new ScrollPane(anchorPane);
    scrollPane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollPane);
    return tab;
  }

  /** Method to limit characters for TextFields.
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

  /** Method to check if TextField is empty.
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
