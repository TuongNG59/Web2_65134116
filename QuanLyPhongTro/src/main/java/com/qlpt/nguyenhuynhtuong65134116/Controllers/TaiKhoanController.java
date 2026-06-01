package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Services.EmailService;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;

@Controller
public class TaiKhoanController {

	@Autowired
    private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;
	
    @Autowired
    private EmailService emailService;
    
    @Autowired
    private NguoiDungService nguoiDungService;

    // 1. Hiển thị trang Đăng Nhập
    @GetMapping("/dangnhap")
    public String xemTrangDangNhap() {
        return "dangnhap"; // Trả về file dangnhap.html
    }

    // 2. Hiển thị trang Đăng Ký
    @GetMapping("/dangky")
    public String xemTrangDangKy(Model model) {
        model.addAttribute("nguoiDung", new NguoiDung()); // Tạo một object rỗng để hứng dữ liệu từ Form
        return "dangky"; // Trả về file dangky.html
    }

    // 3. Xử lý khi người dùng bấm nút "Đăng ký"
    @PostMapping("/dangky")
    public String xuLyDangKy(@ModelAttribute NguoiDung nguoiDung) {
    	// ... (Bên trong hàm xử lý Đăng Ký POST)
    	String matKhauMaHoa = passwordEncoder.encode(nguoiDung.getMatKhau());
        nguoiDung.setMatKhau(matKhauMaHoa);
        // 1. Sinh ra một mã Token ngẫu nhiên và gán cho người dùng
        String token = UUID.randomUUID().toString();
        nguoiDung.setTokenKichHoat(token);
        
        // 2. ÉP trạng thái thành false (Chưa kích hoạt) không cho đăng nhập ngay
        nguoiDung.setTrangThai(false); 
        
        // Lưu xuống Database
        nguoiDungService.luuTaiKhoan(nguoiDung); 

        // 3. Tiến hành gửi Email chứa link kích hoạt
        String linkKichHoat = "http://localhost:8080/kich-hoat?token=" + token;
        String noiDungMail = "Chào " + nguoiDung.getHoVaTen() + ",\n\n"
                           + "Cảm ơn bạn đã đăng ký tài khoản tại Nhà Trọ Adou.\n"
                           + "Vui lòng click vào đường link dưới đây để kích hoạt tài khoản của bạn:\n"
                           + linkKichHoat + "\n\n"
                           + "Trân trọng,\nBan Quản Lý.";
                           
        emailService.guiEmail(nguoiDung.getEmail(), "Xác nhận kích hoạt tài khoản Nhà Trọ Adou", noiDungMail);

        // Chuyển hướng về trang đăng nhập kèm thông báo
        return "redirect:/dangnhap?dang_ky_thanh_cong_vui_long_check_mail";
    }
    
    @GetMapping("/kich-hoat")
    public String kichHoatTaiKhoan(@org.springframework.web.bind.annotation.RequestParam("token") String token) {
        // Tìm người dùng nào đang giữ mã Token này
        com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung user = nguoiDungService.findByTokenKichHoat(token).orElse(null);
        
        if (user != null) {
            // Mở khóa tài khoản!
            user.setTrangThai(true); 
            // Xóa mã Token đi vì đã dùng xong
            user.setTokenKichHoat(null); 
            
            nguoiDungService.capNhatThongTin(user);
            return "redirect:/dangnhap?kich_hoat_thanh_cong";
        }
        
        return "redirect:/dangnhap?kich_hoat_that_bai";
    }
}
