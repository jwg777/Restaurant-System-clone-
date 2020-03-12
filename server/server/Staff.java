package server;

public class Staff {

  private String username;
  private String password;
  private ClientType role;

  public Staff(ClientType role, String username, String password) {
    this.role = role;
    this.username = username;
    this.password = password;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public ClientType getRole() {
    return role;
  }

}
