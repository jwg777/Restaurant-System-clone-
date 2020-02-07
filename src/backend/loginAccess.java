package backend;

import database_cafe.DataInteract;

public class loginAccess {

  DataInteract loginData;

  public loginAccess() {
    loginData = DataInteract.getInstance();
  }

  public boolean checkUser(String username, String password) {
    return false;
    // query databse to check if contained within, if yes return true else false
  }

  public void allowLogin() {
    // if checkUser is True, check whether kitchen or Waiter staff and change view accordingly
  }
}
