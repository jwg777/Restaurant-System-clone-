package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import database_cafe.DataInteract;

// TODO: Auto-generated Javadoc
/**
 * The Class LoginAccess.
 */
public class LoginAccess {

  /** The login data. */
  DataInteract loginData;

  /**
   * Instantiates a new login access.
   */
  public LoginAccess() {
    loginData = DataInteract.getInstance();
  }

  /**
   * Check user.
   *
   * @param username the username
   * @param password the password
   * @return true, if successful
   * @throws SQLException the SQL exception
   */
  public boolean checkUser(String username, String password) throws SQLException {
    ResultSet rs = loginData.select("SELECT * FROM Staff");
    while (rs.next()) {
      if (rs.getString("staff_id").equals(username) && rs.getString("password").equals(password)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Checks if is kitchen.
   *
   * @param username the username
   * @return true, if is kitchen
   * @throws SQLException the SQL exception
   */
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
