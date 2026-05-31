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
@Table(name = "thongbao")
public class ThongBao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manguoigui", nullable = false)
    private NguoiDung nguoiGui;

    @ManyToOne
    @JoinColumn(name = "manguoinhan", nullable = false)
    private NguoiDung nguoiNhan;

    @Column(name = "noidung", length = 1000)
    private String noiDung;

    @Column(name = "thoigian")
    private LocalDateTime thoiGian = LocalDateTime.now();

    @Column(name = "dadoc")
    private Boolean daDoc = false;

    public ThongBao() {
    }

    public ThongBao(Long id, NguoiDung nguoiGui, NguoiDung nguoiNhan, String noiDung, LocalDateTime thoiGian,
            Boolean daDoc) {
        this.id = id;
        this.nguoiGui = nguoiGui;
        this.nguoiNhan = nguoiNhan;
        this.noiDung = noiDung;
        this.thoiGian = thoiGian;
        this.daDoc = daDoc;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NguoiDung getNguoiGui() {
        return nguoiGui;
    }

    public void setNguoiGui(NguoiDung nguoiGui) {
        this.nguoiGui = nguoiGui;
    }

    public NguoiDung getNguoiNhan() {
        return nguoiNhan;
    }

    public void setNguoiNhan(NguoiDung nguoiNhan) {
        this.nguoiNhan = nguoiNhan;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public LocalDateTime getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(LocalDateTime thoiGian) {
        this.thoiGian = thoiGian;
    }

    public Boolean getDaDoc() {
        return daDoc;
    }

    public void setDaDoc(Boolean daDoc) {
        this.daDoc = daDoc;
    }
}