
import java.io.IOException;
import consumable.Consumable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

public class ConsumableCell {

  @FXML
  private AnchorPane cell;

  @FXML
  private ImageView image;

  @FXML
  private VBox vBox;

  @FXML
  private Label title;

  @FXML
  private Label ingredients;

  public ConsumableCell(Consumable consumable) {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ConsumableView.fxml"));
    fxmlLoader.setController(this);
    try {
      fxmlLoader.load();
    } catch (IOException e) {
      e.printStackTrace();
    }
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
