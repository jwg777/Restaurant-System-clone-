package testdatabase;
/** This test class will be used to test the class Database.
 * 
 * @author : Team project group 22
 * 
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import database_cafe.DataInteract;
import database_cafe.Database;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class TestDatabase {

  static DataInteract database;

  /** Creates the database, calling the constructor for all tests to use.
   * 
   * @throws Exception thrown if connection to database is not possible
   * 
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    database = new DataInteract();
  }

  /** Test will check if tables have been created.
   * 
   */
  @Test
  void testCreate() {
    try {
      Database db = new Database();
      db.createTable("Test(test varchar(50) primary key)");
    } catch (Exception e) {
      fail("Could not create table");
    }
  }

  /** Test will check if records can be added to an existing table.
   * 
   */
  @Test
  void testInsert() {
    try {
      database.insertIntoTable("Test", "", "This is a test");
    } catch (Exception e) {
      fail("Insertion failed");
    }
  }

  /** Test will check if a select query is possible for existing tables.
   * 
   */
  @Test
  void testSelect() throws SQLException {
    ResultSet rs = database.select("SELECT * FROM Test");
    while(rs.next()) {
      assertEquals("This is a test", rs.getString(1));
    }
  }
  
  /** Test will check if records can be deleted from an existing table.
   * 
   */
  @Test
  void testDelete() {
    try {
      database.executeDelete("DELETE FROM Test WHERE test = This is a test");
      ResultSet rs = database.select("SELECT * FROM Test");
      if (rs.next()) { //rs should not have anything in it.
        fail("Result set should be empty");
      }
    } catch (SQLException e) {
      fail("DELETE Query can not be carried out");
    }
  }
}
