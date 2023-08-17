package com.example.qltv.fragmentManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qltv.Adapter.SachAdapter;
import com.example.qltv.Adapter.ThanhVienAdapter;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.Dao.ThanhVienDao;
import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class thanhVien extends Fragment {

    RecyclerView recyclerViewThanhVien;
    ThanhVienDao thanhVienDao;
    ArrayList<ThanhVienModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thanh_vien, container, false);
        thanhVienDao = new ThanhVienDao(getContext());
        recyclerViewThanhVien = view.findViewById(R.id.recyclerViewThanhVien);
        list = thanhVienDao.getDSThanhVien();

        loadData();
        return view;
    }

    private void loadData(){
        ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
        ArrayList<ThanhVienModel> list = thanhVienDao.getDSThanhVien();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerViewThanhVien.setLayoutManager(linearLayoutManager);
        ThanhVienAdapter thanhVienAdapter = new ThanhVienAdapter(list, getContext());
        recyclerViewThanhVien.setAdapter(thanhVienAdapter);
    }
}