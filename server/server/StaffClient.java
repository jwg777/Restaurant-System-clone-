package server;

public class StaffClient {

  String username;
  Staff staff;
  SocketThread request;
  SocketThread notification;

  public StaffClient(Staff newStaff) {
    this.staff = newStaff;
    this.username = staff.getUsername();
  }

  public void setRequest(SocketThread request) {
    this.request = request;
  }

  public void setNotification(SocketThread notification) {
    this.notification = notification;
  }

  public String getUsername() {
    return username;
  }

}
