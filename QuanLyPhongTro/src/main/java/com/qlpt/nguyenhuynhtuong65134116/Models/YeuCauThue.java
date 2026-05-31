package com.qlpt.nguyenhuynhtuong65134116.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "yeucauthue")
public class YeuCauThue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manguoidung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "maphong", nullable = false)
    private PhongTro phongTro;

    @Column(name = "ghichu")
    private String ghiChu;

    @Column(name = "thoigian")
    private LocalDateTime thoiGian = LocalDateTime.now();

    @Column(name = "trangthai")
    private String trangThai; 

    public YeuCauThue() {
    }

    public YeuCauThue(Long id, NguoiDung nguoiDung, PhongTro phongTro, String ghiChu, LocalDateTime thoiGian,
            String trangThai) {
        this.id = id;
        this.nguoiDung = nguoiDung;
        this.phongTro = phongTro;
        this.ghiChu = ghiChu;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NguoiDung getNguoiDung() {
        return nguoiDung;
    }

    public void setNguoiDung(NguoiDung nguoiDung) {
        this.nguoiDung = nguoiDung;
    }

    public PhongTro getPhongTro() {
        return phongTro;
    }

    public void setPhongTro(PhongTro phongTro) {
        this.phongTro = phongTro;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}