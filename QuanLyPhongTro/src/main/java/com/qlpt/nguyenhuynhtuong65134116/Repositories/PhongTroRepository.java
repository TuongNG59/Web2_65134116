package com.qlpt.nguyenhuynhtuong65134116.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qlpt.nguyenhuynhtuong65134116.Models.PhongTro;

@Repository
public interface PhongTroRepository extends JpaRepository<PhongTro, Long> {
    List<PhongTro> findByTrangThai(String trangThai);
}
