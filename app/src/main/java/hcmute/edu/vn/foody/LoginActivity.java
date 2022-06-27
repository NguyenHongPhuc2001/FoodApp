package hcmute.edu.vn.foody;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import hcmute.edu.vn.foody.database.Database;
import hcmute.edu.vn.foody.model.Account;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnSignup;
    ArrayList<Account> arrayAccount;


    SQLiteDatabase mydatabase;
    Database database;

    ImageView imgaview;
    TextView txtview;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_login);


        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getReadableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        arrayAccount = new ArrayList<>();
        imgaview = (ImageView) findViewById(R.id.img_MonAn_Food);
        edtUsername = (EditText) findViewById(R.id.edtUserName);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignup = (Button) findViewById(R.id.btn_Log_Signup);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogIn(view);
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp(view);
            }
        });

    }

    public void LogIn(View view) {
        Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
        Intent intent2 = new Intent(LoginActivity.this, AdminActivity.class);
        Account acc = null;
        int accid = 0;

        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        Cursor c = mydatabase.rawQuery("Select * from Account where UserName = ? and Password = ?",
                new String[]{username, password});

        if(c.moveToFirst()== true){
            accid = c.getInt(0);
            String user = c.getString(1);
            String pass = c.getString(2);
            int role = c.getInt(3);
            acc = new Account(accid,user, pass, role);
        }

        if (c.getCount() > 0 &&  acc.getRole() == 1) {
            int test = accid;
            intent1.putExtra("accid",accid);
            startActivity(intent1);
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
        } else if (c.getCount() > 0 && acc.getRole() == 0) {
            startActivity(intent2);
            Toast.makeText(LoginActivity.this, "Đăng nhập thành công !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(LoginActivity.this, "Sai tên tài khoản hoặc mật khẩu ? Vui lòng " +
                    "kiểm tra lại !", Toast.LENGTH_SHORT).show();
        }
    }

    public void SignUp(View view) {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }
}
