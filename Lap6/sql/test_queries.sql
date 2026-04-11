-- Test queries cho Lab 6
USE lab06_jdbc1;
GO

-- 1. Kiểm tra dữ liệu trong bảng student
SELECT * FROM student;
GO

-- 2. Kiểm tra dữ liệu trong bảng tree
SELECT * FROM tree;
GO

-- 3. Tìm sinh viên có GPA >= 3.0
SELECT student_id, student_name, gender, gpa 
FROM student 
WHERE gpa >= 3.0 
ORDER BY gpa DESC;
GO

-- 4. Tìm các node con của Root (parent_id = 1)
SELECT node_id, node_name, parent_id, level 
FROM tree 
WHERE parent_id = 1 
ORDER BY node_id;
GO

-- 5. Đếm số node theo từng level
SELECT level, COUNT(*) as total 
FROM tree 
GROUP BY level 
ORDER BY level;
GO

-- 6. Hiển thị cấu trúc cây với thông tin parent
SELECT 
    t.node_id,
    t.node_name,
    t.level,
    p.node_name as parent_name
FROM tree t
LEFT JOIN tree p ON t.parent_id = p.node_id
ORDER BY t.level, t.node_id;
GO

-- 7. Thống kê sinh viên theo giới tính
SELECT 
    gender,
    COUNT(*) as total,
    AVG(gpa) as avg_gpa,
    MAX(gpa) as max_gpa,
    MIN(gpa) as min_gpa
FROM student
GROUP BY gender;
GO

-- 8. Top 3 sinh viên có GPA cao nhất
SELECT TOP 3 student_id, student_name, gender, gpa
FROM student
ORDER BY gpa DESC;
GO
