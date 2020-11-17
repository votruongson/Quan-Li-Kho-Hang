
package QLKH.Dao;

import QLKH.Helper.JdbcHelper;
import QLKH.Model.NhaCungCap;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class NhaCungCapDAO {

    public List<NhaCungCap> select() {
        String sql = "SELECT * FROM NhaCungCap";
        return select(sql);
    }

    public void insert(NhaCungCap model) {
        String sql = "INSERT INTO NhaCungCap (tenCongTy,diaChi,email,soDienThoai,hinh) VALUES (?,?,?,?,?)";
        JdbcHelper.executeUpdate(sql,
                model.getTenCongTy(),
                model.getDiaChi(),
                model.getEmail(),
                model.getSoDienThoai(),
                model.getHinh());

    }

    public void update(NhaCungCap model) {
        String sql = "UPDATE NhaCungCap SET tenCongTy=?,diaChi=?,email=?,soDienThoai=?,hinh=? WHERE MaNCC=?";
        JdbcHelper.executeUpdate(sql,
                model.getTenCongTy(),
                model.getDiaChi(),
                model.getEmail(),
                model.getSoDienThoai(),
                model.getHinh(),
                model.getMaNCC());

    }
    
      public void delete(String MaNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE MaNCC=?";
        JdbcHelper.executeUpdate(sql, MaNCC);
    }

    public NhaCungCap findById(String makh) {
        String sql = "SELECT * FROM NhaCungCap WHERE MaNCC=?";
        List<NhaCungCap> list = select(sql, makh);
        return list.size() > 0 ? list.get(0) : null;
    }

    private List<NhaCungCap> select(String sql, Object... args) {
        List<NhaCungCap> list = new ArrayList<>();
        try {
            ResultSet rs = null;
            try {
                rs = JdbcHelper.executeQuery(sql, args);
                while (rs.next()) {
                    NhaCungCap model = readFromResultSet(rs);
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

    private NhaCungCap readFromResultSet(ResultSet rs) throws SQLException {
        NhaCungCap model = new NhaCungCap();
        model.setMaNCC(rs.getString("MaNCC"));
        model.setTenCongTy(rs.getString("TenCongTy"));
        model.setDiaChi(rs.getString("DiaChi"));
        model.setEmail(rs.getString("Email"));
        model.setSoDienThoai(rs.getInt("SoDienThoai"));
        model.setHinh(rs.getString("Hinh"));
        return model;
    }
}
