package com.example.qltv;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class myDataBase extends SQLiteOpenHelper {
    public myDataBase( Context context) {
        super(context, "QLTV", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String dbThuThu = "CREATE TABLE THUTHU(maThuThu TEXT PRIMARY KEY, " +
                "hoTen TEXT, " +
                "matKhau TEXT);";
        db.execSQL(dbThuThu);

        String dbLoaiSach = "CREATE TABLE LOAISACH(maLoaiSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenLoaiSach TEXT);";
        db.execSQL(dbLoaiSach);

        String dbSach = "CREATE TABLE SACH(maSach INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenSach TEXT, " +
                "giaThue REAL, " +
                "maLoaiSach INTEGER REFERENCES LOAISACH(maLoaiSach));";
        db.execSQL(dbSach);

        String dbThanhVien = "CREATE TABLE THANHVIEN(maThanhVien INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "hoTen TEXT, " +
                "namSinh TEXT);";
        db.execSQL(dbThanhVien);

        String dbPhieuMuon = "CREATE TABLE PHIEUMUON(maPhieuMuon INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ngayMuon TEXT, " +
                "traSach INTEGER, " +
                "tienThue REAL, " +
                "maThanhVien INTEGER REFERENCES THANHVIEN(maThanhVien), " +
                "maSach INTEGER REFERENCES SACH(maSach)," +
                "maThuThu TEXT REFERENCES THUTHU(maThuThu));";
        db.execSQL(dbPhieuMuon);

        db.execSQL("INSERT INTO THUTHU VALUES('TT01', 'Vũ Đức Huy', '123'), ('TT02', 'Nguyễn Văn Nam', '123'), ('TT03', 'Nguyễn Thị Huệ', '123'), ('Admin', 'Admin', '123');");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS THUTHU");
            db.execSQL("DROP TABLE IF EXISTS ADMIN");
            db.execSQL("DROP TABLE IF EXISTS LOAISACH");
            db.execSQL("DROP TABLE IF EXISTS SACH");
            db.execSQL("DROP TABLE IF EXISTS THANHVIEN");
            db.execSQL("DROP TABLE IF EXISTS PHIEUMUON");
            onCreate(db);
        }
    }
}
