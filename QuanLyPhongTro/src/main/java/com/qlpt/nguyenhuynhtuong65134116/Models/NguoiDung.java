package com.qlpt.nguyenhuynhtuong65134116.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "nguoidung")
public class NguoiDung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tendangnhap", nullable = false, unique = true)
    private String tenDangNhap;

    @Column(name = "matkhau", nullable = false)
    private String matKhau;

    @Column(name = "hovaten")
    private String hoVaTen;

    @Column(name = "sodienthoai")
    private String soDienThoai;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "email")
    private String email;

    @Column(name = "vaitro")
    private String vaiTro; 

    @Column(name = "trangthai")
    private Boolean trangThai = true; 

    @ManyToOne
    @JoinColumn(name = "maphong") 
    private PhongTro phongTro;

    public NguoiDung() {
    }

    public NguoiDung(Long id, String tenDangNhap, String matKhau, String hoVaTen, String soDienThoai, String cccd,
			String email, String vaiTro, Boolean trangThai, PhongTro phongTro) {
		this.id = id;
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.hoVaTen = hoVaTen;
		this.soDienThoai = soDienThoai;
		this.cccd = cccd;
		this.email = email;
		this.vaiTro = vaiTro;
		this.trangThai = trangThai;
		this.phongTro = phongTro;
	}

    private String tokenKichHoat;

    private String maOTP;

    private java.time.LocalDateTime hanSuDungOTP;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getCccd() {
		return cccd;
	}

	public void setCccd(String cccd) {
		this.cccd = cccd;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	public Boolean getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(Boolean trangThai) {
		this.trangThai = trangThai;
	}

	public PhongTro getPhongTro() {
		return phongTro;
	}

	public void setPhongTro(PhongTro phongTro) {
		this.phongTro = phongTro;
	}

	public String getTokenKichHoat() {
		return tokenKichHoat;
	}

	public void setTokenKichHoat(String tokenKichHoat) {
		this.tokenKichHoat = tokenKichHoat;
	}

	public String getMaOTP() {
		return maOTP;
	}

	public void setMaOTP(String maOTP) {
		this.maOTP = maOTP;
	}

	public java.time.LocalDateTime getHanSuDungOTP() {
		return hanSuDungOTP;
	}

	public void setHanSuDungOTP(java.time.LocalDateTime hanSuDungOTP) {
		this.hanSuDungOTP = hanSuDungOTP;
	}
	
}