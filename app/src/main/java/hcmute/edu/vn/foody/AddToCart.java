package hcmute.edu.vn.foody;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import hcmute.edu.vn.foody.adapter.ViewCartAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Cart;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.OrderDetail;
import hcmute.edu.vn.foody.model.Orders;

public class AddToCart extends AppCompatActivity {
    ArrayList<Orders> arrayOrder;
    ArrayList<OrderDetail> arrayOrderDetail;
    ArrayList<Food> arrayFood;
    ArrayList<Cart> carts;

    ImageView imgHome, imgFavourite, imgInfor;
    ViewCartAdapter adapter;
    ListView listView;
    TextView textCartView;
    SQLiteDatabase mydatabase;
    Button btnAddToCart;
    int idOrder;
    Database database;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_cart);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
            mydatabase = database.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        imgHome = findViewById(R.id.img_Viewall_HomeIcon);
        imgFavourite = findViewById(R.id.img_Viewall_FavouriteIcon);
        imgInfor = findViewById(R.id.img_Viewall_AccountIcon);
        listView = (ListView) findViewById(R.id.listview_cart_ListFood);
        textCartView = (TextView) findViewById(R.id.textCartView);
        btnAddToCart = (Button) findViewById(R.id.button_Cart_Pay);

        arrayFood = new ArrayList<>();
        arrayOrderDetail = new ArrayList<>();
        carts = new ArrayList<>();
        SetData();
        adapter = new ViewCartAdapter(this, R.layout.dong_cart, arrayFood, arrayOrderDetail, carts);
        listView.setAdapter(adapter);
        double totalCart = arrayOrderDetail.stream().mapToDouble(OrderDetail::getTotalAmount).sum();
        textCartView.setText("Tổng hoá đơn: " + String.valueOf((int) totalCart) + " VND");

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                int accid = 0;
                if (getIntent().getExtras().getInt("accid") != 0) {
                    accid = getIntent().getExtras().getInt("accid");
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    accid = getIntent().getExtras().getInt("faaccid");

                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    accid = getIntent().getExtras().getInt("orderaccid");

                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    accid = getIntent().getExtras().getInt("viewallaccid");

                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    accid = getIntent().getExtras().getInt("cusaccid");

                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    accid = getIntent().getExtras().getInt("resaccid");

                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    accid = getIntent().getExtras().getInt("resfoodaccid");

                } else {
                    accid = getIntent().getExtras().getInt("homeAccid");

                }
                Toast.makeText(AddToCart.this, "Đặt hàng thành công", Toast.LENGTH_SHORT).show();
                Date d = new Date();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
                String date = dateFormat.format(d);
                cv.put("CusID", accid);
                cv.put("ToalOrder", totalCart);
                cv.put("DateOrder", date);
                mydatabase.insert("Orders", null, cv);
                Cursor c = mydatabase.rawQuery("Select * from Orders", null);
                int orderID = 0;
                while (c.moveToNext() == true) {
                    orderID = c.getInt(0);
                }
                int finalOrderID = orderID;
                arrayOrderDetail.forEach(item -> {
                    ContentValues cvs = new ContentValues();
                    cvs.put("OrderID", finalOrderID);
                    cvs.put("FoodID", item.getFoodID());
                    cvs.put("Amount", item.getAmount());
                    cvs.put("TotalAmount", item.getTotalAmount());
                    mydatabase.insert("OrderDetail", null, cvs);
                });
                mydatabase.delete("Cart", "CartID", null);
            }
        });
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCart.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cartaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cartaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCart.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cartaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cartaccid", AccID);
                }
                startActivity(intent);
            }
        });
        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToCart.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("accid") != 0) {
                    int AccID = getIntent().getExtras().getInt("accid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cartaccid", AccID);
                } else if (getIntent().getExtras().getInt("resfoodaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cartaccid", AccID);
                } else {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cartaccid", AccID);
                }
                startActivity(intent);
            }
        });

    }

    private void SetData() {
        Cursor c = mydatabase.rawQuery("SELECT * FROM Cart JOIN Food USING(FoodID)", null);
        while (c.moveToNext() == true) {
            int cartID = c.getInt(0);
            int quantity = c.getInt(1);
            int id = c.getInt(2);
            String name = c.getString(3);
            double price = c.getDouble(4);
            double totalPrice = price * quantity;
            byte[] img = c.getBlob(5);
            String des = c.getString(6);
            idOrder = cartID;
            arrayFood.add(new Food(id, name, price, img, des));
            arrayOrderDetail.add(new OrderDetail(cartID, cartID, id, quantity, totalPrice));
            carts.add(new Cart(cartID, id, quantity));
        }
    }

}