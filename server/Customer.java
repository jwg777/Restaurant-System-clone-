public class Customer {

  private int id;
  private int table_number;
  private float totalPrice;
  private String notes;
  private boolean paid;

  public Customer(int table_number) {
    this.table_number = table_number;
    this.totalPrice = 0;
    this.paid = false;
  }
}
