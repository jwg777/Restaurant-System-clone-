package oaxacaServer;

/**
 * The listener interface for receiving client events. The class that is interested in processing a
 * client event implements this interface, and the object created with that class is registered with
 * a component using the component's <code>addClientListener<code> method. When the client event
 * occurs, that object's appropriate method is invoked.
 *
 * @see ClientEvent
 * 
 * @author Chak
 *
 */
public interface ClientListener {
  /**
   * method called when client joins or leave the server.
   */
  public void onClientChange();
}
