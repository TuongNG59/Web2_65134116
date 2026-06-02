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
import com.qlpt.nguyenhuynhtuong65134116.Repositories.HoaDonRepository;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.ThongBaoRepository;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.YeuCauThueRepository;
import com.qlpt.nguyenhuynhtuong65134116.Services.NguoiDungService;
import com.qlpt.nguyenhuynhtuong65134116.Services.PhongTroService;

@Controller
@RequestMapping("/admin") // Gom tất cả các đường dẫn của Admin vào cụm /admin
public class AdminController {

    @Autowired
    private PhongTroService phongTroService;
    
    @Autowired
    private NguoiDungService nguoiDungService;
    
    @Autowired
    private YeuCauThueRepository yeuCauThueRepository;
    
    @Autowired
    private ThongBaoRepository thongBaoRepository;

    @Autowired
    private HoaDonRepository hoaDonRepository;
    
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

 // 5. Xử lý Xóa phòng
    @GetMapping("/phong/xoa/{id}")
    public String xoaPhong(@PathVariable(value = "id") Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        try {
            // GỠ LIÊN KẾT trong bảng hoadon 
            // Tìm tất cả hóa đơn dính tới mã phòng này và chuyển mã phòng về null
            java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon> dsHoaDon = hoaDonRepository.findAll().stream()
                .filter(hd -> hd.getPhongTro() != null && hd.getPhongTro().getId().equals(id))
                .toList();
            for (com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon hd : dsHoaDon) {
                hd.setPhongTro(null); // Bẻ gãy răng buộc khóa ngoại với phòng sắp xóa
                hoaDonRepository.save(hd);
            }

            //XÓA SẠCH các yêu cầu thuê liên quan đến phòng này
            java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue> dsYeuCau = yeuCauThueRepository.findAll().stream()
                .filter(yc -> yc.getPhongTro() != null && yc.getPhongTro().getId().equals(id))
                .toList();
            yeuCauThueRepository.deleteAll(dsYeuCau);

            //GIẢI PHÓNG người dùng đang thuê phòng này (nếu có) về trạng thái chưa có phòng
            java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung> dsNguoiDung = nguoiDungService.layTatCaNguoiDung().stream()
                .filter(u -> u.getPhongTro() != null && u.getPhongTro().getId().equals(id))
                .toList();
            for (com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung u : dsNguoiDung) {
                u.setPhongTro(null); // Cho người dùng ra khỏi phòng
                nguoiDungService.capNhatThongTin(u);
            }

            //CHÍNH THỨC xóa phòng trọ khỏi CSDL khi đã sạch ràng buộc
            phongTroService.deletePhongTro(id);
            
        } catch (Exception e) {
            e.printStackTrace();

        }
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
    
    // Xem chi tiết thông tin một tài khoản
    @GetMapping("/tai-khoan/chi-tiet/{id}")
    public String chiTietTaiKhoan(@PathVariable("id") Long id, Model model) {
        com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung user = nguoiDungService.findById(id);
        if (user != null) {
            model.addAttribute("khachHang", user);
            return "admin/admin_chi_tiet_tai_khoan";
        }
        return "redirect:/admin/tai-khoan?loi_khong_tim_thay_user";
    }
    
    @GetMapping("/tai-khoan/xoa/{id}")
    public String xoaTaiKhoan(@PathVariable("id") Long id, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
    	try {
            com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung user = nguoiDungService.findById(id);
            if (user != null) {
                // 1. Nếu người dùng đang ở trong phòng, giải phóng phòng đó về trạng thái TRONG
                if (user.getPhongTro() != null) {
                    phongTroService.capNhatTrangThai(user.getPhongTro().getId(), "TRONG");
                }
                
                // 2. XÓA SẠCH các Yêu Cầu Thuê của người dùng này để tránh kẹt khóa ngoại
                java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue> dsYeuCau = yeuCauThueRepository.findAll().stream()
                    .filter(yc -> yc.getNguoiDung() != null && yc.getNguoiDung().getId().equals(id))
                    .toList();
                yeuCauThueRepository.deleteAll(dsYeuCau);
                
                // 3. XÓA SẠCH Lịch sử chat (Thông báo) liên quan đến người dùng này
                java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.ThongBao> dsChat = thongBaoRepository.findAll().stream()
                    .filter(t -> id.equals(t.getMaNguoiGui()) || id.equals(t.getMaNguoiNhan()))
                    .toList();
                thongBaoRepository.deleteAll(dsChat);
                
                // 4. GỠ LIÊN KẾT Hóa Đơn (Biến hóa đơn của người này thành hóa đơn ẩn danh để giữ lại báo cáo tài chính)
                // Hoặc nếu cậu muốn xóa sạch luôn hóa đơn thì đổi thành: hoaDonRepository.deleteAll(dsHoaDon);
                java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon> dsHoaDon = hoaDonRepository.findByNguoiDungId(id);
                for (com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon hd : dsHoaDon) {
                    hd.setNguoiDung(null); // Gỡ liên kết người dùng
                    hoaDonRepository.save(hd);
                }
                
                // 5. CHÍNH THỨC xóa tài khoản người dùng
                nguoiDungService.deleteNguoiDung(id);
                redirectAttributes.addFlashAttribute("messageSuccess", "🗑️ Đã dọn dẹp dữ liệu liên quan và xóa tài khoản thành công!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("messageError", "❌ Lỗi hệ thống: " + e.getMessage());
        }
        return "redirect:/admin/tai-khoan";
    }
}