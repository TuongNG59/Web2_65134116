package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.ThongBao;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBao, Long> {
	@Query("SELECT t FROM ThongBao t WHERE " +
	           "(t.maNguoiGui = :userId AND t.maNguoiNhan = :adminId) OR " +
	           "(t.maNguoiGui = :adminId AND t.maNguoiNhan = :userId) " +
	           "ORDER BY t.thoiGian ASC")
	List<ThongBao> layLichSuChat(@Param("userId") Long userId, @Param("adminId") Long adminId);
}
