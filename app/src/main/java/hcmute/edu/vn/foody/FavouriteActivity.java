package hcmute.edu.vn.foody;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.adapter.FavouriteAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Favourite;
import hcmute.edu.vn.foody.model.Food;

public class FavouriteActivity extends AppCompatActivity {
    ArrayList<Favourite> arrayFavourite;
    ArrayList<Food> arrayFood;
    FavouriteAdapter adatper;
    ListView lsFavourite;
    ImageView imgHome, imgFavourite, imgCart, imgInfor;
    SearchView searchView;

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_favourite);


        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgHome = findViewById(R.id.img_Favourite_HomeIcon);
        imgCart = findViewById(R.id.img_Favourite_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_Favourite_AccountIcon);

        searchView = (SearchView) findViewById(R.id.searchview_Favourite)
        ;
        lsFavourite = (ListView) findViewById(R.id.lsv_Favourite);
        arrayFavourite = new ArrayList<>();
        arrayFood = new ArrayList<>();
        adatper = new FavouriteAdapter(this, R.layout.dong_mon_an_favourite, arrayFavourite, arrayFood);
        lsFavourite.setAdapter(adatper);
        SetData();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                arrayFood.clear();
                arrayFavourite.clear();
                Cursor c = mydatabase.rawQuery("" +
                        "select * from Food, Favourite " +
                        "where Food.FoodID = Favourite.FoodID and FoodName like '%" + query + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des = c.getString(4);
                    int fa_id = c.getInt(5);

                    arrayFood.add(new Food(id, foodname, price, img, des));
                    arrayFavourite.add(new Favourite(fa_id, id));
                }
                adatper = new FavouriteAdapter(FavouriteActivity.this, R.layout.dong_mon_an_favourite, arrayFavourite, arrayFood);
                lsFavourite.setAdapter(adatper);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayFood.clear();
                arrayFavourite.clear();
                Cursor c = mydatabase.rawQuery("" +
                        "select * from Food, Favourite " +
                        "where Food.FoodID = Favourite.FoodID and FoodName like '%" + newText + "%'", null);
                while (c.moveToNext() == true) {
                    int id = c.getInt(0);
                    String foodname = c.getString(1);
                    double price = c.getDouble(2);
                    byte[] img = c.getBlob(3);
                    String des = c.getString(4);
                    int fa_id = c.getInt(5);

                    arrayFood.add(new Food(id, foodname, price, img, des));
                    arrayFavourite.add(new Favourite(fa_id, id));
                }
                adatper = new FavouriteAdapter(FavouriteActivity.this, R.layout.dong_mon_an_favourite, arrayFavourite, arrayFood);
                lsFavourite.setAdapter(adatper);
                return false;
            }
        });

        lsFavourite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                Intent intent = new Intent(FavouriteActivity.this, OrderActivity.class);
                intent.putExtra("foodid", foodid);
                intent.putExtra("foodname", foodname);
                intent.putExtra("foodimg", bt);
                intent.putExtra("foodprice", price);
                intent.putExtra("description", des);
                intent.putExtra("rate", rate);

                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("faaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("faaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("faaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouriteActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("faaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("faaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("faaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouriteActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("faaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("faaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("faaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FavouriteActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("faaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("faaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("faaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("faaccid", AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetData() {
        Cursor c = mydatabase.rawQuery("Select * from Favourite ", null);
        arrayFood.clear();
        arrayFavourite.clear();
        while (c.moveToNext() == true) {
            int fid = c.getInt(0);
            int foodid = c.getInt(1);
            arrayFavourite.add(new Favourite(fid, foodid));
            Cursor c_food = mydatabase.rawQuery("select * from Food where FoodID = ?", new String[]{String.valueOf(foodid)});
            if (c_food.moveToFirst() == true) {
                int id = c_food.getInt(0);
                String name = c_food.getString(1);
                double price = c_food.getDouble(2);
                byte[] img = c_food.getBlob(3);
                String des = c_food.getString(4);
                arrayFood.add(new Food(id, name, price, img, des));
            }
        }
        adatper.notifyDataSetChanged();
    }


    public void DialogXoaFood(int id) {
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn xóa món ăn này khỏi danh sách yêu thích không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    mydatabase.execSQL("DELETE from Favourite WHERE FavouriteID = ?", new String[]{String.valueOf(id)});
                    Toast.makeText(FavouriteActivity.this, "Xóa thành công !", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(FavouriteActivity.this, "Delete: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
                SetData();
            }
        });
        dialogXoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dialogXoa.show();
    }

}
