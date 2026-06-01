package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon;
import com.qlpt.nguyenhuynhtuong65134116.Services.HoaDonService;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;
import com.qlpt.nguyenhuynhtuong65134116.Services.PhongTroService;

@Controller
@RequestMapping("/admin/hoadon")
public class AdminHoaDonController {

    @Autowired
    private HoaDonService hoaDonService;

    @Autowired
    private PhongTroService phongTroService;

    @Autowired
    private NguoiDungService nguoiDungService;

    // 1. Xem danh sách toàn bộ hóa đơn
    @GetMapping
    public String danhSachHoaDon(Model model) {
        model.addAttribute("danhSachHoaDon", hoaDonService.getAllHoaDon());
        return "admin/quanlyhoadon";
    }

    // 2. Mở form lập hóa đơn mới
    @GetMapping("/them")
    public String formLapHoaDon(Model model) {
        model.addAttribute("hoaDon", new HoaDon());
        model.addAttribute("danhSachPhong", phongTroService.getAllPhongTro());
        model.addAttribute("danhSachUser", nguoiDungService.getAllNguoiDung());
        return "admin/formhoadon";
    }

    // 3. Xử lý lưu hóa đơn (Gọi logic tự động tính tiền từ Service)
    @PostMapping("/luu")
    public String luuHoaDon(@ModelAttribute HoaDon hoaDon) {
        hoaDonService.taoHoacCapNhatHoaDon(hoaDon);
        return "redirect:/admin/hoadon";
    }

    // 4. Xóa hóa đơn
    @GetMapping("/xoa/{id}")
    public String xoaHoaDon(@PathVariable(value = "id") Long id) {
        hoaDonService.deleteHoaDon(id);
        return "redirect:/admin/hoadon";
    }
    
    // 5. Sửa hoá đơn
    @GetMapping("/sua/{id}")
    public String formSuaHoaDon(@PathVariable(value = "id") Long id, Model model) {
        hoaDonService.getHoaDonById(id).ifPresent(hd -> model.addAttribute("hoaDon", hd));
        
        model.addAttribute("danhSachPhong", phongTroService.getAllPhongTro());
        model.addAttribute("danhSachUser", nguoiDungService.getAllNguoiDung());
        
        return "admin/formhoadon"; 
    }
}
