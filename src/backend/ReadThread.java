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
   * 
   */
  private static ReadThread instance = null;
  /**
   * 
   */
  private DataInputStream input;
  
  private String response;
  /**
   * 
   */
  private ReadThread() {}

  /**
   * @return
   */
  public static ReadThread getInstance() {
    if (instance == null) {
      instance = new ReadThread();
    }
    return instance;
  }

  /**
   * @param input
   */
  public void setInput(DataInputStream input) {
    this.input = input;
  }
  
  public String getResponse() {
    String temp = response;
    //clears it every time, so it knows someone has read the response.
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
   * @throws IOException
   */
  public void close() throws IOException {
    input.close();
  }

}
