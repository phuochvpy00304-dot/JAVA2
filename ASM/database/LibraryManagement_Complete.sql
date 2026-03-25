-- ============================================
-- LIBRARY MANAGEMENT SYSTEM - SQL SERVER
-- Complete File: Schema + Sample Data
-- ============================================

-- ============================================
-- PART 1: DATABASE SCHEMA
-- ============================================

USE master;
GO

-- Create database if not exists
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'LibraryManagement')
BEGIN
    CREATE DATABASE LibraryManagement;
END
GO

USE LibraryManagement;
GO

-- ============================================
-- DROP OLD TABLES (IF EXISTS)
-- ============================================
IF OBJECT_ID('dbo.BorrowingDetail', 'U') IS NOT NULL DROP TABLE dbo.BorrowingDetail;
IF OBJECT_ID('dbo.Borrowing', 'U') IS NOT NULL DROP TABLE dbo.Borrowing;
IF OBJECT_ID('dbo.Copy', 'U') IS NOT NULL DROP TABLE dbo.Copy;
IF OBJECT_ID('dbo.Document', 'U') IS NOT NULL DROP TABLE dbo.Document;
IF OBJECT_ID('dbo.Category', 'U') IS NOT NULL DROP TABLE dbo.Category;
IF OBJECT_ID('dbo.Author', 'U') IS NOT NULL DROP TABLE dbo.Author;
IF OBJECT_ID('dbo.Member', 'U') IS NOT NULL DROP TABLE dbo.Member;
IF OBJECT_ID('dbo.CopyStatus', 'U') IS NOT NULL DROP TABLE dbo.CopyStatus;
GO

-- ============================================
-- TABLE: CopyStatus
-- ============================================
CREATE TABLE CopyStatus (
    status_id INT PRIMARY KEY IDENTITY(1,1),
    status_name NVARCHAR(20) NOT NULL UNIQUE,
    description NVARCHAR(100)
);
GO

-- ============================================
-- TABLE: Category
-- ============================================
CREATE TABLE Category (
    category_id INT PRIMARY KEY IDENTITY(1,1),
    category_name NVARCHAR(100) NOT NULL UNIQUE,
    description NVARCHAR(255)
);
GO

-- ============================================
-- TABLE: Author
-- ============================================
CREATE TABLE Author (
    author_id INT PRIMARY KEY IDENTITY(1,1),
    author_name NVARCHAR(255) NOT NULL,
    birth_year INT,
    nationality NVARCHAR(100),
    CONSTRAINT UQ_Author_Name UNIQUE (author_name)
);
GO

-- ============================================
-- TABLE: Document
-- ============================================
CREATE TABLE Document (
    document_id NVARCHAR(50) PRIMARY KEY,
    title NVARCHAR(255) NOT NULL,
    author_id INT NOT NULL,
    category_id INT NOT NULL,
    publisher NVARCHAR(255),
    publish_year INT,
    isbn NVARCHAR(20),
    pages INT,
    language NVARCHAR(50) DEFAULT 'Vietnamese',
    description NVARCHAR(MAX),
    created_date DATETIME DEFAULT GETDATE(),
    updated_date DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Document_Author FOREIGN KEY (author_id) REFERENCES Author(author_id),
    CONSTRAINT FK_Document_Category FOREIGN KEY (category_id) REFERENCES Category(category_id)
);
GO

-- ============================================
-- TABLE: Copy
-- ============================================
CREATE TABLE Copy (
    copy_id NVARCHAR(50) PRIMARY KEY,
    document_id NVARCHAR(50) NOT NULL,
    status_id INT NOT NULL,
    location NVARCHAR(100),
    purchase_date DATE,
    price DECIMAL(10,2),
    notes NVARCHAR(255),
    created_date DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Copy_Document FOREIGN KEY (document_id) REFERENCES Document(document_id) ON DELETE CASCADE,
    CONSTRAINT FK_Copy_Status FOREIGN KEY (status_id) REFERENCES CopyStatus(status_id)
);
GO

-- ============================================
-- TABLE: Member
-- ============================================
CREATE TABLE Member (
    member_id NVARCHAR(50) PRIMARY KEY,
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) UNIQUE,
    phone NVARCHAR(20),
    address NVARCHAR(500),
    member_type NVARCHAR(50) DEFAULT 'Regular',
    registration_date DATE DEFAULT CAST(GETDATE() AS DATE),
    expiry_date DATE,
    is_active BIT DEFAULT 1
);
GO

