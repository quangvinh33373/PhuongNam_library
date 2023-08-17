package com.example.qltv.Model;

import java.util.Date;

public class PhieuMuonModel {

    private int maPhieuMuon;
    private String ngayMuon;
    private int traSach;
    private double tienThue;
    private int maThanhVien;
    private String tenThanhVien;
    private int maSach;
    private String tenSach;
    private String maThuThu;
    private String tenThuThu;

    public PhieuMuonModel(int maPhieuMuon, String ngayMuon, int traSach, double tienThue, int maThanhVien, String tenThanhVien, int maSach, String tenSach, String maThuThu, String tenThuThu) {
        this.maPhieuMuon = maPhieuMuon;
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.tienThue = tienThue;
        this.maThanhVien = maThanhVien;
        this.tenThanhVien = tenThanhVien;
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.maThuThu = maThuThu;
        this.tenThuThu = tenThuThu;
    }

    public PhieuMuonModel(String ngayMuon, int traSach, double tienThue, int maThanhVien, int maSach, String maThuThu) {
        this.ngayMuon = ngayMuon;
        this.traSach = traSach;
        this.tienThue = tienThue;
        this.maThanhVien = maThanhVien;
        this.maSach = maSach;
        this.maThuThu = maThuThu;
    }

    public int getMaPhieuMuon() {
        return maPhieuMuon;
    }

    public void setMaPhieuMuon(int maPhieuMuon) {
        this.maPhieuMuon = maPhieuMuon;
    }

    public String getNgayMuon() {
        return ngayMuon;
    }

    public void setNgayMuon(String ngayMuon) {
        this.ngayMuon = ngayMuon;
    }

    public int getTraSach() {
        return traSach;
    }

    public void setTraSach(int traSach) {
        this.traSach = traSach;
    }

    public double getTienThue() {
        return tienThue;
    }

    public void setTienThue(double tienThue) {
        this.tienThue = tienThue;
    }

    public int getMaThanhVien() {
        return maThanhVien;
    }

    public void setMaThanhVien(int maThanhVien) {
        this.maThanhVien = maThanhVien;
    }

    public String getTenThanhVien() {
        return tenThanhVien;
    }

    public void setTenThanhVien(String tenThanhVien) {
        this.tenThanhVien = tenThanhVien;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getMaThuThu() {
        return maThuThu;
    }

    public void setMaThuThu(String maThuThu) {
        this.maThuThu = maThuThu;
    }

    public String getTenThuThu() {
        return tenThuThu;
    }

    public void setTenThuThu(String tenThuThu) {
        this.tenThuThu = tenThuThu;
    }
}
