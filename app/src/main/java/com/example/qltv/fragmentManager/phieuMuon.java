package com.example.qltv.fragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import com.example.qltv.Adapter.PhieuMuonAdapter;
import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.Dao.ThanhVienDao;
import com.example.qltv.MainActivity2;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class phieuMuon extends Fragment {

    PhieuMuonDao phieuMuonDao;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_phieu_muon, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewPhieuMuon);
        loadData();

        FloatingActionButton fab = view.findViewById(R.id.phieuMuonFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog((MainActivity2)getActivity());
                dialog.setContentView(R.layout.dialog_them_phieu_muon);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int width = (int)(getResources().getDisplayMetrics().widthPixels*0.9);
                int height = (int)(getResources().getDisplayMetrics().heightPixels*0.7);
                dialog.getWindow().setLayout(width, height);

                Spinner spnThemPmThanhVien = dialog.findViewById(R.id.spnThemPmThanhVien);
                Spinner spnThemPmTenSach = dialog.findViewById(R.id.spnThemPmTenSach);
                Button btnThemPmThem = dialog.findViewById(R.id.btnThemPmThem);
                Button btnThemPmHuy = dialog.findViewById(R.id.btnThemPmHuy);

                phieuMuonDao = new PhieuMuonDao(getContext());

//                Set Data Spinner ThanhVien
                ThanhVienDao thanhVienDao = new ThanhVienDao(getContext());
                ArrayList<ThanhVienModel> listTv = thanhVienDao.getDSThanhVien();
                ArrayList<HashMap<String, Object>> listHM = new ArrayList<>();
                for (ThanhVienModel thanhVien : listTv){
                    HashMap<String, Object> tv = new HashMap<>();
                    tv.put("maThanhVien", thanhVien.getMaThanhVien());
                    tv.put("hoTen", thanhVien.getHoTen());
                    listHM.add(tv);
                }
                SimpleAdapter simpleAdapter = new SimpleAdapter(dialog.getContext(),
                        listHM,
                        android.R.layout.simple_list_item_1,
                        new String[]{"hoTen"},
                        new int[]{android.R.id.text1});
                spnThemPmThanhVien.setAdapter(simpleAdapter);

//                Set Data Spinner Sach
                SachDAO sachDAO = new SachDAO(getContext());
                ArrayList<SachModel> listSach = sachDAO.getDSSach();
                ArrayList<HashMap<String, Object>> listHM1 = new ArrayList<>();
                for (SachModel sach : listSach){
                    HashMap<String, Object> s = new HashMap<>();
                    s.put("maSach", sach.getMaSach());
                    s.put("tenSach", sach.getTenSach());
                    s.put("giaThue", sach.getGiaThue());
                    listHM1.add(s);
                }
                SimpleAdapter simpleAdapter1 = new SimpleAdapter(dialog.getContext(),
                        listHM1,
                        android.R.layout.simple_list_item_1,
                        new String[]{"tenSach"},
                        new int[]{android.R.id.text1});
                spnThemPmTenSach.setAdapter(simpleAdapter1);

//                Get ThuThu
                SharedPreferences sharedPreferences = getContext().getSharedPreferences("account", Context.MODE_PRIVATE);
                String maThuThu = sharedPreferences.getString("userName", "");

//                Get NgayMuon
                Date nowDate = Calendar.getInstance().getTime();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String ngayMuon = simpleDateFormat.format(nowDate);

                btnThemPmThem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Lấy mã thành viên
                        HashMap<String, Object> listThanhVien = (HashMap<String, Object>) spnThemPmThanhVien.getSelectedItem();
                        int maThanhVien = (int) listThanhVien.get("maThanhVien");
//                        Lấy mã sách
                        HashMap<String, Object> listSach = (HashMap<String, Object>) spnThemPmTenSach.getSelectedItem();
                        int maSach = (int) listSach.get("maSach");
//                        Tiền thuê
                        double giaThue = (double) listSach.get("giaThue");
                        int traSach = 0;
                        PhieuMuonModel phieuMuon = new PhieuMuonModel(ngayMuon, traSach, giaThue, maThanhVien, maSach, maThuThu);
                        boolean kiemtra = phieuMuonDao.themPhieuMuon(phieuMuon);
                        if (kiemtra){
                            Toast.makeText(getContext(), "Thêm phiếu mượn thành công!", Toast.LENGTH_SHORT).show();
                            loadData();
                        }
                        dialog.dismiss();
                    }
                });

                btnThemPmHuy.setOnClickListener(new View.OnClickListener() {
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
        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(getContext());
        ArrayList<PhieuMuonModel> list = phieuMuonDao.getDSPhieuMuon();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        PhieuMuonAdapter phieuMuonAdapter = new PhieuMuonAdapter(list, getContext());
        recyclerView.setAdapter(phieuMuonAdapter);
    }
}