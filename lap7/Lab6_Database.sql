-- =============================================
-- Lab6 Database - SQL Server
-- Tạo CSDL quản lý nhân viên
-- =============================================

-- Tạo database Lab6
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'Lab6')
BEGIN
    CREATE DATABASE Lab6;
END
GO

USE Lab6;
GO

-- Xóa bảng nếu đã tồn tại
IF OBJECT_ID('dbo.employee', 'U') IS NOT NULL
    DROP TABLE dbo.employee;
GO

-- Tạo bảng employee
CREATE TABLE dbo.employee (
    id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100) NOT NULL,
    salary FLOAT NOT NULL
);
GO

-- Thêm dữ liệu mẫu
INSERT INTO dbo.employee (name, salary) VALUES 
    (N'Nguyễn Văn An', 15000000),
    (N'Trần Thị Bình', 18000000),
    (N'Lê Văn Cường', 12000000),
    (N'Phạm Thị Dung', 20000000),
    (N'Hoàng Văn Em', 16000000);
GO

-- Kiểm tra dữ liệu
SELECT * FROM dbo.employee;
GO
