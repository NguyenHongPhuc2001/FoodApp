package hcmute.edu.vn.foody.model;

public class Rating {
    private int RateID;
    private double NumStar;
    private int FoodID;

    public Rating(int rateID, double numStar, int foodID) {
        RateID = rateID;
        NumStar = numStar;
        FoodID = foodID;
    }

    public int getRateID() {
        return RateID;
    }

    public void setRateID(int rateID) {
        RateID = rateID;
    }

    public double getNumStar() {
        return NumStar;
    }

    public void setNumStar(double numStar) {
        NumStar = numStar;
    }

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }

    public double getNumStar(Food food){
        if(food.getFoodID() == FoodID)
        {
            return NumStar;
        }else{
            return 0;
        }
    }
}
