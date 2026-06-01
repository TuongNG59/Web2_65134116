package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;
import com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;
import com.qlpt.nguyenhuynhtuong65134116.Services.PhongTroService;
import com.qlpt.nguyenhuynhtuong65134116.Services.YeuCauThueService;

@Controller
public class TrangChuController {

    @Autowired
    private PhongTroService phongTroService;
    
    @Autowired
    private YeuCauThueService yeuCauThueService;

    @Autowired
    private NguoiDungService nguoiDungService;

    @GetMapping("/")
    public String xemTrangChu(Model model) {
        // Gọi Service lấy danh sách các phòng có trạng thái "TRONG"
    	List<PhongTro> danhSachPhong = phongTroService.getPhongTrong();
        
        // Đóng gói danh sách này vào Model để gửi sang file HTML
        model.addAttribute("danhSachPhong", danhSachPhong);
        
        // Trả về tên file HTML là "trangchu" (Spring Boot tự hiểu là trangchu.html)
        return "trangchu";
    }
    
    @PostMapping("/thue-phong/{id}")
    public String guiYeuCauThuePhong(@PathVariable(value = "id") Long idPhong, @AuthenticationPrincipal UserDetails userDetails) {
        // Chưa đăng nhập thì đá về trang đăng nhập
        if (userDetails == null) {
            return "redirect:/dangnhap"; 
        }
        
        NguoiDung khach = nguoiDungService.findByTenDangNhap(userDetails.getUsername()).orElse(null);
        PhongTro phong = phongTroService.getPhongTroById(idPhong).orElse(null);
        
        if (khach != null && phong != null) {
            YeuCauThue yeuCau = new YeuCauThue();
            yeuCau.setNguoiDung(khach);
            yeuCau.setPhongTro(phong);
            yeuCau.setGhiChu("Tôi muốn thuê phòng này");
            yeuCau.setTrangThai("CHODUYET"); // Chờ Admin duyệt
            
            yeuCauThueService.guiYeuCauThue(yeuCau);
        }
        
        return "redirect:/?gui_thanh_cong"; // Trả về trang chủ kèm thông báo thành công trên URL
    }
}
