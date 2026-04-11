-- Tạo database lab06_jdbc1
CREATE DATABASE lab06_jdbc1;
GO

USE lab06_jdbc1;
GO

-- Bảng student
CREATE TABLE student (
    student_id INT PRIMARY KEY IDENTITY(1,1),
    student_name NVARCHAR(100) NOT NULL,
    gender NVARCHAR(10) NULL,
    gpa FLOAT NULL
);
GO

-- Bảng tree
CREATE TABLE tree (
    node_id INT PRIMARY KEY IDENTITY(1,1),
    node_name NVARCHAR(100) NOT NULL,
    parent_id INT NULL,
    level INT NOT NULL,
    FOREIGN KEY (parent_id) REFERENCES tree(node_id)
);
GO

-- Insert dữ liệu mẫu cho student
INSERT INTO student (student_name, gender, gpa) VALUES
(N'Nguyễn Văn An', N'Nam', 3.5),
(N'Trần Thị Bình', N'Nữ', 3.8),
(N'Lê Văn Cường', N'Nam', 2.9),
(N'Phạm Thị Dung', N'Nữ', 3.2),
(N'Hoàng Văn Em', N'Nam', 3.9);
GO

-- Insert dữ liệu mẫu cho tree (cấu trúc cây tổ chức)
INSERT INTO tree (node_name, parent_id, level) VALUES
(N'Root', NULL, 0),
(N'Node 1', 1, 1),
(N'Node 2', 1, 1),
(N'Node 1.1', 2, 2),
(N'Node 1.2', 2, 2),
(N'Node 2.1', 3, 2);
GO
