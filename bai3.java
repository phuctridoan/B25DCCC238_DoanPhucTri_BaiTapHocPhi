import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class DuLieuKhongHopLeException extends Exception {
    public DuLieuKhongHopLeException(String message) {
        super(message);
    }
}

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;
    protected String maSV;
    protected String maHe;
    protected int soTinChi;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
    }

    public String getMaSV() {
        return maSV;
    }

    public abstract long hocPhi();

    @Override
    public String toString() {
        return maSV + " " + maHe + " " + hocPhi();
    }
}

class SinhVienCQ extends SinhVien {
    public SinhVienCQ(String maSV, int soTinChi) {
        super(maSV, "CQ", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA;
    }
}

class SinhVienCLC extends SinhVien {
    public SinhVienCLC(String maSV, int soTinChi) {
        super(maSV, "CLC", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA * 3 / 2;
    }
}

class SinhVienLT extends SinhVien {
    public SinhVienLT(String maSV, int soTinChi) {
        super(maSV, "LT", soTinChi);
    }

    @Override
    public long hocPhi() {
        return soTinChi * DON_GIA + 2000000L;
    }
}

class QuanLyHocPhi {
    private ArrayList<SinhVien> danhSach;

    public QuanLyHocPhi() {
        this.danhSach = new ArrayList<>();
    }

    public void themSinhVien(String maHe, String maSV, String tinChiStr) throws DuLieuKhongHopLeException {
        if (!maHe.equals("CQ") && !maHe.equals("CLC") && !maHe.equals("LT")) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        int soTinChi;
        try {
            soTinChi = Integer.parseInt(tinChiStr);
        } catch (NumberFormatException e) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        if (soTinChi < 1 || soTinChi > 30) {
            throw new DuLieuKhongHopLeException("INVALID " + maSV);
        }

        for (SinhVien sv : danhSach) {
            if (sv.getMaSV().equals(maSV)) {
                throw new DuLieuKhongHopLeException("INVALID " + maSV);
            }
        }

        switch (maHe) {
            case "CQ":
                danhSach.add(new SinhVienCQ(maSV, soTinChi));
                break;
            case "CLC":
                danhSach.add(new SinhVienCLC(maSV, soTinChi));
                break;
            case "LT":
                danhSach.add(new SinhVienLT(maSV, soTinChi));
                break;
        }
    }

    public void sapXep() {
        Collections.sort(danhSach, new Comparator<SinhVien>() {
            @Override
            public int compare(SinhVien a, SinhVien b) {
                int cmp = Long.compare(b.hocPhi(), a.hocPhi());
                if (cmp != 0) {
                    return cmp;
                }
                return a.getMaSV().compareTo(b.getMaSV());
            }
        });
    }

    public long tinhTongHocPhi() {
        long tong = 0;
        for (SinhVien sv : danhSach) {
            tong += sv.hocPhi();
        }
        return tong;
    }

    public void inKetQua() {
        sapXep();
        for (SinhVien sv : danhSach) {
            System.out.println(sv);
        }
        System.out.println("TONG " + tinhTongHocPhi());
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        QuanLyHocPhi ql = new QuanLyHocPhi();
        List<String> danhSachLoi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            String tinChiStr = sc.next();
            try {
                ql.themSinhVien(maHe, maSV, tinChiStr);
            } catch (DuLieuKhongHopLeException e) {
                danhSachLoi.add(e.getMessage());
            }
        }

        for (String loi : danhSachLoi) {
            System.out.println(loi);
        }
        ql.inKetQua();
        sc.close();
    }
}