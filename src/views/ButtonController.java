package views;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ButtonController {
	
	private static ButtonController instance = null;
	Stage stage;
	
	public static ButtonController getInstance() {
		if(instance == null) {
			instance = new ButtonController();
		}
		return instance;
	}
	
	private ButtonController() {
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	

	public void startCustomer() throws Exception{
		Parent customerViewParent = FXMLLoader.load(getClass().getResource("CustomerView.fxml"));
		Scene customerScene = new Scene(customerViewParent);
		stage.setTitle("Oaxaca Customer View");
		stage.setScene(customerScene);
		stage.show();
	}
	
	public void startMain() throws Exception {
	    Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
	    Scene mainScene = new Scene(root, 362, 213);
	    stage.setTitle("Oaxaca Management System");
	    stage.setScene(mainScene);
	    stage.show();
	}
	
    public void startLogin() throws Exception {
      Parent loginParent = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
      Scene loginScene = new Scene(loginParent, 290, 235);
      stage.setTitle("Oaxaca Staff Login");
      stage.setScene(loginScene);
      stage.show();
    }
    
}
