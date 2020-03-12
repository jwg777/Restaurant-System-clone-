package application;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import order.Order;

public class OrderCell {

  @FXML
  private Label quantity;

  @FXML
  private Label totalPrice;

  @FXML
  private Label price;

  @FXML
  private Label title;

  @FXML
  private AnchorPane anchorPane;

  public OrderCell() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderCell.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setData(Order order) {
    order.setListener(() -> {
      quantity.setText("x " + order.getQuantity());
      totalPrice.setText(String.format("%.2f", order.getQuantity() * order.getPrice()));
    });
    quantity.setText("x " + order.getQuantity());
    price.setText(String.format("%.2f", order.getPrice()));
    totalPrice.setText(Float.toString(order.getPrice()));
    title.setText(order.getDishName());
  }

  public AnchorPane getCell() {
    return anchorPane;
  }

}
