# 🏢 Bài tập lớn Quản Lý Nhà Trọ Adou

> **Học phần:** Phát tiển ứng dụng web 2  
> **Sinh viên thực hiện:** Nguyễn Huỳnh Tường  65134116
> **Công nghệ nền tảng:** Spring Boot Mvc + Thymeleaf + MySQL

---

## 🧭 Tổng Quan Giải Pháp

Hệ thống **Quản lý phòng trọ** được xây dựng nhằm giải quyết bài toán thủ công trong vận hành nhà trọ truyền thống. Bằng cách số hóa toàn bộ dữ liệu, ứng dụng giúp tối ưu hóa quy trình tương tác hai chiều giữa **Chủ trọ (Admin)** và **Khách thuê (User)**, giảm thiểu sai sót khi tính toán hóa đơn và minh bạch hóa chi phí lưu trú.

### Kỹ Thuật Nổi Bật Trong Dự Án
* **Spring Architecture:** Tổ chức mã nguồn phân tầng rõ ràng (Controller -> Service -> Repository -> Model Entities).
* **Data Integrity Protection:** Triển khai các hàm dọn dẹp liên kết khóa ngoại chạy ngầm (`foreign key constraints`) trong Spring Data JPA, ngăn chặn triệt để lỗi sập luồng (Error 500) khi xóa các thực thể phòng trọ hoặc tài khoản.
* **Security & Authentication:** Trích xuất thông tin trực tiếp từ kiến trúc Session của hệ thống để phân định không gian làm việc độc lập cho từng nhóm quyền `ROLE_ADMIN` và `ROLE_USER`.

---

## 🛠️ Bản Đồ Chức Năng Hệ Thống (System Capabilities)

### 🟢 Phân Hệ Khách Thuê (USER Workspace)
- **Khám phá & Đăng ký lưu trú:** Tra cứu hệ thống phòng trống theo thời gian thực và gửi phiếu yêu cầu thuê phòng trực tuyến lên hàng đợi của Admin.
- **Quản lý chi phí hằng tháng:** Tiếp nhận hóa đơn tính tiền, xem giá cả.
- **Tương tác nội bộ:** Gửi tin nhắn trực tiếp để giải đáp thắc mắc về trung tâm xử lý của chủ trọ.
- **Bảo mật hồ sơ:** Quản lý và cập nhật thông tin định danh cá nhân, ảnh chụp căn cước công dân (CCCD) để đăng ký tạm trú.

### 🔵 Phân Hệ Quản Trị Viên (ADMIN Workspace)
- **Quản lý hạ tầng:** Tạo lập, điều chỉnh thông số diện tích, đơn giá phòng và dọn dẹp các phòng trọ không còn người thuê.
- **Kiểm duyệt vận hành:** Xét duyệt danh sách khách đăng ký thuê phòng, tự động gán mã phòng và cập nhật trạng thái phòng thành công.
- **Kế toán tự động:** Nhập chỉ số tiêu thụ điện nước mới, hệ thống tự trích xuất đơn giá cố định và tự động nhân chia cộng gộp ra tổng tiền hóa đơn.
- **Giám sát an ninh:** Quản lý thông tin hồ sơ của khách, thực thi lệnh khóa/mở khóa quyền truy cập hệ thống của khách thuê, xoá tài khoản khách thuê.

---

## 🏛️ Kiến Trúc Hệ Thống (System Architecture)

Dự án Adou Hostel được xây dựng dựa trên kiến trúc **MVC (Model - View - Controller)** chuẩn mực, kết hợp sức mạnh của các framework hàng đầu trong hệ sinh thái Java để đảm bảo hiệu năng và khả năng mở rộng:

