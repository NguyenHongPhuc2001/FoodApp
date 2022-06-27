package hcmute.edu.vn.foody;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Customer;

public class UserManageInfoActivity extends AppCompatActivity {
    TextView txtName, txtAge, txtPhoneNB;
    ImageView imgUser;
    Button btnBack;
    Customer customer;

    Database database;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_information);


        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (getIntent().getExtras().getInt("cusaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("cusaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("homeaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("homeaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("orderaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("viewallaccid");
            SetInfo(AccID);
        } else {
            int AccID = getIntent().getExtras().getInt("faaccid");
            SetInfo(AccID);
        }


        txtAge = findViewById(R.id.texview_UserInfo_Age);
        txtName = findViewById(R.id.texview_UserInfo_Name);
        txtPhoneNB = findViewById(R.id.texview_UserInfo_PhoneNB);
        imgUser = findViewById(R.id.img_UserInfo);
        btnBack = findViewById(R.id.button_UserInfo_Back);


        txtName.setText(customer.getCusName());
        txtAge.setText(String.valueOf(customer.getCusAge()));
        txtPhoneNB.setText(customer.getCusPhoneNB());
        byte[] img = customer.getCusImg();
        Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgUser.setImageBitmap(bmp);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserManageInfoActivity.this, CustomerActivity.class);
                if (getIntent().getExtras().getInt("cusaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("cusaccid");
                    intent.putExtra("minfoaccid",AccID);
                }
                startActivity(intent);
            }
        });


    }

    private void SetInfo(int AccID) {
        Cursor c = mydatabase.rawQuery("select * from Customer where AccID = ?", new String[]{String.valueOf(AccID)});
        if (c.moveToFirst() == true) {
            int id = c.getInt(0);
            int accid = c.getInt(1);
            String name = c.getString(2);
            String phone = c.getString(4);
            int age = c.getInt(3);
            byte[] img = c.getBlob(5);
            customer = new Customer(id, accid, name, age, phone, img);
        }
    }

}
