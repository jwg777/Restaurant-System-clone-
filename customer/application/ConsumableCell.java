package application;

import java.io.IOException;
import consumable.Consumable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import order.OrderList;

public class ConsumableCell {

  @FXML
  private ImageView image;

  @FXML
  private VBox vBox;

  @FXML
  private Button minusButton;

  @FXML
  private Label ingredients;

  @FXML
  private Button addButton;

  @FXML
  private AnchorPane cell;

  @FXML
  private Label title;

  OrderList orders = OrderList.getInstance();

  public ConsumableCell(Consumable consumable) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConsumableView.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    addButton.setOnAction((EventHandler<ActionEvent>) event -> {
      orders.add(consumable);
    });
    minusButton.setOnAction((EventHandler<ActionEvent>) event -> {
      try {
        orders.minus(consumable);
      } catch (IndexOutOfBoundsException e) {
        
      }
    });
    vBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("92E0E1"), null, null)));
    vBox.setStyle("-fx-opacity:0.0;");
    title.setText(consumable.getName());
    ingredients.setText(consumable.getIngredients());
  }

  @FXML
  public void onHover() {
    vBox.setStyle("-fx-opacity:0.9;");
  }

  @FXML
  public void exitHover() {
    vBox.setStyle("-fx-opacity:0.0;");
  }

  public AnchorPane getCell() {
    return cell;
  }

}
