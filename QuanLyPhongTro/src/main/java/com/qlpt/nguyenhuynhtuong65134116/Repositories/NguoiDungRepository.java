package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;

@Repository
public interface NguoiDungRepository extends JpaRepository<NguoiDung, Long> {
    // Hàm tìm kiếm người dùng bằng tên đăng nhập phục vụ cho Login
    Optional<NguoiDung> findByTenDangNhap(String tenDangNhap);
    
    // Hàm tìm kiếm bằng Email phục vụ cho tính năng Quên mật khẩu
    Optional<NguoiDung> findByEmail(String email);
    // Tìm người dùng bằng Token kích hoạt (dùng khi khách bấm vào link Gmail)
    Optional<NguoiDung> findByTokenKichHoat(String token);
    // Tìm người dùng bằng mã OTP (dùng khi khách nhập 6 số)
    Optional<NguoiDung> findByMaOTP(String otp);
}