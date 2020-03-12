package server;



/**
 * Enum type of clients joining the server.
 * 
 * @author Chak
 *
 */
public enum ClientType {
  /**
   * Custoemr type.
   */
  CUSTOMER,
  /**
   * Waiter type.
   */
  WAITER,
  /**
   * Kitchen type.
   */
  KITCHEN,
  /**
   * Invalid type.
   */
  INVALID;
  
  
  public static ClientType getType(String string) {
    for (ClientType type : ClientType.values()) {
      if (type.toString().equals(string)) {
        return type;
      }
    }
    return INVALID;
  }

}
