package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import QLKH.Model.KhachHang;
import QLKH.Model.NhanVien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KhachHangDAO {

    public List<KhachHang> select() {
        String sql = "SELECT * FROM KhachHang";
        return select(sql);
    }

    public void insert(KhachHang model) {
        String sql = "INSERT INTO KhachHang (TenKH,GioiTinh,Email,DiaChi,SoDienThoai,Hinh) VALUES (?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getTenKH(),
                model.isGioiTinh(),
                model.getEmail(),
                model.getDiaChi(),
                model.getSoDienThoai(),
                model.getHinh());
    }

    public void update(KhachHang model) {
        String sql = "UPDATE KhachHang SET TenKH=?,GioiTinh=?,Email=?,DiaChi=?,SoDienThoai=?,Hinh=? WHERE MaKH=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenKH(),
                model.isGioiTinh(),
                model.getEmail(),
                model.getDiaChi(),
                model.getSoDienThoai(),
                model.getHinh(),
                model.getMaKH());

    }

    public void delete(String MaKH) {
        String sql = "DELETE FROM KhachHang WHERE MaKH=?";
        JdbcHelper.executeUpdate(sql, MaKH);
    }

    public KhachHang findById(String makh) {
        String sql = "SELECT * FROM KhachHang WHERE MaKH=?";
        List<KhachHang> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<KhachHang> select(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    KhachHang model = readFromResultSet(rs);
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

    private KhachHang readFromResultSet(ResultSet rs) throws SQLException {
        KhachHang model = new KhachHang();
        model.setMaKH(rs.getString("MaKH"));
        model.setTenKH(rs.getString("TenKH"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setEmail(rs.getString("Email"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setSoDienThoai(rs.getString("SoDienThoai"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
}
