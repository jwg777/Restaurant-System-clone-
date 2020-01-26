package views;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class ButtonController.
 */
public final class ButtonController {

	/** The instance. */
	private static ButtonController instance = null;

	/** The stage. */
	private Stage stage;

	/**
	 * Gets the single instance of ButtonController.
	 *
	 * @return single instance of ButtonController
	 */
	public static ButtonController getInstance() {
		if (instance == null) {
			instance = new ButtonController();
		}
		return instance;
	}

	/**
	 * Instantiates a new button controller.
	 */
	private ButtonController() {
	}

	/**
	 * Sets the stage.
	 *
	 * @param stage the new stage
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Scene change.
	 *
	 * @param fxmlFile the fxml file
	 * @param title the title
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
	 * Start customer.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startCustomer() throws IOException {
		sceneChange("CustomerView.fxml", "Oaxaca Customer View");
	}

	/**
	 * Start main.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startMain() throws IOException {
		sceneChange("MainView.fxml", "Oaxaca Management System");
	}

	/**
	 * Start login.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startLogin() throws IOException {
		sceneChange("LoginView.fxml", "Oaxaca Staff Login");
	}
	
	/**
	 * Start waiter.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void startWaiter() throws IOException {
	    sceneChange("WaiterView.fxml", "Oaxaca Waiter View");
	}

}
