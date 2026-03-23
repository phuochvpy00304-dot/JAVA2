package Bai4;

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

    public T layPhanTuDauTien() throws EmptyListException {
        if (list.isEmpty()) {
            throw new EmptyListException("Khong the lay phan tu dau tien vi danh sach rong!");
        }
        return list.get(0);
    }

    public T layPhanTuCuoiCung() throws EmptyListException {
        if (list.isEmpty()) {
            throw new EmptyListException("Khong the lay phan tu cuoi cung vi danh sach rong!");
        }
        return list.get(list.size() - 1);
    }

    public ArrayList<T> getList() {
        return list;
    }
}
