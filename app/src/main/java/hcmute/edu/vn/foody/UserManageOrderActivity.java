package hcmute.edu.vn.foody;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.adapter.UserManageOrderAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Food;
import hcmute.edu.vn.foody.model.OrderDetail;
import hcmute.edu.vn.foody.model.Orders;

public class UserManageOrderActivity extends AppCompatActivity {
    ArrayList<Orders> arrayOrder;
    ArrayList<OrderDetail> arrayOrderDetail;
    ArrayList<Food> arrayFood;
    UserManageOrderAdapter adapter;
    ListView lsOrder;
    ImageView imgHome, imgFavourite, imgCart, imgInfor;
    Button btnBack;


    Database database;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_manageorder);


        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        int AccID = getIntent().getExtras().getInt("cusaccid");


        btnBack = findViewById(R.id.button_UserOrder_Back);

        lsOrder = (ListView) findViewById(R.id.lsv_User_ManageOrder);
        arrayOrder = new ArrayList<>();
        arrayFood = new ArrayList<>();
        arrayOrderDetail = new ArrayList<>();
        adapter = new UserManageOrderAdapter(this, R.layout.dong_order_user, arrayOrder,arrayOrderDetail,arrayFood, AccID);
        lsOrder.setAdapter(adapter);
        SetData();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManageOrderActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("morderaccid", AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetData() {
        int accid = getIntent().getExtras().getInt("cusaccid");
        Cursor c = mydatabase.rawQuery("" +
                "Select  * from Orders, OrderDetail, Food " +
                "where CusID = ? and Orders.OrderID = OrderDetail.OrderID and OrderDetail.FoodID = Food.FoodID;",
                new String[]{String.valueOf(accid)});
        while (c.moveToNext() == true) {
            int orderid = c.getInt(0);
            int cusid = c.getInt(1);
            double total = c.getDouble(2);
            String date = c.getString(3);
            int status = c.getInt(4);
            int odetailid = c.getInt(5);
            int orderdetailOrderid = c.getInt(6);
            int foodid = c.getInt(7);
            int amount = c.getInt(8);
            double totalamount = c.getDouble(9);
            int foodmainid = c.getInt(10);
            String foodname = c.getString(11);
            double price = c.getDouble(12);
            byte[] foodimg = c.getBlob(13);
            String fooddes = c.getString(14);
            arrayFood.add(new Food(foodmainid,foodname,price,foodimg,fooddes));
            arrayOrderDetail.add(new OrderDetail(odetailid,orderdetailOrderid,foodid,amount,totalamount));
            arrayOrder.add(new Orders(orderid, cusid, total, date, status));


        }
    }
}
