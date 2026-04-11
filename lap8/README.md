# LAB 8 - Java Advanced Features

## Mục tiêu
Sau khi hoàn thành bài lab này, sinh viên có khả năng:
- Vận dụng Autoboxing và Unboxing để xử lý dữ liệu số trong Collection
- Xây dựng và sử dụng Record để mô hình hóa dữ liệu
- Áp dụng Annotations (@Override, @Deprecated) và tự định nghĩa annotation
- Sử dụng Reflection để đọc annotation tại runtime
- Sử dụng Text Block để quản lý văn bản nhiều dòng
- Tổ chức và xử lý danh sách đối tượng Java

## Cấu trúc thư mục

```
lap8/
├── Bai1/
│   └── Main.java              # Quản lý dữ liệu số với Autoboxing/Unboxing
├── Bai2/
│   ├── Student.java           # Record Student
│   └── Main.java              # Quản lý sinh viên
├── Bai3/
│   ├── Developer.java         # Custom annotation
│   ├── Employee.java          # Class Employee
│   ├── Manager.java           # Class Manager
│   ├── EmployeeService.java   # Service với @Developer
│   └── Main.java              # Demo annotations và Reflection
├── Bai4/
│   └── Main.java              # Text Block examples
├── Bai5/
│   └── Main.java              # Tổng hợp tất cả kiến thức
└── README.md
```

## Hướng dẫn chạy

### Bài 1: Quản lý dữ liệu số
```bash
cd lap8/Bai1
javac Main.java
java lap8.Bai1.Main
```

**Chức năng:**
- Nhập 5 điểm kiểm tra (nhập -1 nếu chưa có điểm)
- Hiển thị danh sách điểm
- Tính điểm trung bình (bỏ qua null)
- Phân loại: Giỏi (≥8), Khá (6.5-8), Trung bình (<6.5)

**Kiến thức:**
- Autoboxing: `int` → `Integer`
- Unboxing: `Integer` → `int`
- Xử lý null an toàn

### Bài 2: Quản lý sinh viên với Record
```bash
cd lap8/Bai2
javac *.java
java lap8.Bai2.Main
```

**Chức năng:**
- Tạo Record Student (id, name, gpa)
- Validation trong constructor (GPA: 0.0-4.0)
- Phương thức `isScholarshipEligible()` (GPA ≥ 3.2)
- Lọc và hiển thị sinh viên đủ điều kiện học bổng

**Kiến thức:**
- Record (Java 14+)
- Compact constructor
- Immutability
- Stream API

### Bài 3: Annotations và Reflection
```bash
cd lap8/Bai3
javac *.java
java lap8.Bai3.Main
```

**Chức năng:**
- Class Employee với phương thức `getSalary()` (deprecated)
- Class Manager override phương thức
- Custom annotation `@Developer`
- Sử dụng Reflection để đọc annotation

**Kiến thức:**
- `@Override`
- `@Deprecated`
- Custom annotation
- Reflection API
- `@Retention`, `@Target`

### Bài 4: Text Block
```bash
cd lap8/Bai4
javac Main.java
java lap8.Bai4.Main
```

**Chức năng:**
- Thông báo chào mừng nhiều dòng
- Email với `.formatted()`
- HTML report
- SQL query

**Kiến thức:**
- Text Block (Java 15+)
- `.formatted()` method
- Multi-line strings
- Code readability

### Bài 5: Tổng hợp (Sử dụng AI)
```bash
cd lap8/Bai5
javac Main.java
java lap8.Bai5.Main
```

**Chức năng:**
- Nhập điểm 5 sinh viên
- Tính điểm trung bình (xử lý null)
- Hiển thị kết quả bằng Text Block
- Giải thích Autoboxing/Unboxing
- Cảnh báo NullPointerException

**Kiến thức:**
- Tổng hợp tất cả kiến thức từ Bài 1-4
- Best practices
- Error handling

## Compile tất cả từ thư mục gốc

```bash
# Từ thư mục JAVA2
javac lap8/Bai1/*.java
javac lap8/Bai2/*.java
javac lap8/Bai3/*.java
javac lap8/Bai4/*.java
javac lap8/Bai5/*.java
```

## Chạy từng bài

```bash
java lap8.Bai1.Main
java lap8.Bai2.Main
java lap8.Bai3.Main
java lap8.Bai4.Main
java lap8.Bai5.Main
```

## Kiến thức chính

### 1. Autoboxing và Unboxing
- **Autoboxing**: Tự động chuyển primitive type → wrapper class
  ```java
  int x = 5;
  Integer y = x; // Autoboxing
  ```
- **Unboxing**: Tự động chuyển wrapper class → primitive type
  ```java
  Integer x = 10;
  int y = x; // Unboxing
  ```
- **Lưu ý**: Unboxing giá trị null → NullPointerException

### 2. Record (Java 14+)
- Immutable data class
- Tự động tạo: constructor, getter, equals, hashCode, toString
- Compact constructor để validation
- Có thể thêm method

### 3. Annotations
- **Built-in**: `@Override`, `@Deprecated`, `@SuppressWarnings`
- **Custom**: Tự định nghĩa với `@interface`
- **Meta-annotations**: `@Retention`, `@Target`
- **Reflection**: Đọc annotation tại runtime

### 4. Text Block (Java 15+)
- Multi-line string literals
- Syntax: `"""..."""`
- Tự động xử lý indentation
- `.formatted()` để chèn dữ liệu

## Yêu cầu hệ thống
- Java 15+ (để sử dụng Text Block)
- Java 14+ (để sử dụng Record)

## Lưu ý
- Tất cả code đã được test và chạy thành công
- Code có comments đầy đủ bằng tiếng Việt
- Xử lý exception và validation đầy đủ
- Follow Java naming conventions
