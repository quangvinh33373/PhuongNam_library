package com.example.qltv.fragmentManager;

import android.app.Dialog;
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
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.qltv.Adapter.LoaiSachAdapter;
import com.example.qltv.Adapter.SachAdapter;
import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.MainActivity2;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.SachModel;
import com.example.qltv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;

public class sach extends Fragment {

    SachDAO sachDAO;
    RecyclerView recyclerView;
    ArrayList<SachModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sach, container, false);

        sachDAO = new SachDAO(getContext());
        recyclerView = view.findViewById(R.id.recyclerViewSach);
        list = sachDAO.getDSSach();

        loadData();

        FloatingActionButton fab = view.findViewById(R.id.sachFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog((MainActivity2)getActivity());
                dialog.setContentView(R.layout.dialog_them_sach);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7);
                dialog.getWindow().setLayout(width, height);

                Spinner spnTSachLoaiSach = dialog.findViewById(R.id.spnTSachLoaiSach);
                EditText edtTSachTenSach = dialog.findViewById(R.id.edtTSachTenSach);
                EditText edtTSachGiaThue = dialog.findViewById(R.id.edtTSachGiaThue);
                Button btnTThemSach = dialog.findViewById(R.id.btnTSachThem);
                Button btnTHuyThemSach = dialog.findViewById(R.id.btnTSachHuy);


//                SetData cho LoaiSach Spinner
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(getContext());
                ArrayList<LoaiSachModel> listLs = loaiSachDAO.getDSLoaiSach();
                ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
                for (LoaiSachModel loaiSach : listLs){
                    HashMap<String, Object> ls = new HashMap<>();
                    ls.put("maLoaiSach", loaiSach.getMaLoaiSach());
                    ls.put("tenLoaiSach", loaiSach.getTenLoaiSach());
                    listHM.add(ls);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(dialog.getContext(),
                        listHM,
                        android.R.layout.simple_list_item_1,
                        new String[]{"tenLoaiSach"},
                        new int[]{android.R.id.text1});
                spnTSachLoaiSach.setAdapter(simpleAdapter);

                btnTThemSach.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String tenSach = edtTSachTenSach.getText().toString();
                        String giaThue = edtTSachGiaThue.getText().toString();
                        boolean checkThemSach = true;
                        HashMap<String, Object> hsLoaiSach = (HashMap<String, Object>) spnTSachLoaiSach.getSelectedItem();
                        int maLoaiSach = (int) hsLoaiSach.get("maLoaiSach");
                        if (tenSach.isEmpty()){
                            Toast.makeText(getContext(), "Nhập tên sách!", Toast.LENGTH_SHORT).show();
                            checkThemSach = false;
                        }

                        if (giaThue.isEmpty()){
                            Toast.makeText(getContext(), "Nhập giá thuê sách!", Toast.LENGTH_SHORT).show();
                            checkThemSach = false;
                        }

                        if (checkThemSach){
                            Double giathue = Double.parseDouble(giaThue);
                            SachModel sachModel = new SachModel(tenSach, giathue, maLoaiSach);
                            boolean kiemtra = sachDAO.themSach(sachModel);
                            if (kiemtra){
                                Toast.makeText(getContext(), "Thêm sách thành công!", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                            dialog.dismiss();
                        }
                    }
                });

                btnTHuyThemSach.setOnClickListener(new View.OnClickListener() {
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
        SachDAO sachDAO = new SachDAO(getContext());
        ArrayList<SachModel> list = sachDAO.getDSSach();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        SachAdapter sachAdapter = new SachAdapter(list, getContext());
        recyclerView.setAdapter(sachAdapter);
    }
}