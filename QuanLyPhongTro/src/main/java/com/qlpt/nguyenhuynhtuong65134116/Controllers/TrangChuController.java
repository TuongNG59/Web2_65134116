package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;
import com.qlpt.nguyenhuynhtuong65134116.Services.PhongTroService;

@Controller
public class TrangChuController {

    @Autowired
    private PhongTroService phongTroService;

    @GetMapping("/")
    public String xemTrangChu(Model model) {
        // Gọi Service lấy danh sách các phòng có trạng thái "TRONG"
    	List<PhongTro> danhSachPhong = phongTroService.getPhongTrong();
        
        // Đóng gói danh sách này vào Model để gửi sang file HTML
        model.addAttribute("danhSachPhong", danhSachPhong);
        
        // Trả về tên file HTML là "trangchu" (Spring Boot tự hiểu là trangchu.html)
        return "trangchu";
    }
}
