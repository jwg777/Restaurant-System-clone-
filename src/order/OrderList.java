package order;

import consumable.Consumable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class OrderList {
  
  private static OrderList instance = null;
  
  private ObservableList<Order> orderList = FXCollections.observableArrayList();
  
  private OrderList() {
  }
  
  public OrderList getInstance() {
    if(instance == null) {
      instance = new OrderList();
    }
    return instance;
  }
  
  public void add(Consumable consumable) {
    
  }
  
  
}