- **Tầng Giao Diện (View):** Sử dụng bộ máy dựng hình động **Thymeleaf** kết hợp cùng HTML5, CSS3 và Bootstrap 5 để kết xuất dữ liệu trực tiếp từ máy chủ trả về, giúp giao diện hiển thị đồng bộ và mượt mà.
- **Tầng Điều Hướng (Controller):** Các lớp `@Controller` trong **Spring Boot** đóng vai trò tiếp nhận các yêu cầu HTTP (HTTP Requests) từ người dùng, điều phối luồng dữ liệu và chuyển tiếp đến các Service tương ứng.
- **Tầng Xử Lý Nghiệp Vụ (Service):** Nơi chứa toàn bộ logic lõi của hệ thống (ví dụ: thuật toán tự động tính tiền điện nước, kịch bản gỡ liên kết phòng trước khi xóa, hoặc thao tác khóa tài khoản).
- **Tầng Truy Xuất Dữ Liệu (Repository/Model):** Ứng dụng **Spring Data JPA** để ánh xạ thực thể đối tượng (ORM) và tương tác trực tiếp với hệ quản trị cơ sở dữ liệu **MySQL**, đảm bảo tính toàn vẹn dữ liệu thông qua các ràng buộc khóa ngoại (Foreign Keys) chặt chẽ.
- **Tầng Bảo Mật:** Tích hợp **Spring Security** với cơ chế phân quyền nghiêm ngặt dựa trên vai trò (`ROLE_ADMIN`, `ROLE_USER`), bảo vệ các đường dẫn nhạy cảm và mã hóa mật khẩu an toàn.

---

## 📸 Giao diện hệ thống

---

### 🌐 PHẦN I: PHÂN HỆ XÁC THỰC & CHỨC NĂNG CHUNG

#### 🔹 Hình 5.1: Giao diện trang chủ hệ thống dành cho khách vãng lai (Chưa đăng nhập)
<img src="demo_images/5_1.png" alt="Trang chủ vãng lai" width="85%"/>

#### 🔹 Hình 5.2: Biểu mẫu đăng ký tài khoản thành viên mới cho khách thuê
<img src="demo_images/5_2.png" alt="Đăng ký tài khoản" width="85%"/>

#### 🔹 Hình 5.3: Giao diện biểu mẫu đăng nhập bảo mật của hệ thống
<img src="demo_images/5_3.png" alt="Đăng nhập hệ thống" width="85%"/>


---

### 👤 PHẦN II: KHÔNG GIAN TƯƠNG TÁC CỦA KHÁCH THUÊ (USER)

#### 🔹 Hình 5.4: Giao diện trang chủ khi tài khoản vừa đăng nhập và chưa liên kết phòng trọ
<img src="demo_images/5_4_1_1.png" alt="Chưa liên kết phòng" width="85%"/>

#### 🔹 Hình 5.5: Minh chứng thao tác gửi phiếu yêu cầu thuê căn phòng trọ được chọn
<img src="demo_images/5_4_1_2.png" alt="Gửi yêu cầu thuê phòng" width="85%"/>

#### 🔹 Hình 5.6: Màn hình trang chủ tự động cập nhật mã phòng sau khi được Admin phê duyệt
<img src="demo_images/5_4_1_3.png" alt="Đã được duyệt phòng" width="85%"/>

#### 🔹 Hình 5.7: Giao diện trang hoá đơn báo khi chưa có hóa đơn chu kỳ mới
<img src="demo_images/5_4_1_4.png" alt="Chưa có hóa đơn tháng" width="85%"/>

#### 🔹 Hình 5.8: Bảng kê chi tiết hóa đơn tiền phòng và khu vực thanh toán
<img src="demo_images/5_4_1_5.png" alt="Chi tiết hóa đơn tiêu thụ" width="85%"/>

#### 🔹 Hình 5.9: Giao diện hộp thư nội bộ gửi báo cáo sự cố hạ tầng về cho chủ trọ
<img src="demo_images/5_4_1_6.png" alt="Hộp thư báo lỗi user" width="85%"/>

#### 🔹 Hình 5.10: Biểu mẫu cập nhật thông tin hồ sơ cá nhân và đính kèm ảnh chụp CCCD hai mặt
<img src="demo_images/5_4_1_7.png" alt="Cập nhật CCCD hồ sơ" width="85%"/>

---

### 🛡️ PHẦN III: KHÔNG GIAN QUẢN TRỊ CỦA CHỦ TRỌ (ADMIN)

#### 🔹 Hình 5.11: Bảng điều khiển quản trị tổng quan (Dashboard) của tài khoản Admin
<img src="demo_images/5_4_2_1.png" alt="Dashboard tổng quan Admin" width="85%"/>

