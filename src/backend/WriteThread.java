package backend;

final public class WriteThread extends Thread {

  private static WriteThread instance = null;

  private WriteThread() {}

  public WriteThread getInstance() {
    if (instance == null) {
      instance = new WriteThread();
    }
    return instance;
  }

  public void run() {

  }
}
