package QLKH.Dao;

import QLKH.Helper.DateHelper;
import QLKH.Helper.JdbcHelper;
import QLKH.Model.HoaDonBanHang;
import QLKH.Model.HoaDonBanHangChiTiet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanh Hau
 */
public class HoaDonBanHangChiTietDAO {

    public List<HoaDonBanHangChiTiet> select() {
        String sql = "SELECT * FROM HoaDonBanChiTiet";
        return select(sql);
    }
    public void Update(HoaDonBanHangChiTiet model) {
        String sql = "UPDATE HoaDonBanChiTiet SET SoLuong=? WHERE MaHDBan=? and MaHH=?";
        JdbcHelper.executeUpdate(sql,
                model.getSoLuong(),
                model.getMaHH(),
                model.getMaHDBan());
    }
    public HoaDonBanHangChiTiet findById(String MaHDBan) {
        String sql = "SELECT * FROM HoaDonBanChiTiet WHERE MaHDBan=?";
        List<HoaDonBanHangChiTiet> list = select(sql, MaHDBan);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDonBanHangChiTiet> select(String sql, Object... args) {
        List<HoaDonBanHangChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonBanHangChiTiet model = readFromResultSet(rs);
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
public void Insert(HoaDonBanHangChiTiet model) {
        String sql = "Insert into HoaDonBanChiTiet(MaHDBan,MaHH,TenHang,SoLuong,DonVi,NgayBan,DonGia,GhiChu) VALUES (?,?,?,?,?,?,?,?);";
        JdbcHelper.executeUpdate(sql,
                model.getMaHDBan(),
                model.getMaHH(),
                model.getTenHang(),
                model.getSoLuong(),
                model.getDonVi(),
              DateHelper.toString( model.getNgayBan()),
                model.getDonGia(),
                model.getGhiChu()
         );
    }
    private HoaDonBanHangChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonBanHangChiTiet model = new HoaDonBanHangChiTiet();
        model.setMaHDBan(rs.getString("MaHDBan"));
        model.setMaHH(rs.getString("MaHH"));
        model.setTenHang(rs.getString("TenHang"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDonVi(rs.getString("DonVi"));
        model.setNgayBan(rs.getDate("NgayBan"));
        model.setDonGia(rs.getDouble("DonGia"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
