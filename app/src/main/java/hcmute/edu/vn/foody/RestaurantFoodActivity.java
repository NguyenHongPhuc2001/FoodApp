package hcmute.edu.vn.foody;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.adapter.RestaurantFoodAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;

public class RestaurantFoodActivity extends AppCompatActivity {
    ArrayList<Food> arrayFood;
    ListView lsFoodRes;
    RestaurantFoodAdapter adapter;
    TextView txtResName, txtResAdd, txtRate;
    ImageView imgRes, imgHome, imgFavourite, imgCart, imgInfor;
    Button btnBack;

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_restaurant_food);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int resid = getIntent().getExtras().getInt("resid");


        imgHome = findViewById(R.id.img_RestaurantFood_HomeIcon);
        imgFavourite = findViewById(R.id.img_RestaurantFood_FavouriteIcon);
        imgCart = findViewById(R.id.img_RestaurantFood_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_RestaurantFood_AccountIcon);
        btnBack = findViewById(R.id.button_RestaurantFood_Back);
        lsFoodRes = findViewById(R.id.lsv_RestaurantFood_FoodList);

        txtResName = (TextView) findViewById(R.id.textview_RestaurantFood_ResName);
        txtResAdd = (TextView) findViewById(R.id.textview_RestaurantFood_ResAdd);
        txtRate = (TextView) findViewById(R.id.textview_RestaurantFood_StarNB);
        imgRes = (ImageView) findViewById(R.id.img_RestauratnFood_ImageRes);


        txtResName.setText(getIntent().getExtras().getString("resname"));
        txtResAdd.setText(getIntent().getExtras().getString("resadd"));
        txtRate.setText(String.valueOf(getIntent().getExtras().getDouble("res_rate")));

        byte[] img = getIntent().getExtras().getByteArray("resimg");
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgRes.setImageBitmap(bmp);

        arrayFood = new ArrayList<>();
        SetData(resid);
        adapter = new RestaurantFoodAdapter(this, R.layout.dong_mon_an_viewall, arrayFood);
        lsFoodRes.setAdapter(adapter);


        lsFoodRes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                Intent intent = new Intent(RestaurantFoodActivity.this, OrderActivity.class);
                intent.putExtra("foodid", foodid);
                intent.putExtra("foodname", foodname);
                intent.putExtra("foodimg", bt);
                intent.putExtra("foodprice", price);
                intent.putExtra("description", des);
                intent.putExtra("rate", rate);

                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }

                startActivity(intent);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantFoodActivity.this, RestaurantActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantFoodActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantFoodActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantFoodActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantFoodActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resfoodaccid", AccID);
                } else if(getIntent().getExtras().getInt("homeAccid")!=0){
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resfoodaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resfoodaccid", AccID);
                }
                startActivity(intent);
            }
        });

    }

    private void SetData(int resid) {
        Cursor c = mydatabase.rawQuery("Select Food.FoodID,FoodName,Price, FoodImg, FoodDescription from FoodRes, Food, Rating where ResID = ? and " +
                "FoodRes.FoodID = Food.FoodID = Rating.FoodID ", new String[]{String.valueOf(resid)});
        while (c.moveToNext() == true) {
            int foodid = c.getInt(0);
            String foodname = c.getString(1);
            double price = c.getDouble(2);
            byte[] img = c.getBlob(3);
            String des = c.getString(4);
            arrayFood.add(new Food(foodid, foodname, price, img, des));
        }
    }
}
