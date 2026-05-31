package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.HoaDonRepository;

@Service
public class HoaDonService {

    @Autowired
    private HoaDonRepository hoaDonRepository;

    // Định nghĩa đơn giá cố định
    private final Double DON_GIA_DIEN = 3500.0;  // 3.500đ / kWh
    private final Double DON_GIA_NUOC = 15000.0; // 15.000đ / m3

    // Lấy tất cả hóa đơn hệ thống (Dành cho Admin quản lý)
    public List<HoaDon> getAllHoaDon() {
        return hoaDonRepository.findAll();
    }

    // Tìm một hóa đơn cụ thể theo ID
    public Optional<HoaDon> getHoaDonById(Long id) {
        return hoaDonRepository.findById(id);
    }

    // Xem danh sách hóa đơn của riêng 1 khách thuê (Dành cho User xem ở trang cá nhân)
    public List<HoaDon> getHoaDonCuaKhach(Long nguoiDungId) {
        return hoaDonRepository.findByNguoiDungId(nguoiDungId);
    }

    // Logic Tự Động Tính Tiền và Lưu Hóa Đơn
    public HoaDon taoHoacCapNhatHoaDon(HoaDon hoaDon) {
        // 1. Tính tiền điện: (Số mới - Số cũ) * Đơn giá
        int luongDienTieuThu = hoaDon.getSoDienMoi() - hoaDon.getSoDienCu();
        if (luongDienTieuThu < 0) luongDienTieuThu = 0; // Tránh lỗi Admin nhập ngược số
        hoaDon.setTienDien(luongDienTieuThu * DON_GIA_DIEN);

        // 2. Tính tiền nước: (Số mới - Số cũ) * Đơn giá
        int luongNuocTieuThu = hoaDon.getSoNuocMoi() - hoaDon.getSoNuocCu();
        if (luongNuocTieuThu < 0) luongNuocTieuThu = 0;
        hoaDon.setTienNuoc(luongNuocTieuThu * DON_GIA_NUOC);

        // 3. Tính Tổng Tiền = Tiền phòng + Tiền điện + Tiền nước + Phí khác (wifi, rác...)
        Double tongTien = hoaDon.getTienPhong() 
                        + hoaDon.getTienDien() 
                        + hoaDon.getTienNuoc() 
                        + (hoaDon.getPhiKhac() != null ? hoaDon.getPhiKhac() : 0.0);
        hoaDon.setTongTien(tongTien);

        // Mặc định hóa đơn mới tạo sẽ ở trạng thái chưa đóng tiền
        if (hoaDon.getTrangThaiThanhToan() == null) {
            hoaDon.setTrangThaiThanhToan("CHUADONG");
        }

        return hoaDonRepository.save(hoaDon);
    }

    // Hàm cập nhật trạng thái thanh toán (Dùng khi khách upload ảnh bill hoặc Admin duyệt hóa đơn)
    public void capNhatTrangThaiThanhToan(Long idHoaDon, String trangThaiMoi, String tenFileAnh) {
        hoaDonRepository.findById(idHoaDon).ifPresent(hoaDon -> {
            hoaDon.setTrangThaiThanhToan(trangThaiMoi);
            if (tenFileAnh != null) {
                hoaDon.setAnhChuyenKhoan(tenFileAnh);
            }
            hoaDonRepository.save(hoaDon);
        });
    }

}
