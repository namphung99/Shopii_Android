package com.example.shopii.model;

public class listMenu {
    public int Id;
    public String ten, hinhanh;

    public listMenu(int id, String ten, String hinhanh) {
        Id = id;
        this.ten = ten;
        this.hinhanh  = hinhanh;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
