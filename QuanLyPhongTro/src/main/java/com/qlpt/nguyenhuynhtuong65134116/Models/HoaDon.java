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
@Table(name = "hoadon")
public class HoaDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "maphong")
    private PhongTro phongTro;

    @ManyToOne
    @JoinColumn(name = "manguoidung")
    private NguoiDung nguoiDung;

    @Column(name = "thangnam", nullable = false)
    private String thangNam;

    @Column(name = "ngaytao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "sodiencu")
    private Integer soDienCu;

    @Column(name = "sodienmoi")
    private Integer soDienMoi;

    @Column(name = "tiendien")
    private Double tienDien;

    @Column(name = "sonuoccu")
    private Integer soNuocCu;

    @Column(name = "sonuocmoi")
    private Integer soNuocMoi;

    @Column(name = "tiennuoc")
    private Double tienNuoc;

    @Column(name = "phikhac")
    private Double phiKhac;

    @Column(name = "tienphong")
    private Double tienPhong;

    @Column(name = "tongtien")
    private Double tongTien;

    @Column(name = "trangthaithanhtoan")
    private String trangThaiThanhToan;

    @Column(name = "anhchuyenkhoan")
    private String anhChuyenKhoan;
    @jakarta.persistence.Lob
    @jakarta.persistence.Column(columnDefinition = "LONGTEXT")
    private String minhChung;
    
    public HoaDon() {
    }

    public HoaDon(Long id, PhongTro phongTro, NguoiDung nguoiDung, String thangNam, LocalDateTime ngayTao,
            Integer soDienCu, Integer soDienMoi, Double tienDien, Integer soNuocCu, Integer soNuocMoi,
            Double tienNuoc, Double phiKhac, Double tienPhong, Double tongTien, String trangThaiThanhToan,
            String anhChuyenKhoan) {
        this.id = id;
        this.phongTro = phongTro;
        this.nguoiDung = nguoiDung;
        this.thangNam = thangNam;
        this.ngayTao = ngayTao;
        this.soDienCu = soDienCu;
        this.soDienMoi = soDienMoi;
        this.tienDien = tienDien;
        this.soNuocCu = soNuocCu;
        this.soNuocMoi = soNuocMoi;
        this.tienNuoc = tienNuoc;
        this.phiKhac = phiKhac;
        this.tienPhong = tienPhong;
        this.tongTien = tongTien;
        this.trangThaiThanhToan = trangThaiThanhToan;
        this.anhChuyenKhoan = anhChuyenKhoan;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PhongTro getPhongTro() {
		return phongTro;
	}

	public void setPhongTro(PhongTro phongTro) {
		this.phongTro = phongTro;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	public String getThangNam() {
		return thangNam;
	}

	public void setThangNam(String thangNam) {
		this.thangNam = thangNam;
	}

	public LocalDateTime getNgayTao() {
		return ngayTao;
	}

	public void setNgayTao(LocalDateTime ngayTao) {
		this.ngayTao = ngayTao;
	}

	public Integer getSoDienCu() {
		return soDienCu;
	}

	public void setSoDienCu(Integer soDienCu) {
		this.soDienCu = soDienCu;
	}

	public Integer getSoDienMoi() {
		return soDienMoi;
	}

	public void setSoDienMoi(Integer soDienMoi) {
		this.soDienMoi = soDienMoi;
	}

	public Double getTienDien() {
		return tienDien;
	}

	public void setTienDien(Double tienDien) {
		this.tienDien = tienDien;
	}

	public Integer getSoNuocCu() {
		return soNuocCu;
	}

	public void setSoNuocCu(Integer soNuocCu) {
		this.soNuocCu = soNuocCu;
	}

	public Integer getSoNuocMoi() {
		return soNuocMoi;
	}

	public void setSoNuocMoi(Integer soNuocMoi) {
		this.soNuocMoi = soNuocMoi;
	}

	public Double getTienNuoc() {
		return tienNuoc;
	}

	public void setTienNuoc(Double tienNuoc) {
		this.tienNuoc = tienNuoc;
	}

	public Double getPhiKhac() {
		return phiKhac;
	}

	public void setPhiKhac(Double phiKhac) {
		this.phiKhac = phiKhac;
	}

	public Double getTienPhong() {
		return tienPhong;
	}

	public void setTienPhong(Double tienPhong) {
		this.tienPhong = tienPhong;
	}

	public Double getTongTien() {
		return tongTien;
	}

	public void setTongTien(Double tongTien) {
		this.tongTien = tongTien;
	}

	public String getTrangThaiThanhToan() {
		return trangThaiThanhToan;
	}

	public void setTrangThaiThanhToan(String trangThaiThanhToan) {
		this.trangThaiThanhToan = trangThaiThanhToan;
	}

	public String getAnhChuyenKhoan() {
		return anhChuyenKhoan;
	}

	public void setAnhChuyenKhoan(String anhChuyenKhoan) {
		this.anhChuyenKhoan = anhChuyenKhoan;
	}

	public String getMinhChung() {
        return minhChung;
    }

    public void setMinhChung(String minhChung) {
        this.minhChung = minhChung;
    }
}
