package application;

import java.io.IOException;
import consumable.Consumable;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;
import order.OrderList;

public class ConsumableCell {

  @FXML
  private ImageView image;

  @FXML
  private ImageView addImagePressed;

  @FXML
  private VBox vBox;

  @FXML
  private ImageView minusImagePressed;

  @FXML
  private Button minusButton;

  @FXML
  private ImageView minusImageDefault;

  @FXML
  private Label ingredients;

  @FXML
  private Button addButton;

  @FXML
  private ImageView addImageDefault;

  @FXML
  private AnchorPane cell;

  @FXML
  private Label title;

  @FXML
  private Label quantityLabel;

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
      pressedAnimation(addImagePressed);
      orders.add(consumable);
      quantityLabel.setText(String.valueOf(Integer.parseInt(quantityLabel.getText()) + 1));
    });
    minusButton.setOnAction((EventHandler<ActionEvent>) event -> {
      pressedAnimation(minusImagePressed);
      orders.minus(consumable);
      int i = Integer.parseInt(quantityLabel.getText());
      if (i != 0) {
        quantityLabel.setText(String.valueOf(--i));
      }
    });
    vBox.setBackground(new Background(new BackgroundFill(Paint.valueOf("92E0E1"), null, null)));
    vBox.setStyle("-fx-opacity:0.0;");
    title.setText(consumable.getName());
    ingredients.setText(consumable.getIngredients());
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

  @FXML
  public void onHover() {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(100));
    ft.setFromValue(0);
    ft.setToValue(0.9);
    ft.setNode(vBox);
    ft.play();
  }

  @FXML
  public void exitHover() {
    FadeTransition ft = new FadeTransition();
    ft.setDuration(Duration.millis(250));
    ft.setFromValue(0.9);
    ft.setToValue(0);
    ft.setNode(vBox);
    ft.play();
  }

  public AnchorPane getCell() {
    return cell;
  }

}
