package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.NguoiDungRepository;

@Service
public class NguoiDungService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    // Khởi tạo bộ mã hóa mật khẩu của Spring Security
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Lấy toàn bộ danh sách người dùng (Admin xem danh sách khách thuê)
    public List<NguoiDung> getAllNguoiDung() {
        return nguoiDungRepository.findAll();
    }

    // Tìm kiếm người dùng bằng ID
    public Optional<NguoiDung> getNguoiDungById(Long id) {
        return nguoiDungRepository.findById(id);
    }

    // Đăng ký tài khoản mới hoặc Thêm khách thuê
    public NguoiDung dangKyTaiKhoan(NguoiDung nguoiDung) {
        // Mã hóa mật khẩu thô thành chuỗi hash bảo mật trước khi lưu vào MySQL
        String matKhauMaHoa = passwordEncoder.encode(nguoiDung.getMatKhau());
        nguoiDung.setMatKhau(matKhauMaHoa);
        
        // Mặc định tài khoản mới tạo sẽ có vai trò là USER (Khách thuê)
        if (nguoiDung.getVaiTro() == null) {
            nguoiDung.setVaiTro("USER");
        }
        return nguoiDungRepository.save(nguoiDung);
    }

    // Cập nhật thông tin người dùng (Không đổi mật khẩu)
    public NguoiDung capNhatThongTin(NguoiDung nguoiDung) {
        return nguoiDungRepository.save(nguoiDung);
    }

    // Tìm kiếm tài khoản bằng tên đăng nhập
    public Optional<NguoiDung> findByTenDangNhap(String tenDangNhap) {
        return nguoiDungRepository.findByTenDangNhap(tenDangNhap);
    }

    // Tìm kiếm tài khoản bằng Email
    public Optional<NguoiDung> findByEmail(String email) {
        return nguoiDungRepository.findByEmail(email);
    }

    // Hàm đổi mật khẩu mới (Dùng khi người dùng chủ động đổi hoặc khi Quên mật khẩu)
    public void doiMatKhau(Long idNguoiDung, String matKhauMoi) {
        nguoiDungRepository.findById(idNguoiDung).ifPresent(nguoiDung -> {
            nguoiDung.setMatKhau(passwordEncoder.encode(matKhauMoi));
            nguoiDungRepository.save(nguoiDung);
        });
    }

    // Xóa tài khoản người dùng
    public void deleteNguoiDung(Long id) {
        nguoiDungRepository.deleteById(id);
    }
}