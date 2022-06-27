package hcmute.edu.vn.foody.model;

import android.text.format.DateFormat;

import java.sql.Date;

public class Orders {
    private int OrderID;
    private int CusID;
    private double TotalOrder;
    private String DateOrder;
    private int Status;

    public Orders(int orderID, int cusID, double totalOrder, String dateOrder, int status) {
        OrderID = orderID;
        CusID = cusID;
        TotalOrder = totalOrder;
        DateOrder = dateOrder;
        Status = status;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getCusID() {
        return CusID;
    }

    public void setCusID(int cusID) {
        CusID = cusID;
    }

    public double getTotalOrder() {
        return TotalOrder;
    }

    public void setTotalOrder(double totalOrder) {
        TotalOrder = totalOrder;
    }

    public String getDateOrder() {
        return DateOrder;
    }

    public void setDateOrder(String dateOrder) {
        DateOrder = dateOrder;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
