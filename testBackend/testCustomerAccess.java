import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import backend.CustomerAccess;

class testCustomerAccess {
  // Note: the following are templates for using assert functions in JUnit 5, as seen from my
  // software engineering coursework
  // assertEquals(actualResult, expectedResult, "error message")



  @BeforeEach
  CustomerAccess customerData = new CustomerAccess();
  String[] exampleList = {"Allergen1", "Allergen2", "Allergen3"};

  void setUp() throws Exception {

  }

  @Test
  void testGetAllergens() {
    String[] myAllergenList = customerData.getAllergens("Apple");
    assertEquals(myAllergenList, exampleList,
        "the allergens from the database don't match the expected values");
  }

}
