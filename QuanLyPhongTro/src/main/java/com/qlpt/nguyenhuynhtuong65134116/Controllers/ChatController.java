package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;
import com.qlpt.nguyenhuynhtuong65134116.Services.ThongBaoService;

@Controller
public class ChatController {

    @Autowired
    private ThongBaoService thongBaoService;

    @Autowired
    private NguoiDungService nguoiDungService;

    private final Long ADMIN_ID = 1L;

    // =============== GIAO DIỆN CHAT CỦA KHÁCH THUÊ ===============
    @GetMapping("/tro-chuyen")
    public String chatCuaKhach(Model model, Authentication authentication) {
        // Lấy thông tin khách đang đăng nhập
    	NguoiDung khachHang = nguoiDungService.findByTenDangNhap(authentication.getName()).orElse(null);
        
        model.addAttribute("lichSuChat", thongBaoService.getCuocTroChuyenVoiAdmin(khachHang.getId()));
        model.addAttribute("myId", khachHang.getId());
        model.addAttribute("tenKhach", khachHang.getHoVaTen());
        return "chat_khach"; // Tạo file chat_khach.html
    }

    // Khách nhấn gửi tin nhắn
    @PostMapping("/tro-chuyen/gui")
    public String khachGuiTin(@RequestParam("noiDung") String noiDung, Authentication authentication) {
    	NguoiDung khachHang = nguoiDungService.findByTenDangNhap(authentication.getName()).orElse(null);
        thongBaoService.guiTinNhan(khachHang.getId(), ADMIN_ID, noiDung);
        return "redirect:/tro-chuyen";
    }

    // =============== GIAO DIỆN CHAT CỦA ADMIN ===============
    // Xem danh sách và chat với 1 khách cụ thể theo id
    @GetMapping("/admin/tro-chuyen/{khachId}")
    public String chatCuaAdmin(@PathVariable("khachId") Long khachId, Model model) {
        // Lấy tất cả người dùng dưới DB lên
        List<NguoiDung> tatCa = nguoiDungService.layTatCaNguoiDung();
        
        // Mẹo nhỏ: Chỉ giữ lại những người có vai trò là USER (Khách thuê) trong danh sách bên trái
        List<NguoiDung> danhSachKhachThu_e = tatCa.stream()
            .filter(u -> "USER".equalsIgnoreCase(u.getVaiTro()))
            .collect(java.util.stream.Collectors.toList());
            
        model.addAttribute("danhSachKhach", danhSachKhachThu_e);
        model.addAttribute("lichSuChat", thongBaoService.getCuocTroChuyenVoiAdmin(khachId));
        model.addAttribute("khachDangChat", nguoiDungService.findById(khachId));
        model.addAttribute("myId", ADMIN_ID);
        
        return "admin/chat_admin";
    }

    // Admin nhấn gửi trả lời khách
    @PostMapping("/admin/tro-chuyen/gui")
    public String adminGuiTin(@RequestParam("khachId") Long khachId, @RequestParam("noiDung") String noiDung) {
        thongBaoService.guiTinNhan(ADMIN_ID, khachId, noiDung);
        return "redirect:/admin/tro-chuyen/" + khachId;
    }
}
