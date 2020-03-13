package application;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import order.Order;
import order.OrderList;

public class OrderCell {

  @FXML
  private ImageView plusPressed;

  @FXML
  private Button plusButton;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private Label quantity;

  @FXML
  private ImageView plusDefault;

  @FXML
  private Label totalPrice;

  @FXML
  private Label price;

  @FXML
  private Button minusButton;

  @FXML
  private ImageView minusDefault;

  @FXML
  private Label title;

  @FXML
  private ImageView minusPressed;

  OrderList orderList = OrderList.getInstance();

  public OrderCell() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderCell.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void pressedAnimation(ImageView image) {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(200));
    ft.setFromValue(1);
    ft.setToValue(0);
    ft.setNode(image);
    image.setStyle("-fx-opacity:10.0");
    ft.play();
    image.setStyle("-fx-opacity:0.0");
  }

  public void setData(Order order) {
    order.addListener(() -> {
      quantity.setText("" + order.getQuantity());
      totalPrice.setText(String.format("%.2f", order.getQuantity() * order.getPrice()));
    });
    plusButton.setOnAction((EventHandler<ActionEvent>) event -> {
      orderList.add(order);
      pressedAnimation(plusPressed);
    });
    minusButton.setOnAction((EventHandler<ActionEvent>) event -> {
      orderList.minus(order);
      pressedAnimation(minusPressed);
    });
    quantity.setText(order.getQuantity() + "");
    price.setText(String.format("%.2f", order.getPrice()));
    totalPrice.setText(Float.toString(order.getPrice()));
    title.setText(order.getDishName());
  }

  public AnchorPane getCell() {
    return anchorPane;
  }

}
