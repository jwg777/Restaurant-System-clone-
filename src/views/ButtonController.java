package views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ButtonController {

	private static ButtonController instance = null;

	private Stage stage;

	public static ButtonController getInstance() {
		if (instance == null) {
			instance = new ButtonController();
		}
		return instance;
	}

	private ButtonController() {
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	private void sceneChange(String fxmlFile, String title) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
		Scene scene = new Scene(parent);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	public void startCustomer() throws IOException {
		sceneChange("CustomerView.fxml", "Oaxaca Customer View");
	}

	public void startMain() throws IOException {
		sceneChange("MainView.fxml", "Oaxaca Management System");
	}

	public void startLogin() throws IOException {
		sceneChange("LoginView.fxml", "Oaxaca Staff Login");
	}

}
