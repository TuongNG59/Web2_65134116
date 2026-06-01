package com.qlpt.nguyenhuynhtuong65134116.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "thongbao")
public class ThongBao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "manguoigui", nullable = false)
    private Long maNguoiGui;

    @Column(name = "manguoinhan", nullable = false)
    private Long maNguoiNhan;

    @Column(name = "noidung", nullable = false, columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "thoigian")
    private LocalDateTime thoiGian = LocalDateTime.now();

    @Column(name = "dadoc")
    private boolean daDoc = false;

    // --- Constructors ---
    public ThongBao() {}

    public ThongBao(Long maNguoiGui, Long maNguoiNhan, String noiDung) {
        this.maNguoiGui = maNguoiGui;
        this.maNguoiNhan = maNguoiNhan;
        this.noiDung = noiDung;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMaNguoiGui() {
		return maNguoiGui;
	}

	public void setMaNguoiGui(Long maNguoiGui) {
		this.maNguoiGui = maNguoiGui;
	}

	public Long getMaNguoiNhan() {
		return maNguoiNhan;
	}

	public void setMaNguoiNhan(Long maNguoiNhan) {
		this.maNguoiNhan = maNguoiNhan;
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

	public boolean isDaDoc() {
		return daDoc;
	}

	public void setDaDoc(boolean daDoc) {
		this.daDoc = daDoc;
	}

    
    
}