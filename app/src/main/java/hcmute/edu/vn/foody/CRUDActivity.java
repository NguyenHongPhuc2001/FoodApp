package hcmute.edu.vn.foody;

import android.Manifest;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;

import hcmute.edu.vn.foody.database.Database;

public class CRUDActivity extends AppCompatActivity {
    private ImageView imgaview;
    private TextView txtview;
    private EditText edtnhapid, edtnhapten;
    private Button btnInsert, btnDelete, btnFetch;

    Database database;
    SQLiteDatabase mydatabase;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insert_food_img);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);


        //Tạo database
        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        mydatabase.execSQL("create table if not exists Cart(CartID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "Quantity INTEGER,"+
//                "foreign key(FoodID) references Food(FoodID))");

//        //Database of Project
//        mydatabase.execSQL("create table if not exists Account(AccID INTEGER PRIMARY KEY AUTOINCREMENT," +
//                "UserName TEXT," +
//                "Password TEXT," +
//                "Role INTEGER default 1);");
//        mydatabase.execSQL("create table if not exists Customer(CusID INTEGER PRIMARY KEY AUTOINCREMENT, " +
//                "AccID INTEGER,"+
//                "CusName TEXT, " +
//                "CusAGE INTEGER, " +
//                "CusPhoneNB TEXT, " +
//                "CusImg BLOB," +
//                "foreign key(AccID) references Account(AccID))");
//        mydatabase.execSQL("create table if not exists Food(FoodID INTEGER  primary key AUTOINCREMENT, " +
//                "FoodName TEXT, " +
//                "Price double, " +
//                "FoodImg BLOB, " +
//                "FoodDescription TEXT)");
//        mydatabase.execSQL("create table if not exists Orders(\n" +
//                "OrderID INTEGER  primary key AUTOINCREMENT,\n" +
//                "CusID int,\n" +
//                "ToalOrder double,\n" +
//                "DateOrder TEXT,\n" +
//                "Status int default 0,\n" +
//                "foreign key(CusID) references Customer(CusID));");
//        mydatabase.execSQL("create table if not exists OrderDetail(\n" +
//                "ODetailID INTEGER  primary key AUTOINCREMENT,\n" +
//                "OrderID int,\n" +
//                "FoodID int,\n" +
//                "Amount int,\n" +
//                "TotalAmount double,\n" +
//                "foreign key(OrderID) references Orders(OrderID),\n" +
//                "foreign key(FoodID) references Food(FoodID));");
//        mydatabase.execSQL("create table if not exists Rating(\n" +
//                "RateID INTEGER  primary key AUTOINCREMENT,\n" +
//                "NB_Star double,\n" +
//                "FoodID int," +
//                "foreign key(FoodID) references Food(FoodID));");
//        mydatabase.execSQL("create table if not exists Favourite(\n" +
//                "FavouriteID INTEGER  primary key AUTOINCREMENT,\n" +
//                "FoodID int,\n" +
//                "foreign key(FoodID) references Food(FoodID));");
//        mydatabase.execSQL("create table if not exists Advertisement(AdID INTEGER primary key autoincrement," +
//                "AdImg BLOB)");
//        mydatabase.execSQL("create table if not exists Restaurant(" +
//                "ResID INTEGER primary key autoincrement," +
//                "ResName TEXT," +
//                "ResImg BLOB," +
//                "ResAddress TEXT)");
//        mydatabase.execSQL("create table if not exists FoodRes(" +
//                "FoodResID INTEGER primary key autoincrement," +
//                "FoodID int," +
//                "ResID int," +
//                "foreign key(FoodID) references Food(FoodID)," +
//                "foreign key(ResID) references Restaurant(ResID))");
//        mydatabase.execSQL("create table if not exists RatingRes (" +
//                "RatingResID INTEGER primary key autoincrement," +
//                "Res_StarNB double," +
//                "ResID int," +
//                "foreign key(ResID) references Restaurant(ResID))");


//        //1. Table Account
//        mydatabase.execSQL("insert into Account values(null,'phuc','phuc',1);");
//        mydatabase.execSQL("insert into Account values(null,'sang','sang',1);");
//        mydatabase.execSQL("insert into Account values(null,'admin','admin',0);");
//
//        //2. Table Customer.
//        mydatabase.execSQL("insert into Customer " +
//                "values (null," +
//                "1," +
//                "'Nguyễn Hồng Phúc'," +
//                " 20, " +
//                "'0398746524'," +
//                "null);");
//        mydatabase.execSQL("insert into Customer " +
//                "values (null," +
//                "2," +
//                "'Tạ Quang Sang'," +
//                " 20, " +
//                "'0384763532'," +
//                "null);");
//        //3. Table Food
//        mydatabase.execSQL("insert into Food values(null,'Phở bò',30000, null,'Protein: 40g\n" +
//                "Fat: 20g\n" +
//                "Calories: 300 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh mì chảo',52000,null,'Protein: 60g\n" +
//                "Fat: 14g\n" +
//                "Calories: 360 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh cuốn',22000,null,'Protein: 20g\n" +
//                "Fat: 10g\n" +
//                "Calories: 250 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh mì thịt nướng',16000,null,'Protein: 20g\n" +
//                "Fat: 14g\n" +
//                "Calories: 270 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Cơm tấm Phúc Lộc Thọ',26000,null,'Protein: 30g\n" +
//                "Fat: 21g\n" +
//                "Calories: 320 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh bao trứng muối',20000,null,'Protein: 20g\n" +
//                "Fat: 7g\n" +
//                "Calories: 120 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bún bò huế',35000,null,'Protein: 40g\n" +
//                "Fat: 17g\n" +
//                "Calories: 250 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Xôi bắp',15000,null,'Protein: 20g\n" +
//                "Fat: 10g\n" +
//                "Calories: 200 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Salad trứng muối',45000,null,'Protein: 25g\n" +
//                "Fat: 3g\n" +
//                "Calories: 280 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Thịt kho',53000,null,'Protein: 55g\n" +
//                "Fat: 30g\n" +
//                "Calories: 450 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Tôm rim chua ngọt',44000,null,'Protein: 25g\n" +
//                "Fat: 20g\n" +
//                "Calories: 300 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Ếch chiên nước mắm',35000,null,'Protein: 33g\n" +
//                "Fat: 22g\n" +
//                "Calories: 210 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Thịt xào chua ngọt',30000,null,'Protein: 23g\n" +
//                "Fat: 17g\n" +
//                "Calories: 245 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Canh chua cá lóc',30000,null,'Protein: 20g\n" +
//                "Fat: 11g\n" +
//                "Calories: 230 kcal')");
//        mydatabase.execSQL("\n" +
//                "insert into Food values(null,'Cá lóc chiên',32000,null,'Protein: 24g\n" +
//                "Fat: 15g\n" +
//                "Calories: 260 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Sữa chua ngũ cốc',22000,null,'Protein: 16g\n" +
//                "Fat: 8g\n" +
//                "Calories: 140 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Sữa chua xoài',20000,null,'Protein: 16g\n" +
//                "Fat: 6g\n" +
//                "Calories: 120 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Mini Gato Dâu',30000,null,'Protein: 18g\n" +
//                "Fat: 10g\n" +
//                "Calories: 190 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh Plang',10000,null,'Protein: 12g\n" +
//                "Fat: 2g\n" +
//                "Calories: 80 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Sinh tố',13000,null,'Protein: 18g\n" +
//                "Fat: 0g\n" +
//                "Calories: 50 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Trà sữa matcha',24000,null,'Protein: 18g\n" +
//                "Fat: 10g\n" +
//                "Calories: 100 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Trà sữa trân châu',22000,null,'Protein: 20g\n" +
//                "Fat: 13g\n" +
//                "Calories: 110 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Trà sữa trân châu đường đen',29000,null,'Protein: 23g\n" +
//                "Fat: 12g\n" +
//                "Calories: 120 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Hồng trà sữa',25000,null,'Protein: 22g\n" +
//                "Fat: 10g\n" +
//                "Calories: 100 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh cuốn Bà Hai',25000,null,'Protein: 29g\n" +
//                "Fat: 13g\n" +
//                "Calories: 180 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Banh khot',27000,null,'Protein: 30g\n" +
//                "Fat: 16g\n" +
//                "Calories: 200 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Gà quay Roti',260000,null,'Protein: 120g\n" +
//                "Fat: 80g\n" +
//                "Calories: 560 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Cơm tấm Hoàng Diệu',27000,null,'Protein: 40g\n" +
//                "Fat: 29g\n" +
//                "Calories: 360 kcal')");
//        mydatabase.execSQL("insert into Food values(null,'Bánh tét',45000,null,'Protein: 30g\n" +
//                "Fat: 23g\n" +
//                "Calories: 260 kcal')");


//        //4. Table Rating
//        mydatabase.execSQL("insert into Rating values (null,4.8,1)");
//        mydatabase.execSQL("insert into Rating values (null,4.5,2)");
//        mydatabase.execSQL("insert into Rating values (null,4.3,3)");
//        mydatabase.execSQL("insert into Rating values (null,4.2,4)");
//        mydatabase.execSQL("insert into Rating values (null,4.1,5)");
//        mydatabase.execSQL("insert into Rating values (null,5,6)");
//        mydatabase.execSQL("insert into Rating values (null,5,7)");
//        mydatabase.execSQL("insert into Rating values (null,4.9,8)");
//        mydatabase.execSQL("insert into Rating values (null,4.5,9)");
//        mydatabase.execSQL("insert into Rating values (null,4.4,10)");
//        mydatabase.execSQL("insert into Rating values (null,4.4,11)");
//        mydatabase.execSQL("insert into Rating values (null,4.2,12)");
//        mydatabase.execSQL("insert into Rating values (null,4.8,13)");
//        mydatabase.execSQL("insert into Rating values (null,4.7,14)");
//        mydatabase.execSQL("insert into Rating values (null,4.7,15)");
//        mydatabase.execSQL("insert into Rating values (null,4.6,16)");
//        mydatabase.execSQL("insert into Rating values (null,4.1,17)");
//        mydatabase.execSQL("insert into Rating values (null,4.2,18)");
//        mydatabase.execSQL("insert into Rating values (null,4.9,19)");
//        mydatabase.execSQL("insert into Rating values (null,5,20)");
//        mydatabase.execSQL("insert into Rating values (null,5,21)");
//        mydatabase.execSQL("insert into Rating values (null,4.7,22)");
//        mydatabase.execSQL("insert into Rating values (null,4,23)");
//        mydatabase.execSQL("insert into Rating values (null,4,24)");
//        mydatabase.execSQL("insert into Rating values (null,4.3,25)");
//        mydatabase.execSQL("insert into Rating values (null,4.2,26)");
//        mydatabase.execSQL("insert into Rating values (null,4.1,27)");
//        mydatabase.execSQL("insert into Rating values (null,4.8,28)");
//        mydatabase.execSQL("insert into Rating values (null,4.9,29)");


//        //5. Orders.
//        mydatabase.execSQL("insert into Orders values(null,1,50000,'09/05/2022',0)");
//        mydatabase.execSQL("insert into Orders values(null,2,60000,'10/05/2022',0)");
//
//
//        //6. OrderDetail.
//        mydatabase.execSQL("insert into OrderDetail values (null,1,24,1,25000)");
//        mydatabase.execSQL("insert into OrderDetail values (null,1,25,1,25000)");
//        mydatabase.execSQL("insert into OrderDetail values (null,2,13,1,30000)");
//        mydatabase.execSQL("insert into OrderDetail values (null,2,14,1,30000)");


//        //7. Advertisement.
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");
//        mydatabase.execSQL("insert into Advertisement values(null,null)");


        //8. Favourite.
//        mydatabase.execSQL("insert into Favourite values(null, 1)");
//        mydatabase.execSQL("insert into Favourite values(null, 2)");
//        mydatabase.execSQL("insert into Favourite values(null, 5)");
//        mydatabase.execSQL("insert into Favourite values(null, 3)");
//        mydatabase.execSQL("insert into Favourite values(null, 23)");
//        mydatabase.execSQL("insert into Favourite values(null, 18)");

//      9. Table Restaurant
//        mydatabase.execSQL("insert into Restaurant values(null,'Quán ăn Hoàng Diệu 2',null,'33/28, Đặng Văn Bi, Trường Thọ, Thủ Đức')");
//        mydatabase.execSQL("insert into Restaurant values(null,'Nhà hàng Việt',null,'1 Khổng Tử, Bình Thọ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam')");
//        mydatabase.execSQL("insert into Restaurant values(null,'Nhà hàng Ao Quê',null,'14 Einstein, Bình Thọ, Thành Phố Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam')");
//        mydatabase.execSQL("insert into Restaurant values(null,'Trà sữa Wings Tea',null,'16 Lê Quý Đôn, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam')");
//        mydatabase.execSQL("insert into Restaurant values(null,'Quán ăn Phố Cổ',null,'216 Đ. Võ Văn Ngân, Bình Thọ, Thủ Đức, Thành phố Hồ Chí Minh, Việt Nam')");
//
//
//        //11. Table FoodRes
//        mydatabase.execSQL("insert into FoodRes values (null, 1,1)");
//        mydatabase.execSQL("insert into FoodRes values (null, 2,1)");
//        mydatabase.execSQL("insert into FoodRes values (null, 3,1)");
//        mydatabase.execSQL("insert into FoodRes values (null, 4,1)");
//        mydatabase.execSQL("insert into FoodRes values (null, 5,1)");
//        mydatabase.execSQL("insert into FoodRes values (null, 6,1)");
//
//        mydatabase.execSQL("insert into FoodRes values (null, 7,2)");
//        mydatabase.execSQL("insert into FoodRes values (null, 8,2)");
//        mydatabase.execSQL("insert into FoodRes values (null, 9,2)");
//        mydatabase.execSQL("insert into FoodRes values (null, 10,2)");
//        mydatabase.execSQL("insert into FoodRes values (null, 11,2)");
//        mydatabase.execSQL("insert into FoodRes values (null, 12,2)");
//
//        mydatabase.execSQL("insert into FoodRes values (null, 13,3)");
//        mydatabase.execSQL("insert into FoodRes values (null, 14,3)");
//        mydatabase.execSQL("insert into FoodRes values (null, 15,3)");
//        mydatabase.execSQL("insert into FoodRes values (null, 16,3)");
//        mydatabase.execSQL("insert into FoodRes values (null, 17,3)");
//        mydatabase.execSQL("insert into FoodRes values (null, 18,3)");
//
//        mydatabase.execSQL("insert into FoodRes values (null, 19,4)");
//        mydatabase.execSQL("insert into FoodRes values (null, 20,4)");
//        mydatabase.execSQL("insert into FoodRes values (null, 21,4)");
//        mydatabase.execSQL("insert into FoodRes values (null, 22,4)");
//        mydatabase.execSQL("insert into FoodRes values (null, 23,4)");
//        mydatabase.execSQL("insert into FoodRes values (null, 24,4)");
//
//        mydatabase.execSQL("insert into FoodRes values (null, 25,5)");
//        mydatabase.execSQL("insert into FoodRes values (null, 26,5)");
//        mydatabase.execSQL("insert into FoodRes values (null, 27,5)");
//        mydatabase.execSQL("insert into FoodRes values (null, 28,5)");
//        mydatabase.execSQL("insert into FoodRes values (null, 29,5)");
//
//        // 10. Table RatingRes
//        mydatabase.execSQL("insert into RatingRes values(null,4.9,1)");
//        mydatabase.execSQL("insert into RatingRes values(null,4.7,2)");
//        mydatabase.execSQL("insert into RatingRes values(null,4.8,3)");
//        mydatabase.execSQL("insert into RatingRes values(null,4.6,4)");
//        mydatabase.execSQL("insert into RatingRes values(null,4.7,5)");




//        mydatabase.execSQL("Update Food set FoodName = 'Cơm tấm Hoàng Diệu 2' where FoodID = 5");
//        mydatabase.execSQL("Update Food set FoodName = 'Bánh cuốn Phố Cổ' where FoodID = 22");
//        mydatabase.execSQL("Update Food set FoodName = 'Cơm tấm phố cổ' where FoodID = 28");


//        Toast.makeText(this, " sucess !", Toast.LENGTH_SHORT).show();


        edtnhapten = (EditText) findViewById(R.id.edt_EnterFile);
        edtnhapid = (EditText) findViewById(R.id.edt_Id);
        imgaview = (ImageView) findViewById(R.id.imageView);
        txtview = (TextView) findViewById(R.id.txtView);
        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnFetch = (Button) findViewById(R.id.btnFetch);


        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Insert(v);
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Delete(v);
            }
        });

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fetch(v);
            }
        });


    }

    public void Insert(View view) {
        String path = Environment.getExternalStorageDirectory().getPath() +
                "/Download/" + edtnhapten.getText().toString() + ".png";

        Bitmap bmp = BitmapFactory.decodeFile(path);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);

        byte[] byteImage = byteArrayOutputStream.toByteArray();
        ContentValues cv = new ContentValues();
        cv.put("FoodImg", byteImage);

        String id = edtnhapid.getText().toString();
        mydatabase.update("Food", cv, "FoodID = ?", new String[]{id});
        txtview.setText("Update Success !");
    }

    public void Delete(View view) {
        mydatabase = database.getWritableDatabase();
        String id = edtnhapid.getText().toString();
        mydatabase.delete("Customer", "CusID = ?", new String[]{id});
        txtview.setText("Delete Success !");
    }


    public void Fetch(View view) {
        int id = Integer.parseInt(edtnhapid.getText().toString());
        String string = "Select * from Food where FoodID = " + id;

        Cursor cursor = mydatabase.rawQuery("Select * from Food where FoodID = ?", new String[]{String.valueOf(id)});
        try {
            cursor.moveToFirst();
            byte[] byteimg = cursor.getBlob(3);
            cursor.close();

            Bitmap bitmapimg = BitmapFactory.decodeByteArray(byteimg, 0, byteimg.length);

            imgaview.setImageBitmap(bitmapimg);

            txtview.setText("Fetch Success!");
        } catch (Exception e) {
            txtview.setText("Error !");
        }
    }
}
