package QLKH.Model;

import java.util.Date;

public class HoaDonMuaHangChiTiet {

    private String MaHDMua;
    private String MaHH;
    private String TenHang;
    private int SoLuong;
    private String DonVi;
    private Date NgayMua;
    private double donGia;
    private String GhiChu;

    public HoaDonMuaHangChiTiet(String MaHDMua, String MaHH, String TenHang, int SoLuong, String DonVi, Date NgayMua, double donGia, String GhiChu) {
        this.MaHDMua = MaHDMua;
        this.MaHH = MaHH;
        this.TenHang = TenHang;
        this.SoLuong = SoLuong;
        this.DonVi = DonVi;
        this.NgayMua = NgayMua;
        this.donGia = donGia;
        this.GhiChu = GhiChu;
    }

    public HoaDonMuaHangChiTiet() {
    }

    public String getMaHDMua() {
        return MaHDMua;
    }

    public void setMaHDMua(String MaHDMua) {
        this.MaHDMua = MaHDMua;
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

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date NgayMua) {
        this.NgayMua = NgayMua;
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
