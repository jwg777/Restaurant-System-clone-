package database_cafe;

import java.sql.ResultSet;

// TODO: Auto-generated Javadoc
/**
 * The Class DataInteract.
 */
public final class DataInteract {

  /** The instance. */
  private static DataInteract instance;

  /** The database. */
  Database database;

  /**
   * Instantiates a new data interact.
   */
  private DataInteract() {
  }

  /**
   * Gets the single instance of DataInteract.
   *
   * @return single instance of DataInteract
   */
  public static DataInteract getInstance() {
    if (instance == null) {
      instance = new DataInteract();
    }
    return instance;
  }

  /**
   * Select.
   *
   * @param query the query
   * @return the result set
   */
  public ResultSet select(String query) {
    return database.select(query);

  }

  public void update(String update) {
    database.update(update);
  }

  /**
   * Insert into table.
   *
   * @param tableName the table name
   * @param attributes the attributes
   * @param values the values
   */
  public void insertIntoTable(String tableName, String attributes, String values) {
    database.insertIntoTable(tableName, attributes, values);
  }

  public void executeDelete(String query) {
    database.execute(query);
  }
}


