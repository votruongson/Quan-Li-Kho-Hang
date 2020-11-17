package QLKH.Dao;

import QLKH.Helper.DateHelper;
import QLKH.Helper.JdbcHelper;
import QLKH.Model.HoaDonBanHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class HoaDonBanHangDAO {

    public List<HoaDonBanHang> select() {
        String sql = "SELECT * FROM HoaDonBanHang";
        return select(sql);
    }

    public void Insert(HoaDonBanHang model) {
        String sql = "INSERT INTO HoaDonBanHang (MaKH,MaNV,NgayBan,TrangThai,GhiChu) VALUES (?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getMaNV(),
                DateHelper.toString(model.getNgayBan(), "yyyy-MM-dd"),
                model.isTrangThai(),
                model.getGhiChu());

    }

    public void Update(HoaDonBanHang model) {
        String sql = "UPDATE HoaDonBanHang SET MaKH=?,MaNV=?,NgayBan=?,TrangThai=?,GhiChu=? WHERE MaHDBan=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                model.getMaNV(),
                DateHelper.toString(model.getNgayBan(), "yyyy-MM-dd"),
                model.isTrangThai(),
                model.getGhiChu(),
                model.getMaHDBan());

    }

    public void delete(String MaHDMua) {
        String sql = "DELETE FROM HoaDonBanHang WHERE MaHDBan=?";
        JdbcHelper.executeUpdate(sql, MaHDMua);
    }

    public HoaDonBanHang findById(String MaHDBan) {
        String sql = "SELECT * FROM HoaDonBanHang WHERE MaHDBan=?";
        List<HoaDonBanHang> list = select(sql, MaHDBan);
        return list.size() > 0 ? list.get(0) : null;
    }
     public HoaDonBanHang findByIdMax() {
        String sql = "SELECT Top 1* FROM HoaDonBanHang WHERE MaHDBan=?";
        List<HoaDonBanHang> list = select(sql);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDonBanHang> select(String sql, Object... args) {
        List<HoaDonBanHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonBanHang model = readFromResultSet(rs);
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

    private HoaDonBanHang readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonBanHang model = new HoaDonBanHang();
        model.setMaHDBan(rs.getString("MaHDBan"));
        model.setMaKH(rs.getString("MaKH"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayBan(rs.getDate("NgayBan"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
