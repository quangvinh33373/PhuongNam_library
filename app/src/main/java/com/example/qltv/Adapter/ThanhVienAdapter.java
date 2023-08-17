package com.example.qltv.Adapter;

import android.annotation.SuppressLint;
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

import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Dao.SachDAO;
import com.example.qltv.Dao.ThanhVienDao;
import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class ThanhVienAdapter extends RecyclerView.Adapter<ThanhVienAdapter.ViewHolder> {

    private ArrayList<ThanhVienModel> list;
    private Context context;

    public ThanhVienAdapter(ArrayList<ThanhVienModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thanh_vien, parent, false);
        return new ThanhVienAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.itemTvSTT.setText("STT: " + (position + 1) + "");
        holder.itemTvTenThanhVien.setText("Họ và tên: " + list.get(position).getHoTen());
        holder.itemTvNamSinh.setText("Năm sinh: " + list.get(position).getNamSinh());
        holder.itemTvBtnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa thành viên");
                builder.setMessage("Bạn có muốn xóa thành viên");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ThanhVienDao thanhVienDao = new ThanhVienDao(context);
                        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                        int checkXoa = phieuMuonDao.checkXoaThanhVien(list.get(position).getMaThanhVien());
                        if (checkXoa == 0){
                            boolean kiemtra = thanhVienDao.xoaThanhVien(list.get(holder.getAdapterPosition()).getMaThanhVien());
                            if (kiemtra){
                                Toast.makeText(context, "Đã xóa thành viên!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = thanhVienDao.getDSThanhVien();
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(context, "Không thể xóa thành viên!", Toast.LENGTH_SHORT).show();
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

        TextView itemTvSTT, itemTvTenThanhVien, itemTvNamSinh;
        ImageView itemTvBtnXoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTvSTT = itemView.findViewById(R.id.itemTvSTT);
            itemTvTenThanhVien = itemView.findViewById(R.id.itemTvTenThanhVien);
            itemTvNamSinh = itemView.findViewById(R.id.itemTvNamSinh);
            itemTvBtnXoa = itemView.findViewById(R.id.itemTvBtnXoa);
        }
    }

}
