package hcmute.edu.vn.foody;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.adapter.AdminAdapter;
import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Customer;
import hcmute.edu.vn.foody.model.Orders;

public class AdminActivity extends AppCompatActivity {
    Button btnLogout;
    ArrayList<Orders> arrayOrder;
    ArrayList<Customer> arrayCustomer;
    ListView lsOrder;
    AdminAdapter adapter;


    Database database;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_admin);

        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        lsOrder = (ListView) findViewById(R.id.lsv_admin);
        btnLogout = (Button) findViewById(R.id.button_Admin_Logout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout(v);
            }
        });


        arrayOrder = new ArrayList<>();
        arrayCustomer = new ArrayList<>();

        adapter = new AdminAdapter(this, R.layout.dong_admin_manageorder, arrayOrder, arrayCustomer);
        lsOrder.setAdapter(adapter);
        SetData();
    }

    private void Logout(View view) {
        Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void SetData() {
        Cursor c = mydatabase.rawQuery("select * from Orders", null);
        int temp = 0;
        arrayCustomer.clear();
        arrayOrder.clear();
        while (c.moveToNext() == true) {
            int odID = c.getInt(0);
            int oCID = c.getInt(1);
            double Ototal = c.getDouble(2);
            String Odate = c.getString(3);
            int status = c.getInt(4);
            arrayOrder.add(new Orders(odID, oCID, Ototal, Odate, status));
            Cursor c_Cus = mydatabase.rawQuery("select * from Customer where CusID = ?",
                    new String[]{String.valueOf(oCID)});
            if (c_Cus.moveToFirst() == true) {
                int check = c_Cus.getInt(0);
                if (check != temp) {
                    int cusid = c_Cus.getInt(0);
                    int accid = c_Cus.getInt(1);
                    String cusname = c_Cus.getString(2);
                    int cusage = c_Cus.getInt(3);
                    String cusphone = c_Cus.getString(4);
                    byte[] imgcus = c_Cus.getBlob(5);
                    arrayCustomer.add(new Customer(cusid, accid, cusname, cusage, cusphone, imgcus));
                    temp = cusid;
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void DialogAccept(int id){
        AlertDialog.Builder dialogXoa = new AlertDialog.Builder(this);
        dialogXoa.setMessage("Bạn có muốn chấp nhận đơn hàng này không ?");
        dialogXoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                try {
                    mydatabase.execSQL("update Orders set Status = 1 where OrderID = ?", new String[]{String.valueOf(id)});
                    Toast.makeText(AdminActivity.this, "Đã duyệt !", Toast.LENGTH_SHORT).show();
                } catch (Exception ex) {
                    Toast.makeText(AdminActivity.this, "Error !" + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
