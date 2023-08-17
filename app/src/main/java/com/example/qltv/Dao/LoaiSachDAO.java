package com.example.qltv.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.myDataBase;

import java.util.ArrayList;

public class LoaiSachDAO {
    myDataBase db;

    public LoaiSachDAO(Context context) {
        db = new myDataBase(context);
    }

    public ArrayList<LoaiSachModel> getDSLoaiSach(){
        ArrayList<LoaiSachModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM LOAISACH", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maLoaiSach = cursor.getInt(0);
                String tenLoaiSach = cursor.getString(1);
                list.add(new LoaiSachModel(maLoaiSach, tenLoaiSach));
                }   while (cursor.moveToNext());
        }
        return list;
    }

    public int getSoLuongSach(int maLoaiSach){
        ArrayList<LoaiSachModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SACH", null);
        int count = 0;
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int mMaLoaiSach = cursor.getInt(3);
                if (maLoaiSach == mMaLoaiSach){
                    count++;
                }
            }   while (cursor.moveToNext());
        }
        return count;
    }

    public boolean xoaLoaiSach(int maLoaiSach){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        long check = sqLiteDatabase.delete("LOAISACH", "maLoaiSach = ?", new String[]{String.valueOf(maLoaiSach)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean themLoaiSach(LoaiSachModel loaiSachModel) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("tenLoaiSach", loaiSachModel.getTenLoaiSach());
        long check = sqLiteDatabase.insert("LOAISACH", null, contentValues);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

}
