import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import backend.CustomerAccess;

class testCustomerView {

  CustomerAccess customerData;
 
  //test customerData method successfully connects to database.
  @Before
  public void setUp() {
    customerData = new CustomerAccess();
  }
  @Test
  void test1() {
  }

}
