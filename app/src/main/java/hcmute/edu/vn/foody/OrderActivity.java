package hcmute.edu.vn.foody;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody.database.Database;

public class OrderActivity extends AppCompatActivity {
    TextView txtFoodName, txtFoodPrice, txtFoodDescription, txtRate, textViewOrderSoLuong;
    ImageView imgFood, imgHome, imgFavourite, imgCart, imgInfor;
    Button btnBack, addToCart, buttonOrderAdd, buttonOrderMinus;

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_fooddetail);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        imgHome = findViewById(R.id.img_Order_HomeIcon);
        imgFavourite = findViewById(R.id.img_Order_FavouriteIcon);
        imgCart = findViewById(R.id.img_Order_ShopingCartIcon);
        imgInfor = findViewById(R.id.img_Order_AccountIcon);

        btnBack = findViewById(R.id.img_Order_Back);
        txtFoodName = (TextView) findViewById(R.id.textview_Order_Tenmon);
        txtFoodPrice = (TextView) findViewById(R.id.textview_Order_Price);
        txtFoodDescription = (TextView) findViewById(R.id.textview_Order_Description);
        txtRate = (TextView) findViewById(R.id.textview_Order_StarNB);
        imgFood = (ImageView) findViewById(R.id.img_Order);

        addToCart = (Button) findViewById(R.id.button_Order_Addtocart);
        buttonOrderAdd = (Button) findViewById(R.id.button_Order_Add);
        buttonOrderMinus = (Button) findViewById(R.id.button_Order_Minus);
        textViewOrderSoLuong = (TextView) findViewById(R.id.textview_Order_Soluong);

        double price = (double)getIntent().getExtras().getDouble("foodprice");
        txtFoodName.setText(getIntent().getExtras().getString("foodname"));
        txtFoodPrice.setText(String.valueOf((int)getIntent().getExtras().getDouble("foodprice")) + " VNĐ");
        txtFoodDescription.setText(getIntent().getExtras().getString("description"));
        txtRate.setText(String.valueOf(getIntent().getExtras().getDouble("rate")));
        textViewOrderSoLuong.setText("1");
        byte[] img = getIntent().getExtras().getByteArray("foodimg");
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgFood.setImageBitmap(bmp);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        buttonOrderAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(textViewOrderSoLuong.getText().toString());
                soLuong++;
                textViewOrderSoLuong.setText(String.valueOf(soLuong));
            }
        });
        buttonOrderMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int soLuong = Integer.parseInt(textViewOrderSoLuong.getText().toString());
                if (soLuong > 1){
                    soLuong--;
                    textViewOrderSoLuong.setText(String.valueOf(soLuong));
                }

            }
        });

        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, AddToCart.class);
                int quantity = Integer.parseInt(textViewOrderSoLuong.getText().toString());
                int foodID = getIntent().getExtras().getInt("foodid");
                Cursor c = mydatabase.rawQuery("Select * from Cart", null);
                boolean kt= true;
                while (c.moveToNext() == true){
                    int id = c.getInt(2);
                    if (id == foodID ){
                        kt = false;
                        break;
                    }
                }
                if (kt == false) {
                    Toast.makeText(OrderActivity.this, "Sản phẩm đã có trong giỏ!", Toast.LENGTH_SHORT).show();
                }else {
                    ContentValues cv = new ContentValues();
                    cv.put("Quantity",quantity);
                    cv.put("FoodID", foodID);
                    Toast.makeText(OrderActivity.this, "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    mydatabase.insert("Cart",null,cv);
                }
            }
        });


        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if(getIntent().getExtras().getInt("cusaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("orderaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("orderaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if(getIntent().getExtras().getInt("cusaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("orderaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("orderaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if(getIntent().getExtras().getInt("cusaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("orderaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("orderaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OrderActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("orderaccid", AccID);
                } else if(getIntent().getExtras().getInt("cusaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("orderaccid", AccID);
                }else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("orderaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("orderaccid", AccID);
                }
                startActivity(intent);
            }
        });

    }
}
