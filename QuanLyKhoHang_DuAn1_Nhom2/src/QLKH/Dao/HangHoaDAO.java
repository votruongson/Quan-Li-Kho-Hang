package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import QLKH.Model.HangHoa;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HangHoaDAO {

    public List<HangHoa> select() {
        String sql = "SELECT * FROM HangHoa ";
        return select(sql);
    }
    public List<HangHoa> select1() {
        String sql = "SELECT DISTINCT  FROM HangHoa ";
        return select(sql);
    }

    public void insert(HangHoa model) {
        String sql = "INSERT INTO HangHoa (MaLoai,TenHang,DonGia,DonVi,GhiChu,Hinh) VALUES (?,?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getMaLoai(),
                model.getTenHH(),
                model.getDonGia(),
                model.getDonVi(),
                 model.getGhiChu(),
                model.getHinh()
               );
    }

    public void update(HangHoa model) {
        String sql = "UPDATE HangHoa SET MaLoai=?,TenHang=?,DonGia=?,DonVi=?,GhiChu=?,Hinh=? WHERE MaHH=?";
        JdbcHelper.executeUpdate(sql,
                model.getMaLoai(),
                model.getTenHH(),
                model.getDonGia(),
                model.getDonVi(),
                model.getGhiChu(),
                model.getHinh(),
                model.getMaHH());

    }

    public void delete(String MaHH) {
        String sql = "DELETE FROM HangHoa WHERE MaHH=?";
        JdbcHelper.executeUpdate(sql, MaHH);
    }

    public HangHoa findById(String maHH) {
        String sql = "SELECT * FROM HangHoa WHERE MaHH=?";
        List<HangHoa> list = select(sql, maHH);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<HangHoa> select(String sql, Object... args) {
        List<HangHoa> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    HangHoa model = readFromResultSet(rs);
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

    private HangHoa readFromResultSet(ResultSet rs) throws SQLException {
        HangHoa model = new HangHoa();
        model.setMaHH(rs.getString("MaHH"));
        model.setMaLoai(rs.getString("MaLoai"));
        model.setTenHH(rs.getString("TenHang"));
        model.setDonGia(rs.getString("DonGia"));
        model.setDonVi(rs.getString("DonVi"));
        model.setGhiChu(rs.getString("GhiChu"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
}
