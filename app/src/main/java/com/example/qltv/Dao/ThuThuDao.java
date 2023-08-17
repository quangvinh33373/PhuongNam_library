package com.example.qltv.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv.Model.SachModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.Model.ThuThuModel;
import com.example.qltv.myDataBase;

import java.util.ArrayList;
import java.util.List;

public class ThuThuDao {
    myDataBase db;

    public ThuThuDao(Context context) {
        db = new myDataBase(context);
    }

    public ArrayList<ThuThuModel> checkDangNhap(String userName, String password){
        ArrayList<ThuThuModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE maThuThu = ? AND matKhau = ?", new String[]{userName, password});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            String maThuThu = cursor.getString(0);
            String tenThuThu = cursor.getString(1);
            String matKhau = cursor.getString(2);
            list.add(new ThuThuModel(maThuThu, tenThuThu, matKhau));
        }
        return list;
    }

    public ArrayList<ThuThuModel> checkUserName(String userName){
        ArrayList<ThuThuModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE maThuThu = ?", new String[]{userName});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            String maThuThu = cursor.getString(0);
            String tenThuThu = cursor.getString(1);
            String matKhau = cursor.getString(2);
            list.add(new ThuThuModel(maThuThu, tenThuThu, matKhau));
        }
        return list;
    }

    public boolean suaPass(ThuThuModel thuThu){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maThuThu", thuThu.getMaThuThu());
        contentValues.put("hoTen", thuThu.getHoTen());
        contentValues.put("matKhau", thuThu.getMatKhau());
        long check = sqLiteDatabase.update("THUTHU", contentValues, "maThuThu = ?", new String[]{String.valueOf(thuThu.getMaThuThu())});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean themThuThu(ThuThuModel thuThuModel) {
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("maThuThu", thuThuModel.getMaThuThu());
        contentValues.put("hoTen", thuThuModel.getHoTen());
        contentValues.put("matKhau", thuThuModel.getMatKhau());
        long check = sqLiteDatabase.insert("THUTHU", null, contentValues);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }
}
