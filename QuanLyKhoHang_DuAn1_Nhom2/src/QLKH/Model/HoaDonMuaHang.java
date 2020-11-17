package QLKH.Model;

import java.util.Date;

public class HoaDonMuaHang {

    private String MaHDMua;
    private String MaNCC;
    private String MaNV;
    private Date NgayMua;
    private boolean TrangThai;
    private String GhiChu;

    public HoaDonMuaHang(String MaHDMua, String MaNCC, String MaNV, Date NgayMua, boolean TrangThai, String GhiChu) {
        this.MaHDMua = MaHDMua;
        this.MaNCC = MaNCC;
        this.MaNV = MaNV;
        this.NgayMua = NgayMua;
        this.TrangThai = TrangThai;
        this.GhiChu = GhiChu;
    }

    public HoaDonMuaHang() {
    }

    public String getMaHDMua() {
        return MaHDMua;
    }

    public void setMaHDMua(String MaHDMua) {
        this.MaHDMua = MaHDMua;
    }

    public String getMaNCC() {
        return MaNCC;
    }

    public void setMaNCC(String MaNCC) {
        this.MaNCC = MaNCC;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayMua() {
        return NgayMua;
    }

    public void setNgayMua(Date NgayMua) {
        this.NgayMua = NgayMua;
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
