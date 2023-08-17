package com.example.qltv.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.MainActivity2;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.SachModel;
import com.example.qltv.R;

import java.util.ArrayList;
import java.util.HashMap;

public class SachAdapter extends RecyclerView.Adapter<SachAdapter.ViewHolder> {

    private ArrayList<SachModel> list;
    private Context context;

    public SachAdapter(ArrayList<SachModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_sach, parent, false);
        return new SachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemSachTenSach.setText((position + 1) + " - " + list.get(position).getTenSach());
        holder.itemSachTenLoai.setText("Loại sách: " + list.get(position).getTenLoaiSach());
        holder.itemSachGiaThue.setText("Giá thuê: " + list.get(position).getGiaThue());
        holder.itemSachBtnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_thong_tin_sach);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                int width = (int)((context).getResources().getDisplayMetrics().widthPixels*0.9);
                int height = (int)((context).getResources().getDisplayMetrics().heightPixels*0.7);
                dialog.getWindow().setLayout(width, height);

                ImageView btnSachBack = dialog.findViewById(R.id.btnSachBack);
                Spinner spnSachLoaiSach = dialog.findViewById(R.id.spnSachLoaiSach);
                EditText edtSachTenSach = dialog.findViewById(R.id.edtSachTenSach);
                EditText edtSachGiaThue = dialog.findViewById(R.id.edtSachGiaThue);
                Button btnSachSua = dialog.findViewById(R.id.btnSachSua);
                Button btnSachXoa = dialog.findViewById(R.id.btnSachXoa);

                SachModel sachSua = list.get(position);

//                SetData cho LoaiSach Spinner
                LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
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
                spnSachLoaiSach.setAdapter(simpleAdapter);

                int count = simpleAdapter.getCount();
                String itemSelect = sachSua.getTenLoaiSach();
                for (int i = 0; i < count; i++) {
                    String item = (String) listHM.get(i).get("tenLoaiSach");
                    if (item.equalsIgnoreCase(itemSelect)){
                        spnSachLoaiSach.setSelection(i);
                    }
                }
                edtSachTenSach.setText(sachSua.getTenSach());
                edtSachGiaThue.setText((int) sachSua.getGiaThue() + "");


                btnSachBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                btnSachSua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        HashMap<String, Object> hsLoaiSach = (HashMap<String, Object>) spnSachLoaiSach.getSelectedItem();
                        int maLoaiSach = (int) hsLoaiSach.get("maLoaiSach");
                        String tenSach = edtSachTenSach.getText().toString();
                        double giaThue = Double.parseDouble(edtSachGiaThue.getText().toString());
                        sachSua.setTenSach(tenSach);
                        sachSua.setGiaThue(giaThue);
                        sachSua.setMaLoaiSach(maLoaiSach);
                        SachDAO sachDAO = new SachDAO(context);
                        boolean kiemtra = sachDAO.suaSach(sachSua);
                        if (kiemtra){
                            Toast.makeText(context, "Cập nhật sách thành công!", Toast.LENGTH_SHORT).show();
                            list.clear();
                            list = sachDAO.getDSSach();
                            notifyDataSetChanged();
                        }
                        dialog.dismiss();
                    }
                });

                btnSachXoa.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setTitle("Xóa sách");
                        builder.setMessage("Bạn có muốn xóa sách");
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SachDAO sachDAO = new SachDAO(context);
                                PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                                int checkXoa = phieuMuonDao.checkXoaSach(list.get(position).getMaSach());
                                if (checkXoa == 0){
                                    boolean kiemtra = sachDAO.xoaSach(list.get(holder.getAdapterPosition()).getMaSach());
                                    if (kiemtra){
                                        Toast.makeText(context, "Đã xóa sách!", Toast.LENGTH_SHORT).show();
                                        list.clear();
                                        list = sachDAO.getDSSach();
                                        notifyDataSetChanged();
                                    }
                                }   else {
                                    Toast.makeText(context, "Không thể xóa sách!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        builder.show();
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemSachTenSach, itemSachTenLoai, itemSachGiaThue;
        ImageView itemSachBtnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemSachTenSach = itemView.findViewById(R.id.itemSachTenSach);
            itemSachTenLoai = itemView.findViewById(R.id.itemSachTenLoai);
            itemSachGiaThue = itemView.findViewById(R.id.itemSachGiaThue);
            itemSachBtnXoa = itemView.findViewById(R.id.itemSachBtnXoa);
        }
    }
}
