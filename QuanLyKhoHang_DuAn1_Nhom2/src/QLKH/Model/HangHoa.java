package QLKH.Model;

public class HangHoa {

    private String maHH;
    private String tenHH;
    private String maLoai;
    private String donGia;
    private String donVi;
    private String hinh;
    private String ghiChu;

    public HangHoa(String maHH,  String maLoai,String tenHH, String donGia, String donVi,String ghiChu,String hinh) {
        this.maHH = maHH;
        this.tenHH = tenHH;
        this.maLoai = maLoai;
        this.donGia = donGia;
        this.donVi = donVi;
        this.hinh = hinh;
        this.ghiChu = ghiChu;
    }

    public HangHoa() {

    }

    public String getMaHH() {
        return maHH;
    }

    public String getTenHH() {
        return tenHH;
    }

    public String getMaLoai() {
        return maLoai;
    }

    public String getDonGia() {
        return donGia;
    }

    public String getDonVi() {
        return donVi;
    }

    public String getHinh() {
        return hinh;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setMaHH(String maHH) {
        this.maHH = maHH;
    }

    public void setTenHH(String tenHH) {
        this.tenHH = tenHH;
    }

    public void setMaLoai(String maLoai) {
        this.maLoai = maLoai;
    }

    public void setDonGia(String donGia) {
        this.donGia = donGia;
    }

    public void setDonVi(String donVi) {
        this.donVi = donVi;
    }

    public void setHinh(String hinh) {
        this.hinh = hinh;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    @Override
    public String toString() {
        return getTenHH();
    }

   

}
