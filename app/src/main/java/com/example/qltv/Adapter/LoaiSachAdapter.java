package com.example.qltv.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.Dao.LoaiSachDAO;
import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class LoaiSachAdapter extends RecyclerView.Adapter<LoaiSachAdapter.ViewHolder> {

    private ArrayList<LoaiSachModel> list;
    private Context context;

    public LoaiSachAdapter(ArrayList<LoaiSachModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_loai_sach, parent, false);
        return new LoaiSachAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
        holder.itemLoaiSachSTT.setText("STT: " + (position + 1) + "");
        holder.itemLoaiSachTen.setText("Tên loại: " + list.get(position).getTenLoaiSach());
        holder.itemLoaiSachSoLuong.setText("Số lượng: " + loaiSachDAO.getSoLuongSach(list.get(position).getMaLoaiSach()));
//        Xóa loại sách
        holder.itemLoaiSachBtnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa loại sách");
                builder.setMessage("Bạn có muốn xóa loại sách");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoaiSachDAO loaiSachDAO = new LoaiSachDAO(context);
                        int slSach = loaiSachDAO.getSoLuongSach(list.get(holder.getAdapterPosition()).getMaLoaiSach());
                        if (slSach == 0){
                            boolean kiemtra = loaiSachDAO.xoaLoaiSach(list.get(holder.getAdapterPosition()).getMaLoaiSach());
                            if (kiemtra){
                                Toast.makeText(context, "Đã xóa loại sách!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = loaiSachDAO.getDSLoaiSach();
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(context, "Không thể xóa loại sách!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemLoaiSachSTT, itemLoaiSachTen, itemLoaiSachSoLuong;
        ImageView itemLoaiSachBtnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemLoaiSachSTT = itemView.findViewById(R.id.itemLoaiSachSTT);
            itemLoaiSachTen = itemView.findViewById(R.id.itemLoaiSachTen);
            itemLoaiSachSoLuong = itemView.findViewById(R.id.itemLoaiSachSoLuong);
            itemLoaiSachBtnXoa = itemView.findViewById(R.id.itemLoaiSachBtnXoa);
        }
    }

}
