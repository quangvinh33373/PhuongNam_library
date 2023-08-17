package com.example.qltv.fragmentManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qltv.Dao.ThuThuDao;
import com.example.qltv.Model.ThuThuModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class themThuThu extends Fragment {

    EditText edtTenThuThu, edtTenDangNhapThuThu, edtMatKhauThuThu, edtReMatKhauThuThu;
    Button btnThemThuThu, btnThemTTHuy;
    ThuThuDao thuThuDao;
    ArrayList<ThuThuModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_them_thu_thu, container, false);

        edtTenThuThu = view.findViewById(R.id.edtTenThuThu);
        edtTenDangNhapThuThu = view.findViewById(R.id.edtTenDangNhapThuThu);
        edtMatKhauThuThu = view.findViewById(R.id.edtMatKhauThuThu);
        edtReMatKhauThuThu = view.findViewById(R.id.edtReMatKhauThuThu);
        btnThemThuThu = view.findViewById(R.id.btnThemThuThu);
        btnThemTTHuy = view.findViewById(R.id.btnThemTTHuy);

        thuThuDao = new ThuThuDao(getContext());
        list = new ArrayList<>();


        btnThemThuThu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = edtTenThuThu.getText().toString();
                String userName = edtTenDangNhapThuThu.getText().toString();
                String pass = edtMatKhauThuThu.getText().toString();
                String rePass = edtReMatKhauThuThu.getText().toString();

                boolean check = true;
                if (fullName.isEmpty()){
                    Toast.makeText(getContext(), "Nhập tên thủ thư!", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if (userName.isEmpty()){
                    Toast.makeText(getContext(), "Nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                else {
                    list = thuThuDao.checkUserName(userName);
                    if (list.size() != 0){
                        Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại!", Toast.LENGTH_SHORT).show();
                        check = false;
                    }
                }
                if (pass.isEmpty()){
                    Toast.makeText(getContext(), "Nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                if (rePass.isEmpty()){
                    Toast.makeText(getContext(), "Nhập xác nhận mật khẩu!", Toast.LENGTH_SHORT).show();
                    check = false;
                }
                else {
                    if (!rePass.equalsIgnoreCase(pass)){
                        Toast.makeText(getContext(), "Xác nhận mật khẩu sai!", Toast.LENGTH_SHORT).show();
                        check = false;
                    }
                }

                if (check){
                    ThuThuModel thuThuModel = new ThuThuModel(userName, fullName, pass);
                    boolean kiemtra = thuThuDao.themThuThu(thuThuModel);
                    if (kiemtra){
                        Toast.makeText(getContext(), "Thêm thủ thư thành công!", Toast.LENGTH_SHORT).show();
                        resetForm();
                    }
                }
            }
        });
        
        btnThemTTHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hủy!", Toast.LENGTH_SHORT).show();
                resetForm();
            }
        });

        return view;
    }

    public void resetForm(){
        edtTenThuThu.setText(null);
        edtTenDangNhapThuThu.setText(null);
        edtMatKhauThuThu.setText(null);
        edtReMatKhauThuThu.setText(null);
    }
}