#### 🔹 Hình 5.12: Giao diện danh sách quản lý hạ tầng hệ thống phòng trọ kèm bộ lọc trạng thái
<img src="demo_images/5_4_2_2.png" alt="Quản lý phòng trọ" width="85%"/>

#### 🔹 Hình 5.13: Biểu mẫu thiết lập các thông số kỹ thuật và cấu hình thêm phòng trọ mới
<img src="demo_images/5_4_2_3.png" alt="Thêm phòng trọ mới" width="85%"/>

#### 🔹 Hình 5.14: Trung tâm giám sát trạng thái thanh toán của toàn bộ hóa đơn
<img src="demo_images/5_4_2_4.png" alt="Danh sách quản lý hóa đơn" width="85%"/>

#### 🔹 Hình 5.15: Biểu mẫu khởi tạo hóa đơn tự động tính toán theo chỉ số tiêu thụ điện nước
<img src="demo_images/5_4_2_5.png" alt="Lập hóa đơn tự động" width="85%"/>

#### 🔹 Hình 5.16: Danh sách tiếp nhận, xử lý và phê duyệt các yêu cầu thuê phòng trực tuyến từ khách
<img src="demo_images/5_4_2_6.png" alt="Phê duyệt yêu cầu thuê" width="85%"/>

#### 🔹 Hình 5.17: Giao diện quản lý danh sách tài khoản, kiểm tra ảnh CCCD lưu trú và chức năng khóa quyền
<img src="demo_images/5_4_2_7.png" alt="Quản lý hồ sơ tài khoản" width="85%"/>

#### 🔹 Hình 5.18: Trung tâm điều phối, tiếp nhận và phản hồi luồng tin nhắn báo lỗi từ khách thuê
<img src="demo_images/5_4_2_8.png" alt="Hộp thư điều phối tin nhắn" width="85%"/>

---

## ⚙️ Tính Năng Chi Tiết

### 🌐 Khu Vực Công Khai & Xác Thực

| Trang | Mô tả chức năng |
|---|---|
| **Trang Chủ** | Hiển thị danh sách toàn bộ phòng trọ hiện có trên hệ thống kèm theo các thông số cơ bản (diện tích, giá phòng cố định) giúp khách vãng lai dễ dàng tham khảo. |
| **Đăng Ký** | Biểu mẫu tạo tài khoản mới dành cho khách thuê với các trường thông tin bắt buộc, đảm bảo tính xác thực và tự động mã hóa mật khẩu trước khi lưu xuống CSDL. |
| **Đăng Nhập** | Form xác thực thông tin người dùng. Tự động nhận diện quyền hạn (`ROLE_ADMIN` hoặc `ROLE_USER`) để điều hướng vào đúng không gian làm việc tương ứng. |

---

### 👤 Không Gian Làm Việc — Khách Thuê (USER)

| Trang | Mô tả chức năng |
|---|---|
| **Danh Sách Phòng** | Tra cứu danh sách các phòng trống. Cho phép nhấn "Gửi yêu cầu thuê" trực tiếp và theo dõi trạng thái chờ chủ trọ phê duyệt. |
| **Hóa Đơn Của Tôi** | Bảng kê chi tiết tài chính hằng tháng. Hiển thị tổng chi phí. Cho phép tải lên ảnh chụp biên lai chuyển khoản để làm minh chứng. |
| **Hộp Thư chat với admin** | Hệ thống tin nhắn nội bộ giúp khách thuê nói ý kiến của mình cho chủ trọ biết. |
| **Hồ Sơ Cá Nhân** | Form quản lý thông tin định danh. Cho phép khách thuê cập nhật số điện thoại, quê quán và đặc biệt là tải lên hình ảnh Căn cước công dân (CCCD) mặt trước/sau để đăng ký tạm trú. |

---

### 🛡️ Không Gian Làm Việc — Chủ Trọ (ADMIN)

