package backend;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Arrays;
import consumable.Consumable;
import consumable.MenuMap;
import views.SceneController;

/**
 * Read Thread Class to read from server at real time.
 * 
 * @author Chak
 */
final public class ReadThread extends Thread {

  /**
   * Singleton instance for ReadThread
   */
  private static ReadThread instance = null;
  /**
   * input stream to read from server
   */
  private DataInputStream input;
  /**
   * response given by the server.
   */
  private String response;

  MenuMap menu = MenuMap.getInstance();
  
  static SceneController controller = SceneController.getInstance();

  /**
   * Private constructor for the singleton class.
   */
  private ReadThread() {}

  /**
   * Returns the instance of ReadThread.
   * 
   * @return ReadThread
   */
  public static ReadThread getInstance() {
    if (instance == null) {
      instance = new ReadThread();
    }
    return instance;
  }

  /**
   * sets the input stream.
   * 
   * @param input
   */
  public void setInput(DataInputStream input) {
    this.input = input;
  }

  /**
   * Returns the response given by the server, clears it every time it's returns.
   * 
   * @return response
   */
  public String getResponse() {
    String temp = response;
    // clears it every time, so it knows someone has read the response.
    response = "";
    return temp;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    String operator;
    String operand;
    do {
      try {
        String[] responseArray = ((String) input.readUTF()).split(" ");
        System.out.println(Arrays.toString(responseArray));
        if (responseArray.length > 2 || responseArray.length == 0) {
          continue;
        }
        operator = responseArray[0];
        if (responseArray.length == 1) {
          response = operator;
          continue;
        }
        operand = responseArray[1];
        switch (operator.toUpperCase()) {
          case "ADD":
            menu.put(new Consumable(operand));
            controller.getMenuListener().onMenuChange();
            break;
        }

      } catch (IOException e) {
      }
    } while (true);
  }



  /**
   * Closes the input stream.
   * 
   * @throws IOException
   */
  public void close() throws IOException {
    input.close();
  }

}
