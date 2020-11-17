package QLKH.Dao;

import QLKH.Model.NhanVien;
import QLKH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NhanVienDAO {

    public void insert(NhanVien model) {
        String sql = "INSERT INTO NhanVien (  HoTen, TaiKhoan, MatKhau, GioiTinh, SoDienThoai, Email,  DiaChi, VaiTro, Hinh) VALUES (?, ?, ?, ?, ?, ?, ?, ?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getHoTen(),
                model.getTaiKhoan(),
                model.getMatKhau(),
                model.isGioiTinh(),
                model.getSoDienThoai(),
                model.getEmail(),
                model.getDiaChi(),
                model.isVaiTro(),
                model.getHinh());
    }

    public void update(NhanVien model) {
        String sql = "UPDATE NhanVien SET HoTen=?,TaiKhoan=?,MatKhau=?,GioiTinh=?,Email=?,DiaChi=?,SoDienThoai=?,VaiTro=?,Hinh=? WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql,
                model.getHoTen(),
                model.getTaiKhoan(),
                model.getMatKhau(),
                model.isGioiTinh(),
                model.getEmail(),
                model.getDiaChi(),
                model.getSoDienThoai(),
                model.isVaiTro(),
                model.getHinh(),
                model.getMaNV());
    }
    public void updateMK(NhanVien model) {
        String sql = "UPDATE NhanVien SET MatKhau=? WHERE TaiKhoan=?";
        JdbcHelper.executeUpdate(sql,
                model.getMatKhau(),
               model.getTaiKhoan());
   }
    public void delete(String MaNV) {
        String sql = "DELETE FROM NhanVien WHERE MaNV=?";
        JdbcHelper.executeUpdate(sql, MaNV);
    }

    public List<NhanVien> select() {
        String sql = "SELECT * FROM NhanVien";
        return select(sql);
    }

    public NhanVien findById(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }
    public NhanVien findById1(String manv) {
        String sql = "SELECT * FROM NhanVien WHERE TaiKhoan=?";
        List<NhanVien> list = select(sql, manv);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhanVien> select(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhanVien model = readFromResultSet(rs);
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

    private NhanVien readFromResultSet(ResultSet rs) throws SQLException {
        NhanVien model = new NhanVien();
        model.setMaNV(rs.getString("MaNV"));
        model.setHoTen(rs.getString("HoTen"));
        model.setTaiKhoan(rs.getString("TaiKhoan"));
        model.setMatKhau(rs.getString("MatKhau"));
        model.setGioiTinh(rs.getBoolean("GioiTinh"));
        model.setSoDienThoai(rs.getString("SoDienThoai"));
        model.setEmail(rs.getString("Email"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setVaiTro(rs.getBoolean("VaiTro"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
}
