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

import hcmute.edu.vn.foody.adapter.ViewallAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.Rating;

public class ViewAllActivity extends AppCompatActivity {
    ArrayList<Food> arrayFood;
    ViewallAdapter adapter;
    ListView lsViewall;
    Button btnAddtocart, btnBack;
    ArrayList<Rating> arrayRate;
    ImageView imgHome, imgFavourite, imgCart, imgInfor;
    SearchView searchView;

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_viewall);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        searchView = (SearchView) findViewById(R.id.searchview_Viewall);
        btnBack = findViewById(R.id.img_ViewAll_Back);
        imgHome = findViewById(R.id.img_Viewall_HomeIcon);
        imgFavourite = findViewById(R.id.img_Viewall_FavouriteIcon);
        imgCart = findViewById(R.id.img_Viewall_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_Viewall_AccountIcon);


        lsViewall = (ListView) findViewById(R.id.listview_ViewAll_ListFood);
        arrayFood = new ArrayList<>();
        arrayRate = new ArrayList<>();
        SetData();

        adapter = new ViewallAdapter(this, R.layout.dong_mon_an_viewall, arrayFood, arrayRate);
        lsViewall.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayFood.clear();
                arrayRate.clear();
                Cursor c = mydatabase.rawQuery("Select * from Food where FoodName like '%" + query + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des = c.getString(4);
                    Cursor c_rate = mydatabase.rawQuery("Select * from Rating where FoodID = ?", new String[]{String.valueOf(id)});
                    if (c_rate.moveToFirst() == true) {
                        int rid = c.getInt(0);
                        double rate = c.getDouble(1);
                        int rfid = c.getInt(2);
                        arrayRate.add(new Rating(rid, rate, rfid));
                    }
                    arrayFood.add(new Food(id, foodname, price, img, des));
                }
                adapter = new ViewallAdapter(ViewAllActivity.this, R.layout.dong_mon_an_viewall, arrayFood, arrayRate);
                lsViewall.setAdapter(adapter);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayFood.clear();
                arrayRate.clear();
                Cursor c = mydatabase.rawQuery("Select * from Food where FoodName like '%" + newText + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des = c.getString(4);
                    Cursor c_rate = mydatabase.rawQuery("Select * from Rating where FoodID = ?", new String[]{String.valueOf(id)});
                    if (c_rate.moveToFirst() == true) {
                        int rid = c.getInt(0);
                        double rate = c.getDouble(1);
                        int rfid = c.getInt(2);
                        arrayRate.add(new Rating(rid, rate, rfid));
                    }
                    arrayFood.add(new Food(id, foodname, price, img, des));
                }
                adapter = new ViewallAdapter(ViewAllActivity.this, R.layout.dong_mon_an_viewall, arrayFood, arrayRate);
                lsViewall.setAdapter(adapter);
                return false;
            }
        });


        lsViewall.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

                Intent intent = new Intent(ViewAllActivity.this, OrderActivity.class);
                intent.putExtra("foodid", foodid);
                intent.putExtra("foodname", foodname);
                intent.putExtra("foodimg", bt);
                intent.putExtra("foodprice", price);
                intent.putExtra("description", des);
                intent.putExtra("rate", rate);

                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("viewallaccid", AccID);
                }

                startActivity(intent);
            }
        });


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("viewallaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("viewallaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("viewallaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewAllActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("viewallaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("viewallaccid", AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetData() {
        Cursor c = mydatabase.rawQuery("Select * from Food ", null);
        while (c.moveToNext() == true) {
            int id = c.getInt(0);
            String name = c.getString(1);
            double price = c.getDouble(2);
            byte[] img = c.getBlob(3);
            String des = c.getString(4);
            arrayFood.add(new Food(id, name, price, img, des));
            Cursor c_rate = mydatabase.rawQuery("Select * from Rating where FoodID = ? ",
                    new String[]{String.valueOf(id)});
            if (c_rate.moveToFirst() == true) {
                int r_id = c_rate.getInt(0);
                double nbstar = c_rate.getDouble(1);
                int foodid = c_rate.getInt(2);
                arrayRate.add(new Rating(r_id, nbstar, foodid));
            }
        }
    }


}
