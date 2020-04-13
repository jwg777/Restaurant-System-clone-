import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import application.IllegalDetails;
import connection.ServerAccess;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.animation.FadeTransition;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
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
import server.Server;

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

  @FXML
  private AnchorPane editMenuPane;

  @FXML
  private AnchorPane editInfoPane;

  @FXML
  private HBox editOptionsHBox;

  @FXML
  private Button addItem = new Button("Add Item");

  @FXML
  private Button editItem = new Button("Edit Item");

  @FXML
  private Button deleteItem = new Button("Delete item");

  @FXML
  ComboBox<ObservableList<Consumable>> menuItems = new ComboBox<ObservableList<Consumable>>();

  @FXML
  private Button applyButton = new Button("Apply");

  @FXML
  Label dishNameLabel = new Label("Dish Name : ");

  @FXML
  TextField dishNameTF = new TextField("");

  @FXML
  Label dishPriceLabel = new Label("Price : " + (char) 163);

  @FXML
  TextField dishPriceTF = new TextField("");

  @FXML
  Label dishCategoryLabel = new Label("Category : ");

  @FXML
  TextField dishCategoryTF = new TextField("");

  @FXML
  Label dishCaloriesLabel = new Label("Calories : ");

  @FXML
  TextField dishCaloriesTF = new TextField("");

  @FXML
  Label dishIngredientsLabel = new Label("Ingredients : ");

  @FXML
  TextField dishIngredientsTF = new TextField("");

  private Node frontPane;

  MenuMap menu = MenuMap.getInstance();

  HashMap<String, Button> buttons = new HashMap<>();

  OrderMap order = OrderMap.getInstance();

  // ServerAccess connection = ServerAccess.getInstance();

  @FXML
  private void initialize() throws IOException {
    editMenu();
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
  private void editMenuPressed() {
    fade(editMenuPane);
    editInfoPane.getChildren().clear();
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

  private void editMenu() {
    addItem.getStylesheets().add(getClass().getResource("editMenuOptions.css").toExternalForm());
    editItem.getStylesheets().add(getClass().getResource("editMenuOptions.css").toExternalForm());
    editItem.setLayoutX(150);
    addItem.setOnAction(actionAddItem);
    editItem.setOnAction(actionEditItem);
    editOptionsHBox.getChildren().add(addItem);
    editOptionsHBox.getChildren().add(editItem);
  }

  EventHandler<ActionEvent> actionAddItem = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      editInfoPane.getChildren().clear();
      fillEditInfoPane();
    }
  };

  EventHandler<ActionEvent> actionEditItem = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      editInfoPane.getChildren().clear();
      deleteItem.getStylesheets()
          .add(getClass().getResource("editMenuOptions.css").toExternalForm());
      deleteItem.setLayoutX(400);
      deleteItem.setOnAction(actionDeleteItem);
      MenuMap menu = MenuMap.getInstance();
      for (String key : menu.keyArray()) {
        menuItems.getItems().addAll(menu.get(key));
      }
      menuItems.setLayoutY(5);
      menuItems.setLayoutX(80);
      menuItems.getStylesheets()
          .add(getClass().getResource("menuItemsComboBox.css").toExternalForm());
      fillEditInfoPane();
      if (!menuItems.getSelectionModel().isEmpty()) {
        dishNameTF.setText(menuItems.getValue().get(1).toString());
        dishPriceTF.setText(menuItems.getValue().get(2).toString());
        dishCategoryTF.setText(menuItems.getValue().get(3).toString());
        dishCaloriesTF.setText(menuItems.getValue().get(4).toString());
        dishIngredientsTF.setText(menuItems.getValue().get(5).toString());
      }
      editInfoPane.getChildren().add(menuItems);
      editInfoPane.getChildren().add(deleteItem);
    }
  };

  EventHandler<ActionEvent> actionDeleteItem = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      // Delete chosen item in comboBox.
    }
  };

  private void fillEditInfoPane() {
    applyButton.getStylesheets()
        .add(getClass().getResource("editMenuOptions.css").toExternalForm());
    applyButton.setLayoutX(200);
    applyButton.setLayoutY(400);
    applyButton.setOnAction(applyItem);
    dishNameLabel.setLayoutY(75);
    dishNameTF.setLayoutX(120);
    dishNameTF.setLayoutY(75);
    dishNameLabel.getStylesheets().add(getClass().getResource("dishLabels.css").toExternalForm());
    dishNameTF.getStylesheets().add(getClass().getResource("dishTextFields.css").toExternalForm());
    dishPriceLabel.setLayoutY(125);
    dishPriceTF.setLayoutX(120);
    dishPriceTF.setLayoutY(125);
    dishPriceLabel.getStylesheets().add(getClass().getResource("dishLabels.css").toExternalForm());
    dishPriceTF.getStylesheets().add(getClass().getResource("dishTextFields.css").toExternalForm());
    dishCategoryLabel.setLayoutY(175);
    dishCategoryTF.setLayoutX(120);
    dishCategoryTF.setLayoutY(175);
    dishCategoryLabel.getStylesheets()
        .add(getClass().getResource("dishLabels.css").toExternalForm());
    dishCategoryTF.getStylesheets()
        .add(getClass().getResource("dishTextFields.css").toExternalForm());
    dishCaloriesLabel.setLayoutY(225);
    dishCaloriesTF.setLayoutX(120);
    dishCaloriesTF.setLayoutY(225);
    dishCaloriesLabel.getStylesheets()
        .add(getClass().getResource("dishLabels.css").toExternalForm());
    dishCaloriesTF.getStylesheets()
        .add(getClass().getResource("dishTextFields.css").toExternalForm());
    dishIngredientsLabel.setLayoutY(275);
    dishIngredientsTF.setLayoutX(120);
    dishIngredientsTF.setLayoutY(275);
    dishIngredientsLabel.getStylesheets()
        .add(getClass().getResource("dishLabels.css").toExternalForm());
    dishIngredientsTF.getStylesheets()
        .add(getClass().getResource("dishTextFields.css").toExternalForm());
    dishIngredientsTF.setPrefWidth(600);
    editInfoPane.getChildren().add(applyButton);
    editInfoPane.getChildren().add(dishNameLabel);
    editInfoPane.getChildren().add(dishNameTF);
    editInfoPane.getChildren().add(dishPriceLabel);
    editInfoPane.getChildren().add(dishPriceTF);
    editInfoPane.getChildren().add(dishCategoryLabel);
    editInfoPane.getChildren().add(dishCategoryTF);
    editInfoPane.getChildren().add(dishCaloriesLabel);
    editInfoPane.getChildren().add(dishCaloriesTF);
    editInfoPane.getChildren().add(dishIngredientsLabel);
    editInfoPane.getChildren().add(dishIngredientsTF);
  }
  
  EventHandler<ActionEvent> applyItem = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      int validInput = 0;
      if (dishNameTF.getText().equals("")) {
        dishNameTF.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Dish name is invalid");
      } else {
        dishNameTF.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }
      if (dishPriceTF.getText().equals("")) {
        dishPriceTF.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Dish price is invalid");
      } else {
        dishPriceTF.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }
      if (dishCategoryTF.getText().equals("")) {
        dishCategoryTF.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Dish category is invalid");
      } else {
        dishCategoryTF.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }
      if (dishCaloriesTF.getText().equals("")) {
        dishCaloriesTF.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Dish calories are invalid");
      } else {
        dishCaloriesTF.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }
      if (dishIngredientsTF.getText().equals("")) {
        dishIngredientsTF.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Dish ingredients are invalid");
      } else {
        dishIngredientsTF.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }
      if (validInput == 5) {
        // Add edited/added item to menu.
      }
    }
  };

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


