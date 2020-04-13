package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class SocketThread {
  private DataInputStream input;
  private DataOutputStream output;

  public SocketThread(DataInputStream input, DataOutputStream output) {
    this.input = input;
    this.output = output;
  }

  public String read() {
    try {
      return input.readUTF();
    } catch (IOException e) {
      return null;
    }
  }

  public void write(String line) {
    try {
      output.writeUTF(line);
      output.flush();
    } catch (IOException e) {
      System.out.print("Connection error while writing to client");
    }
  }
}
