package hcmute.edu.vn.foody.model;

public class Food {
    private Integer foodID;
    private String foodName;
    private double price;
    private byte[] foodImg;
    private String foodDescription;

    public Food(Integer foodID, String foodName, double price, byte[] foodImg, String foodDescription) {
        this.foodID = foodID;
        this.foodName = foodName;
        this.price = price;
        this.foodImg = foodImg;
        this.foodDescription = foodDescription;
    }

    public Integer getFoodID() {
        return foodID;
    }

    public void setFoodID(Integer foodID) {
        this.foodID = foodID;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public byte[] getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(byte[] foodImg) {
        this.foodImg = foodImg;
    }

    public String getFoodDescription() {
        return foodDescription;
    }

    public void setFoodDescription(String foodDescription) {
        this.foodDescription = foodDescription;
    }
}
