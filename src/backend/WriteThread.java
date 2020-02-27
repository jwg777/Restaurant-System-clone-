package backend;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Write Thread to write to server at real time.
 * 
 * @author Chak
 */
final public class WriteThread {

  /**
   * Singleton instance of WriteThread Class.
   */
  private static WriteThread instance = null;
  /**
   * Output stream to write to Server
   */
  private DataOutputStream output;

  /**
   * Private constructor for singleton class.
   */
  private WriteThread() {}

  /**
   * Gets the instance of the class.
   * 
   * @return instance.
   */
  public static WriteThread getInstance() {
    if (instance == null) {
      instance = new WriteThread();
    }
    return instance;
  }

  /**
   * Sets the output stream.
   * 
   * @param output
   */
  public void setOutput(DataOutputStream output) {
    this.output = output;
  }

  public void write(String string) throws IOException {
    output.writeUTF(string);
    output.flush();
  }

  /**
   * Closes the output stream.
   * 
   * @throws IOException
   */
  public void close() throws IOException {
    output.close();
  }
}
