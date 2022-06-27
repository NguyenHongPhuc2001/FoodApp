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

public class RestaurantActivity extends AppCompatActivity {
    ArrayList<Restaurant> arrayRes;
    ListView lsRes;
    RestaurantAdapter adapter;
    Button btnBack;
    ImageView imgHome, imgFavourite, imgCart, imgInfor;
    SearchView searchview;


    Database database;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_restaurant);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgHome = findViewById(R.id.img_Restaurant_HomeIcon);
        imgFavourite = findViewById(R.id.img_Restaurant_FavouriteIcon);
        imgCart = findViewById(R.id.img_Restaurant_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_Restaurant_AccountIcon);

        searchview = findViewById(R.id.searchview_Restaurant);
        btnBack = findViewById(R.id.button_Restaurant_Back);
        lsRes = findViewById(R.id.lsv_Restaurant_ListRestaurant);
        arrayRes = new ArrayList<>();
        SetData();
        adapter = new RestaurantAdapter(this, R.layout.dong_restaurant, arrayRes);
        lsRes.setAdapter(adapter);


        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayRes.clear();
                Cursor c = mydatabase.rawQuery("Select * from Food where FoodName like '%" + query + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String resname = c.getString(1);
                    byte[] img = c.getBlob(2);
                    String address = c.getString(3);
                    arrayRes.add(new Restaurant(id, resname, img, address));
                }
                adapter = new RestaurantAdapter(RestaurantActivity.this, R.layout.dong_restaurant, arrayRes);
                lsRes.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayRes.clear();
                Cursor c = mydatabase.rawQuery("Select * from Restaurant where ResName like '%" + newText + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String resname = c.getString(1);
                    byte[] img = c.getBlob(2);
                    String address = c.getString(3);
                    arrayRes.add(new Restaurant(id, resname, img, address));
                }
                adapter = new RestaurantAdapter(RestaurantActivity.this, R.layout.dong_restaurant, arrayRes);
                lsRes.setAdapter(adapter);
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

                Intent intent = new Intent(RestaurantActivity.this, RestaurantFoodActivity.class);
                intent.putExtra("resid", resid);
                intent.putExtra("resname", resname);
                intent.putExtra("resimg", img);
                intent.putExtra("resadd", add);
                intent.putExtra("res_rate", rate);

                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }

                startActivity(intent);
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }
                startActivity(intent);
            }
        });


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestaurantActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("resaccid", AccID);
                } else if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("resaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("resaccid", AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetData() {
        Cursor c = mydatabase.rawQuery("select * from Restaurant", null);
        while (c.moveToNext() == true) {
            int id = c.getInt(0);
            String name = c.getString(1);
            byte[] img = c.getBlob(2);
            String add = c.getString(3);
            arrayRes.add(new Restaurant(id, name, img, add));
        }
    }
}
