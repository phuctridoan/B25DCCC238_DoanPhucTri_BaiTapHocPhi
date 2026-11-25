import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

interface MienGiam {
    long tinhGiam(long hocPhi);
}

class ChinhSachCS implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 50 / 100;
    }
}

class ChinhSachHN implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return hocPhi * 70 / 100;
    }
}

class ChinhSachHB implements MienGiam {
    @Override
    public long tinhGiam(long hocPhi) {
        return Math.min(hocPhi, 3000000L);
    }
}

abstract class SinhVien {
    protected static final long DON_GIA = 450000L;
    protected String maSV;
    protected String maHe;
    protected int soTinChi;
    protected MienGiam mienGiam;

    public SinhVien(String maSV, String maHe, int soTinChi) {
        this.maSV = maSV;
        this.maHe = maHe;
        this.soTinChi = soTinChi;
        this.mienGiam = null;
    }

    public String getMaSV() {
        return maSV;
    }

    public String getMaHe() {
        return maHe;
    }

    public void setMienGiam(MienGiam mienGiam) {
        this.mienGiam = mienGiam;
    }

    public abstract long hocPhi();

    public long soTienGiam() {
        if (mienGiam == null) {
            return 0L;
        }
        return mienGiam.tinhGiam(hocPhi());
    }

    public long phaiNop() {
        return hocPhi() - soTienGiam();
    }

    @Override
    public String toString() {
        long hp = hocPhi();
        long giam = soTienGiam();
        long nop = hp - giam;
        return maSV + " " + maHe + " " + hp + " " + giam + " " + nop;
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

public class bai4 {
    private static MienGiam taoChinhSach(String maChinhSach) {
        switch (maChinhSach) {
            case "CS":
                return new ChinhSachCS();
            case "HN":
                return new ChinhSachHN();
            case "HB":
                return new ChinhSachHB();
            default:
                return null;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        if (!sc.hasNextInt()) return;
        int n = sc.nextInt();

        Map<String, SinhVien> mapSV = new LinkedHashMap<>();
        for (int i = 0; i < n; i++) {
            String maHe = sc.next();
            String maSV = sc.next();
            int soTinChi = sc.nextInt();

            SinhVien sv = null;
            switch (maHe) {
                case "CQ":
                    sv = new SinhVienCQ(maSV, soTinChi);
                    break;
                case "CLC":
                    sv = new SinhVienCLC(maSV, soTinChi);
                    break;
                case "LT":
                    sv = new SinhVienLT(maSV, soTinChi);
                    break;
            }
            if (sv != null) {
                mapSV.put(maSV, sv);
            }
        }

        int m = sc.nextInt();
        List<String> notFoundList = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            String maSV = sc.next();
            String maChinhSach = sc.next();

            SinhVien sv = mapSV.get(maSV);
            if (sv == null) {
                notFoundList.add("NOT FOUND " + maSV);
            } else {
                sv.setMienGiam(taoChinhSach(maChinhSach));
            }
        }

        for (String msg : notFoundList) {
            System.out.println(msg);
        }

        for (SinhVien sv : mapSV.values()) {
            System.out.println(sv);
        }

        String[] thuTuHe = {"CQ", "CLC", "LT"};
        for (String he : thuTuHe) {
            int dem = 0;
            long tongPhaiNop = 0;
            for (SinhVien sv : mapSV.values()) {
                if (sv.getMaHe().equals(he)) {
                    dem++;
                    tongPhaiNop += sv.phaiNop();
                }
            }
            if (dem > 0) {
                System.out.println(he + " " + dem + " " + tongPhaiNop);
            }
        }

        sc.close();
    }
}