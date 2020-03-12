package application;

import java.io.IOException;
import consumable.Consumable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class OrderCellController {

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

  public OrderCellController() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("OrderCell.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void setData(Consumable consumable) {
    quantity.setText("x1");
    totalPrice.setText(Float.toString(consumable.getPrice()));
    price.setText(Float.toString(consumable.getPrice()));
    title.setText(consumable.getName());
  }
  
  public AnchorPane getCell() {
    return anchorPane;
  }

}
