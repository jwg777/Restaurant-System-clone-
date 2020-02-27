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

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void run() {
    String response;
    while (true) {
      try {
        response = input.readUTF();
        String[] responseArray = response.split(" ");
        switch (responseArray[0]) {
          case "UPDATEMENU":
            break;
          case "ADD":
            break;
          case "DELETE":
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
