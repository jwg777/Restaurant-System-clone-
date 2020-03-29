package server;

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

  public Customer(int id, int table_number, float totalPrice, String notes) {
    this.id = id;
    this.table_number = table_number;
    this.totalPrice = totalPrice;
    this.notes = notes;
    this.paid = false;
  }

  public int getId() {
    return id;
  }

  public int getTable_number() {
    return table_number;
  }

  public float getTotalPrice() {
    return totalPrice;
  }

  public String getNotes() {
    return notes;
  }

  public boolean havePaid() {
    return paid;
  }

  public void setId(int id) {
    this.id = id;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public void setPaid(boolean paid) {
    this.paid = paid;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Customer other = (Customer) obj;
    if (table_number != other.table_number)
      return false;
    return true;
  }


}