-- ============================================
-- TABLE: Borrowing
-- ============================================
CREATE TABLE Borrowing (
    borrowing_id INT PRIMARY KEY IDENTITY(1,1),
    member_id NVARCHAR(50) NOT NULL,
    borrow_date DATE NOT NULL DEFAULT CAST(GETDATE() AS DATE),
    due_date DATE NOT NULL,
    return_date DATE,
    status NVARCHAR(20) DEFAULT 'Borrowing',
    notes NVARCHAR(255),
    CONSTRAINT FK_Borrowing_Member FOREIGN KEY (member_id) REFERENCES Member(member_id),
    CONSTRAINT CHK_Borrowing_Status CHECK (status IN ('Borrowing', 'Returned', 'Overdue'))
);
GO

-- ============================================
-- TABLE: BorrowingDetail
-- ============================================
CREATE TABLE BorrowingDetail (
    detail_id INT PRIMARY KEY IDENTITY(1,1),
    borrowing_id INT NOT NULL,
    copy_id NVARCHAR(50) NOT NULL,
    actual_return_date DATE,
    condition_on_borrow NVARCHAR(50),
    condition_on_return NVARCHAR(50),
    fine_amount DECIMAL(10,2) DEFAULT 0,
    CONSTRAINT FK_BorrowingDetail_Borrowing FOREIGN KEY (borrowing_id) REFERENCES Borrowing(borrowing_id) ON DELETE CASCADE,
    CONSTRAINT FK_BorrowingDetail_Copy FOREIGN KEY (copy_id) REFERENCES Copy(copy_id)
);
GO

-- ============================================
-- INDEXES FOR PERFORMANCE OPTIMIZATION
-- ============================================
CREATE INDEX IX_Document_Title ON Document(title);
CREATE INDEX IX_Document_Author ON Document(author_id);
CREATE INDEX IX_Document_Category ON Document(category_id);
CREATE INDEX IX_Copy_Document ON Copy(document_id);
CREATE INDEX IX_Copy_Status ON Copy(status_id);
CREATE INDEX IX_Borrowing_Member ON Borrowing(member_id);
CREATE INDEX IX_Borrowing_Status ON Borrowing(status);
CREATE INDEX IX_BorrowingDetail_Borrowing ON BorrowingDetail(borrowing_id);
CREATE INDEX IX_BorrowingDetail_Copy ON BorrowingDetail(copy_id);
GO

PRINT 'Schema created successfully!';
GO

-- ============================================
-- PART 2: SAMPLE DATA
-- ============================================

-- ============================================
-- 1. COPY STATUS
-- ============================================
INSERT INTO CopyStatus (status_name, description) VALUES
('GOOD', 'Good condition, available for borrowing'),
('DAMAGED', 'Damaged, needs repair'),
('LOST', 'Lost'),
('MAINTENANCE', 'Under maintenance');
GO

-- ============================================
-- 2. CATEGORIES
-- ============================================
INSERT INTO Category (category_name, description) VALUES
('Literature', 'Literature books domestic and foreign'),
('Science', 'Natural science books'),
('Technology', 'Information technology and engineering books'),
('History', 'Vietnam and world history books'),
('Economics', 'Economics and management books'),
('Psychology', 'Psychology and self-development books'),
('Children', 'Books for children'),
('Textbook', 'Textbooks for all levels');
GO

-- ============================================
-- 3. AUTHORS
-- ============================================
INSERT INTO Author (author_name, birth_year, nationality) VALUES
('Nguyen Nhat Anh', 1955, 'Vietnam'),
('To Hoai', 1920, 'Vietnam'),
('Nam Cao', 1915, 'Vietnam'),
('Nguyen Du', 1766, 'Vietnam'),
('Dale Carnegie', 1888, 'USA'),
('Robert Kiyosaki', 1947, 'USA'),
('Yuval Noah Harari', 1976, 'Israel'),
('J.K. Rowling', 1965, 'UK'),
('Haruki Murakami', 1949, 'Japan'),
('Paulo Coelho', 1947, 'Brazil');
GO

