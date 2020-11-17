package QLKH.Model;

public class HoaDonChiTiet {

    private String MaHDCT;
    private String MaHD;
    private String MaHH;
    private String TenHH;
    private int Soluong;
    private String Donvi;
    private String NgayLap;
    private double Dongia;
    private String GhiChu;

    public HoaDonChiTiet() {
    }

    public HoaDonChiTiet(String MaHDCT, String MaHD, String MaHH, String TenHH, int Soluong, String Donvi, String NgayLap, double Dongia, String GhiChu) {
        this.MaHDCT = MaHDCT;
        this.MaHD = MaHD;
        this.MaHH = MaHH;
        this.TenHH = TenHH;
        this.Soluong = Soluong;
        this.Donvi = Donvi;
        this.NgayLap = NgayLap;
        this.Dongia = Dongia;
        this.GhiChu = GhiChu;
    }

    public String getMaHDCT() {
        return MaHDCT;
    }

    public void setMaHDCT(String MaHDCT) {
        this.MaHDCT = MaHDCT;
    }

    public String getMaHD() {
        return MaHD;
    }

    public void setMaHD(String MaHD) {
        this.MaHD = MaHD;
    }

    public String getMaHH() {
        return MaHH;
    }

    public void setMaHH(String MaHH) {
        this.MaHH = MaHH;
    }

    public String getTenHH() {
        return TenHH;
    }

    public void setTenHH(String TenHH) {
        this.TenHH = TenHH;
    }

    public int getSoluong() {
        return Soluong;
    }

    public void setSoluong(int Soluong) {
        this.Soluong = Soluong;
    }

    public String getDonvi() {
        return Donvi;
    }

    public void setDonvi(String Donvi) {
        this.Donvi = Donvi;
    }

    public String getNgayLap() {
        return NgayLap;
    }

    public void setNgayLap(String NgayLap) {
        this.NgayLap = NgayLap;
    }

    public double getDongia() {
        return Dongia;
    }

    public void setDongia(double Dongia) {
        this.Dongia = Dongia;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

}
