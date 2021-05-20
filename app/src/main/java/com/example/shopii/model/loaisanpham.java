package com.example.shopii.model;

public class loaisanpham {
    public int Id;
    public String tenloaisanpham, hinhanh;

    public loaisanpham(int id, String tenloaisanpham, String hinhanh) {
        Id = id;
        this.tenloaisanpham = tenloaisanpham;
        this.hinhanh  = hinhanh;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenloaisanpham() {
        return tenloaisanpham;
    }

    public void setTenloaisanpham(String tenloaisanpham) {
        this.tenloaisanpham = tenloaisanpham;
    }

    public String getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(String hinhanh) {
        this.hinhanh = hinhanh;
    }
}
