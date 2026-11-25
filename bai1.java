import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class SinhVien {
    private String maSV;
    private String hoTen;
    private String ngaySinh;

    public SinhVien(String maSV, String hoTen, String ngaySinh) {
        this.maSV = maSV.trim();
        this.hoTen = chuanHoaHoTen(hoTen);
        this.ngaySinh = chuanHoaNgaySinh(ngaySinh);
    }

    private String chuanHoaHoTen(String s) {
        String[] words = s.trim().toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (!words[i].isEmpty()) {
                sb.append(Character.toUpperCase(words[i].charAt(0)))
                  .append(words[i].substring(1));
                if (i < words.length - 1) {
                    sb.append(" ");
                }
            }
        }
        return sb.toString();
    }

    private String chuanHoaNgaySinh(String s) {
        String[] parts = s.trim().split("/");
        int ngay = Integer.parseInt(parts[0].trim());
        int thang = Integer.parseInt(parts[1].trim());
        int nam = Integer.parseInt(parts[2].trim());
        return String.format("%02d/%02d/%04d", ngay, thang, nam);
    }

    @Override
    public String toString() {
        return maSV + " " + hoTen + " " + ngaySinh;
    }
}

public class bai1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextLine()) return;
        int n = Integer.parseInt(sc.nextLine().trim());
        List<SinhVien> danhSach = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String maSV = sc.nextLine();
            String hoTen = sc.nextLine();
            String ngaySinh = sc.nextLine();
            danhSach.add(new SinhVien(maSV, hoTen, ngaySinh));
        }

        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }
        sc.close();
    }
}