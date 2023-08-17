package com.example.qltv.fragmentManager;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.qltv.Adapter.SachAdapter;
import com.example.qltv.Adapter.ThongKeAdapter;
import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThongKeModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class thongKe extends Fragment {

    RecyclerView recyclerView;
    PhieuMuonDao phieuMuonDao;
    ArrayList<ThongKeModel> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top10, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewThongKe);
        phieuMuonDao = new PhieuMuonDao(getContext());
        list = phieuMuonDao.getSoLanMuon();
        loadData();
        return view;
    }

    private void loadData(){
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
        ArrayList<ThongKeModel> list = phieuMuonDao.getSoLanMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        ThongKeAdapter thongKeAdapter = new ThongKeAdapter(list, getContext());
        recyclerView.setAdapter(thongKeAdapter);
    }
}