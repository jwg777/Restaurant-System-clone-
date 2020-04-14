package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import database_cafe.DataInteract;


/**
 * Class containing methods for accessing and interacting with the database for the Login view
 * @author joshuagargan
 *
 */
public class LoginAccess {

  /** Field for accessing methods from DataInteract class */
  DataInteract loginData;

  /**
   * Constructor to get instance of DataInteract class.
   */
  public LoginAccess() {
    loginData = DataInteract.getInstance();
  }

  /**

   * This method interacts with the databse to check if the username and password combination
   * is stored and then returning true or false depending on this.
   * @param username
   * @param password
   * @return boolean
   * @throws SQLException
   */
  public boolean checkUser(String username, String password) throws SQLException {
    ResultSet rs = loginData.select("SELECT * FROM Staff");
    while (rs.next()) {
      if (rs.getString("staff_username").equals(username) 
          && rs.getString("password").equals(password)) {
        return true;
      }
    }

    return false;
  }

  /**
   * This method is used for checking if a user that is already given access is either
   * a kitchen staff or waiter staff. If they are a kitchen staff then the method will
   * return true.
   * @param username
   * @return boolean
   * @throws SQLException
   */
  public String checkRole(String username) throws SQLException {
    ResultSet rs = loginData.select("SELECT * FROM Staff WHERE staff_username = '"
        + username + "'");
    while (rs.next()) {
      if (rs.getString("role").equals("kitchen")) {
        return "KITCHEN";
      }
      if (rs.getString("role").equals("waiter")) {
        return "WAITER";
      }
    }
    return null;
  }
  
}
