package hcmute.edu.vn.foody;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.adapter.FoodAdapter;
import hcmute.edu.vn.foody.adapter.RestaurantAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.Restaurant;

public class HomeActivity extends AppCompatActivity {
    ArrayList<Food> arrayFood;
    ArrayList<Restaurant> arrayRes;
    FoodAdapter homeAdapter;
    RestaurantAdapter resAdapter;
    ListView lsFood, lsRes;
    Button btnViewall, btnViewallRes;
    ImageView imgHome, imgFavourite, imgCart, imgInfor;
    SearchView searchViewHome;

    SQLiteDatabase mydatabase;
    Database database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_home);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        imgHome = findViewById(R.id.img_Home_HomeIcon);
        imgFavourite = findViewById(R.id.img_Home_FavouriteIcon);
        imgCart = findViewById(R.id.img_Home_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_Home_AccountIcon);


        searchViewHome = (SearchView) findViewById(R.id.sv_Home);
        lsRes = (ListView) findViewById(R.id.ls_Home_Res);
        lsFood = (ListView) findViewById(R.id.listview_Home_ListAllFood);
        btnViewall = (Button) findViewById(R.id.button_Home_ViewAll);
        btnViewallRes = (Button) findViewById(R.id.button_Home_ViewAllRes);
        arrayFood = new ArrayList<>();
        arrayRes = new ArrayList<>();

        SetDataFood();
        SetDataRes();

        homeAdapter = new FoodAdapter(this, R.layout.dong_mon_an_home, arrayFood);
        resAdapter = new RestaurantAdapter(this, R.layout.dong_restaurant, arrayRes);
        lsFood.setAdapter(homeAdapter);
        lsRes.setAdapter(resAdapter);

        btnViewall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAll(v);
            }
        });

        btnViewallRes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewAllRes(v);
            }
        });


        searchViewHome.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayFood.clear();
                Cursor c = mydatabase.rawQuery("Select * from Food where FoodName like '%"+ query+"%'", null);
                while(c.moveToNext()==true){
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des =c.getString(4);
                    arrayFood.add(new Food(id, foodname, price, img, des));
                }
                homeAdapter = new FoodAdapter(HomeActivity.this, R.layout.dong_mon_an_home, arrayFood);
                lsFood.setAdapter(homeAdapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayFood.clear();
                Cursor c = mydatabase.rawQuery("Select * from Food where FoodName like '%"+ newText+"%'", null);
                while(c.moveToNext()==true){
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des =c.getString(4);
                    arrayFood.add(new Food(id, foodname, price, img, des));
                }
                homeAdapter = new FoodAdapter(HomeActivity.this, R.layout.dong_mon_an_home, arrayFood);
                lsFood.setAdapter(homeAdapter);
                return false;
            }
        });


        lsRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Restaurant obj = (Restaurant) parent.getAdapter().getItem(position);

                int resid = obj.getResID();
                String resname = obj.getResName();
                byte[] img = obj.getResImg();
                String add = obj.getResAddress();
                double rate = 0;
                Cursor c = mydatabase.rawQuery("select * from RatingRes where ResID = ?", new String[]{String.valueOf(resid)});
                if (c.moveToFirst() == true) {
                    rate = c.getDouble(1);
                }

                Intent intent = new Intent(HomeActivity.this, RestaurantFoodActivity.class);
                intent.putExtra("resid", resid);
                intent.putExtra("resname", resname);
                intent.putExtra("resimg", img);
                intent.putExtra("resadd", add);
                intent.putExtra("res_rate", rate);

                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("homeAccid", AccID);
                } else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("homeAccid", AccID);
                }

                startActivity(intent);
            }
        });


        lsFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Food obj = (Food) parent.getAdapter().getItem(position);

                int foodid = obj.getFoodID();
                String foodname = obj.getFoodName();
                byte[] bt = obj.getFoodImg();
                double price = obj.getPrice();
                String des = obj.getFoodDescription();
                double rate = 0;
                Cursor c = mydatabase.rawQuery("select * from Rating where FoodID = ?", new String[]{String.valueOf(foodid)});
                if (c.moveToFirst() == true) {
                    rate = c.getDouble(1);
                }

                Intent intent = new Intent(HomeActivity.this, OrderActivity.class);
                intent.putExtra("foodid", foodid);
                intent.putExtra("foodname", foodname);
                intent.putExtra("foodimg", bt);
                intent.putExtra("foodprice", price);
                intent.putExtra("description", des);
                intent.putExtra("rate", rate);

                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("homeAccid", AccID);
                } else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("homeAccid", AccID);
                }

                startActivity(intent);
            }
        });


        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("homeAccid", AccID);
                } else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("homeAccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("homeAccid", AccID);
                } else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("homeAccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("homeAccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("homeAccid", AccID);
                } else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("homeAccid", AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetDataFood() {
        Cursor c = mydatabase.rawQuery("Select * from Food ", null);
        while (c.moveToNext() == true) {
            int id = c.getInt(0);
            String name = c.getString(1);
            double price = c.getDouble(2);
            byte[] img = c.getBlob(3);
            String des = c.getString(4);
            arrayFood.add(new Food(id, name, price, img, des));
        }

    }

    private void ViewAll(View view) {
        Intent intent = new Intent(HomeActivity.this, ViewAllActivity.class);
        if (getIntent().getExtras().getInt("accid") != 0) {
            int AccID = getIntent().getExtras().getInt("accid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("faaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("faaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("orderaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("viewallaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("cusaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("resaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("resaccid");
            intent.putExtra("homeAccid", AccID);
        } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
            int AccID = getIntent().getExtras().getInt("resfoodaccid");
            intent.putExtra("homeAccid", AccID);
        } else{
            int AccID = getIntent().getExtras().getInt("cartaccid");
            intent.putExtra("homeAccid", AccID);
        }
        startActivity(intent);
    }

    private void ViewAllRes(View view) {
        Intent intent = new Intent(HomeActivity.this, RestaurantActivity.class);
        if (getIntent().getExtras().getInt("accid") != 0) {
            int AccID = getIntent().getExtras().getInt("accid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("faaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("faaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("orderaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("viewallaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("cusaccid");
            intent.putExtra("homeAccid", AccID);
        } else if (getIntent().getExtras().getInt("resaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("resaccid");
            intent.putExtra("homeAccid", AccID);
        } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
            int AccID = getIntent().getExtras().getInt("resfoodaccid");
            intent.putExtra("homeAccid", AccID);
        } else{
            int AccID = getIntent().getExtras().getInt("cartaccid");
            intent.putExtra("homeAccid", AccID);
        }
        startActivity(intent);
    }

    private void SetDataRes() {
        Cursor c = mydatabase.rawQuery("Select * from Restaurant ", null);
        while (c.moveToNext() == true) {
            int id = c.getInt(0);
            String resname = c.getString(1);
            byte[] img = c.getBlob(2);
            String add = c.getString(3);
            arrayRes.add(new Restaurant(id, resname, img, add));
        }

    }


}
