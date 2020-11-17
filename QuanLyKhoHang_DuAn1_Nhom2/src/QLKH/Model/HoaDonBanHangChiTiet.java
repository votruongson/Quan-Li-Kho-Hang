package QLKH.Model;

import java.util.Date;

public class HoaDonBanHangChiTiet {
    private String MaHDBan;
    private String MaHH;
    private String TenHang;
    private int SoLuong;
    private String DonVi;
    private Date NgayBan;
    private double donGia;
    private String GhiChu;

    public HoaDonBanHangChiTiet(String MaHDBan, String MaHH, String TenHang, int SoLuong, String DonVi, Date NgayBan, double donGia, String GhiChu) {
        this.MaHDBan = MaHDBan;
        this.MaHH = MaHH;
        this.TenHang = TenHang;
        this.SoLuong = SoLuong;
        this.DonVi = DonVi;
        this.NgayBan = NgayBan;
        this.donGia = donGia;
        this.GhiChu = GhiChu;
    }

    public HoaDonBanHangChiTiet() {
    }

    public String getMaHDBan() {
        return MaHDBan;
    }

    public void setMaHDBan(String MaHDBan) {
        this.MaHDBan = MaHDBan;
    }

    public String getMaHH() {
        return MaHH;
    }

    public void setMaHH(String MaHH) {
        this.MaHH = MaHH;
    }

    public String getTenHang() {
        return TenHang;
    }

    public void setTenHang(String TenHang) {
        this.TenHang = TenHang;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public String getDonVi() {
        return DonVi;
    }

    public void setDonVi(String DonVi) {
        this.DonVi = DonVi;
    }

    public Date getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(Date NgayBan) {
        this.NgayBan = NgayBan;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
    

}
