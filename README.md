# fplhn-capstone-register
Website đăng ký DATN cho các sinh viên kỳ 7 của Cao đẳng FPT Polytechnic
---
## Quy định về số phiên bản
### Số phiên bản: x.y.z
#### 1. x: Sẽ tăng khi có sự bổ sung một luồng mới hay bắt đầu một phase mới trong toàn bộ dự án. Ví dụ:
  * Các phiên bản ở phase 1: `1.x.x`
  * Các phiên bản ở phase 2: `2.x.x`
#### 2. y: Sẽ tăng khi có một chức năng mới, tắt một chức năng đã có hoặc thay đổi giao diện. Ví dụ:
  * Đang ở phiên bản `1.2.9` và bổ sung thêm chức năng `Đổi tên nhóm DATN` thì khi release số phiên bản sẽ là `1.2.10`
  * Đang ở phiên bản `1.2.9` và tắt hoặc loại bỏ chức năng quét nhóm DATN không đủ điều kiện thì khi release số phiên bản sẽ là `1.2.10`
  * Đang ở phiên bản `1.2.9` và tiến hành thay đổi vị trí nút Tạo nhóm DATN thì khi release số phiên bản sẽ là `1.2.10`
#### 3. z: Sẽ tăng khi fix một lỗi tồn tại trong phiên bản trước đó. Ví dụ:
  * Đang ở phiên bản `1.4.2` và cần fix lỗi khi SV tạo nhóm có thể tạo được nhiều nhóm cùng một lúc thì khi release số phiên bản sẽ là `1.4.3`
  * Đang ở phiên bản `1.4.11` và cần fix lỗi không gửi được email thì khi release số phiên bản sẽ là `1.4.12`
---
## Các phiên bản cũ
### 1. `v1.2.0` - 12:23 16/11/2022: 
  * Fix thêm, sửa môn datn
  * Bỏ validate SĐT, Khóa khi import Sinh viên và GVHD
  * Sửa validate mã môn khi import môn DATN
### 2. `v1.2.1` - 17:20 16/11/2022
  * Phân trang mới
  * Import GVHD, SV có thể để trống SĐT. 
  * Import môn: Khi mã nhóm môn không tồn tại -> thêm nhóm môn mới
### 3. `v1.3.0` - 24:00 16/11/2022
  * Thêm chức năng: Thêm, sửa, xóa sinh viên của đào tạo
  * Fix Gen mã nhóm (Khi gen đến 10 không tự tăng tiêp)
  * Thêm validate import môn DATN: Nếu tạo mới nhóm môn thì không được trùng với môn hoặc nhóm môn đã tồn tại
### 4. `v1.3.1` - 14:00 17/11/2023
  * Fix lỗi hiển thị mã nhóm của màn dao-tao/sinh-vien
  * Thêm chức năng khi chọn chuyên ngành mới fill ra môn của chuyên ngành đó
  * Update giao diện của sinh viên, giao diện QLSV của đào tạo
  * Đặt synchronized cho các chức năng: SV tạo nhóm, Trưởng nhóm chọn GVHD
### 5. `v1.3.2` - 24:00 17/11/2022
  * Fix gửi email cho trưởng nhóm mới khi chuyển quyền trưởng nhóm
  * Sửa: CNBM tạo nhóm sẽ k chọn Nhóm môn
  * Bổ sung chức năng: Gửi email thông báo xóa nhóm không đủ điều kiện - không đủ 4 thành viên sau 12 tiếng
### 6. `v1.3.3` - 12:30 18/11/2022
  * Fix lỗi validate định dạng file khi import (import SV, GVHD, loại SV, CNBM import nhóm)
### 7. `v1.3.4` - 24:00 18/11/2022
  * Fix lỗi sinh viên rời nhóm nhưng không xóa nhóm, k update thời gian rời nhóm
  * Fix hiển thị stt trong QL nhóm DATN
### 8. `v1.3.5` - 24:00 20/11/2022
  * Update gửi email cho sinh viên khi CNBM xác nhận nhóm (Ghi rõ sđt và email của GV)
  * Fix khi thêm sinh viên hiển thị nhóm môn
  * Thêm where dot_dang_ky_id cho các hàm của màn sinh viên
  * Fix import sinh viên (Validate sinh viên theo đợt)
  * Fix DailyCheckNhomDatn
### 9. `v1.3.6`
### 10. `v1.3.7` - 
### 11. `v1.3.8` - 16/7/2023
  * Fix lỗi validate khi tạo nhóm. 2 nhóm chọn cùng 1 GV ở màn hình đăng ký nhóm DATN
### 12. `v1.3.9` - 20/7/2023
  * Fix lôi import excel màn hình QL môn DATN của Đào tạo
### 13. `v1.4.0` - 22/07/2023
  * Xóa validate số ký tự tối thiểu tên đợt đăng ký
  * Sửa lỗi import sinh viên (khi có 2 SĐT)
### 14. `v1.4.1` - 13:50 25/07/2023
  * Sửa sinh viên khi tạo nhóm có thể bấm liên tục để tạo được 2 nhóm (2 nhóm có cùng 1 trưởng nhóm)
  * Sửa job quét nhóm không đủ điều kiện: 30p quét một lần. Xóa toàn bộ nhóm không đủ 4 thành viên sau 2h từ khi tạo nhóm
### 15. `v1.4.2` - 19:00 25/07/2023
  * Tắt job ngầm quét nhóm không đủ điều kiện
