package oaxacaServer;


// TODO: Auto-generated Javadoc
/**
 * The Class Main.
 */
public class OaxacaServer{

  /** The server. */
  static Server server = Server.getInstance();
  
  /** CustomerData */
  static Customer customer = Customer.getInstance();


  /**
   * Main method to run the server.
   * @param args
   */
  public static void main(String[] args) {
    //server.setPort(Integer.parseInt(args[0]));
    
    server.setPort(6666);
    server.start();
  }
}
