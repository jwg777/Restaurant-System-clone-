package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

public class CategoryCell {

  @FXML
  private ScrollPane scrollPane;

  @FXML
  private TilePane tilePane;

  public CategoryCell() {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CategoryView.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
    AnchorPane.setTopAnchor(scrollPane, 0.);
    AnchorPane.setBottomAnchor(scrollPane, 0.);
    AnchorPane.setLeftAnchor(scrollPane, 0.);
    AnchorPane.setRightAnchor(scrollPane, 0.);
  }

  public TilePane getTilePane() {
    return tilePane;
  }

  public ScrollPane getScrollPane() {
    return scrollPane;
  }
}
