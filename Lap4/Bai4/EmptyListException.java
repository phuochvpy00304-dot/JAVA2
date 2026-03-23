package Bai4;

public class EmptyListException extends Exception {
    public EmptyListException() {
        super("Danh sach rong! Khong the thuc hien thao tac.");
    }

    public EmptyListException(String message) {
        super(message);
    }
}
