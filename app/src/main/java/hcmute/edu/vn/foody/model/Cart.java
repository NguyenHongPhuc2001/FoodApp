package hcmute.edu.vn.foody.model;

public class Cart {
    private  Integer cartID;
    private Integer foodID;
    private Integer quantity;

    public Cart(Integer cartID, Integer foodID, Integer quantity) {
        this.foodID = foodID;
        this.quantity = quantity;
        this.cartID = cartID;
    }
    public Integer getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getCartID() {
        return cartID;
    }

    public void setCartID(Integer cartID) {
        this.cartID = cartID;
    }
}
