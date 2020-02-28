package backend;

import java.io.DataInputStream;
import java.io.IOException;

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
    String action;
    while (true) {
      try {
        action = input.readUTF();
        String[] actionArray = action.split(" ");
        switch (actionArray[0]) {
          case "ACCEPTED":
          case "DENIED":
          case "OK":
            response = actionArray[0];
            break;
          case "UPDATEMENU":
            /*
             * Updates whole menu
             */
            break;
          case "ADD":
            /*
             * Adds consumable to menu
             */
            break;
          case "DELETE":
            /*
             * Deletes consumable from menu
             */
            break;
        }
      } catch (IOException e) {
      }
    }
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
