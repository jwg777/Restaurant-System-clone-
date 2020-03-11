package connection;

/**
 * Class to manage accessing server, include reading and writing.
 * 
 * @author Chak
 */
final public class ServerAccess {

  /**
   * Singleton instance of ServerAccess class.
   */
  private static ServerAccess instance = null;

   
  private ServerAccess() {}

  /**
   * Gets the instance of ServerAccess.
   * 
   * @return instance
   */
  public ServerAccess getInstance() {
    if (instance == null) {
      instance = new ServerAccess();
    }
    return instance;
  }
  
  
}
