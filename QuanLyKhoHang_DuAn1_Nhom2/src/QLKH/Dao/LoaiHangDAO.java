package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import QLKH.Model.HangHoa;
import QLKH.Model.LoaiHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoaiHangDAO {

    public void insert(LoaiHang model) {
        String sql = "INSERT INTO LoaiHang (TenLoai) VALUES (?)";
        JdbcHelper.executeUpdate(sql,
                model.getTenLoai());

    }

    public void delete(String MaLoai) {
        String sql = "DELETE FROM LoaiHang WHERE MaLoai=?";
        JdbcHelper.executeUpdate(sql, MaLoai);
    }

    public List<LoaiHang> select() {
        String sql = "SELECT * FROM LoaiHang";
        return select(sql);
    }

    public LoaiHang findById(String maLoai) {
        String sql = "SELECT * FROM LoaiHang WHERE MaLoai=?";
        List<LoaiHang> list = select(sql, maLoai);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<LoaiHang> select(String sql, Object... args) {
        List<LoaiHang> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    LoaiHang model = readFromResultSet(rs);
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

    private LoaiHang readFromResultSet(ResultSet rs) throws SQLException {
        LoaiHang model = new LoaiHang();
        model.setMaLoai(rs.getString("MaLoai"));
        model.setTenLoai(rs.getString("TenLoai"));
        return model;
    }
}
