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

public class CustomerActivity extends AppCompatActivity {
    Button btnInfor, btnMangeOrder, btnLogout;
    ImageView imgHome, imgFavourite, imgCart, imgCus;
    TextView txtName;


    SQLiteDatabase mydatabase;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_user);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        imgHome = findViewById(R.id.img_Customer_HomeIcon);
        imgFavourite = findViewById(R.id.img_Customer_FavouriteIcon);
        imgCart = findViewById(R.id.img_Customer_ShopingCartIcon);
        imgCus = findViewById(R.id.img_Account_Image);
        txtName = findViewById(R.id.textview_Account_UserName);

        btnInfor = (Button) findViewById(R.id.button_Cus_Infor);
        btnMangeOrder = (Button) findViewById(R.id.button_Cus_OrderMange);
        btnLogout = findViewById(R.id.button_User_Logout);

        if (getIntent().getExtras().getInt("homeAccid") != 0) {
            int AccID = getIntent().getExtras().getInt("homeAccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("faaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("faaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("orderaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("viewallaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("minfoaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("morderaccid");
            SetInfo(AccID);
        } else if (getIntent().getExtras().getInt("resaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("resaccid");
            SetInfo(AccID);
        } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
            int AccID = getIntent().getExtras().getInt("resfoodaccid");
            SetInfo(AccID);
        }else{
            int AccID = getIntent().getExtras().getInt("cartaccid");
            SetInfo(AccID);
        }


        btnMangeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MangeOrder();
            }
        });
        imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, AddToCart.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("minfoaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("morderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cusaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("cusaccid", AccID);
                }
                startActivity(intent);
            }
        });
        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, UserManageInfoActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("minfoaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("morderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cusaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("cusaccid", AccID);
                }
                startActivity(intent);
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, HomeActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("minfoaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("morderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cusaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("cusaccid", AccID);
                }
                startActivity(intent);
            }
        });

        imgFavourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerActivity.this, FavouriteActivity.class);
                if (getIntent().getExtras().getInt("homeAccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("homeAccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("faaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("faaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("orderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("viewallaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("minfoaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("morderaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if (getIntent().getExtras().getInt("resaccid") != 0) {
                    int AccID = getIntent().getExtras().getInt("resaccid");
                    intent.putExtra("cusaccid", AccID);
                } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
                    int AccID = getIntent().getExtras().getInt("resfoodaccid");
                    intent.putExtra("cusaccid", AccID);
                }else{
                    int AccID = getIntent().getExtras().getInt("cartaccid");
                    intent.putExtra("cusaccid", AccID);
                }
                startActivity(intent);
            }
        });


    }


    private void MangeOrder() {
        Intent intent = new Intent(CustomerActivity.this, UserManageOrderActivity.class);
        if (getIntent().getExtras().getInt("homeAccid") != 0) {
            int AccID = getIntent().getExtras().getInt("homeAccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("faaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("faaccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("orderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("orderaccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("viewallaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("viewallaccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("minfoaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("minfoaccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("morderaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("morderaccid");
            intent.putExtra("cusaccid", AccID);
        } else if (getIntent().getExtras().getInt("resaccid") != 0) {
            int AccID = getIntent().getExtras().getInt("resaccid");
            intent.putExtra("cusaccid", AccID);
        } else if(getIntent().getExtras().getInt("resfoodaccid")!=0){
            int AccID = getIntent().getExtras().getInt("resfoodaccid");
            intent.putExtra("cusaccid", AccID);
        }else{
            int AccID = getIntent().getExtras().getInt("cartaccid");
            intent.putExtra("cusaccid", AccID);
        }
        startActivity(intent);
    }

    private void SetInfo(int AccID) {
        Cursor c = mydatabase.rawQuery("select * from Customer where AccID = ?", new String[]{String.valueOf(AccID)});
        if (c.moveToFirst() == true) {
            txtName.setText(c.getString(2));
            byte[] img = c.getBlob(5);
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
            imgCus.setImageBitmap(bmp);
        }
    }

}
