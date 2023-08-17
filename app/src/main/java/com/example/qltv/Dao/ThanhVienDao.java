package com.example.qltv.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv.Model.LoaiSachModel;
import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.myDataBase;

import java.util.ArrayList;

public class ThanhVienDao {
    myDataBase db;

    public ThanhVienDao(Context context){
        db = new myDataBase(context);
    }

    public ArrayList<ThanhVienModel> getDSThanhVien(){
        ArrayList<ThanhVienModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THANHVIEN", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maThanhVien = cursor.getInt(0);
                String hoTen = cursor.getString(1);
                String namSinh = cursor.getString(2);
                list.add(new ThanhVienModel(maThanhVien, hoTen, namSinh));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    public boolean xoaThanhVien(int maThanhVien){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        long check = sqLiteDatabase.delete("THANHVIEN", "maThanhVien = ?", new String[]{String.valueOf(maThanhVien)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean themThanhVien(ThanhVienModel thanhVienModel) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("hoTen", thanhVienModel.getHoTen());
        contentValues.put("namSinh", thanhVienModel.getNamSinh());
        long check = sqLiteDatabase.insert("THANHVIEN", null, contentValues);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
