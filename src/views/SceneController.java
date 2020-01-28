package views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Button Controller decides and handles the action taken when a button is pressed in the application.
 */
public final class SceneController {

	/** The Singleton instance. */
	private static SceneController instance = null;

	/** The stage. */
	private Stage stage;

	/**
	 * Gets the single instance of ButtonController.
	 *
	 * @return the instance
	 */
	public static SceneController getInstance() {
		if (instance == null) {
			instance = new SceneController();
		}
		return instance;
	}

	/**
	 * Instantiates a new button controller.
	 */
	private SceneController() {
	}

	/**
	 * Changes the stage.
	 *
	 * @param stage the new stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Changes the view currently being displayed in the application.
	 *
	 * @param fxmlFile the fxml file containing the view's design
	 * @param title the title of the view, displayed at the top of the window
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private void sceneChange(String fxmlFile, String title) throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource(fxmlFile));
		Scene scene = new Scene(parent);
		stage.setTitle(title);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * Opens the customer view.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startCustomer() throws IOException {
		sceneChange("CustomerView.fxml", "Oaxaca Customer View");
	}

	/**
	 * Opens the main menu view.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startMain() throws IOException {
		sceneChange("MainView.fxml", "Oaxaca Management System");
	}

	/**
	 * Opens the staff login view.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startLogin() throws IOException {
		sceneChange("LoginView.fxml", "Oaxaca Staff Login");
	}
	
	/**
	 * Opens the waiter view.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startWaiter() throws IOException {
	    sceneChange("WaiterView.fxml", "Oaxaca Waiter View");
	}

}
