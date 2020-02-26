package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

final public class ServerAccess extends Thread {

  private static ServerAccess instance = null;

  private Socket socket;
  private static ReadThread read = ReadThread.getInstance();
  private WriteThread write;

  private ServerAccess() {}

  public ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }

  public void setConnection(Socket socket) throws IOException {
    this.socket = socket;
    read.setInput(new DataInputStream(socket.getInputStream()));
  }

  public void run() {

  }

  public void close() {
    try {
      socket.close();
    } catch (IOException e) {
    }
  }
}
