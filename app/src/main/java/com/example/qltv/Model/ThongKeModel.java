package com.example.qltv.Model;

public class ThongKeModel {

    private String tenSach;
    private int soLanMuon;

    public ThongKeModel(String tenSach, int soLanMuon) {
        this.tenSach = tenSach;
        this.soLanMuon = soLanMuon;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public int getSoLanMuon() {
        return soLanMuon;
    }

    public void setSoLanMuon(int soLanMuon) {
        this.soLanMuon = soLanMuon;
    }
}
