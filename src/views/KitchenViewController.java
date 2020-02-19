package views;

import java.util.ArrayList;
import consumable.Consumable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * The Class KitchenViewController.
 */
public class KitchenViewController {

  /** The menu tab pane. */
  @FXML
  private TabPane menuTabPane;

  /** The vbox new. */
  @FXML
  private VBox vboxNew;

  /** The vbox in progress. */
  @FXML
  private VBox vboxInProgress;

  /** The vbox completed. */
  @FXML
  private VBox vboxCompleted;

  /** The ordered list. */
  @FXML
  private ListView<?> orderedList;

  /** The reload. */
  @FXML
  private Button reload;

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
      vbox.getChildren().add(tempHBox); // Add consumable to the list
    }
    return vbox;
  }

  private Node initialiseLabel(String name, int i, int j) {
    // TODO Auto-generated method stub
    return null;
  }

  private Node initialiseGap() {
    // TODO Auto-generated method stub
    return null;
  }

  @FXML
  private Tab createTab(String name, ArrayList<Consumable> list) {
    AnchorPane anchorpane = new AnchorPane();
    anchorpane.setMaxWidth(580);
    anchorpane.getChildren().add(createVBox(list));
    ScrollPane scrollpane = new ScrollPane(anchorpane);
    scrollpane.setMaxWidth(600);
    Tab tab = new Tab(name.toUpperCase());
    return tab;

  }


}
