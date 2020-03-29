package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class SocketThread {
  private DataInputStream input;
  private DataOutputStream output;

  public SocketThread(DataInputStream input, DataOutputStream output) {
    this.input = input;
    this.output = output;
  }

}
