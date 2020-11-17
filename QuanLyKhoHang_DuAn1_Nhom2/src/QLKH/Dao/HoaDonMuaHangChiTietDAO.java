/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLKH.Dao;

import QLKH.Helper.DateHelper;
import QLKH.Helper.JdbcHelper;
import QLKH.Model.HoaDonMuaHangChiTiet;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thanh Hau
 */
public class HoaDonMuaHangChiTietDAO {

    public List<HoaDonMuaHangChiTiet> select() {
        String sql = "SELECT * FROM HoaDonMuaChiTiet";
        return select(sql);
    }
       public void Insert(HoaDonMuaHangChiTiet model) {
        String sql = "Insert into HoaDonMuaChiTiet(MaHDMua,MaHH,TenHang,SoLuong,DonVi,NgayMua,DonGia,GhiChu) VALUES (?,?,?,?,?,?,?,?);";
        JdbcHelper.executeUpdate(sql,
                model.getMaHDMua(),
                model.getMaHH(),
                model.getTenHang(),
                model.getSoLuong(),
                model.getDonVi(),
              DateHelper.toString( model.getNgayMua()),
                model.getDonGia(),
                model.getGhiChu()
         );
    }
    public void Update(HoaDonMuaHangChiTiet model) {
        String sql = "UPDATE HoaDonMuaChiTiet SET SoLuong=? WHERE MaHDMua=? and MaHH=?";
        JdbcHelper.executeUpdate(sql,
                model.getSoLuong(),
                model.getMaHH(),
                model.getMaHDMua());
    }
    public HoaDonMuaHangChiTiet findById(String MaHDMua) {
        String sql = "SELECT * FROM HoaDonMuaChiTiet WHERE MaHDMua=?";
        List<HoaDonMuaHangChiTiet> list = select(sql, MaHDMua);
        return list.size() > 0 ? list.get(0) : null;
        
    } public List findById1(String MaHDMua) {
        String sql = "SELECT * FROM HoaDonMuaChiTiet WHERE MaHDMua=?";
        List<HoaDonMuaHangChiTiet> list = select(sql, MaHDMua);
        return list ;
    }

    private List<HoaDonMuaHangChiTiet> select(String sql, Object... args) {
        List<HoaDonMuaHangChiTiet> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HoaDonMuaHangChiTiet model = readFromResultSet(rs);
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

    private HoaDonMuaHangChiTiet readFromResultSet(ResultSet rs) throws SQLException {
        HoaDonMuaHangChiTiet model = new HoaDonMuaHangChiTiet();
        model.setMaHDMua(rs.getString("MaHDMua"));
        model.setMaHH(rs.getString("MaHH"));
        model.setTenHang(rs.getString("TenHang"));
        model.setSoLuong(rs.getInt("SoLuong"));
        model.setDonVi(rs.getString("DonVi"));
        model.setNgayMua(rs.getDate("NgayMua"));
        model.setDonGia(rs.getDouble("DonGia"));
        model.setGhiChu(rs.getString("GhiChu"));
        return model;
    }
}
