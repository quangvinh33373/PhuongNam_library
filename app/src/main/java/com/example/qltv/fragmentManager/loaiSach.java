package com.example.qltv.fragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.qltv.Adapter.LoaiSachAdapter;
import com.example.qltv.Adapter.PhieuMuonAdapter;
import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.MainActivity2;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class loaiSach extends Fragment {

    LoaiSachDAO loaiSachDAO;
    RecyclerView recyclerView;
    ArrayList<LoaiSachModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_loai_sach, container, false);

        loaiSachDAO = new LoaiSachDAO(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewLoaiSach);
        list = loaiSachDAO.getDSLoaiSach();

        loadData();

        FloatingActionButton fab = view.findViewById(R.id.loaiSachFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog((MainActivity2)getActivity());
                dialog.setContentView(R.layout.dialog_them_loai_sach);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7);
                dialog.getWindow().setLayout(width, height);

                EditText edtTenLoaiSach = dialog.findViewById(R.id.edtTenLoaiSach);
                Button btnThemLoaiSach = dialog.findViewById(R.id.btnThemLoaiSach);
                Button btnHuyThemLoaiSach = dialog.findViewById(R.id.btnHuyThemLoaiSach);

                btnThemLoaiSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenLoaiSach = edtTenLoaiSach.getText().toString();
                        LoaiSachModel loaiSachModel = new LoaiSachModel(tenLoaiSach);
                        if (!tenLoaiSach.isEmpty()){
                            boolean kiemtra = loaiSachDAO.themLoaiSach(loaiSachModel);
                            if (kiemtra){
                                Toast.makeText(getContext(), "Thêm loại sách thành công!", Toast.LENGTH_SHORT).show();
                                loadData();
                                dialog.dismiss();
                            }
                        }   else {
                            Toast.makeText(getContext(), "Nhập tên loại sách!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                btnHuyThemLoaiSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });
        return view;
    }

    private void loadData(){
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
        ArrayList<LoaiSachModel> list = loaiSachDAO.getDSLoaiSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        LoaiSachAdapter loaiSachAdapter = new LoaiSachAdapter(list, getContext());
        recyclerView.setAdapter(loaiSachAdapter);
    }
}