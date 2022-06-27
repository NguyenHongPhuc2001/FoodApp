package hcmute.edu.vn.foody;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import hcmute.edu.vn.foody.database.Database;

public class SignupActivity extends AppCompatActivity {
    Button btnLogin, btnSignup;
    EditText edtUsername, edtPassword, edtConfirm;

    SQLiteDatabase mydatabase;
    Database database;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_signup);
        try {
            database = new Database(this, "food.sqlite", null, 1);
            mydatabase = database.getWritableDatabase();
        } catch (Exception e) {
            e.printStackTrace();
        }


        btnLogin = (Button) findViewById(R.id.btn_Log_From_Sign);
        btnSignup = (Button) findViewById(R.id.btn_Signup);
        edtUsername = (EditText) findViewById(R.id.edt_Signup_UserName);
        edtPassword = (EditText) findViewById(R.id.edt_Signup_Password);
        edtConfirm = (EditText) findViewById(R.id.edt_Signup_CFPassword);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignupAcc(view);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignToLog(view);
            }
        });
    }

    public void SignToLog(View view) {
        Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    public void SignupAcc(View view) {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();
        String confirm = edtConfirm.getText().toString();

        if (confirm.equals(password)) {
            ContentValues cv = new ContentValues();
            cv.put("UserName", username);
            cv.put("Password", password);
            mydatabase.insert("Account", null,cv);
            Toast.makeText(this, "Đăng ký thành công !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Mật khẩu không trùng khớp ? Vui lòng kiểm tra lại !", Toast.LENGTH_SHORT).show();
        }
    }
}
