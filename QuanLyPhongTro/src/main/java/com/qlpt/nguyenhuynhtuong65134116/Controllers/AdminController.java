package com.qlpt.nguyenhuynhtuong65134116.Controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;
import com.qlpt.nguyenhuynhtuong65134116.Services.PhongTroService;

@Controller
@RequestMapping("/admin") // Gom tất cả các đường dẫn của Admin vào cụm /admin
public class AdminController {

    @Autowired
    private PhongTroService phongTroService;
    
    @Autowired
    private NguoiDungService nguoiDungService;
    
    // 1. Trang quản lý danh sách phòng trọ
    @GetMapping("/phong")
    public String quanLyPhong(Model model) {
    	List<PhongTro> danhSachPhong = phongTroService.getAllPhongTro();
    	
        model.addAttribute("danhSachPhong", phongTroService.getAllPhongTro());
        model.addAttribute("phongTroService", phongTroService);
        return "admin/quanlyphong";
    }

    // 2. Mở form thêm phòng mới
    @GetMapping("/phong/them")
    public String formThemPhong(Model model) {
        model.addAttribute("phongTro", new PhongTro());
        return "admin/formphong";
    }

    // 3. Xử lý lưu phòng (Cả Thêm mới và Cập nhật)
    @PostMapping("/phong/luu")
    public String luuPhong(@ModelAttribute PhongTro phongTro) {
        if (phongTro.getTrangThai() == null) {
            phongTro.setTrangThai("TRONG"); // Mặc định phòng mới tạo là TRONG
        }
        phongTroService.savePhongTro(phongTro);
        return "redirect:/admin/phong"; // Lưu xong trở về trang danh sách
    }

    // 4. Mở form sửa thông tin phòng
    @GetMapping("/phong/sua/{id}")
    public String formSuaPhong(@PathVariable(value = "id") Long id, Model model) {
        phongTroService.getPhongTroById(id).ifPresent(phong -> model.addAttribute("phongTro", phong));
        return "admin/formphong";
    }

    // 5. Xóa phòng
    @GetMapping("/phong/xoa/{id}")
    public String xoaPhong(@PathVariable(value = "id") Long id) {
        phongTroService.deletePhongTro(id);
        return "redirect:/admin/phong";
    }
    
    // 6. Trang quản lý danh sách tài khoản người dùng
    @GetMapping("/tai-khoan")
    public String danhSachTaiKhoan(Model model) {
        List<com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung> danhSach = nguoiDungService.layTatCaNguoiDung();
        model.addAttribute("danhSachNguoiDung", danhSach);
        
        // SỬA Ở ĐÂY: Trả về file nằm trong thư mục templates/admin/
        return "admin/admin_quan_ly_tai_khoan"; 
    }

    // 7. Xử lý Khóa hoặc Mở khóa tài khoản
    @GetMapping("/tai-khoan/doi-trang-thai/{id}")
    public String doiTrangThaiTaiKhoan(@PathVariable("id") Long id) {
        com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung user = nguoiDungService.findById(id);
        if (user != null) {
            user.setTrangThai(!user.getTrangThai()); // Đảo trạng thái true <-> false
            nguoiDungService.capNhatThongTin(user);
        }
        return "redirect:/admin/tai-khoan"; // Đổi xong quay về lại trang danh sách tài khoản
    }
}