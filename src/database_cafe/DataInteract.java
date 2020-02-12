package database_cafe;

import java.sql.ResultSet;

public final class DataInteract {
  
  private static DataInteract instance;
  Database database;
  
  private DataInteract() {
    
    System.out.println("Database connection flag...");
    
    database = new Database();
    database.createTable("Customers(cust_id int primary key, password varchar(20), tableNumber int)");
    database.createTable("Menu(dish varChar(100) primary key, price numeric(4, 2), allergens varchar(500), calories int, type varchar(50))");
    database.createTable("Orders(orderID int primary key, cust_id int, total_price float, orderTime Timestamp, dish varchar(100), status varchar(100), foreign key (cust_id) references Customers(cust_id) on delete cascade,"
            + " foreign key (dish) references Menu(dish) on delete cascade)");
    database.createTable("Staff(staff_id int primary key, password varchar(100), role varchar(20))");
    database.createTable("Issues(issue_id int primary key, issue_note varchar(200), cust_id int, foreign key (cust_id) references Customers(cust_id) on delete cascade)");
    
    database.importFile("Menu");
  }
  
  public static DataInteract getInstance() {
    if (instance == null) {
      instance = new DataInteract();
    }
    return instance;
  }
  
  public ResultSet select(String query) {
    return database.select(query);
  }
  
  public void insertIntoTable(String tableName, String attributes, String values) {
    database.insertIntoTable(tableName, attributes, values);
  }
  
  public void executeDelete(String query) {
    database.execute(query);
  }
}