package views;

import java.util.ArrayList;
import consumable.Consumable;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 * The Class KitchenViewController.
 */
public class KitchenViewController {

  /** The menu tab pane. */
  @FXML
  private TabPane orderTabPane = new TabPane();

  /** The vbox new. */
  @FXML
  private VBox vboxNew = new VBox();

  /** The vbox in progress. */
  @FXML
  private VBox vboxInProgress = new VBox();

  /** The vbox completed. */
  @FXML
  private VBox vboxCompleted = new VBox();

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

  /**
   * the createNewOrderVBox method to create the VBox for the New order tab.
   * 
   * @param consumables return consumable menu value.
   * @return the corresponding VBox value.
   */
  private VBox createNewOrderVBox(ArrayList<Consumable> consumables) {
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

  /**
   * To initialiseGap for the kitchenView.java.
   * 
   * @return the Gap of the kitchen view.
   */
  private Node initialiseGap() {
    Pane gap = new Pane();
    gap.setPrefSize(25, 50);
    return gap;
  }

  /**
   * Constructor to create the new order tab.
   * 
   * @param name the name of the tab.
   * @param list of the consumable.
   * @return the corresponding tab been created.
   */
  private Tab createNewOrderTab(String name, ArrayList<Consumable> list) {
    AnchorPane anchorpane = new AnchorPane();
    anchorpane.setPrefWidth(580);
    anchorpane.getChildren().add(createNewOrderVBox(list));
    ScrollPane scrollpane = new ScrollPane(anchorpane);
    scrollpane.setPrefWidth(600);
    Tab tab = new Tab(name.toUpperCase(), scrollpane);
    return tab;
  }


}
