package com.example.qltv.fragmentManager;

import android.content.Context;
import android.content.SharedPreferences;
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

public class doiMatKhau extends Fragment {

    ThuThuDao thuThuDao;
    EditText edtMatKhauCu, edtMatKhauMoi, edtReMatKhauMoi;
    Button btnDoiMatKhau, btnDoiMkHuy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doi_mat_khau, container, false);
        edtMatKhauCu = view.findViewById(R.id.edtMatKhauCu);
        edtMatKhauMoi = view.findViewById(R.id.edtMatKhauMoi);
        edtReMatKhauMoi = view.findViewById(R.id.edtReMatKhauMoi);
        btnDoiMatKhau = view.findViewById(R.id.btnDoiMatKhau);
        btnDoiMkHuy = view.findViewById(R.id.btnDoiMkHuy);
        thuThuDao = new ThuThuDao(getContext());

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String matkhauCu = edtMatKhauCu.getText().toString();
                String matkhauMoi = edtMatKhauMoi.getText().toString();
                String reMatkhauMoi = edtReMatKhauMoi.getText().toString();
                boolean checkDoiMk = true;
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                String passWord = sharedPreferences.getString("passWord", "");

                if (matkhauCu.isEmpty()){
                    Toast.makeText(getContext(), "Nhập mật khẩu cũ!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }

                if (!matkhauCu.equals(passWord)){
                    Toast.makeText(getContext(), "Nhập sai mật khẩu!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }

                if (matkhauMoi.isEmpty()){
                    Toast.makeText(getContext(), "Nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }

                if (reMatkhauMoi.isEmpty()){
                    Toast.makeText(getContext(), "Nhập lại mật khẩu mới!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }   else if (!matkhauMoi.equals(reMatkhauMoi)){
                    Toast.makeText(getContext(), "Nhập xác nhận mật khẩu sai!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }

                if (matkhauMoi.equals(passWord)){
                    Toast.makeText(getContext(), "Mật khẩu đã được sử dụng!", Toast.LENGTH_SHORT).show();
                    checkDoiMk = false;
                }

                if (checkDoiMk){
                    String userName = sharedPreferences.getString("userName", "");
                    String fullName = sharedPreferences.getString("fullName", "");
                    ThuThuModel thuThu = new ThuThuModel(userName, fullName, reMatkhauMoi);
                    boolean kiemtra = thuThuDao.suaPass(thuThu);
                    if (kiemtra){
                        Toast.makeText(getContext(), "Thay đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                        sharedPreferences.edit().clear().commit();
                        resetForm();
                    }
                }
            }
        });

        btnDoiMkHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hủy!", Toast.LENGTH_SHORT).show();
                resetForm();
            }
        });

        return view;
    }

    public void resetForm(){
        edtMatKhauCu.setText(null);
        edtMatKhauCu.setHint("Nhập mật khẩu cũ");
        edtMatKhauMoi.setText(null);
        edtMatKhauMoi.setHint("Nhập mật khẩu mới");
        edtReMatKhauMoi.setText(null);
        edtReMatKhauMoi.setHint("Nhập lại mật khẩu mới");
    }
}