-- ============================================
-- 4. DOCUMENTS
-- ============================================
INSERT INTO Document (document_id, title, author_id, category_id, publisher, publish_year, isbn, pages, language) VALUES
('DOC001', 'I See Yellow Flowers on Green Grass', 1, 1, 'Tre Publishing House', 2010, '978-604-1-00000-1', 368, 'Vietnamese'),
('DOC002', 'Cricket Adventure', 2, 7, 'Kim Dong Publishing House', 1941, '978-604-1-00000-2', 200, 'Vietnamese'),
('DOC003', 'Chi Pheo', 3, 1, 'Literature Publishing House', 1941, '978-604-1-00000-3', 150, 'Vietnamese'),
('DOC004', 'The Tale of Kieu', 4, 1, 'Literature Publishing House', 1820, '978-604-1-00000-4', 250, 'Vietnamese'),
('DOC005', 'How to Win Friends and Influence People', 5, 6, 'HCMC General Publishing House', 1936, '978-604-1-00000-5', 320, 'Vietnamese'),
('DOC006', 'Rich Dad Poor Dad', 6, 5, 'Labor Publishing House', 1997, '978-604-1-00000-6', 280, 'Vietnamese'),
('DOC007', 'Sapiens: A Brief History of Humankind', 7, 4, 'World Publishing House', 2011, '978-604-1-00000-7', 512, 'Vietnamese'),
('DOC008', 'Harry Potter and the Philosophers Stone', 8, 7, 'Tre Publishing House', 1997, '978-604-1-00000-8', 450, 'Vietnamese'),
('DOC009', 'Kafka on the Shore', 9, 1, 'Writers Association Publishing House', 2002, '978-604-1-00000-9', 505, 'Vietnamese'),
('DOC010', 'The Alchemist', 10, 1, 'Writers Association Publishing House', 1988, '978-604-1-00001-0', 227, 'Vietnamese'),
('DOC011', 'Java Programming Basics', 5, 3, 'Statistics Publishing House', 2020, '978-604-1-00001-1', 450, 'Vietnamese'),
('DOC012', 'Data Structures and Algorithms', 5, 3, 'National University Publishing House', 2019, '978-604-1-00001-2', 520, 'Vietnamese');
GO

-- ============================================
-- 5. COPIES
-- ============================================
INSERT INTO Copy (copy_id, document_id, status_id, location, purchase_date, price) VALUES
-- I See Yellow Flowers on Green Grass (3 copies)
('COPY001', 'DOC001', 1, 'Shelf A1', '2023-01-15', 95000),
('COPY002', 'DOC001', 1, 'Shelf A1', '2023-01-15', 95000),
('COPY003', 'DOC001', 2, 'Shelf A1', '2023-01-15', 95000),
-- Cricket Adventure (2 copies)
('COPY004', 'DOC002', 1, 'Shelf B1', '2023-02-10', 65000),
('COPY005', 'DOC002', 1, 'Shelf B1', '2023-02-10', 65000),
-- Chi Pheo (2 copies)
('COPY006', 'DOC003', 1, 'Shelf A2', '2023-03-05', 45000),
('COPY007', 'DOC003', 1, 'Shelf A2', '2023-03-05', 45000),
-- The Tale of Kieu (3 copies)
('COPY008', 'DOC004', 1, 'Shelf A3', '2023-01-20', 120000),
('COPY009', 'DOC004', 1, 'Shelf A3', '2023-01-20', 120000),
('COPY010', 'DOC004', 3, 'Shelf A3', '2023-01-20', 120000),
-- How to Win Friends (4 copies)
('COPY011', 'DOC005', 1, 'Shelf C1', '2023-02-15', 85000),
('COPY012', 'DOC005', 1, 'Shelf C1', '2023-02-15', 85000),
('COPY013', 'DOC005', 1, 'Shelf C1', '2023-02-15', 85000),
('COPY014', 'DOC005', 2, 'Shelf C1', '2023-02-15', 85000),
-- Rich Dad Poor Dad (3 copies)
('COPY015', 'DOC006', 1, 'Shelf C2', '2023-03-10', 75000),
('COPY016', 'DOC006', 1, 'Shelf C2', '2023-03-10', 75000),
('COPY017', 'DOC006', 1, 'Shelf C2', '2023-03-10', 75000),
-- Sapiens (2 copies)
('COPY018', 'DOC007', 1, 'Shelf D1', '2023-04-01', 180000),
('COPY019', 'DOC007', 1, 'Shelf D1', '2023-04-01', 180000),
-- Harry Potter (3 copies)
('COPY020', 'DOC008', 1, 'Shelf B2', '2023-02-20', 150000),
('COPY021', 'DOC008', 1, 'Shelf B2', '2023-02-20', 150000),
('COPY022', 'DOC008', 1, 'Shelf B2', '2023-02-20', 150000),
-- Kafka on the Shore (2 copies)
('COPY023', 'DOC009', 1, 'Shelf A4', '2023-03-15', 135000),
('COPY024', 'DOC009', 1, 'Shelf A4', '2023-03-15', 135000),
-- The Alchemist (3 copies)
('COPY025', 'DOC010', 1, 'Shelf A5', '2023-01-25', 79000),
('COPY026', 'DOC010', 1, 'Shelf A5', '2023-01-25', 79000),
('COPY027', 'DOC010', 1, 'Shelf A5', '2023-01-25', 79000),
-- Java Programming (2 copies)
('COPY028', 'DOC011', 1, 'Shelf E1', '2023-04-10', 250000),
('COPY029', 'DOC011', 1, 'Shelf E1', '2023-04-10', 250000),
-- Data Structures (2 copies)
('COPY030', 'DOC012', 1, 'Shelf E2', '2023-04-15', 280000),
('COPY031', 'DOC012', 1, 'Shelf E2', '2023-04-15', 280000);
GO

