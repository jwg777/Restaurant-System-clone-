package testbackend;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.SQLException;
import org.junit.jupiter.api.Test;
import backend.CustomerAccess;

class testCustomerAccess {
  // Note: the following are templates for using assert functions in JUnit 5, as seen from my
  // software engineering coursework
  // assertEquals(actualResult, expectedResult, "error message")



  CustomerAccess customerData = new CustomerAccess();
  String[] exampleList = {"Allergen1", "Allergen2", "Allergen3"};

  @Test
  void testGetAllergens() { // test1 - can i retrieve the allergens info of an item from the
                            // database
    try {
      String[] myAllergenList = customerData.getAllergens("Apple");
      for (int i = 0; i < exampleList.length; i++) {
        assertEquals(myAllergenList[i], exampleList[i],
            "the allergens from the database don't match the expected values");
      }

    } catch (SQLException e) {
      e.printStackTrace();
      fail("an SQL Exception occurred");
    }
  }

  @Test
  void testGetCalories() { // test2 - can I retrieve the calories of an item from the database
    try {
      String itemCalories = customerData.getCalories("Apple");
      assertEquals(itemCalories, "2000",
          "the calorie count from the database don't match the expected value");
    } catch (SQLException e) {
      e.printStackTrace();
      fail("an SQL Exception occurred");
    }
  }

}
