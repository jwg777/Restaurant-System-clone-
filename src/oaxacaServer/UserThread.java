package oaxacaServer;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class UserThread extends Thread {

  private Socket socket;
  private Server server = Server.getInstance();

  public UserThread(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    try {
      DataInputStream dis = new DataInputStream(socket.getInputStream());
      ClientType type = ClientType.valueOf((String) dis.readUTF());
      String name = type.name() + "_" + server.addNumebr();
      server.addUserName(name);
      server.write("New client connected: " + name);
      String response;
      do {
        dis = new DataInputStream(socket.getInputStream());
        response = (String) dis.readUTF();
        server.write("[" + name + "] : " + response);
      } while (!response.equals("STOP"));
      socket.close();
      server.removeUser(name, this);
      server.write(name + " has disconnected");
    } catch (IOException e) {

    } catch (IllegalArgumentException e) {
      server.removeThread(this);
    }
  }

}
