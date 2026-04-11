# HƯỚNG DẪN CHUẨN HÓA CƠ SỞ DỮ LIỆU

## Tổng quan

Database của hệ thống quản lý thư viện đã được chuẩn hóa đến dạng chuẩn 3 (3NF - Third Normal Form) để:
- Loại bỏ dữ liệu trùng lặp
- Đảm bảo tính toàn vẹn dữ liệu
- Tối ưu hóa hiệu suất truy vấn
- Dễ dàng bảo trì và mở rộng

---

## Quá trình chuẩn hóa

### Dạng chưa chuẩn hóa (Unnormalized Form)

Ban đầu, có thể lưu tất cả thông tin trong một bảng:

```
Document_All (
    document_id,
    title,
    author_name,
    author_birth_year,
    author_nationality,
    category_name,
    category_description,
    publisher,
    publish_year,
    copy_id,
    copy_status,
    copy_location,
    member_name,
    member_phone,
    borrow_date,
    due_date
)
```

**Vấn đề:**
- Dữ liệu trùng lặp (tác giả, thể loại lặp lại nhiều lần)
- Khó cập nhật (phải sửa nhiều dòng)
- Lãng phí bộ nhớ
- Dữ liệu không nhất quán

---

### Dạng chuẩn 1 (1NF - First Normal Form)

**Yêu cầu:**
- Mỗi cột chỉ chứa giá trị nguyên tử (atomic)
- Không có nhóm lặp lại
- Có khóa chính

**Áp dụng:**

```sql
Document (
    document_id PK,
    title,
    author_name,
    author_birth_year,
    author_nationality,
    category_name,
    category_description,
    publisher,
    publish_year
)

Copy (
    copy_id PK,
    document_id FK,
    status,
    location
)

Borrowing (
    borrowing_id PK,
    copy_id FK,
    member_name,
    member_phone,
    borrow_date,
    due_date
)
```

**Cải thiện:**
- Tách bản sao và phiếu mượn ra bảng riêng
- Mỗi bảng có khóa chính
- Không còn nhóm lặp

**Vẫn còn vấn đề:**
- Thông tin tác giả và thể loại vẫn trùng lặp trong Document
- Phụ thuộc bộ phận (partial dependency)

---

### Dạng chuẩn 2 (2NF - Second Normal Form)

**Yêu cầu:**
- Đạt 1NF
- Loại bỏ phụ thuộc bộ phận (mọi thuộc tính không khóa phụ thuộc hoàn toàn vào khóa chính)

**Áp dụng:**

```sql
Author (
    author_id PK,
    author_name,
    birth_year,
    nationality
)

Category (
    category_id PK,
    category_name,
    description
)

Document (
    document_id PK,
    title,
    author_id FK,
    category_id FK,
    publisher,
    publish_year
)

Copy (
    copy_id PK,
    document_id FK,
    status,
    location
)

Member (
    member_id PK,
    full_name,
    phone,
    email,
    address
)

Borrowing (
    borrowing_id PK,
    member_id FK,
    copy_id FK,
    borrow_date,
    due_date
)
```

**Cải thiện:**
- Tách Author và Category thành bảng riêng
- Tách Member thành bảng riêng
- Sử dụng khóa ngoại để liên kết

**Vẫn còn vấn đề:**
- Phụ thuộc bắc cầu (transitive dependency)
- Status trong Copy nên là bảng riêng

---

### Dạng chuẩn 3 (3NF - Third Normal Form)

**Yêu cầu:**
- Đạt 2NF
- Loại bỏ phụ thuộc bắc cầu (không có thuộc tính không khóa phụ thuộc vào thuộc tính không khóa khác)

**Áp dụng (Thiết kế cuối cùng):**

```sql
-- Bảng tra cứu trạng thái
CopyStatus (
    status_id PK,
    status_name UNIQUE,
    description
)

Author (
    author_id PK,
    author_name UNIQUE,
    birth_year,
    nationality
)

Category (
    category_id PK,
    category_name UNIQUE,
    description
)

Document (
    document_id PK,
    title,
    author_id FK → Author,
    category_id FK → Category,
    publisher,
    publish_year,
    isbn,
    pages,
    language
)

Copy (
    copy_id PK,
    document_id FK → Document,
    status_id FK → CopyStatus,
    location,
    purchase_date,
    price
)

Member (
    member_id PK,
    full_name,
    email UNIQUE,
    phone,
    address,
    member_type,
    registration_date,
    expiry_date
)

Borrowing (
    borrowing_id PK,
    member_id FK → Member,
    borrow_date,
    due_date,
    return_date,
    status
)

BorrowingDetail (
    detail_id PK,
    borrowing_id FK → Borrowing,
    copy_id FK → Copy,
    actual_return_date,
    condition_on_borrow,
    condition_on_return,
    fine_amount
)
```

