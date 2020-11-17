/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLKH.Dao;

import QLKH.Helper.DateHelper;
import QLKH.Helper.JdbcHelper;
import QLKH.Model.HoaDonBanHang;
import QLKH.Model.HoaDonMuaHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kiss
 */
public class HoaDonMuaHangDAO {
     public List<HoaDonMuaHang> select() {
        String sql = "SELECT * FROM HoaDonMuaHang";
        return select(sql);
    }

    public void Insert(HoaDonMuaHang model) {
        String sql = "INSERT INTO HoaDonMuaHang (MaNCC,MaNV,NgayMua,TrangThai,GhiChu) VALUES (?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaNCC(),
                model.getMaNV(),
                DateHelper.toString(model.getNgayMua(), "yyyy-MM-dd"),
                model.isTrangThai(),
                model.getGhiChu());

    }

    public void Update(HoaDonMuaHang model) {
        String sql = "UPDATE HoaDonMuaHang SET MaNCC=?,MaNV=?,NgayMua=?,TrangThai=?,GhiChu=? WHERE MaHDMua=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaNCC(),
                model.getMaNV(),
                DateHelper.toString(model.getNgayMua(), "yyyy-MM-dd"),
                model.isTrangThai(),
                model.getGhiChu(),
                model.getMaHDMua());

    }

    public void delete(String MaHDMua) {
        String sql = "DELETE FROM HoaDonMuaHang WHERE MaHDMua=?";
        JdbcHelper.executeUpdate(sql, MaHDMua);
    }

    public HoaDonMuaHang findById(String MaHDMua) {
        String sql = "SELECT * FROM HoaDonMuaHang WHERE MaHDMua=?";
        List<HoaDonMuaHang> list = select(sql, MaHDMua);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HoaDonMuaHang> select(String sql, Object... args) {
        List<HoaDonMuaHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonMuaHang model = readFromResultSet(rs);
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

    private HoaDonMuaHang readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonMuaHang model = new HoaDonMuaHang();
        model.setMaHDMua(rs.getString("MaHDMua"));
        model.setMaNCC(rs.getString("MaNCC"));
        model.setMaNV(rs.getString("MaNV"));
        model.setNgayMua(rs.getDate("NgayMua"));
        model.setTrangThai(rs.getBoolean("TrangThai"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}

