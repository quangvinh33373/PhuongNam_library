package com.example.qltv.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.Model.SachModel;
import com.example.qltv.myDataBase;

import java.util.ArrayList;

public class SachDAO {
    myDataBase db;

    public SachDAO(Context context) {
        db = new myDataBase(context);
    }

    public ArrayList<SachModel> getDSSach(){
        ArrayList<SachModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SACH.maSach, SACH.tenSach, SACH.giaThue, SACH.maLoaiSach, LOAISACH.tenloaisach FROM SACH, LOAISACH WHERE SACH.maloaisach = LOAISACH.maLoaiSach", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maSach = cursor.getInt(0);
                String tenSach = cursor.getString(1);
                double giaThue = cursor.getDouble(2);
                int maLoaiSach = cursor.getInt(3);
                String tenLoaiSach = cursor.getString(4);
                list.add(new SachModel(maSach, tenSach, giaThue, maLoaiSach, tenLoaiSach));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    public boolean suaSach(SachModel sachSua){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maSach", sachSua.getMaSach());
        contentValues.put("tenSach", sachSua.getTenSach());
        contentValues.put("giaThue", sachSua.getGiaThue());
        contentValues.put("maLoaiSach", sachSua.getMaLoaiSach());
        long check = sqLiteDatabase.update("SACH", contentValues, "maSach = ?", new String[]{String.valueOf(sachSua.getMaSach())});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean xoaSach(int maSach){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        long check = sqLiteDatabase.delete("SACH", "maSach = ?", new String[]{String.valueOf(maSach)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean themSach(SachModel sachModel) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenSach", sachModel.getTenSach());
        contentValues.put("giaThue", sachModel.getGiaThue());
        contentValues.put("maLoaiSach", sachModel.getMaLoaiSach());
        long check = sqLiteDatabase.insert("SACH", null, contentValues);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
