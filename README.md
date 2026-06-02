<div align="center">

# 🏠 Adou Hostel — Hệ Thống Quản Lý & Cho Thuê Phòng Trọ

---

![Java](https://img.shields.io/badge/Java_17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=springsecurity&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=thymeleaf&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap_5-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)

</div>

---

## 📖 Giới Thiệu Dự Án

**Adou Hostel** là một ứng dụng web được thiết kế nhằm số hóa quy trình quản lý kinh doanh phòng trọ. Hệ thống giải quyết các bài toán của 1 người chủ trọ trong việc tính toán hóa đơn điện nước, quản lý khách thuê; đồng thời mang lại trải nghiệm tìm phòng, theo dõi chi phí và tiện lợi nhất cho người đi thuê.

### ✨ Điểm Nổi Bật

- 🏛️ **Kiến Trúc MVC Chuẩn Mực** — Codebase được tổ chức theo mô hình Model-View-Controller (MVC) với Spring Boot, đảm bảo tính chặt chẽ, dễ bảo trì và mở rộng.
- 🔐 **Bảo Mật Tối Ưu** — Tích hợp Spring Security phân quyền khép kín cho 2 vai trò: `ROLE_ADMIN` (Chủ trọ) và `ROLE_USER` (Khách thuê).
- 💰 **Tự Động Hóa Kế Toán** — Thuật toán tự động tính toán tổng tiền dựa trên chỉ số điện nước cũ/mới và giá phòng cố định.
- 📂 **Quản Lý Dữ Liệu An Toàn** — Xử lý triệt để các vấn đề về khóa ngoại (Foreign Key Constraints) khi dọn dẹp dữ liệu, đảm bảo hệ thống không bao giờ bị gián đoạn.

---

## 📸 Giao Diện Hệ Thống

### 👤 Khu Vực Khách Thuê (USER)

<table>
  <tr>
    <td align="center" width="50%">
      <strong>🏠 Trang Chủ & Danh Sách Phòng</strong><br/>
      <img src="demo_images/trang_chu.png" alt="Trang Chủ" width="100%"/>
      <br/><sub>Hiển thị trực quan danh sách các phòng trống. Khách có thể bấm "Gửi Yêu Cầu Thuê" trực tiếp.</sub>
    </td>
    <td align="center" width="50%">
      <strong>🧾 Hóa Đơn Hàng Tháng</strong><br/>
      <img src="demo_images/xem_hoa_don.png" alt="Hóa Đơn" width="100%"/>
      <br/><sub>Bảng kê chi tiết chỉ số điện/nước cũ mới, tiền phòng và tổng chi phí cần thanh toán cực kỳ minh bạch.</sub>
    </td>
  </tr>
</table>

### 👤 Không Gian Làm Việc — Chủ Trọ (ADMIN)

<table>
  <tr>
    <td align="center" width="50%">
      <strong>📊 Quản Lý Phòng Trọ</strong><br/>
      <img src="demo_images/quan_ly_phong.png" alt="Quản Lý Phòng" width="100%"/>
      <br/><sub>Thêm, sửa, xóa phòng trọ. Theo dõi trạng thái từng phòng (Trống, Chờ duyệt, Đã thuê).</sub>
    </td>
    <td align="center" width="50%">
      <strong>👥 Quản Lý Tài Khoản Khách</strong><br/>
      <img src="demo_images/quan_ly_tai_khoan.png" alt="Quản Lý Tài Khoản" width="100%"/>
      <br/><sub>Giám sát thông tin, xem ảnh CCCD mặt trước/sau và thao tác Khóa/Mở khóa tài khoản khách vi phạm.</sub>
    </td>
  </tr>
  <tr>
    <td align="center" width="50%">
      <strong>✅ Duyệt Yêu Cầu Thuê</strong><br/>
      <img src="demo_images/duyet_yeu_cau.png" alt="Duyệt Yêu Cầu" width="100%"/>
      <br/><sub>Phê duyệt khách vào phòng. Tự động chuyển đổi trạng thái phòng và gán mã phòng cho khách.</sub>
    </td>
    <td align="center" width="50%">
      <strong>📝 Lập Hóa Đơn Tự Động</strong><br/>
      <img src="demo_images/lap_hoa_don.png" alt="Lập Hóa Đơn" width="100%"/>
      <br/><sub>Tự động điền giá phòng cơ bản, chỉ cần nhập chỉ số điện nước mới, hệ thống tự lo phần tính toán.</sub>
    </td>
  </tr>
</table>

---

## ⚙️ Tính Năng Chi Tiết

| Phân hệ | Chức năng cốt lõi |
|---|---|
| **Dùng Chung** | <ul><li>Đăng ký / Đăng nhập tài khoản an toàn với mật khẩu mã hóa.</li><li>Cập nhật hồ sơ cá nhân.</li></ul> |
| **Khách Thuê (USER)** | <ul><li>Xem danh sách phòng trống trên hệ thống.</li><li>Gửi yêu cầu thuê phòng bất đồng bộ (trạng thái chờ duyệt).</li><li>Theo dõi hóa đơn điện nước chi tiết hằng tháng.</li><li>Sử dụng hộp thư nội bộ để báo cáo sự cố hỏng hóc cho chủ trọ.</li></ul> |
| **Chủ Trọ (ADMIN)** | <ul><li>**Quản lý Phòng:** Thêm mới, chỉnh sửa giá/diện tích, xóa phòng (tự động dọn dẹp khóa ngoại).</li><li>**Quản lý Khách hàng:** Xem chi tiết hồ sơ CCCD, Khóa/Mở khóa quyền đăng nhập của khách.</li><li>**Xét duyệt:** Duyệt yêu cầu thuê phòng từ khách.</li><li>**Kế toán:** Lập hóa đơn tính tiền tự động dựa trên chỉ số tiêu thụ.</li><li>**Thông báo:** Đọc và phản hồi tin nhắn từ khách thuê.</li></ul> |

---

## 🏛️ Kiến Trúc Cơ Sở Dữ Liệu

Dự án sử dụng cơ sở dữ liệu quan hệ **MySQL** được thiết kế chuẩn hóa, đảm bảo tính toàn vẹn dữ liệu. Bao gồm 5 bảng chính liên kết chặt chẽ:

- `phongtro`: Quản lý danh mục phòng, giá cơ bản, trạng thái.
- `nguoidung`: Lưu hồ sơ khách thuê, tài khoản, mật khẩu, quyền truy cập và liên kết tới `phongtro`.
- `hoadon`: Quản lý chỉ số điện, nước, tính toán tổng tiền hằng tháng.
- `yeucauthue`: Bảng trung gian ghi nhận yêu cầu đặt phòng.
- `thongbao`: Hệ thống hộp thư nội bộ.

---

## 🚀 Hướng Dẫn Cài Đặt & Chạy Dự Án

### 📋 Yêu Cầu Môi trường
- **Java:** JDK 17 hoặc mới hơn.
- **IDE:** Eclipse hoặc IntelliJ IDEA.
- **Database:** XAMPP (MySQL).

### Bước 1 — Khởi Tạo Cơ Sở Dữ Liệu (MySQL)
1. Mở **XAMPP Control Panel** và Start module **Apache** & **MySQL**.
2. Truy cập `http://localhost/phpmyadmin/`.
3. Tạo một database mới tên là `quanlyphongtro` (Collation: `utf8mb4_general_ci`).
4. Import file cơ sở dữ liệu `quanlyphongtro.sql` (đính kèm trong thư mục dự án) vào để khởi tạo các bảng và dữ liệu mẫu.

### Bước 2 — Cấu Hình Ứng Dụng (Spring Boot)
Mở dự án bằng Eclipse/IntelliJ, tìm đến file `src/main/resources/application.properties` và cập nhật thông tin:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quanlyphongtro?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
# Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true