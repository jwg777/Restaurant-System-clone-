package views;

import javafx.fxml.FXML;

public class MainViewController{
	
	ButtonController butController = ButtonController.getInstance();
	
	@FXML
	private void customerPush() throws Exception{
		System.out.println("Customer Pushed");
		butController.startCustomer();
	}

}