package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

final public class ServerAccess extends Thread {

  private static ServerAccess instance = null;
  
  private Socket socket;
  private ReadThread read;
  private WriteThread write;

  private ServerAccess() {}

  public ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }
  
  public void setConnection(Socket socket) {
    this.socket = socket;
    
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
