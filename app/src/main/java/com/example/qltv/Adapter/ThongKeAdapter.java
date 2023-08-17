package com.example.qltv.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThongKeModel;
import com.example.qltv.R;

import java.util.ArrayList;

public class ThongKeAdapter extends RecyclerView.Adapter<ThongKeAdapter.ViewHolder> {

    private ArrayList<ThongKeModel> list;
    private Context context;

    public ThongKeAdapter(ArrayList<ThongKeModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_thong_ke, parent, false);
        return new ThongKeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.itemTkTop.setText("Top " + (position + 1) + "");
        holder.itemTkTenSach.setText("Tên sách: " + list.get(position).getTenSach());
        holder.itemTkSlm.setText("Số lượt mượn: " + list.get(position).getSoLanMuon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView itemTkTop, itemTkTenSach, itemTkSlm;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemTkTop = itemView.findViewById(R.id.itemTkTop);
            itemTkTenSach = itemView.findViewById(R.id.itemTkTenSach);
            itemTkSlm = itemView.findViewById(R.id.itemTkSlm);
        }
    }
}
