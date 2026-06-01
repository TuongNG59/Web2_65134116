package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.YeuCauThueRepository;

@Service
public class YeuCauThueService {

    @Autowired
    private YeuCauThueRepository yeuCauThueRepository;

    @Autowired
    private PhongTroService phongTroService;

    @Autowired
    private NguoiDungService nguoiDungService;

    // Lấy tất cả yêu cầu (Admin xem)
    public List<YeuCauThue> getAllYeuCau() {
        return yeuCauThueRepository.findAll();
    }

    // Khách vãng lai gửi yêu cầu thuê phòng
    public YeuCauThue guiYeuCauThue(YeuCauThue yeuCau) {
        yeuCau.setTrangThai("CHODUYET");
        return yeuCauThueRepository.save(yeuCau);
    }

    // Logic xử lý Duyệt hoặc Từ chối yêu cầu thuê
    public void xuLyYeuCau(Long idYeuCau, String trangThaiMoi) {
        yeuCauThueRepository.findById(idYeuCau).ifPresent(yeuCau -> {
            yeuCau.setTrangThai(trangThaiMoi);
            yeuCauThueRepository.save(yeuCau);

            // Nếu Admin bấm 'DADUYET'
            if ("DADUYET".equals(trangThaiMoi)) {
                // 1. Đổi trạng thái phòng sang DANGTHUE
                phongTroService.capNhatTrangThai(yeuCau.getPhongTro().getId(), "DANGTHUE");
                
                // 2. Gắn căn phòng đó vào thông tin của người dùng vừa duyệt
                yeuCau.getNguoiDung().setPhongTro(yeuCau.getPhongTro());
                nguoiDungService.capNhatThongTin(yeuCau.getNguoiDung());
                
                // 3. Nếu Model PhongTro của cậu có trường giữ thông tin NguoiDung (khách thuê), 
                // thì cậu nên bổ sung thêm dòng dưới đây để lưu quan hệ ngược lại:
                // yeuCau.getPhongTro().setNguoiDung(yeuCau.getNguoiDung());
                // phongTroService.savePhongTro(yeuCau.getPhongTro());
            } 
            // THÊM NHÁNH NÀY: Nếu Admin bấm 'TUCHOI'
            else if ("TUCHOI".equals(trangThaiMoi)) {
                // Đảm bảo phòng trọ quay về trạng thái TRONG để khách khác có thể nhìn thấy và thuê
                phongTroService.capNhatTrangThai(yeuCau.getPhongTro().getId(), "TRONG");
            }
        });
    }
}
