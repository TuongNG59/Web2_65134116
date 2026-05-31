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

    // Gửi thông báo mới (Admin nhắn tin nhắc nợ, nhắc giữ trật tự...)
    public ThongBao guiThongBao(ThongBao thongBao) {
        thongBao.setDaDoc(false);
        return thongBaoRepository.save(thongBao);
    }

    // Lấy hộp thư đến của một người dùng cụ thể (Xếp tin nhắn mới nhất lên đầu)
    public List<ThongBao> getHopThuDenCuaUser(Long manguoinhan) {
        return thongBaoRepository.findByNguoiNhanIdOrderByThoiGianDesc(manguoinhan);
    }

    // Đánh dấu là đã đọc khi người dùng bấm vào xem tin nhắn
    public void danhDauDaDoc(Long idThongBao) {
        thongBaoRepository.findById(idThongBao).ifPresent(tb -> {
            tb.setDaDoc(true);
            thongBaoRepository.save(tb);
        });
    }
}
