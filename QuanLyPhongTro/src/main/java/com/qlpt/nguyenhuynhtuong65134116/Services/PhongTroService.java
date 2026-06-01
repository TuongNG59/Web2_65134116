package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.NguoiDungRepository;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.PhongTroRepository;

@Service
public class PhongTroService {

    @Autowired
    private PhongTroRepository phongTroRepository;
    
    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    // Lấy toàn bộ danh sách phòng trọ
    public List<PhongTro> getAllPhongTro() {
        return phongTroRepository.findAll();
    }

    // Tìm một phòng trọ cụ thể bằng ID
    public Optional<PhongTro> getPhongTroById(Long id) {
        return phongTroRepository.findById(id);
    }

    // Thêm mới hoặc cập nhật thông tin phòng trọ
    public PhongTro savePhongTro(PhongTro phongTro) {
        return phongTroRepository.save(phongTro);
    }

    // Xóa một phòng trọ theo ID
    public void deletePhongTro(Long id) {
        phongTroRepository.deleteById(id);
    }

    // Lấy danh sách các phòng còn trống để hiển thị cho khách vãng lai xem
    public List<PhongTro> getPhongTrong() {
        return phongTroRepository.findByTrangThai("TRONG");
    }

    // Hàm cập nhật nhanh trạng thái phòng
    public void capNhatTrangThai(Long idPhong, String trangThaiMoi) {
        phongTroRepository.findById(idPhong).ifPresent(phong -> {
            phong.setTrangThai(trangThaiMoi);
            phongTroRepository.save(phong);
        });
    }
    
    // HÀM MỚI THÊM: Tìm tên người thuê dựa vào phòng
    public String getTenNguoiThue(Long idPhong) {
        // Xuống DB tìm xem có User nào đang có phong_tro_id trùng với idPhong không
        return nguoiDungRepository.findAll().stream()
            .filter(user -> user.getPhongTro() != null && user.getPhongTro().getId().equals(idPhong))
            .map(NguoiDung::getHoVaTen)
            .findFirst()
            .orElse("Chưa có"); // Nếu không có ai thuê thì hiện "Chưa có"
    }
}