-- ============================================
-- 6. MEMBERS
-- ============================================
INSERT INTO Member (member_id, full_name, email, phone, address, member_type, registration_date, expiry_date, is_active) VALUES
('MEM001', 'Nguyen Van An', 'nguyenvanan@email.com', '0901234567', '123 Le Loi, District 1, HCMC', 'VIP', '2023-01-01', '2024-01-01', 1),
('MEM002', 'Tran Thi Binh', 'tranthibinh@email.com', '0902345678', '456 Nguyen Hue, District 1, HCMC', 'Regular', '2023-02-15', '2024-02-15', 1),
('MEM003', 'Le Van Cuong', 'levancuong@email.com', '0903456789', '789 Tran Hung Dao, District 5, HCMC', 'Regular', '2023-03-10', '2024-03-10', 1),
('MEM004', 'Pham Thi Dung', 'phamthidung@email.com', '0904567890', '321 Vo Van Tan, District 3, HCMC', 'Student', '2023-01-20', '2024-01-20', 1),
('MEM005', 'Hoang Van Em', 'hoangvanem@email.com', '0905678901', '654 Dien Bien Phu, District 10, HCMC', 'Student', '2023-04-05', '2024-04-05', 1);
GO

-- ============================================
-- 7. BORROWING RECORDS
-- ============================================
INSERT INTO Borrowing (member_id, borrow_date, due_date, return_date, status) VALUES
('MEM001', '2024-01-10', '2024-01-24', '2024-01-22', 'Returned'),
('MEM002', '2024-01-15', '2024-01-29', NULL, 'Borrowing'),
('MEM003', '2024-02-01', '2024-02-15', '2024-02-14', 'Returned'),
('MEM004', '2024-02-10', '2024-02-24', NULL, 'Borrowing'),
('MEM005', '2024-03-01', '2024-03-15', NULL, 'Overdue');
GO

-- ============================================
-- 8. BORROWING DETAILS
-- ============================================
INSERT INTO BorrowingDetail (borrowing_id, copy_id, actual_return_date, condition_on_borrow, condition_on_return, fine_amount) VALUES
-- Borrowing 1 (Returned)
(1, 'COPY001', '2024-01-22', 'Good', 'Good', 0),
(1, 'COPY011', '2024-01-22', 'Good', 'Good', 0),
-- Borrowing 2 (Borrowing)
(2, 'COPY004', NULL, 'Good', NULL, 0),
(2, 'COPY015', NULL, 'Good', NULL, 0),
-- Borrowing 3 (Returned)
(3, 'COPY020', '2024-02-14', 'Good', 'Good', 0),
(3, 'COPY025', '2024-02-14', 'Good', 'Good', 0),
-- Borrowing 4 (Borrowing)
(4, 'COPY028', NULL, 'Good', NULL, 0),
(4, 'COPY030', NULL, 'Good', NULL, 0),
-- Borrowing 5 (Overdue)
(5, 'COPY018', NULL, 'Good', NULL, 50000),
(5, 'COPY023', NULL, 'Good', NULL, 50000);
GO

PRINT 'Sample data inserted successfully!';
PRINT 'Complete file created - ready to import into SQL Server!';
GO
