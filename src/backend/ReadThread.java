package backend;

import java.io.DataInputStream;
import java.io.IOException;

final public class ReadThread extends Thread{
  
  private static ReadThread instance = null;
  private DataInputStream input;
  
  private ReadThread() {
  }
  
  public ReadThread getInstance() {
    if(instance == null) {
      instance = new ReadThread();
    }
    return instance;
  }
  
  public void setInput(DataInputStream input) {
    this.input = input;
  }
  
  public void run() {
    while(true) {
      try {
        String response;
        response = input.readUTF();
      } catch (IOException e) {
      }
    }
  }
}
