package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Services.EmailService;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;

@Controller
@RequestMapping("/quen-mat-khau")
public class QuenMatKhauController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 1. Mở form nhập Email
    @GetMapping
    public String hienThiTrangQuenMatKhau() {
        return "quen_mat_khau";
    }

    // 2. Xử lý khi khách bấm nút "Gửi mã OTP"
    @PostMapping("/gui-otp")
    public String xuLyGuiOTP(@RequestParam("email") String email, Model model) {
        NguoiDung user = nguoiDungService.findByEmail(email).orElse(null);
        
        if (user != null) {
            // Random ra 6 con số ngẫu nhiên
            String otp = String.format("%06d", new Random().nextInt(999999));
            
            // Lưu OTP vào DB và cài đặt thời gian sống là 5 phút
            user.setMaOTP(otp);
            user.setHanSuDungOTP(LocalDateTime.now().plusMinutes(5));
            nguoiDungService.capNhatThongTin(user); // Lưu lại
            
            // Gửi email cho khách
            String noiDung = "Chào bạn,\n\nMã OTP để đặt lại mật khẩu của bạn là: " + otp + "\n"
                           + "Mã này sẽ hết hạn sau 5 phút.\n\nTrân trọng,\nBan Quản Lý.";
            emailService.guiEmail(user.getEmail(), "Mã OTP Khôi Phục Mật Khẩu", noiDung);
            
            // Chuyển sang form nhập OTP, mang theo cái email để điền sẵn cho khách
            model.addAttribute("email", email);
            return "nhap_otp"; 
        }
        
        // Nếu nhập bậy bạ email không có trong DB thì đuổi về
        return "redirect:/quen-mat-khau?loi_email_khong_ton_tai";
    }

    // 3. Xử lý khi khách nhập OTP và Mật khẩu mới
    @PostMapping("/dat-lai-mat-khau")
    public String xuLyDatLaiMatKhau(@RequestParam("email") String email,
                                    @RequestParam("maOTP") String maOTP,
                                    @RequestParam("matKhauMoi") String matKhauMoi) {
        
        NguoiDung user = nguoiDungService.findByEmail(email).orElse(null);
        
        if (user != null) {
            // Kiểm tra xem OTP có khớp và còn hạn sử dụng không
            if (maOTP.equals(user.getMaOTP()) && user.getHanSuDungOTP().isAfter(LocalDateTime.now())) {
                
                // Cập nhật mật khẩu mới (nhớ băm ra)
                user.setMatKhau(passwordEncoder.encode(matKhauMoi));
                
                // Dùng xong thì xóa OTP đi cho an toàn
                user.setMaOTP(null);
                user.setHanSuDungOTP(null);
                
                nguoiDungService.capNhatThongTin(user);
                
                return "redirect:/dangnhap?doi_mat_khau_thanh_cong";
            }
        }
        
        // OTP sai hoặc hết hạn
        return "redirect:/quen-mat-khau?loi_otp";
    }
}
