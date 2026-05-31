package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.ThongBao;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {
    // Lấy toàn bộ thông báo gửi đến cho một người dùng cụ thể (hộp thư đến)
    List<ThongBao> findByNguoiNhanIdOrderByThoiGianDesc(Long nguoiNhanId);
}
