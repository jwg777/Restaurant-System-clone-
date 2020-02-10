package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import database_cafe.DataInteract;

public class LoginAccess {
  
  DataInteract loginData;
  
  public LoginAccess() {
    loginData = DataInteract.getInstance();
  }
  
  public boolean checkUser(String username, String password) throws SQLException {
    ResultSet rs = loginData.select("SELECT * FROM Staff");
    while (rs.next()) {
      if (rs.getString("staff_id").equals(username) && rs.getString("password").equals(password)) {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean isKitchen(String username) throws SQLException {
    ResultSet rs = loginData.select("SELECT * FROM Staff");
    while (rs.next()) {
      if (rs.getString("staff_id").equals(username) && rs.getString("role").equals("kitchen")) {
        return true;
      }
    }
    return false;
  }

}
