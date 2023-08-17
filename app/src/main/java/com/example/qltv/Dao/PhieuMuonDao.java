package com.example.qltv.Dao;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.qltv.Model.PhieuMuonModel;
import com.example.qltv.Model.ThanhVienModel;
import com.example.qltv.Model.ThongKeModel;
import com.example.qltv.myDataBase;

import java.util.ArrayList;
import java.util.Currency;

public class PhieuMuonDao {
    myDataBase db;

    public PhieuMuonDao(Context context) {
        db = new myDataBase(context);
    }

    public ArrayList<PhieuMuonModel> getDSPhieuMuon(){
        ArrayList<PhieuMuonModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT PHIEUMUON.maphieumuon, PHIEUMUON.mathanhvien, THANHVIEN.hoten, PHIEUMUON.mathuthu, THUTHU.hoten, PHIEUMUON.masach, SACH.tensach, PHIEUMUON.ngaymuon, PHIEUMUON.trasach, PHIEUMUON.tienthue FROM PHIEUMUON, THANHVIEN, THUTHU, SACH WHERE PHIEUMUON.mathanhvien = THANHVIEN.mathanhvien and PHIEUMUON.mathuthu = THUTHU.mathuthu AND PHIEUMUON.masach = SACH.masach ORDER BY PHIEUMUON.trasach ASC;", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                int maPhieuMuon = cursor.getInt(0);
                String ngayMuon = cursor.getString(7);
                int traSach = cursor.getInt(8);
                double tienThue = cursor.getDouble(9);
                int maThanhVien = cursor.getInt(1);
                String tenThanhVien = cursor.getString(2);
                int maSach = cursor.getInt(5);
                String tenSach = cursor.getString(6);
                String maThuThu = cursor.getString(3);
                String tenThuThu = cursor.getString(4);
                list.add(new PhieuMuonModel(maPhieuMuon, ngayMuon, traSach, tienThue, maThanhVien, tenThanhVien, maSach, tenSach, maThuThu, tenThuThu));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<ThongKeModel> getSoLanMuon(){
        ArrayList<ThongKeModel> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SACH.tensach, COUNT(PHIEUMUON.masach) AS soLuotMuon FROM SACH, PHIEUMUON WHERE SACH.masach = PHIEUMUON.maSach GROUP BY PHIEUMUON.masach ORDER BY soLuotMuon DESC LIMIT 10;", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                String tenSach = cursor.getString(0);
                int soLanMuon = cursor.getInt(1);
                list.add(new ThongKeModel(tenSach, soLanMuon));
            }   while (cursor.moveToNext());
        }
        return list;
    }

    public int checkXoaThanhVien(int maTv){
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE traSach = 0 AND maThanhVien = ?;", new String[]{String.valueOf(maTv)});
        return cursor.getCount();
    }

    public int checkXoaSach(int maSach){
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PHIEUMUON WHERE traSach = 0 AND masach = ?;", new String[]{String.valueOf(maSach)});
        return cursor.getCount();
    }

    public double getDoanhThu(String dStart, String dEnd){
        double doanhThu = 0;
        SQLiteDatabase sqLiteDatabase = db.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT SUM(tienThue) AS doanhThu FROM PHIEUMUON WHERE ngayMuon BETWEEN ? AND ?;", new String[]{dStart, dEnd});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                doanhThu += cursor.getDouble(0);
            }   while (cursor.moveToNext());
        }
        return doanhThu;
    }

    public boolean thayDoiTraSach(int maPhieuMuon){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("traSach", 1);
        long check =  sqLiteDatabase.update("PHIEUMUON", contentValues, "maPhieuMuon = ?", new String[]{String.valueOf(maPhieuMuon)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean xoaPhieuMuon(int maPhieuMuon){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        long check = sqLiteDatabase.delete("PHIEUMUON", "maPhieuMuon = ?", new String[]{String.valueOf(maPhieuMuon)});
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean themPhieuMuon(PhieuMuonModel phieuMuonModel){
        SQLiteDatabase sqLiteDatabase = db.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ngayMuon", phieuMuonModel.getNgayMuon());
        contentValues.put("traSach", phieuMuonModel.getTraSach());
        contentValues.put("tienThue", phieuMuonModel.getTienThue());
        contentValues.put("maThanhVien", phieuMuonModel.getMaThanhVien());
        contentValues.put("maSach", phieuMuonModel.getMaSach());
        contentValues.put("maThuThu", phieuMuonModel.getMaThuThu());
        long check = sqLiteDatabase.insert("PHIEUMUON", null, contentValues);
        if (check == -1){
            return false;
        }
        else {
            return true;
        }
    }

}
