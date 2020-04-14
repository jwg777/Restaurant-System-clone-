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
    System.out.println("Database connection flag...");
    database = new Database();
    database
        .createTable("Customers(cust_id int primary key, password varchar(20), tableNumber int, paid boolean)");
    database.createTable(
        "Menu(dish varChar(100) primary key, price numeric(4, 2), allergens varchar(500), calories int, type varchar(100))");
    database.createTable(
        "Orders(orderID int primary key, cust_id int, total_price float, orderTime Timestamp, dish varchar(100), status varchar(100), foreign key (cust_id) references Customers(cust_id) on delete cascade)");
    database.createTable(
        "Staff(staff_username varchar(50) primary key, password varchar(50), role varchar(20))");
    database.createTable("Messages(message varChar(500) primary key)");
    database.createTable("Reviews(name varchar(100), star_rating int, review varchar(500))");
    database.importFile("Menu", "Menu");
    database.importFile("Customers", "Customers");
    database.importFile("Authentication", "Staff");
    database.importFile("Orders", "Orders");
    database.importFile("Reviews", "Reviews");

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


