package application;

import connection.ServerAccess;
import consumable.Consumable;
import consumable.MenuMap;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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
  private HBox paymentTypeHBox;

  @FXML
  private TextField tableField;

  @FXML
  private AnchorPane ordersPane;

  @FXML
  private AnchorPane paymentPane;

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
  private AnchorPane trackPane;

  @FXML
  private Button confirmButton;

  @FXML
  private VBox ordersList;

  @FXML
  private AnchorPane payingPane;

  @FXML
  private Button cardButton = new Button("Card / Contactless");

  @FXML
  private Button cashButton = new Button("Cash");

  @FXML
  private Button oxacaAccButton = new Button("Oxaca account");

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

  @FXML
  TextField cardNumber = new TextField("");

  @FXML
  TextField expMonth = new TextField("");

  @FXML
  TextField expYear = new TextField("");

  @FXML
  TextField threeDigits = new TextField("");

  @FXML
  Button refreshStatus = new Button("Refresh");

  @FXML
  Label lastUpdatedLabel = new Label("Last Updated : ");

  @FXML
  Label updateLabel = new Label("Current status : ");

  private Node frontPane;

  MenuMap menu = MenuMap.getInstance();

  HashMap<String, Button> buttons = new HashMap<>();

  ArrayList<Integer> ordersIndex = new ArrayList<>();

  OrderList orders = OrderList.getInstance();

  boolean isPaid = false;

  boolean validCard = false;

  ServerAccess connection = ServerAccess.getInstance();



  @FXML
  private void initialize() throws IOException {
    stars();
    paymentTab();
    statusTab();
    menuPane.toFront();
    frontPane = menuPane;
    confirmationPane.toFront();
    // Listener for price change
    orders.addPriceListener(() -> {
      Platform.runLater(() -> {
        totalPrice.setText(String.format("%.2f", orders.getTotalPrice()));
      });
    });
    // Listener for menu change
    menu.getMenu().addListener(
        (MapChangeListener<String, ObservableList<Consumable>>) change -> addCategory(change));
    // Listener for order change
    orders.getOrderList().addListener((ListChangeListener<Order>) c -> {
      Platform.runLater(() -> {
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
    });
    //connection.getMenu();
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
    ordersPane.setVisible(true);
  }

  @FXML
  private void paymentPressed() {
    fade(paymentPane);
    payingPane.getChildren().clear();
    if (isPaid) {
      Label paidMessage = new Label("Order has been paid for");
      paidMessage.getStylesheets().add(getClass().getResource("priceLabel.css").toExternalForm());
      paidMessage.setMaxWidth(Double.MAX_VALUE);
      paidMessage.setAlignment(Pos.CENTER);
      payingPane.getChildren().add(paidMessage);
      paymentTypeHBox.getChildren().clear();
    }
  }

  @FXML
  private void statusPressed() {
    fade(statusPane);
    statusPane.setVisible(true);
  }

  @FXML
  private void reviewPressed() {
    fade(reviewPane);
  }

  @FXML
  private int tableNumConfirmed() {
    int tableNum = 0;
    try {
      tableNum = Integer.valueOf(tableField.getText());
      /*
       * Confirm table number with server. And get the customer ID.
       */
      confirmationPane.toBack();
      connection.setConnection("167.99.149.174", Integer.toString(tableNum));
    } catch (Exception e) {
      tableField.setText("");
    }
    return tableNum;
  }

  @FXML
  private void orderPlaced() {
    for (Order order : orders.getOrderList()) {
      // connection.order(order);
      order.setStatus("WAITING");
    }
  }

  @FXML
  private void paymentConfirmed() {
    // connection.paymentConfirmed();
  }

  @FXML
  private void orderCancelled() {
    for (Order order : orders.getOrderList()) {
      // connection.cancelOrder(order);
      order.setStatus("CANCELLED");
    }
  }

  /**
   * This method is used to set up all information needed for the payment tab.
   * 
   */
  private void paymentTab() {
    cardButton.getStylesheets()
        .add(getClass().getResource("paymentTypeButtons.css").toExternalForm());
    cashButton.getStylesheets()
        .add(getClass().getResource("paymentTypeButtons.css").toExternalForm());
    oxacaAccButton.getStylesheets()
        .add(getClass().getResource("paymentTypeButtons.css").toExternalForm());
    cardButton.setOnAction(cardPush);
    cashButton.setOnAction(cashPush);
    oxacaAccButton.setOnAction(oxacaAccPush);
    paymentTypeHBox.getChildren().add(cardButton);
    paymentTypeHBox.getChildren().add(cashButton);
    paymentTypeHBox.getChildren().add(oxacaAccButton);
  }

  EventHandler<ActionEvent> cardPush = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      payingPane.getChildren().clear();
      Label ifNotOrdered;
      if (orders.getTotalPrice() == 0.00) { // Change to check the order exists rather than price.
        ifNotOrdered = new Label("Please place an order before payment. Thank you");
        ifNotOrdered.getStylesheets()
            .add(getClass().getResource("priceLabel.css").toExternalForm());
        ifNotOrdered.setMaxWidth(Double.MAX_VALUE);
        ifNotOrdered.setAlignment(Pos.CENTER);
        payingPane.getChildren().add(ifNotOrdered);
      } else {
        Button payButton = new Button("Validate and Pay " + (char)163 + totalPrice.getText());
        Label cardNumberLabel = new Label("Long Card Number (16 Digits) : ");
        Label expDateLabel =
            new Label("Expiry Date (MM/YY) :                                                /");
        Label secCodeLabel = new Label("Three Digit Security Code (On The Back) : ");
        payButton.getStylesheets().add(getClass().getResource("cardButton.css").toExternalForm());
        cardNumberLabel.getStylesheets()
            .add(getClass().getResource("cardLabel.css").toExternalForm());
        expDateLabel.getStylesheets().add(getClass().getResource("cardLabel.css").toExternalForm());
        secCodeLabel.getStylesheets().add(getClass().getResource("cardLabel.css").toExternalForm());
        cardNumber.getStylesheets()
            .add(getClass().getResource("cardDetailTextFields.css").toExternalForm());
        expMonth.getStylesheets()
            .add(getClass().getResource("cardDetailTextFields.css").toExternalForm());
        expYear.getStylesheets()
            .add(getClass().getResource("cardDetailTextFields.css").toExternalForm());
        threeDigits.getStylesheets()
            .add(getClass().getResource("cardDetailTextFields.css").toExternalForm());
        cardNumberLabel.setLayoutY(50);
        expDateLabel.setLayoutY(150);
        secCodeLabel.setLayoutY(250);
        cardNumber.setPrefWidth(320);
        cardNumber.setLayoutY(50);
        cardNumber.setLayoutX(400);
        expMonth.setPrefWidth(50);
        expMonth.setLayoutY(150);
        expMonth.setLayoutX(400);
        expYear.setPrefWidth(50);
        expYear.setLayoutY(150);
        expYear.setLayoutX(470);
        threeDigits.setPrefWidth(60);
        threeDigits.setLayoutY(250);
        threeDigits.setLayoutX(400);
        payButton.setLayoutX(250);
        payButton.setLayoutY(350);
        payButton.setOnAction(validate);
        payingPane.getChildren().add(cardNumberLabel);
        payingPane.getChildren().add(expDateLabel);
        payingPane.getChildren().add(secCodeLabel);
        payingPane.getChildren().add(cardNumber);
        payingPane.getChildren().add(expMonth);
        payingPane.getChildren().add(expYear);
        payingPane.getChildren().add(threeDigits);
        payingPane.getChildren().add(payButton);
      }
    }
  };

  EventHandler<ActionEvent> cashPush = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      payingPane.getChildren().clear();
      Label price;
      if (orders.getTotalPrice() == 0.00) {
        price = new Label("Please place an order before payment. Thank you");
      } else {
        price = new Label(
            "Please pay " + (char)163 + totalPrice.getText() + " to a member staff. Thank you");
      }
      price.getStylesheets().add(getClass().getResource("priceLabel.css").toExternalForm());
      price.setMaxWidth(Double.MAX_VALUE);
      price.setAlignment(Pos.CENTER);
      payingPane.getChildren().add(price);

    }
  };

  EventHandler<ActionEvent> oxacaAccPush = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      payingPane.getChildren().clear();
      Label ifNotOrdered;
      if (orders.getTotalPrice() == 0.00) { // Change to check the order exists rather than price.
        ifNotOrdered = new Label("Please place an order before payment. Thank you");
        ifNotOrdered.getStylesheets()
            .add(getClass().getResource("priceLabel.css").toExternalForm());
        ifNotOrdered.setMaxWidth(Double.MAX_VALUE);
        ifNotOrdered.setAlignment(Pos.CENTER);
        payingPane.getChildren().add(ifNotOrdered);
      } else {
        // input Oxaca account details.
      }
    }
  };

  EventHandler<ActionEvent> validate = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      int validInput = 0; // Counts how many inputs are valid card details.
      if (cardNumber.getLength() != 16) {
        cardNumber.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Card number is invalid");
      } else {
        cardNumber.setStyle("-fx-border-width: 0 0 0 0;");
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
      if (threeDigits.getLength() != 3) {
        threeDigits.setStyle("-fx-border-color: red; -fx-border-width: 1 1 1 1;");
        throw new IllegalDetails("Security code is invalid");
      } else {
        threeDigits.setStyle("-fx-border-width: 0 0 0 0;");
        validInput++;
      }

      if (validInput == 3) {
        isPaid = true;
        // Set order to paid in the database.
        paymentPressed();
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

  /**
   * On start up, sets up status Tab.
   * 
   */
  public void statusTab() {
    Label trackLabel = new Label("Tracking order");
    trackLabel.getStylesheets().add(getClass().getResource("priceLabel.css").toExternalForm());
    refreshStatus.getStylesheets().add(getClass().getResource("cardButton.css").toExternalForm());
    refreshStatus.setOnAction(refreshStat);
    lastUpdatedLabel.getStylesheets().add(getClass().getResource("cardLabel.css").toExternalForm());
    updateLabel.getStylesheets().add(getClass().getResource("cardLabel.css").toExternalForm());
    refreshStatus.setLayoutX(600);
    refreshStatus.setLayoutY(100);
    lastUpdatedLabel.setLayoutY(200);
    lastUpdatedLabel.setLayoutX(50);
    updateLabel.setLayoutX(50);
    updateLabel.setLayoutY(300);
    trackPane.getChildren().add(trackLabel);
    trackPane.getChildren().add(lastUpdatedLabel);
    trackPane.getChildren().add(updateLabel);
    trackPane.getChildren().add(refreshStatus);
  }

  EventHandler<ActionEvent> refreshStat = new EventHandler<ActionEvent>() {
    public void handle(ActionEvent e) {
      // Get last updated time, set label to time
      // Get last status, set label to status
    }
  };
}
