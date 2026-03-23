package Bai3;

import java.util.ArrayList;

public class GenericManager<T> {
    private ArrayList<T> list;

    public GenericManager() {
        list = new ArrayList<>();
    }

    public void themDoiTuong(T obj) {
        list.add(obj);
        System.out.println("Them doi tuong thanh cong!");
    }

    public void hienThiDanhSach() {
        if (list.isEmpty()) {
            System.out.println("Danh sach rong!");
            return;
        }
        System.out.println("\n=== DANH SACH ===");
        for (T obj : list) {
            System.out.println(obj);
        }
    }

    public int laySoLuong() {
        return list.size();
    }

    public ArrayList<T> getList() {
        return list;
    }
}
