package database_cafe;

import java.sql.*;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Database {
	
	
	
	public String[] connInfoSetup() {
		String user = "", password = "";
	    System.out.println("Please enter your username");
	    Scanner scan = new Scanner(System.in);
	    user = scan.nextLine();
	    System.out.println("Please enter your password");
	    password = scan.nextLine();
	    scan.close();
	 
	    //String database = "//localhost/CS2855/";
	    String database = "//teachdb.cs.rhul.ac.uk/CS2855/";
	    
	    String[] connInfo = new String[3];
	    
	    connInfo[0] = user;
	    connInfo[1] = password;
	    connInfo[2] = database;
	    
	    return connInfo;
	    
	}
	
	public void buildTables(Connection connection) {
		createTable(connection, "Customers(cust_id int primary key, password varchar(20), tableNumber int)");
	    createTable(connection, "Menu(dish varChar(100) primary key, price float, info varchar(300), type varchar(100))");
	    createTable(connection, "Orders(orderID int primary key, cust_id int, total_price float, orderTime Timestamp, dish varchar(100), status varchar(100), foreign key (cust_id) references Customers(cust_id) on delete cascade,"
	            + " foreign key (dish) references Menu(dish) on delete cascade)");
	    createTable(connection, "Staff(staff_id int primary key, password varchar(100), role varchar(20))");
	    createTable(connection, "Issues(issue_id int primary key, issue_note varchar(200), cust_id int, foreign key (cust_id) references Customers(cust_id) on delete cascade)");
	    
	}
	
	public void importFile(Connection connection, String file0) {
		File inputFile = new File(file0);
	    String line = "";
	    BufferedReader br;
	    try {
	    	  br = new BufferedReader(new FileReader(inputFile));
	    	  line = br.readLine();
	    	  while(line != null) {
	    		String[] splitRead = line.split(">");
	    		line = "'";
	    		for(int i = 0; i < splitRead.length; i++) {
	    	      if(i == 0) {
	    		    line += splitRead[i];
	    	      } else {
	    	        line += "', '" + splitRead[i];
	    	      }
	    		}
	    		line += "'";
	    		insertIntoTable(connection, "Menu", "", line);
	    	    line = br.readLine();
	    	  }
	    	} catch (Exception e) {
	    	  e.printStackTrace();
	    	}
	}

//  public static void main(String[] args) throws SQLException {
//    //test 
//    
//   //connection test
//    Connection connection = connectToDatabase(user, password, database);
//    if(connection != null) {
//      System.out.println("Database is activated!");
//    } else {
//      System.out.println("Failed to make connection!");
//      return;
//    }
//    
//    buildTables();
//    
//    
//    //ViewMenu(connection);
//  }

  public Connection connectToDatabase(String user, String password, String database) {
    System.out.println("~~~~~~~~~~~~~~~ PostgreSQL___JDBC Connection Testing ~~~~~~~~~~~~~~~");
    Connection connection = null;
    try {
      connection = DriverManager.getConnection("jdbc:postgresql:" + database + user, user, password);
    } catch (SQLException e) {
      System.out.println("Connection failed! Check output console");
      e.printStackTrace();
    }
    return connection;
  }
 
  public void createTable(Connection connection, String tableName) {
    Statement st = null;
    String table = "";
    for (int i = 0; i < tableName.length(); i++) {
        if (tableName.charAt(i) == '(') {
            table = tableName.substring(0, i);
            break;
        }
    }
    try {
      st = connection.createStatement();
      st.execute("DROP TABLE IF EXISTS " + table + " CASCADE");
      st.execute("CREATE TABLE " + tableName);
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public void insertIntoTable(Connection connection, String tableName, String attributes, String values) {
    Statement st = null;
    try {
      st = connection.createStatement();
      if(attributes == "") {
        st.execute("INSERT INTO " + tableName + " VALUES (" + values + ");");
      } else {
        st.execute("INSERT INTO " + tableName + "(" + attributes + ") VALUES (" + values + ");");
      }
      st.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
  
  public ResultSet Select(Connection connection, String query) {
    Statement st = null;
    ResultSet rs = null;
    try {
      st = connection.createStatement();
      rs = st.executeQuery(query);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return rs;
  }
  
//  public static void ViewMenu(Connection connection) {
//    System.out.println("~~~~~~~~~~~~~~~~~Menu~~~~~~~~~~~~~~~~");
//    String query = "SELECT * " +
//                   "FROM Menu ";
//    ResultSet rs = Select(connection, query);
//    try {
//		while(rs.next()) {
//		  for(int i = 1; i < 4; i++) {
//			if(i == 2) {
//			  System.out.print("£");
//			}
//		    System.out.print(rs.getString(i) + " ");
//		  }
//		  System.out.println("");
//		}
//	} catch (SQLException e) {
//		e.printStackTrace();
//	}
//  }

}