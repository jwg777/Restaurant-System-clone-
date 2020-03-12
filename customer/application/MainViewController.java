package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import consumable.Consumable;
import consumable.MenuMap;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import order.Order;
import order.OrderList;

public class MainViewController {


  @FXML
  private HBox categoryHBox;

  @FXML
  private TextField tableField;

  @FXML
  private AnchorPane ordersPane;

  @FXML
  private AnchorPane menuAnchor;

  @FXML
  private StackPane confirmationPane;

  @FXML
  private Label totalPrice;

  @FXML
  private AnchorPane menuPane;

  @FXML
  private AnchorPane reviewPane;

  @FXML
  private AnchorPane statusPane;

  @FXML
  private Button confirmButton;

  @FXML
  private VBox ordersList;

  private Node frontPane;

  MenuMap menu = MenuMap.getInstance();

  HashMap<String, Button> buttons = new HashMap<>();

  ArrayList<Integer> ordersIndex = new ArrayList<>();

  OrderList orders = OrderList.getInstance();

  @FXML
  private void initialize() throws IOException {
    menuPane.toFront();
    frontPane = menuPane;
    confirmationPane.toFront();
    menu.getMenu().addListener(
        (MapChangeListener<String, ObservableList<Consumable>>) change -> addCategory(change));
    orders.getOrderList().addListener((ListChangeListener<Order>) c -> {
      while (c.next()) {
        if (c.wasAdded()) {
          for (Order order : c.getAddedSubList()) {
            OrderCell oCell = new OrderCell();
            oCell.setData(order);
            ordersList.getChildren().add(oCell.getCell());
            ordersIndex.add(order.getDishID());
          }
        } else if (c.wasRemoved()) {
          for (Order order : c.getRemoved()) {
            for (int i = 0; i < ordersIndex.size(); i++) {
              if (order.getDishID() == ordersIndex.get(i)) {
                ordersList.getChildren().remove(i);
                ordersIndex.remove(i);
              }
            }
          }
        }
      }
    });

  }

  private void addCategory(
      MapChangeListener.Change<? extends String, ? extends ObservableList<Consumable>> change) {
    Platform.runLater(() -> {
      if (change.wasAdded()) {
        String category = change.getKey();
        Button button = new Button(category);
        CategoryCell categoryCell = new CategoryCell();
        button.setOnAction((EventHandler<ActionEvent>) event -> {
          fade(categoryCell.getScrollPane());
        });
        menuAnchor.getChildren().add(categoryCell.getScrollPane());
        button.getStylesheets().add(getClass().getResource("menuButtons.css").toExternalForm());
        categoryHBox.getChildren().add(button);
        buttons.put(category, button);
        addCellToCategory(change.getValueAdded().get(0), categoryCell);
        menu.get(category)
            .addListener((ListChangeListener<Consumable>) c -> addConsumable(c, categoryCell));
      } else if (change.wasRemoved()) {
        categoryHBox.getChildren().remove(buttons.get(change.getKey()));
      }
    });
  }

  private void addConsumable(Change<? extends Consumable> c, CategoryCell categoryCell) {
    Platform.runLater(() -> {
      while (c.next()) {
        if (c.wasReplaced() || c.wasRemoved()) {
          categoryCell.getTilePane().getChildren().clear();
          for (Consumable consumable : c.getList()) {
            addCellToCategory(consumable, categoryCell);
          }
        } else if (c.wasAdded()) {
          for (Consumable consumable : c.getAddedSubList()) {
            addCellToCategory(consumable, categoryCell);
          }
        }
      }
    });
  }

  private void addCellToCategory(Consumable consumable, CategoryCell categoryCell) {
    ConsumableCell cCell = new ConsumableCell(consumable);
    categoryCell.getTilePane().getChildren().add(cCell.getCell());
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
  private void menuPressed() {
    fade(menuPane);
  }

  @FXML
  private void ordersPressed() {
    fade(ordersPane);
  }

  @FXML
  private void statusPressed() {
    fade(statusPane);
  }

  @FXML
  private void reviewPressed() {
    fade(reviewPane);
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
    menu.put(new Consumable(i1 + 100, type, name, 10.10f, 100, true, "Ingredient1, " + i1));
  }

  @FXML
  private void button2() {
    String type = "Category2";
    String name = "Consumable " + (i2++);
    menu.put(new Consumable(i1 + 200, type, name, 10.10f, 100, true, "Ingredient1, " + i2));
  }

  @FXML
  private void button3() {
    String type = "Category3";
    String name = "Consumable " + (i3++);
    menu.put(new Consumable(i1 + 300, type, name, 10.10f, 100, true, "Ingredient1, " + i3));
  }

}
