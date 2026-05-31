package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;

@Controller
public class TaiKhoanController {

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
        // Gọi Service lưu xuống Database (mật khẩu đã được tự động mã hóa BCrypt)
        nguoiDungService.dangKyTaiKhoan(nguoiDung);
        
        // Lưu xong thì chuyển hướng người dùng về trang đăng nhập kèm theo thông báo
        return "redirect:/dangnhap?thanhcong";
    }
}
