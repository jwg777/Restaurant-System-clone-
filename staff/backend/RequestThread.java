package backend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RequestThread extends Thread {

  private DataInputStream input;
  private DataOutputStream output;

  public RequestThread(Socket socket) {
    try {
      input = new DataInputStream(socket.getInputStream());
      output = new DataOutputStream(socket.getOutputStream());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean staffLogin(String username, String password) throws IOException {
    try {
      output.writeUTF("STAFF");
      output.flush();
      if (!input.readUTF().equals("OK")) {
        throw new IOException();
      }
      output.writeUTF(username + " " + password);
      output.flush();
      if (input.readUTF().equals("ACCEPTED WAITER")) {
        return true;
      }
      if (input.readUTF().equals("ACCEPTED KITCHEN")) {
        return true;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return false;
  }
}
