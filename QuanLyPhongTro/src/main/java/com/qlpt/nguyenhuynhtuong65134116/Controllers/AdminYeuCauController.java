package com.qlpt.nguyenhuynhtuong65134116.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qlpt.nguyenhuynhtuong65134116.Services.YeuCauThueService;

@Controller
@RequestMapping("/admin/yeucau")
public class AdminYeuCauController {

    @Autowired
    private YeuCauThueService yeuCauThueService;

    // 1. Hiển thị danh sách khách đang chờ duyệt phòng
    @GetMapping
    public String danhSachYeuCau(Model model) {
        model.addAttribute("danhSachYeuCau", yeuCauThueService.getAllYeuCau());
        return "admin/quanlyyeucau";
    }

    // 2. Xử lý bấm Duyệt (DADUYET)
    @GetMapping("/duyet/{id}")
    public String duyetYeuCau(@PathVariable(value = "id") Long id) {
        // Hàm này sẽ tự động đổi trạng thái phòng sang DANGTHUE và gán phòng cho User luôn
        yeuCauThueService.xuLyYeuCau(id, "DADUYET");
        return "redirect:/admin/yeucau";
    }

    // 3. Xử lý bấm Từ chối (TUCHOI)
    @GetMapping("/tuchoi/{id}")
    public String tuChoiYeuCau(@PathVariable(value = "id") Long id) {
        yeuCauThueService.xuLyYeuCau(id, "TUCHOI");
        return "redirect:/admin/yeucau";
    }
}
