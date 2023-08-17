package com.example.qltv.fragmentManager;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qltv.Adapter.LoaiSachAdapter;
import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.ThanhVienDao;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class themNguoiDung extends Fragment {

    EditText edtTenThanhVien, edtNamSinh;
    Button btnThemThanhVien, btnThemTVHuy;
    ThanhVienDao thanhVienDao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_nguoi_dung, container, false);

        edtTenThanhVien = view.findViewById(R.id.edtTenThanhVien);
        edtNamSinh = view.findViewById(R.id.edtNamSinh);
        btnThemThanhVien = view.findViewById(R.id.btnThemThanhVien);
        btnThemTVHuy = view.findViewById(R.id.btnThemTVHuy);

        thanhVienDao = new ThanhVienDao(getContext());

        btnThemThanhVien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checkThemTv = true;
                String tenThanhVien = edtTenThanhVien.getText().toString();
                String namSinh = edtNamSinh.getText().toString();

                if (tenThanhVien.isEmpty()){
                    Toast.makeText(getContext(), "Nhập tên thành viên!", Toast.LENGTH_SHORT).show();
                    checkThemTv = false;
                }
                if (namSinh.isEmpty()){
                    Toast.makeText(getContext(), "Nhập năm sinh của thành viên!", Toast.LENGTH_SHORT).show();
                    checkThemTv = false;
                }
                if (checkThemTv){
                    ThanhVienModel thanhVienModel = new ThanhVienModel(tenThanhVien, namSinh);
                    boolean kiemtra = thanhVienDao.themThanhVien(thanhVienModel);
                    if (kiemtra){
                        Toast.makeText(getContext(), "Thêm thành viên thành công!", Toast.LENGTH_SHORT).show();
                        resetForm();
                    }
                }
            }
        });

        btnThemTVHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Hủy!", Toast.LENGTH_SHORT).show();
                resetForm();
            }
        });
        
        return view;
    }

    public void resetForm(){
        edtTenThanhVien.setText(null);
        edtTenThanhVien.setHint("Tên thành viên");
        edtNamSinh.setText(null);
        edtNamSinh.setHint("Năm sinh");
    }
}