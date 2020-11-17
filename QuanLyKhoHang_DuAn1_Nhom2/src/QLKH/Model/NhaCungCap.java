package QLKH.Model;

public class NhaCungCap {

    private String maNCC;
    private String tenCongTy;
    private String diaChi;
    private String email;
    private int soDienThoai;
    private String hinh;

    public NhaCungCap(String maNCC, String tenCongTy, String diaChi, String email, int soDienThoai, String hinh) {
        this.maNCC = maNCC;
        this.tenCongTy = tenCongTy;
        this.diaChi = diaChi;
        this.email = email;
        this.soDienThoai = soDienThoai;
        this.hinh = hinh;
    }

    public NhaCungCap() {

    }

    public String getMaNCC() {
        return maNCC;
    }

    public String getTenCongTy() {
        return tenCongTy;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getEmail() {
        return email;
    }

    public int getSoDienThoai() {
        return soDienThoai;
    }

    public String getHinh() {
        return hinh;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setTenCongTy(String tenCongTy) {
        this.tenCongTy = tenCongTy;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSoDienThoai(int soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

}
