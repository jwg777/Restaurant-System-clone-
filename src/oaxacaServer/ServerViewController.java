package oaxacaServer;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Queue;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

// TODO: Auto-generated Javadoc
/**
 * The Class ServerViewController.
 */
public class ServerViewController {

  /** The server. */
  Server server = Server.getInstance();

  /** The log scroll pane. */
  @FXML
  ScrollPane logScrollPane = new ScrollPane();

  /** The log list view. */
  @FXML
  ListView<String> logListView = new ListView<>();

  /** The ipv 4 label. */
  @FXML
  Label ipv4Label = new Label();

  /** The port text field. */
  @FXML
  TextField portTextField = new TextField();

  /** The circle. */
  @FXML
  Circle circle = new Circle();

  /**
   * Initialize.
   *
   * @throws Exception the exception
   */
  @FXML
  public void initialize() throws Exception {
    reloadPush();
  }

  /**
   * Reload push.
   *
   * @throws Exception the exception
   */
  @FXML
  public void reloadPush() throws Exception {
    updateIPv4();
  }

  /**
   * Stop push.
   */
  @FXML
  public void stopPush() {
    server.close();
    circle.setFill(Color.RED);
  }

  /**
   * Gets the i pv 4.
   *
   * @return the i pv 4
   * @throws UnknownHostException the unknown host exception
   * @throws SocketException the socket exception
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
   * Update I pv 4.
   *
   * @throws UnknownHostException the unknown host exception
   * @throws SocketException the socket exception
   */
  public void updateIPv4() throws UnknownHostException, SocketException {
    ipv4Label.setText(getIPv4() + " : ");
  }

  /**
   * Start server.
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
    server.addListener(new ListListener() {
      @Override
      public void onListChange() {
        updateListView(server.getQueue());
      }
    });
    Thread t = new Thread(server);
    t.start();
    circle.setFill(server.running ? Color.GREEN : Color.RED);
  }

  /**
   * Checks if is port.
   *
   * @param string the string
   * @return true, if is port
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
   * Update list view.
   *
   * @param q the q
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