| Trang | Mô tả chức năng |
|---|---|
| **Bảng Điều Khiển (Dashboard)** | Cung cấp cái nhìn tổng quan về hệ thống và cung cấp các phím tắt truy cập nhanh vào các phân hệ quản lý cốt lõi. |
| **Quản Lý Phòng Trọ** | Trung tâm quản trị hạ tầng. Cho phép thêm phòng mới, cập nhật giá/diện tích và xóa phòng. Tích hợp thuật toán tự động dọn dẹp liên kết khóa ngoại khi xóa để tránh lỗi hệ thống. |
| **Duyệt Yêu Cầu Thuê** | Xét duyệt các phiếu đăng ký đặt phòng từ khách. Khi nhấn "Duyệt", hệ thống tự động gán mã phòng cho khách và đổi trạng thái phòng thành "Đã thuê". |
| **Quản Lý & Lập Hóa Đơn** | Công cụ kế toán tự động. Chủ trọ chỉ cần chọn phòng và nhập chỉ số tiêu thụ mới, hệ thống sẽ tự động đối chiếu chỉ số cũ và tính toán ra tổng tiền. |
| **Quản Lý Tài Khoản** | Giám sát toàn bộ khách thuê trong hệ thống. Cung cấp tính năng xem chi tiết hồ sơ CCCD và nút "Khóa tài khoản" để chặn quyền truy cập của các khách thuê vi phạm nội quy hoặc chậm đóng tiền. |
| **Quản Lý Thông Báo** | Kênh tiếp nhận và quản lý các tin nhắn từ khách thuê, hỗ trợ chủ trọ phản hồi và nắm bắt tình hình vận hành của từng phòng. |

---

## 💾 Thiết Kế Lược Đồ Dữ Liệu (MySQL)

Hệ thống tổ chức lưu trữ thông tin tập trung trên RDBMS MySQL thông qua 5 bảng quan hệ logic đã được chuẩn hóa cấu trúc:
1. `phongtro`: Ghi nhận dữ liệu cốt lõi về tên phòng, giá phòng cố định, diện tích và trạng thái phòng.
2. `nguoidung`: Quản lý định danh tài khoản, mật khẩu băm, thông tin cá nhân và trường khóa ngoại `maphong`.
3. `hoadon`: Lưu trữ lịch sử tính tiền, mốc thời gian, các chỉ số điện nước cũ/mới và chuỗi ảnh minh chứng.
4. `yeucauthue`: Hàng đợi lưu trữ các phiếu đăng ký đặt phòng từ khách thuê gửi lên.
5. `thongbao`: Kênh lưu trữ nội dung tin nhắn chat nội bộ giữa các tài khoản.

---

## ⚙️ Hướng Dẫn Triển Khai Hệ Thống Tại Máy Cục Bộ (Local)

### ⌨️ Chuẩn bị môi trường máy chủ
* Nền tảng Java: JDK 17+.
* Cơ sở dữ liệu: XAMPP Server v3.3+.
* Trình biên dịch: Eclipse IDE hoặc IntelliJ IDEA.

### Bước 1: Đồng bộ hóa cơ sở dữ liệu
1. Khởi động hai dịch vụ **Apache** và **MySQL** bên trong XAMPP Control Panel.
2. Sử dụng trình duyệt truy cập vào hệ quản trị `http://localhost/phpmyadmin/`.
3. Tạo mới một cơ sở dữ liệu trống với tên chính xác là: `quanlyphongtro`.
4. Chọn tab **Import**, tải file script dữ liệu `quanlyphongtro.sql` đính kèm lên hệ thống để tự động thiết lập cấu trúc bảng và nạp dữ liệu demo.

### Bước 2: Thiết lập tham số kết nối cấu hình Spring Boot
Mở mã nguồn dự án trên IDE, tìm đến file cấu hình hệ thống `src/main/resources/application.properties` để điều chỉnh thông số truy cập MySQL của máy cậu:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/quanlyphongtro?useUnicode=true&characterEncoding=UTF-8
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

***

### Bước 3: Khởi chạy và Trải nghiệm quyền truy cập
Tìm đến class chạy chính chứa annotation `@SpringBootApplication` để khởi động ứng dụng Server bằng Eclipse. Khi hệ thống báo chạy thành công, truy cập đường dẫn `http://localhost:8080` trên trình duyệt.

Sử dụng các tài khoản kiểm thử đã được cấu hình sẵn dưới cơ sở dữ liệu để test luồng nghiệp vụ:
* **Tài khoản nhóm Admin (Chủ trọ):** `admin1` | Mật khẩu: `123`