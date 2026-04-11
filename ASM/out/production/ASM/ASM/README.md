# Hệ Thống Quản Lý Thư Viện

## Cấu trúc dự án

```
ASM/
├── model/              # Các lớp mô hình dữ liệu
│   ├── Document.java   # Tài liệu
│   ├── Copy.java       # Bản sao
│   └── CopyStatus.java # Trạng thái bản sao (enum)
├── service/            # Lớp nghiệp vụ (Phase 1)
│   ├── DocumentService.java
│   └── CopyService.java
├── dao/                # Data Access Object (Phase 2)
│   ├── DocumentDAO.java
│   └── CopyDAO.java
├── database/           # Kết nối database
│   └── DatabaseConnection.java
├── util/               # Tiện ích
│   └── FileManager.java
├── exception/          # Ngoại lệ tùy chỉnh
│   └── DuplicateIdException.java
├── phase1/             # Giai đoạn 1: Lưu trữ file
│   └── LibraryManagementPhase1.java
└── phase2/             # Giai đoạn 2: Lưu trữ database
    └── LibraryManagementPhase2.java
```

## Chức năng

### Phase 1: Quản lý với File
- CRUD tài liệu và bản sao
- Lưu/đọc dữ liệu từ file text
- Tìm kiếm theo tiêu đề, tác giả
- Sắp xếp và thống kê

### Phase 2: Quản lý với Database
- CRUD tài liệu và bản sao qua database
- Tìm kiếm và thống kê từ SQL
- Sử dụng JDBC và Derby database

## Cách chạy

### Phase 1:
```bash
java ASM.phase1.LibraryManagementPhase1
```

### Phase 2:
```bash
java ASM.phase2.LibraryManagementPhase2
```

## Yêu cầu
- Java 17+
- Apache Derby (cho Phase 2 - mặc định)
- SQL Server (tùy chọn - xem hướng dẫn bên dưới)

## Cơ sở dữ liệu SQL Server

### Cấu trúc database đã chuẩn hóa (3NF)

Hệ thống sử dụng 8 bảng chính:

1. **CopyStatus** - Trạng thái bản sao (GOOD, DAMAGED, LOST, MAINTENANCE)
2. **Category** - Thể loại sách (Văn học, Khoa học, Công nghệ, v.v.)
3. **Author** - Thông tin tác giả
4. **Document** - Tài liệu (có khóa ngoại đến Author và Category)
5. **Copy** - Bản sao vật lý (có khóa ngoại đến Document và CopyStatus)
6. **Member** - Thành viên thư viện
7. **Borrowing** - Phiếu mượn (có khóa ngoại đến Member)
8. **BorrowingDetail** - Chi tiết mượn (có khóa ngoại đến Borrowing và Copy)

### Cài đặt SQL Server

1. Chạy script tạo schema:
```sql
sqlcmd -S localhost -i database/sqlserver_schema.sql
```

2. Thêm dữ liệu mẫu:
```sql
sqlcmd -S localhost -i database/sqlserver_sample_data.sql
```

3. Tạo stored procedures và views:
```sql
sqlcmd -S localhost -i database/sqlserver_queries.sql
```

### Các tính năng nâng cao

- **Indexes**: Tối ưu hóa truy vấn trên các cột thường xuyên tìm kiếm
- **Stored Procedures**: Tìm kiếm, kiểm tra khả dụng, quản lý mượn trả
- **Views**: Báo cáo sách quá hạn, thống kê mượn nhiều nhất
- **Triggers**: Tự động cập nhật trạng thái quá hạn, giới hạn số sách mượn

### Ví dụ truy vấn

```sql
-- Tìm sách theo tiêu đề
EXEC sp_SearchDocumentByTitle N'Harry Potter';

-- Kiểm tra bản sao có sẵn
EXEC sp_CheckCopyAvailability 'DOC001';

-- Xem sách quá hạn
SELECT * FROM vw_OverdueBooks;

-- Thống kê sách được mượn nhiều nhất
SELECT * FROM vw_MostBorrowedBooks;
```
