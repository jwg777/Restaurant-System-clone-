package application;

import java.io.IOException;
import java.util.HashMap;

import consumable.Consumable;
import consumable.MenuMap;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class MainViewController {

  @FXML
  private AnchorPane statusPane;

  @FXML
  private AnchorPane reviewPane;

  @FXML
  private AnchorPane ordersPane;

  @FXML
  private AnchorPane menuPane;

  @FXML
  private AnchorPane menuAnchor;

  @FXML
  private HBox category;

  @FXML
  private StackPane confirmationPane;

  @FXML
  private TextField tableField;

  @FXML
  private Button confirmButton;

  private Node frontPane;

  MenuMap menu = MenuMap.getInstance();

  HashMap<String, Button> buttons = new HashMap<>();

  @FXML
  private void initialize() throws IOException {
    menuPane.toFront();
    frontPane = menuPane;
    confirmationPane.toFront();
    // keep pointers to buttons
    for (String string : menu.keyArray()) {
      Button button = new Button(string);
      CategoryCell categoryCell = new CategoryCell();
      for (Consumable consumable : menu.get(string)) {
        ConsumableCell tempC = new ConsumableCell(consumable);
        categoryCell.getTilePane().getChildren().add(tempC.getCell());
      }
      button.setOnAction(new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
          fade(categoryCell.getScrollPane());
        }
      });
      menuAnchor.getChildren().add(categoryCell.getScrollPane());
      button.getStylesheets().add(getClass().getResource("menuButtons.css").toExternalForm());
      category.getChildren().add(button);
      buttons.put(string, button);
    }
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
      confirmationPane.toBack();
    } catch (Exception e) {
      tableField.setText("");
    }
  }

  @FXML
  private void login() {

  }

  public void createTabs() {

  }

}
