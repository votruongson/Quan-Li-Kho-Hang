package QLKH.Model;

public class NhanVien {

    private String maNV;
    private String hoTen;
    private String taiKhoan;
    private String matKhau;
    private boolean gioiTinh = false;
    private String soDienThoai;
    private String email;
    private String diaChi;
    private boolean vaiTro = false;
    private String hinh;

    public NhanVien(String maNV, String hoTen, String taiKhoan, String matKhau, String soDienThoai, String email, String diaChi, String hinh) {
        this.maNV = maNV;
        this.hoTen = hoTen;
        this.taiKhoan = taiKhoan;
        this.matKhau = matKhau;
        this.soDienThoai = soDienThoai;
        this.email = email;
        this.diaChi = diaChi;
        this.hinh = hinh;
    }

    public NhanVien() {

    }

    public NhanVien(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getTaiKhoan() {
        return taiKhoan;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public String getEmail() {
        return email;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public boolean isVaiTro() {
        return vaiTro;
    }

    public String getHinh() {
        return hinh;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setTaiKhoan(String taiKhoan) {
        this.taiKhoan = taiKhoan;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setVaiTro(boolean vaiTro) {
        this.vaiTro = vaiTro;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}
