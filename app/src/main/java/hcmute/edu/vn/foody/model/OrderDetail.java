package hcmute.edu.vn.foody.model;

public class OrderDetail {
    private int ODetailID;
    private int OrderID;
    private int FoodID;
    private int Amount;
    private double TotalAmount;

    public OrderDetail(int ODetailID, int orderID, int foodID, int amount, double totalAmount) {
        this.ODetailID = ODetailID;
        OrderID = orderID;
        FoodID = foodID;
        Amount = amount;
        TotalAmount = totalAmount;
    }

    public int getODetailID() {
        return ODetailID;
    }

    public void setODetailID(int ODetailID) {
        this.ODetailID = ODetailID;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }

    public int getAmount() {
        return Amount;
    }

    public void setAmount(int amount) {
        Amount = amount;
    }

    public double getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        TotalAmount = totalAmount;
    }
}
