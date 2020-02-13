package oaxacaServer;

/**
 * interface for client listener
 * @author Chak
 *
 */
public interface ClientListener {
  /**
   * method called when client joins or leave the server.
   */
  public void onClientChange();
}
