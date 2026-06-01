package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.ThongBao;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.ThongBaoRepository;

@Service
public class ThongBaoService {

    @Autowired
    private ThongBaoRepository thongBaoRepository;

    // ID mặc định của tài khoản Admin là 1 (dựa theo ảnh database người dùng của cậu)
    private final Long ADMIN_ID = 1L;

    // Lấy tin nhắn giữa một user cụ thể và Admin
    public List<ThongBao> getCuocTroChuyenVoiAdmin(Long userId) {
        return thongBaoRepository.layLichSuChat(userId, ADMIN_ID);
    }

    // Gửi tin nhắn mới
    public void guiTinNhan(Long maNguoiGui, Long maNguoiNhan, String noiDung) {
        if(noiDung != null && !noiDung.trim().isEmpty()) {
            ThongBao tinNhan = new ThongBao(maNguoiGui, maNguoiNhan, noiDung.trim());
            thongBaoRepository.save(tinNhan);
        }
    }
}
