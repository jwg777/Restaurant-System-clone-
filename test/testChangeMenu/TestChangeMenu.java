package testChangeMenu;
/** Testing for the AddDeleteViewController
 * 
 * @author : TeamProject 2020 group 22
 * 
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import database_cafe.DataInteract;
import views.AddDeleteViewController;
import views.WaiterViewController;

class TestChangeMenu {
  
  static DataInteract database;
  static AddDeleteViewController advCont;
  static WaiterViewController wvCont;
  
  /** Set up the database and controllers beforehand.
   * 
   * @throws Exception thrown if controllers or database can not be instantiated.
   * 
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception {
    database = DataInteract.getInstance();
    advCont = new AddDeleteViewController();
    wvCont = new WaiterViewController();
  }

  /** Test that items can be added to the menu.
   * 
   */
  @Test
  void testAddItem() {
    
  }
  
  /** Test that items can be deleted from the menu.
   * 
   */
  @Test
  void testDeleteItem() {
    fail("Not yet implemented");
  }

}
