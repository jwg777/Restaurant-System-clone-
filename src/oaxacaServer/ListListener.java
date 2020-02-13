package oaxacaServer;

/**
  * The listener interface for receiving list events. The class that is interested in processing a
 * list event implements this interface, and the object created with that class is registered with a
 * component using the component's <code>addListListener<code> method. When the list event occurs,
 * that object's appropriate method is invoked.
 *
 * @see ListEvent
 *
 * @author chak
 *
 */
public interface ListListener {
  /**
   * method when list is changed.
   */
  public void onListChange();
}
