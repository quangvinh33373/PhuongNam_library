package com.example.qltv.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.Dao.PhieuMuonDao;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class PhieuMuonAdapter extends RecyclerView.Adapter<PhieuMuonAdapter.ViewHolder>{

    private ArrayList<PhieuMuonModel> list;
    private Context context;

    public PhieuMuonAdapter(ArrayList<PhieuMuonModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phieu_muon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemPhieuMuonSTT.setText("STT: " + (position + 1) + "");
        holder.itemPhieuMuonTenThanhVien.setText("Người mượn: " + list.get(position).getTenThanhVien());
        holder.itemPhieuMuonTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.itemPhieuMuonThuThu.setText("Thủ thư: " + list.get(position).getTenThuThu());
        holder.itemPhieuMuonNgayMuon.setText("Ngày mượn: " + list.get(position).getNgayMuon());
        holder.itemPhieuMuonTienThue.setText("Giá: " + (int) list.get(position).getTienThue() + " VNĐ");
        String traSach = "";
        if (list.get(position).getTraSach() == 1){
            traSach = "Đã trả";
            holder.itemPhieuMuonChkTraSach.setChecked(true);
            holder.itemPhieuMuonChkTraSach.setEnabled(false);
            holder.itemPhieuMuonTraSach.setTextColor(Color.parseColor("#42FF00"));
            holder.itemPhieuMuonTraSach.setText(traSach);
        }   else {
            traSach = "Chưa trả";
            holder.itemPhieuMuonTraSach.setText(traSach);
            holder.itemPhieuMuonChkTraSach.setChecked(false);
        }

//      Set trạng thái trả sách qua CheckBox
        holder.itemPhieuMuonChkTraSach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                boolean kiemtra = phieuMuonDao.thayDoiTraSach(list.get(holder.getAdapterPosition()).getMaPhieuMuon());
                if (kiemtra){
                    list.clear();
                    list = phieuMuonDao.getDSPhieuMuon();
                    notifyDataSetChanged();
                }
            }
        });

//      Xóa phiếu mượn
        holder.itemPhieuMuonBtnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("Xóa phiếu mượn");
                builder.setMessage("Bạn có muốn xóa phiếu mượn");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PhieuMuonDao phieuMuonDao = new PhieuMuonDao(context);
                        if (list.get(holder.getAdapterPosition()).getTraSach() == 1){
                            boolean kiemtra = phieuMuonDao.xoaPhieuMuon(list.get(holder.getAdapterPosition()).getMaPhieuMuon());
                            if (kiemtra){
                                Toast.makeText(context, "Đã xóa phiếu mượn!", Toast.LENGTH_SHORT).show();
                                list.clear();
                                list = phieuMuonDao.getDSPhieuMuon();
                                notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(context, "Không thể xóa phiếu mượn!", Toast.LENGTH_SHORT).show();
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

        TextView itemPhieuMuonSTT, itemPhieuMuonTenThanhVien, itemPhieuMuonTenSach, itemPhieuMuonThuThu, itemPhieuMuonNgayMuon, itemPhieuMuonTienThue, itemPhieuMuonTraSach;
        ImageView itemPhieuMuonBtnXoa;
        CheckBox itemPhieuMuonChkTraSach;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemPhieuMuonSTT = itemView.findViewById(R.id.itemPhieuMuonSTT);
            itemPhieuMuonTenThanhVien = itemView.findViewById(R.id.itemPhieuMuonTenThanhVien);
            itemPhieuMuonTenSach = itemView.findViewById(R.id.itemPhieuMuonTenSach);
            itemPhieuMuonThuThu = itemView.findViewById(R.id.itemPhieuMuonThuThu);
            itemPhieuMuonNgayMuon = itemView.findViewById(R.id.itemPhieuMuonNgayMuon);
            itemPhieuMuonTienThue = itemView.findViewById(R.id.itemPhieuMuonTienThue);
            itemPhieuMuonTraSach = itemView.findViewById(R.id.itemPhieuMuonTraSach);
            itemPhieuMuonBtnXoa = itemView.findViewById(R.id.itemPhieuMuonBtnXoa);
            itemPhieuMuonChkTraSach = itemView.findViewById(R.id.itemPhieuMuonChkTraSach);
        }
    }
}
