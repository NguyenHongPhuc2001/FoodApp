package hcmute.edu.vn.foody.model;

public class FoodRes {
    private int FoodResID;
    private int FoodID;
    private int ResID;

    public FoodRes(int foodResID, int foodID, int resID) {
        FoodResID = foodResID;
        FoodID = foodID;
        ResID = resID;
    }

    public int getFoodResID() {
        return FoodResID;
    }

    public void setFoodResID(int foodResID) {
        FoodResID = foodResID;
    }

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }

    public int getResID() {
        return ResID;
    }

    public void setResID(int resID) {
        ResID = resID;
    }
}
