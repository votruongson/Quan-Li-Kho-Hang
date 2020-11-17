package QLKH.Model;

import java.util.Date;

public class HoaDonBanHang {

    private String MaHDBan;
    private String MaKH;
    private String MaNV;
    private Date NgayBan;
    private boolean TrangThai;
    private String GhiChu;

    public HoaDonBanHang(String MaHDBan, String MaKH, String MaNV, Date NgayBan, boolean TrangThai, String GhiChu) {
        this.MaHDBan = MaHDBan;
        this.MaKH = MaKH;
        this.MaNV = MaNV;
        this.NgayBan = NgayBan;
        this.TrangThai = TrangThai;
        this.GhiChu = GhiChu;
    }

    public HoaDonBanHang() {
    }

    public String getMaHDBan() {
        return MaHDBan;
    }

    public void setMaHDBan(String MaHDBan) {
        this.MaHDBan = MaHDBan;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayBan() {
        return NgayBan;
    }

    public void setNgayBan(Date NgayBan) {
        this.NgayBan = NgayBan;
    }

    public boolean isTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(boolean TrangThai) {
        this.TrangThai = TrangThai;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }
}
