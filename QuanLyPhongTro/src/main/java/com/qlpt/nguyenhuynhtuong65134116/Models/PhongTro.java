package com.qlpt.nguyenhuynhtuong65134116.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "phongtro")
public class PhongTro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(name = "tenphong", nullable = false)
    private String tenPhong;

    @Column(name = "giacoban", nullable = false)
    private Double giaCoBan;

    @Column(name = "dientich")
    private Double dienTich;

    @Column(name = "trangthai")
    private String trangThai;

    @Column(name = "hinhanh")
    private String hinhAnh; 

    public PhongTro() {
    }

    public PhongTro(Long id, String tenPhong, Double giaCoBan, Double dienTich, String trangThai, String hinhAnh) {
        this.id = id;
        this.tenPhong = tenPhong;
        this.giaCoBan = giaCoBan;
        this.dienTich = dienTich;
        this.trangThai = trangThai;
        this.hinhAnh = hinhAnh;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public void setTenPhong(String tenPhong) {
        this.tenPhong = tenPhong;
    }

    public Double getGiaCoBan() {
        return giaCoBan;
    }

    public void setGiaCoBan(Double giaCoBan) {
        this.giaCoBan = giaCoBan;
    }

    public Double getDienTich() {
        return dienTich;
    }

    public void setDienTich(Double dienTich) {
        this.dienTich = dienTich;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
