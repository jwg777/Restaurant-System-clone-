package testorders;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import consumable.Consumable;
import order.Order;

// TODO: Auto-generated Javadoc
/**
 * The Class TestOrder.
 */
public class TestOrder {
  
  /** The items. */
  private List<Consumable> items;
  
  /** The order. */
  Order order;
  
  /**
   * Sets the up.
   */
  @Before
  public void setUp() {
    items = new ArrayList<>();
    items.add(new Consumable("drink", 3f));
    items.add(new Consumable("starter", 4.70f));
    items.add(new Consumable("main", 8.95f));
    items.add(new Consumable("side", 3.20f));
    items.add(new Consumable("dessert", 4.80f));
    order = new Order(00001, 1337, items);
  }

  /**
   * Test constructor.
   */
  @Test
  public void testConstructor() {
    float total = order.getTotalPrice();
    assertEquals("Failed to get order ID", 00001, order.getOrderID());
    assertEquals("Failed to get customer ID", 1337, order.getCustID());
    assertEquals("Falied to get items in order", items, order.getItems());
    assertEquals("Failed to get total price of order", total, 24.65f, 0.001);
  }
  
  /**
   * Test add and remove.
   */
  @Test
  public void testAddAndRemove() {
    Consumable special = new Consumable("special");
    order.addItem(special);
    assertTrue("Special item not added to order", order.getItems().contains(special));
    order.removeItem(special);
    assertFalse("Special item not removed from order", order.getItems().contains(special));
  }
  
  /**
   * Test status setters.
   */
  @Test
  public void testStatusSetters() {
    order.setNew();
    assertTrue("Order status is not new", order.isNew());
    assertFalse("Order status is in progress", order.isInProgress());
    assertFalse("Order status is completed", order.isCompleted());
    
    order.setInProgress();
    assertTrue(order.isInProgress());
    assertFalse(order.isNew());
    assertFalse(order.isCompleted());
    
    order.setCompleted();
    assertTrue(order.isCompleted());
    assertFalse(order.isNew());
    assertFalse(order.isInProgress());
  }
}
