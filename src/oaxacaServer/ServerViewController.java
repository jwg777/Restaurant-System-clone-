package oaxacaServer;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ServerViewController {

	Server server = Server.getInstance();

	@FXML
	ScrollPane logScrollPane = new ScrollPane();

	@FXML
	ListView<String> logListView = new ListView<>();

	@FXML
	Label ipv4Label = new Label();

	@FXML
	TextField portTextField = new TextField();

	@FXML
	Circle circle = new Circle();

	@FXML
	public void initialize() throws Exception {
		reloadPush();
	}

	@FXML
	public void reloadPush() throws Exception {
		updateIPv4();
	}

	@FXML
	public void stopPush() {
		server.close();
		circle.setFill(Color.RED);
	}

	public String getIPv4() throws UnknownHostException {
		InetAddress ip = InetAddress.getLocalHost();
		return ip.toString().split("/")[1];
	}

	public void updateIPv4() throws UnknownHostException {
		ipv4Label.setText(getIPv4() + " : ");
	}

	@FXML
	public void startServer() {
		String port = portTextField.getText();
		if (!isPort(port)) {
			Alert alert = new Alert(AlertType.ERROR, "Please enter correct port.", ButtonType.OK);
			alert.show();
			if (alert.getResult() == ButtonType.OK) {
				alert.close();
			}
			return;
		}
		server.setPort(Integer.parseInt(port));
		server.addListener(new Listener() {
			@Override
			public void onListChange() {
				updateListView(server.getLine());
			}
		});
		Thread t = new Thread(server);
		t.start();
		circle.setFill(server.running ? Color.GREEN : Color.RED);
	}

	private boolean isPort(String string) {
		if (string.isEmpty()) {
			return false;
		}
		if (string.length() != 4) {
			return false;
		}
		for (char c : string.toCharArray()) {
			if (c < '0' || c > '9') {
				return false;
			}
		}
		return true;
	}

	public void updateListView(String line) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				logListView.getItems().add(line);
			}
		});

	}

}
