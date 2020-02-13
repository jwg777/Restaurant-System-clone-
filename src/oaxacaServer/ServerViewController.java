package oaxacaServer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Queue;
import java.util.Set;
import javax.sql.rowset.CachedRowSet;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.MediaException.Type;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Controller for server view
 * 
 * @author chak_hang
 */
public class ServerViewController {

  /**
   * Singleton object to manage the server
   */
  Server server = Server.getInstance();

  /**
   * FXML scroll pane
   */
  @FXML
  ScrollPane logScrollPane = new ScrollPane();

  /**
   * FXML List view for logs
   */
  @FXML
  ListView<String> logListView = new ListView<>();

  /**
   * FXML label to show the ipv4 address.
   */
  @FXML
  Label ipv4Label = new Label();

  /**
   * FXML text field for user to input the port.
   */
  @FXML
  TextField portTextField = new TextField();

  /**
   * FXML circle to show if the server is running.
   */
  @FXML
  Circle circle = new Circle();

  /**
   * FXML vbox to show online clients that are customers.
   */
  @FXML
  VBox customerVbox = new VBox();

  /**
   * FXML vbox to show online clients that are waiter staff.
   */
  @FXML
  VBox waiterVbox = new VBox();

  /**
   * FXML vbox to show online clients that are kitchen staff.
   */
  @FXML
  VBox kitchenVbox = new VBox();


  /**
   * Starts this method before the scene run.
   * 
   * @throws Exception
   */
  @FXML
  public void initialize() throws Exception {
    reloadPush();
  }

  /**
   * Method called when refresh button is pressed updates ipv4 address.
   * 
   * @throws Exception
   */
  @FXML
  public void reloadPush() throws Exception {
    updateIPv4();
  }

  /**
   * Method for when stop is pressed, it stops the server.
   */
  @FXML
  public void stopPush() {
    server.close();
    circle.setFill(Color.RED);
  }

  /**
   * Gets the ipv4 address of the current machine.
   * 
   * @return ipv4 addresss
   * @throws UnknownHostException
   * @throws SocketException
   */
  public String getIPv4() throws UnknownHostException, SocketException {
    String ip = InetAddress.getLocalHost().getHostAddress();
    if (!ip.split("\\.")[0].equals("192")) {
      Enumeration<NetworkInterface> allNet = NetworkInterface.getNetworkInterfaces();
      while (allNet.hasMoreElements()) {
        NetworkInterface e = allNet.nextElement();
        Enumeration<InetAddress> a = e.getInetAddresses();
        while (a.hasMoreElements()) {
          InetAddress addr = a.nextElement();
          if (addr.getHostAddress().split("\\.")[0].equals("192")) {
            ip = addr.getHostAddress();
            break;
          }
        }

      }
    }
    return ip;
  }

  /**
   * Updates the ipv4 address on the label.
   * 
   * @throws UnknownHostException
   * @throws SocketException
   */
  public void updateIPv4() throws UnknownHostException, SocketException {
    ipv4Label.setText(getIPv4() + " : ");
  }

  /**
   * Starts the server.
   */
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
    server.addListListener(new ListListener() {
      @Override
      public void onListChange() {
        updateListView(server.getQueue());
      }
    });
    server.addClientListener(new ClientListener() {
      @Override
      public void onClientChange() {
        updateUsers(server.getUsernames());
      }
    });
    Thread t = new Thread(server);
    t.start();
    circle.setFill(server.running ? Color.GREEN : Color.RED);
  }

  /**
   * Updates the clients that are online.
   * 
   * @param usernames
   */
  private void updateUsers(Set<String> usernames) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        clearTabs();
        for (String username : usernames) {
          String[] split = username.split("_");
          ClientType type = ClientType.valueOf(split[0]);
          String number = split[1];
          switch (type) {
            case CUSTOMER:
              addClient(number, customerVbox);
              break;
            case KITCHEN:
              addClient(number, kitchenVbox);
              break;
            case WAITER:
              addClient(number, waiterVbox);
              break;
          }
        }

      }
    });
  }

  /**
   * Checks if the port number is correct.
   * 
   * @param string
   * @return isPort
   */
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

  /**
   * Clears all the online clients on UI.
   */
  private void clearTabs() {
    customerVbox.getChildren().clear();
    waiterVbox.getChildren().clear();
    kitchenVbox.getChildren().clear();
  }

  /**
   * Adds the client to the vbox 
   * @param number
   * @param vbox
   */
  private void addClient(String number, VBox vbox) {
    HBox hBox = new HBox();
    // Alignment CENTRE_LEFT
    hBox.getChildren().add(gap());
    Label label = new Label(number);
    label.setPrefSize(100, 20);
    hBox.getChildren().add(label);
    hBox.getChildren().add(gap());
    Button button = new Button("Disconnect");
    button.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        /*
         * 1. Disconnect the client. 2. Remove client hbox from vbox.
         */
      }
    });
    button.setPrefSize(100, 27);
    hBox.getChildren().add(button);

  }

  /**
   * Creates a gap.
   * @return Pane.
   */
  private Pane gap() {
    Pane gap = new Pane();
    gap.setPrefSize(20, 30);
    return gap;
  }

  /**
   * Updates the list view (log).
   * @param q
   */
  public void updateListView(Queue<String> q) {
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        while (!q.isEmpty()) {
          logListView.getItems().add(q.poll());
        }
      }
    });
  }
}
