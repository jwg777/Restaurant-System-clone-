package backend;

import java.io.DataInputStream;
import java.io.IOException;

final public class ReadThread extends Thread {

  private static ReadThread instance = null;
  private DataInputStream input;

  private ReadThread() {}

  public static ReadThread getInstance() {
    if (instance == null) {
      instance = new ReadThread();
    }
    return instance;
  }

  public void setInput(DataInputStream input) {
    this.input = input;
  }

  public void run() {
    String response;
    while (true) {
      try {
        response = input.readUTF();
        switch (response.split(" ")[0].toUpperCase()) {
          case "UPDATE":
            break;
        }
      } catch (IOException e) {
      }
    }
  }
}
