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
        .createTable("Customers(cust_id int primary key, password varchar(20), tableNumber int)");
    database.createTable(
        "Menu(dish varChar(100) primary key, price numeric(4, 2), allergens varchar(500), calories int, type varchar(100))");
    database.createTable(
        "Orders(orderID int primary key, cust_id int, total_price float, orderTime Timestamp, dish varchar(100), status varchar(100), foreign key (cust_id) references Customers(cust_id) on delete cascade)");
    database
        .createTable("Staff(staff_id int primary key, password varchar(100), role varchar(20))");
    database.createTable(
        "Issues(issue_id int primary key, issue_note varchar(200), cust_id int, foreign key (cust_id) references Customers(cust_id) on delete cascade)");

    database.importFile("Menu", "Menu");
    database.importFile("Customers", "Customers");
    database.importFile("Authentication", "Staff");
    database.importFile("Orders", "Orders");

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
}
