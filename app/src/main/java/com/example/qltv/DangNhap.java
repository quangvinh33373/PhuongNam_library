package com.example.qltv;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qltv.Dao.ThuThuDao;
import com.example.qltv.Model.ThuThuModel;

import java.util.ArrayList;
import java.util.List;

public class DangNhap extends AppCompatActivity {

    EditText edtTenDangNhap, edtMatKhau;
    CheckBox chkNhoMK;
    Button btnDangNhap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dang_nhap);

        edtTenDangNhap = findViewById(R.id.edtTenDangNhap);
        edtMatKhau = findViewById(R.id.edtMatKhau);
        chkNhoMK = findViewById(R.id.chkNhoMK);
        btnDangNhap = findViewById(R.id.btnDangNhap);

        List<Object> list;
        list = readAccount();
        if(list.size() > 0){
            if ((boolean)list.get(3)){
                edtTenDangNhap.setText(list.get(0).toString());
                edtMatKhau.setText(list.get(2).toString());
                chkNhoMK.setChecked((boolean) list.get(3));
            }
        }

        ThuThuDao thuThuDao = new ThuThuDao(this);
        
        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtTenDangNhap.getText().toString();
                String password = edtMatKhau.getText().toString();
                boolean saveAcc = chkNhoMK.isChecked();

                boolean checkLogin = true;
                if (userName.length() == 0){
                    Toast.makeText(DangNhap.this, "Chưa nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }

                if (password.length() == 0){
                    Toast.makeText(DangNhap.this, "Chưa nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }

                if (checkLogin){
                    ArrayList<ThuThuModel> list = thuThuDao.checkDangNhap(userName, password);
//                Nếu tồn tại tài khoản thì lưu vào SR;
                    if (list.size() != 0){
                        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("userName", userName);
                        editor.putString("fullName", list.get(0).getHoTen());
                        editor.putString("passWord", password);
                        editor.putBoolean("saveAcc", saveAcc);
                        editor.commit();
                        Intent intent = new Intent(DangNhap.this, MainActivity2.class);
                        intent.putExtra("userName", userName);
                        intent.putExtra("fullName", list.get(0).getHoTen());
                        Toast.makeText(DangNhap.this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(DangNhap.this, "Tên đăng nhập hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    public List<Object> readAccount(){
        List<Object> list = new ArrayList<>();
        SharedPreferences sharedPreferences = getSharedPreferences("account", MODE_PRIVATE);
        list.add(sharedPreferences.getString("userName", ""));
        list.add(sharedPreferences.getString("fullName", ""));
        list.add(sharedPreferences.getString("passWord", ""));
        list.add(sharedPreferences.getBoolean("saveAcc", false));
        return list;
    }
}