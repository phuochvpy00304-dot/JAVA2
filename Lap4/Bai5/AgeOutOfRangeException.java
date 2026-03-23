package Lap4.Bai5;

/**
 * Custom Exception cho tinh huong tuoi khong hop le
 * 
 * Tinh huong: Khi quan ly sinh vien hoac nhan vien, tuoi phai nam trong khoang
 * hop ly
 * - Sinh vien: 16-60 tuoi
 * - Nhan vien: 18-65 tuoi
 * 
 * Thiet ke:
 * - Ke thua tu Exception (checked exception) vi day la loi co the du doan va xu
 * ly
 * - Chua cac field: age (tuoi nhap vao), minAge, maxAge (khoang hop le)
 * - Cung cap cac constructor va getter de lay thong tin chi tiet
 */
public class AgeOutOfRangeException extends Exception {
    private int age;
    private int minAge;
    private int maxAge;

    public AgeOutOfRangeException(int age, int minAge, int maxAge) {
        super(String.format("Tuoi khong hop le: %d. Tuoi phai nam trong khoang %d-%d!",
                age, minAge, maxAge));
        this.age = age;
        this.minAge = minAge;
        this.maxAge = maxAge;
    }

    public int getAge() {
        return age;
    }

    public int getMinAge() {
        return minAge;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public String getDetailedMessage() {
        return String.format("Tuoi nhap vao: %d\nKhoang hop le: %d-%d\nChenh lech: %d",
                age, minAge, maxAge,
                age < minAge ? minAge - age : age - maxAge);
    }
}
