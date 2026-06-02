package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon;
import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;
import com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue;
import com.qlpt.nguyenhuynhtuong65134116.Services.HoaDonService;
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
    
    @Autowired
    private HoaDonService hoaDonService;

    @GetMapping("/")
    public String xemTrangChu(Model model, @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails) {
        // Gọi Service lấy danh sách các phòng có trạng thái "TRONG"
    	List<PhongTro> danhSachPhong = phongTroService.getPhongTrong();
        
        // Đóng gói danh sách này vào Model để gửi sang file HTML
        model.addAttribute("danhSachPhong", danhSachPhong);
        
        if (userDetails != null) {
            // Lọc tìm thông tin đầy đủ của người dùng hiện tại
            NguoiDung khach = nguoiDungService.findByTenDangNhap(userDetails.getUsername()).orElse(null);
            
            // Nếu tìm thấy khách và khách này thực sự đã được Admin duyệt phòng (phongTro != null)
            if (khach != null && khach.getPhongTro() != null) {
                model.addAttribute("tenPhongCuaToi", khach.getPhongTro().getTenPhong());
            }
        }
        
        // Trả về tên file HTML là "trangchu" (Spring Boot tự hiểu là trangchu.html)
        return "trangchu";
    }
    
    @PostMapping("/thue-phong/{id}")
    public String guiYeuCauThuePhong(@PathVariable(value = "id") Long idPhong, 
            @org.springframework.security.core.annotation.AuthenticationPrincipal org.springframework.security.core.userdetails.UserDetails userDetails,
            org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
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
            
            phong.setTrangThai("CHODUYET");
            phongTroService.savePhongTro(phong);
            redirectAttributes.addFlashAttribute("thongBaoThanhCong", "🎉 Gửi yêu cầu thuê " + phong.getTenPhong() + " thành công! Vui lòng đợi chủ trọ duyệt.");
        }
        
        return "redirect:/"; // Trả về trang chủ kèm thông báo thành công trên URL
    }
    
    @GetMapping("/hoa-don/thanh-toan/{id}")
    public String trangThanhToanQR(@PathVariable(value = "id") Long idHoaDon, Model model) {
    	com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon hoaDon = hoaDonService.getHoaDonById(idHoaDon).orElse(null);
        if (hoaDon != null) {
            model.addAttribute("hoaDon", hoaDon);
            return "thanh_toan_qr"; // Sẽ tạo file thanh_toan_qr.html
        }
        return "redirect:/hoa-don-cua-toi";
    }
    
    @PostMapping("/hoa-don/xac-nhan-thanh-toan")
    public String xacNhanThanhToan(@RequestParam("idHoaDon") Long idHoaDon, 
                                   @RequestParam("fileMinhChung") MultipartFile fileMinhChung) {
        
        HoaDon hoaDon = hoaDonService.getHoaDonById(idHoaDon).orElse(null);
        if (hoaDon != null) {
            // Xử lý file ảnh: Biến nó thành chuỗi Base64
            if (!fileMinhChung.isEmpty()) {
                try {
                    String base64Image = Base64.getEncoder().encodeToString(fileMinhChung.getBytes());
                    // Đính thêm tiền tố để HTML hiểu đây là ảnh JPEG/PNG
                    hoaDon.setMinhChung("data:image/jpeg;base64," + base64Image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            
            hoaDon.setTrangThaiThanhToan("DADONG");
            hoaDonService.taoHoacCapNhatHoaDon(hoaDon);
        }
        return "redirect:/hoa-don-cua-toi?thanh_toan_thanh_cong";
    }
    
    @GetMapping("/hoa-don-cua-toi")
    public String xemHoaDonCaNhan(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/dangnhap";
        }
        
        // Tìm khách hàng đang đăng nhập
        com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung khach = nguoiDungService.findByTenDangNhap(userDetails.getUsername()).orElse(null);
        
        if (khach != null) {
            // Lọc ra toàn bộ hóa đơn thuộc về người khách này (lấy theo ID)
            java.util.List<com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon> danhSachHoaDon = hoaDonService.getAllHoaDon().stream()
                .filter(hd -> hd.getNguoiDung() != null && hd.getNguoiDung().getId().equals(khach.getId()))
                .toList(); // Yêu cầu Java 16 trở lên. Nếu Eclipse báo lỗi chỗ toList(), đổi thành .collect(java.util.stream.Collectors.toList());
            
            model.addAttribute("danhSachHoaDon", danhSachHoaDon);
            model.addAttribute("tenKhach", khach.getHoVaTen());
        }
        
        return "hoadon_khach"; // Gọi đúng tên file hoadon_khach.html vừa tạo
    }
}
