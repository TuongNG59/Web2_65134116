package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.HoaDon;

@Repository
public interface HoaDonRepository extends JpaRepository<HoaDon, Long> {
    List<HoaDon> findByNguoiDungId(Long nguoiDungId);
}