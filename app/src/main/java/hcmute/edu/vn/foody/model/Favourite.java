package hcmute.edu.vn.foody.model;

public class Favourite {
    private int FavouriteID;
    private int FoodID;

    public Favourite(int favouriteID, int foodID) {
        FavouriteID = favouriteID;
        FoodID = foodID;
    }

    public int getFavouriteID() {
        return FavouriteID;
    }

    public void setFavouriteID(int favouriteID) {
        FavouriteID = favouriteID;
    }

    public int getFoodID() {
        return FoodID;
    }

    public void setFoodID(int foodID) {
        FoodID = foodID;
    }
}
