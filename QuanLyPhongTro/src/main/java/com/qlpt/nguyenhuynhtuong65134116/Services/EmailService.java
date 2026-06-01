package com.qlpt.nguyenhuynhtuong65134116.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Hàm dùng để gửi email dạng văn bản thuần túy (gửi mã OTP, link kích hoạt)
     * @param to: Địa chỉ Gmail người nhận
     * @param subject: Tiêu đề thư
     * @param content: Nội dung bức thư
     */
    public void guiEmail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            
            // Lệnh thực hiện gửi thư đi bay vèo vèo
            mailSender.send(message);
            System.out.println("👉 [Hệ thống] Đã gửi thành công email tới: " + to);
        } catch (Exception e) {
            System.out.println("❌ [Lỗi Hệ thống] Gửi email thất bại: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
