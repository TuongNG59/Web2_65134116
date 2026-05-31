package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.YeuCauThue;

@Repository
public interface YeuCauThueRepository extends JpaRepository<YeuCauThue, Long> {
    List<YeuCauThue> findByTrangThai(String trangThai);
}
