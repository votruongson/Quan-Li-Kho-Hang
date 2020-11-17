/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ThongKeDAO {

    public List<Object[]> getKhachHang() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeKhachHang}";
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("MaKhachHang"),
                        rs.getInt("MaHangHoa"),
                        rs.getInt("MaHDBan"),
                        rs.getInt("SoLuongBan"),
                        rs.getFloat("DoanhThu"),
                    };
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

    public List<Object[]> getNhanVien() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeNhanVien}";
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("MaNhanVien"),
                        rs.getInt("MaHangHoa"),
                        rs.getInt("MaHDMua"),
                        rs.getInt("SoLuongMua"),
                        rs.getFloat("DoanhThu")

                    };
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

    public List<Object[]> getHangHoa() {
        List<Object[]> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                String sql = "{call sp_ThongKeDoanhThu }";
                rs = JdbcHelper.executeQuery(sql);
                while (rs.next()) {
                    Object[] model = {
                        rs.getInt("MaHangHoa"),
                        rs.getInt("SoLuongNhap"),
                        rs.getInt("SoLuongNhap"),
                        rs.getInt("SoLuongTonKho"),
                        rs.getFloat("TongTienNhap"),
                        rs.getFloat("TongTienXuat"),
                        rs.getFloat("TongTienChenhLech"),};
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
}