**Cải thiện:**
- Tách CopyStatus thành bảng riêng
- Tách Borrowing và BorrowingDetail (một phiếu mượn có nhiều sách)
- Không còn phụ thuộc bắc cầu
- Dữ liệu được tổ chức tối ưu

---

## Lợi ích của thiết kế 3NF

### 1. Loại bỏ dữ liệu trùng lặp
```sql
-- Trước: Tên tác giả lặp lại 100 lần cho 100 cuốn sách
-- Sau: Tên tác giả chỉ lưu 1 lần trong bảng Author
```

### 2. Dễ dàng cập nhật
```sql
-- Thay đổi tên tác giả chỉ cần update 1 dòng
UPDATE Author SET author_name = N'Nguyễn Nhật Ánh' WHERE author_id = 1;
```

### 3. Tính toàn vẹn dữ liệu
```sql
-- Khóa ngoại đảm bảo không thể xóa tác giả đang có sách
-- Cascade delete tự động xóa bản sao khi xóa tài liệu
```

### 4. Hiệu suất tốt hơn
```sql
-- Index trên các khóa ngoại giúp JOIN nhanh
-- Bảng nhỏ hơn, cache hiệu quả hơn
```

---

## Các mối quan hệ

### One-to-Many (1:N)

```
Author (1) ──────< Document (N)
Category (1) ────< Document (N)
Document (1) ────< Copy (N)
Member (1) ──────< Borrowing (N)
Borrowing (1) ───< BorrowingDetail (N)
```

### Many-to-Many (M:N) - Qua bảng trung gian

```
Copy (M) ←──── BorrowingDetail ────→ Borrowing (N)
```

---

## Ví dụ thực tế

### Thêm tài liệu mới

```sql
-- 1. Kiểm tra/thêm tác giả
IF NOT EXISTS (SELECT 1 FROM Author WHERE author_name = N'Nguyễn Nhật Ánh')
    INSERT INTO Author (author_name, birth_year, nationality) 
    VALUES (N'Nguyễn Nhật Ánh', 1955, N'Việt Nam');

-- 2. Lấy author_id
DECLARE @author_id INT = (SELECT author_id FROM Author WHERE author_name = N'Nguyễn Nhật Ánh');

-- 3. Lấy category_id
DECLARE @category_id INT = (SELECT category_id FROM Category WHERE category_name = N'Văn học');

-- 4. Thêm tài liệu
INSERT INTO Document (document_id, title, author_id, category_id, publisher, publish_year)
VALUES ('DOC013', N'Mắt Biếc', @author_id, @category_id, N'NXB Trẻ', 2007);

-- 5. Thêm bản sao
DECLARE @status_id INT = (SELECT status_id FROM CopyStatus WHERE status_name = 'GOOD');
INSERT INTO Copy (copy_id, document_id, status_id, location, purchase_date, price)
VALUES ('COPY032', 'DOC013', @status_id, N'Kệ A1', GETDATE(), 89000);
```

### Mượn sách

```sql
-- 1. Tạo phiếu mượn
INSERT INTO Borrowing (member_id, borrow_date, due_date, status)
VALUES ('MEM001', GETDATE(), DATEADD(DAY, 14, GETDATE()), N'Đang mượn');

-- 2. Lấy borrowing_id vừa tạo
DECLARE @borrowing_id INT = SCOPE_IDENTITY();

-- 3. Thêm chi tiết mượn
INSERT INTO BorrowingDetail (borrowing_id, copy_id, condition_on_borrow)
VALUES (@borrowing_id, 'COPY032', N'Tốt');
```

---

## Kết luận

Thiết kế database đã chuẩn hóa 3NF giúp:
- ✅ Dữ liệu không trùng lặp
- ✅ Dễ bảo trì và mở rộng
- ✅ Đảm bảo tính toàn vẹn
- ✅ Hiệu suất tối ưu
- ✅ Linh hoạt trong truy vấn

Đây là nền tảng vững chắc cho một hệ thống quản lý thư viện chuyên nghiệp.
