package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import QLKH.Model.HangHoa;
import QLKH.Model.HoaDonChiTiet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhapDAO {
    // Insert Hàng Hóa Và Hóa Đơn chi Tiết

    /**
     *
     * @param model
     */
    public void insertHH(HangHoa model) {
        String sql = "INSERT INTO HangHoa (MaHH, TenHH, MaLoaiHang,  TenLoaiHang, DonVi, Hinh, Ghichu) VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        JdbcHelper.executeUpdate(sql, model.getMaHH(), model.getTenHH(), model.getMaLoai(), model.getDonVi(), model.getGhiChu());
    }

    public void insertHD(HoaDonChiTiet model) {
        String sql = "INSERT INTO HoaDonBanChiTiet (MaHDBanCT, MaHDBan, MaHH, TenHH, SoLuong, DonVi, NgayBan, DonGia, GhiChu) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        JdbcHelper.executeUpdate(sql, model.getMaHDCT(), model.getMaHD(), model.getMaHH(), model.getMaHH(), model.getSoluong(), model.getDonvi(), model.getNgayLap(), model.getDongia(), model.getGhiChu());
    }

    /**
     *
     * @param model
     */
    public void UpdateHH(HangHoa model) {
        String sql = "UPDATE HangHoa SET MaHH=? ,TenHang=?, MaLoai=?, TenLoaiHang=? Donvi=?, Hinh=?, Ghichu=?";
        JdbcHelper.executeUpdate(sql, model.getMaHH(),model.getTenHH(), model.getMaLoai(),  model.getDonVi(), model.getGhiChu());
    }

    public void UpdateHD(HoaDonChiTiet model) {
        String sql = "UPDATE HoaDonBanChiTiet SET MaHDBanCT=? , MaHDBan=? , MaHH=? , TenHH=? , SoLuong=? , DonVi=? , NgayBan=? , DonGia=? , GhiChu=? ";
        JdbcHelper.executeUpdate(sql, model.getMaHDCT(), model.getMaHD(), model.getMaHH(), model.getMaHH(), model.getSoluong(), model.getDonvi(), model.getNgayLap(), model.getDongia(), model.getGhiChu());
    }

    public void deleteHH(String MaHH) {
        String sql = "DELETE FROM HangHoa WHERE MaHH=?";
        JdbcHelper.executeUpdate(sql, MaHH);
    }

    public void deleteHD(String MaHD) {
        String sql = "DELETE FROM HangHoa WHERE MaHD=?";
        JdbcHelper.executeUpdate(sql, MaHD);
    }

    public List<HangHoa> selectHH() {
        String sql = "SELECT * FROM HangHoa";
        return selectHH(sql);
    }

    public HangHoa findByIdHH(String maHH) {
        String sql = "SELECT * FROM HangHoa WHERE MaHH=?";
        List<HangHoa> list = selectHH(sql, maHH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HangHoa> selectHH(String sql, Object... args) {
        List<HangHoa> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);

                while (rs.next()) {
                    HangHoa model = readFromResultSetHH(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    public List<HoaDonChiTiet> selectHD() {
        String sql = "SELECT * FROM HoaDonBanChiTiet";
        return selectHD(sql);
    }

    public List<HoaDonChiTiet> findByIdHD(String maHDMuaCT) {
        String sql = "SELECT * FROM HoaDonBanChiTiet WHERE MaHDMua=?";
        List<HoaDonChiTiet> list = selectHD(sql, maHDMuaCT);
        return list;
    }

    private List<HoaDonChiTiet> selectHD(String sql, Object... args) {
        List<HoaDonChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);

                while (rs.next()) {
                    HoaDonChiTiet model = readFromResultSetHD(rs);
                    list.add(model);
                }
            } finally {
                rs.getStatement().getConnection().close();
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        return list;
    }

    private HangHoa readFromResultSetHH(ResultSet rs) throws SQLException {
        HangHoa model = new HangHoa(
                rs.getString("MaHH"),
                rs.getString("TenHH"),
                rs.getString("MaLoai"),            
                rs.getString("DonGia"),
                rs.getString("DonVi"),
                rs.getString("Hinh"),
                rs.getString("Ghichu"));

        return model;
    }

    private HoaDonChiTiet readFromResultSetHD(ResultSet rs) throws SQLException {
        HoaDonChiTiet model = new HoaDonChiTiet(rs.getString("MaHDBanCT"),
                rs.getString("MaHDBan"),
                rs.getString("MaHH"),
                rs.getString("TenHH"),
                rs.getInt("SoLuong"),
                rs.getString("DonVi"),
                rs.getString("NgayBan"),
                rs.getDouble("DonGia"),
                rs.getString("GhiChu ")
        );

        return model;
    }
}
