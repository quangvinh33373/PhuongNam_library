package com.example.qltv.Model;

public class SachModel {

    private int maSach;
    private String tenSach;
    private double giaThue;
    private int maLoaiSach;
    private String tenLoaiSach;

    public SachModel(int maSach, String tenSach, double giaThue, int maLoaiSach, String tenLoaiSach) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
        this.tenLoaiSach = tenLoaiSach;
    }

    public SachModel(String tenSach, double giaThue, int maLoaiSach) {
        this.tenSach = tenSach;
        this.giaThue = giaThue;
        this.maLoaiSach = maLoaiSach;
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

    public double getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoaiSach() {
        return maLoaiSach;
    }

    public void setMaLoaiSach(int maLoaiSach) {
        this.maLoaiSach = maLoaiSach;
    }

    public String getTenLoaiSach() {
        return tenLoaiSach;
    }

    public void setTenLoaiSach(String tenLoaiSach) {
        this.tenLoaiSach = tenLoaiSach;
    }
}
