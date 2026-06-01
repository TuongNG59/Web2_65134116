package com.qlpt.nguyenhuynhtuong65134116.Services;

import java.util.Collections;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.qlpt.nguyenhuynhtuong65134116.Models.NguoiDung;
import com.qlpt.nguyenhuynhtuong65134116.Repositories.NguoiDungRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Xuống MySQL tìm người dùng theo tên đăng nhập
        Optional<NguoiDung> taiKhoanXamp = nguoiDungRepository.findByTenDangNhap(username);
        
        // 2. Nếu không thấy tài khoản nào, lập tức báo lỗi công khai
        if (taiKhoanXamp.isEmpty()) {
            throw new UsernameNotFoundException("Không tìm thấy tài khoản: " + username);
        }

        NguoiDung nd = taiKhoanXamp.get();

        // Kiểm tra trạng thái kích hoạt (đề phòng trường hợp dưới DB bị null thì mặc định là false)
        boolean isEnabled = nd.getTrangThai() != null ? nd.getTrangThai() : false;

        // Đóng gói dữ liệu với đầy đủ thông số bảo mật
        return new User(
                nd.getTenDangNhap(),
                nd.getMatKhau(), 
                isEnabled, // true = Cho phép đăng nhập, false = Tài khoản bị vô hiệu hóa
                true,      // accountNonExpired (Tài khoản không hết hạn)
                true,      // credentialsNonExpired (Mật khẩu không hết hạn)
                true,      // accountNonLocked (Tài khoản không bị khóa)
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + nd.getVaiTro()))
        );
    }
}
