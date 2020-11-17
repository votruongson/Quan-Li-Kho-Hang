package QLKH.Main;

import QLKH.Dao.NhanVienDAO;
import QLKH.Helper.DialogHelper;
import QLKH.Helper.ShareHelper;
import QLKH.Model.NhanVien;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class NhanVienJFrame extends javax.swing.JFrame {

    /**
     * Creates new form NhanVienJFrame
     */
    int index = 0;
    NhanVienDAO NV = new NhanVienDAO();
    JTextField x;
    ArrayList<NhanVien> List = new ArrayList<>();
boolean t= false;
    public NhanVienJFrame() {
        initComponents();
        setLocationRelativeTo(this);

    }

    public NhanVienJFrame(JTextField x) {
        this();
        this.x = x;
        t=true;
    }

    void Load() {
        DefaultTableModel model = (DefaultTableModel) tblGridViewNV.getModel();
        model.setRowCount(0);
        try {
            List<NhanVien> list = NV.select();
            for (NhanVien nv : list) {
                Object[] row = {
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getTaiKhoan(),
                    nv.getMatKhau(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.getSoDienThoai(),
                    nv.getEmail(),
                    nv.getDiaChi(),
                    nv.isVaiTro() ? "Thủ Kho" : "Nhân viên",
                    nv.getHinh(),};
                model.addRow(row);
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//            System.out.println(e);
        }
    }

    void Insert() {
        NhanVien model = getModel();
        String confirm = new String(txtXacNhanMK.getText());
        if (confirm.equals(model.getMatKhau())) {
            try {
                NV.insert(model);
                this.Load();
                this.Clear();
                DialogHelper.alert(this, "Thêm mới thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Thêm mới thất bại!");
                System.out.println(e);
            }
        } else {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        }
    }

    void Update() {
        NhanVien model = getModel();
        String confirm = new String(txtXacNhanMK.getText());
        if (!confirm.equals(model.getMatKhau())) {
            DialogHelper.alert(this, "Xác nhận mật khẩu không đúng!");
        } else {
            try {
                NV.update(model);
                this.Load();
                DialogHelper.alert(this, "Cập nhật thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Cập nhật thất bại!");
                System.out.println(e);
            }
        }
    }

    void Delete() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa nhân viên này?")) {
            String MaNV = txtMaNV.getText();

            try {
                NV.delete(MaNV);
                this.Load();
                this.Clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
         
            }
        }
    }

    void Clear() {
        this.setModel(new NhanVien());
        this.setStatus(true);
        lblHinh.setIcon(null);
    }

    void setModel(NhanVien model) {
        txtMaNV.setText(model.getMaNV());
        txtTenNV.setText(model.getHoTen());
        txtTaiKhoan.setText(model.getTaiKhoan());
        txtMatKhau.setText(model.getMatKhau());
        txtXacNhanMK.setText(model.getMatKhau());
        cboGioiTinh.setSelectedIndex(model.isGioiTinh() ? 0 : 1);
        txtSDT.setText(model.getSoDienThoai() + "");
        txtEmail.setText(model.getEmail());
        txtDiaChi.setText(model.getDiaChi());
        rdoThuKho.setSelected(model.isVaiTro());
        rdoNhanVien.setSelected(!model.isVaiTro());
//        lblHinh.setIcon(null);
        if (model.getHinh() != null) {
            lblHinh.setIcon(ShareHelper.readLogo(model.getHinh()));
        }
        tblGridViewNV.setRowSelectionInterval(index, index);

    }

    NhanVien getModel() {
        NhanVien model = new NhanVien();
        model.setMaNV(txtMaNV.getText());
        model.setHoTen(txtTenNV.getText());
        model.setTaiKhoan(txtTaiKhoan.getText());
        model.setMatKhau(new String(txtMatKhau.getText()));
        model.setGioiTinh(cboGioiTinh.getSelectedIndex() == 0);
        model.setSoDienThoai(txtSDT.getText());
        model.setEmail(txtEmail.getText());
        model.setDiaChi(txtDiaChi.getText());
        model.setVaiTro(rdoThuKho.isSelected());
        model.setHinh(lblHinh.getToolTipText());
               
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaNV.setEditable(insertable);
        btnSave.setEnabled(true  && ShareHelper.USER.isVaiTro());
        btnAdd.setEnabled(true&&t);
        btnUpdate.setEnabled(!insertable && ShareHelper.USER.isVaiTro());
        btnDelete.setEnabled(!insertable && ShareHelper.USER.isVaiTro());
        boolean first = this.index > 0;
        boolean last = this.index < tblGridViewNV.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    void Edit() {
        try {
            String manv = (String) tblGridViewNV.getValueAt(this.index, 0);
            NhanVien model = NV.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void selectImage() {
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.setDialogTitle("Chọn Hình Nhân Viên");
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            if (ShareHelper.saveLogo(file)) {
                // Hiển thị hình lên form
                lblHinh.setIcon(ShareHelper.readLogo(file.getName()));
                lblHinh.setToolTipText(file.getName());

            }

        }
    }

    public boolean Check() {

        try {
            String ma = txtMaNV.getText();
            NhanVien model;
            model = NV.findById(ma);
            if (txtTenNV.getText().length() == 0) {
                DialogHelper.alert(this, "Tên Nhân Viên KHÔNG được để trống!");
                txtTenNV.requestFocus();
                return false;
            } else if (txtTenNV.getText().matches("[0-9]+")) {
                DialogHelper.alert(this, "Tên Nhân Viên chỉ chứa alphabet và ký tự trắng!");
                txtTenNV.requestFocus();
                return false;
            }
            if (txtTaiKhoan.getText().length() == 0) {
                DialogHelper.alert(this, "Tên tài khoản KHÔNG được để trống!");
                txtTaiKhoan.requestFocus();
                return false;
            } else if (!txtTaiKhoan.getText().matches("[a-z A-Z]+")) {
                DialogHelper.alert(this, "Tên tài khoản gồm tên và viết tắt của họ và tên đệm!");
                txtTaiKhoan.requestFocus();
                return false;
            }
            if (txtMatKhau.getText().length() == 0) {
                DialogHelper.alert(this, "Mật khẩu KHÔNG được để trống!");
                txtMatKhau.requestFocus();
                return false;
            } else if (txtMatKhau.getText().length()<4) {
                DialogHelper.alert(this, "Mật khẩu phải nhập ít nhất 4 ký tự!");
                txtMatKhau.requestFocus();
                return false;
            }
            if (txtXacNhanMK.getText().length() == 0) {
                DialogHelper.alert(this, "Mật khẩu xác nhận KHÔNG được để trống!");
                txtXacNhanMK.requestFocus();
                return false;
            } else if (!txtXacNhanMK.getText().equalsIgnoreCase(txtMatKhau.getText())) {
                DialogHelper.alert(this, "Mật khẩu KHÔNG Giống Nhau!");
                txtXacNhanMK.requestFocus();
                return false;
            }

            if (txtSDT.getText().length() == 0) {
                DialogHelper.alert(this, "Chưa nhập số điện thoại! ");
                txtSDT.requestFocus();
                return false;
            } else if (!txtSDT.getText().matches("0[0-9]+")) {
                DialogHelper.alert(this, "Bạn đã nhập KHÔNG đúng số điện thoại!");
                txtSDT.requestFocus();
                return false;
            } else if (txtSDT.getText().length() < 10) {
                DialogHelper.alert(this, "Số điện thoại phải nhập đủ 10 số!");
                txtSDT.requestFocus();
                return false;

            } else {
                txtSDT.setBackground(Color.white);

            }
            String email = "\\w+@\\w+(\\.\\w+){1,2}";
            if (txtEmail.getText().length() == 0) {
                DialogHelper.alert(this, "Chưa nhập Email! ");
                txtEmail.requestFocus();
                return false;
            }
            if (!txtEmail.getText().matches(email)) {
                DialogHelper.alert(this, "Bạn đã nhập KHÔNG đúng dạng Email!");
                txtEmail.requestFocus();
                return false;

            } else {
                txtEmail.setBackground(Color.white);

            }
            if (txtDiaChi.getText().length() == 0) {
                DialogHelper.alert(this, "Địa chỉ KHÔNG được để trống!");
                txtDiaChi.requestFocus();
                return false;

            }
            if (!rdoThuKho.isSelected() && !rdoNhanVien.isSelected()) {
                DialogHelper.alert(this, "Bạn phải chọn vai trò! ");
                return false;

            }
            if (lblHinh.getIcon() == null) {
                DialogHelper.alert(this, "Bạn phải chọn Hình!");
                return false;
            }

        } catch (Exception e) {
            DialogHelper.alert(this,"Lỗi Định Dạng");
        }
        return true;

    }

    public void Find() {

        try {
            String ma = txtTimKiem.getText();
            List<NhanVien> list = NV.select();
            boolean find = false;
            for (NhanVien nv : list) {
                if (ma.equalsIgnoreCase(nv.getMaNV()) || (ma.contains(nv.getHoTen()))) {
                    index = list.indexOf(nv);
                    this.setModel(nv);
                    find = true;
                    break;
                }
            }

            if (find) {
                DialogHelper.alert(this, "Đã Tìm Thấy " + ma);

            } else {
                DialogHelper.alert(this, "Không tìm Thấy  " + ma);

            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi ");

        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        lblLogoName = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblMaNV = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        lblTenNV = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        cboGioiTinh = new javax.swing.JComboBox<>();
        lblVaiTro = new javax.swing.JLabel();
        rdoThuKho = new javax.swing.JRadioButton();
        rdoNhanVien = new javax.swing.JRadioButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblDiaChi = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblMatKhau = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JTextField();
        lblXacNhanMK = new javax.swing.JLabel();
        txtXacNhanMK = new javax.swing.JTextField();
        lblHinh = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        btnFirst = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblTimKiem = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        txtTimKiem = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridViewNV = new javax.swing.JTable();
        lblTaiKhoan = new javax.swing.JLabel();
        txtTaiKhoan = new javax.swing.JTextField();
        btnXuatExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogo.setBackground(new java.awt.Color(255, 51, 51));
        pnlLogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoName.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoName.setText("Nhân Viên");
        pnlLogo.add(lblLogoName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 60));

        lblExit.setBackground(new java.awt.Color(255, 255, 255));
        lblExit.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_cancel_48px.png"))); // NOI18N
        lblExit.setToolTipText("Close");
        lblExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblExitMouseClicked(evt);
            }
        });
        pnlLogo.add(lblExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(792, 0, -1, -1));

        lblMini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_minus_48px.png"))); // NOI18N
        lblMini.setToolTipText("Miniform");
        lblMini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMiniMouseClicked(evt);
            }
        });
        pnlLogo.add(lblMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, -1, -1));

        jPanel2.add(pnlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 90));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));

        lblMaNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaNV.setText("Mã Nhân Viên:");

        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtMaNV.setEnabled(false);

        lblTenNV.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenNV.setText("Tên Nhân Viên:");

        txtTenNV.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGioiTinh.setText("Giới Tính:");

        cboGioiTinh.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        cboGioiTinh.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Nam", "Nữ", "", "" }));
        cboGioiTinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboGioiTinhActionPerformed(evt);
            }
        });

        lblVaiTro.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblVaiTro.setText("Vai Trò:");

        rdoThuKho.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoThuKho);
        rdoThuKho.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoThuKho.setText("Thủ Kho");

        rdoNhanVien.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(rdoNhanVien);
        rdoNhanVien.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNhanVien.setText("Nhân Viên");
        rdoNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdoNhanVienActionPerformed(evt);
            }
        });

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiaChi.setText("Địa Chỉ:");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSDT.setText("Số Điện Thoại:");

        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblMatKhau.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMatKhau.setText("Mật Khẩu:");

        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblXacNhanMK.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblXacNhanMK.setText("Xác Nhận Mật Khẩu:");

        txtXacNhanMK.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N

        lblHinh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 0, 0), java.awt.Color.pink, new java.awt.Color(255, 255, 0), new java.awt.Color(102, 255, 51)));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        btnSave.setBackground(new java.awt.Color(51, 255, 255));
        btnSave.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_save.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnUpdate.setBackground(new java.awt.Color(51, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_update_32px.png"))); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });

        btnDelete.setBackground(new java.awt.Color(51, 255, 255));
        btnDelete.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_delete_bin_32px.png"))); // NOI18N
        btnDelete.setText("Clear");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setBackground(new java.awt.Color(51, 255, 255));
        btnAdd.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnFirst.setBackground(new java.awt.Color(51, 255, 255));
        btnFirst.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_skip_to_start.png"))); // NOI18N
        btnFirst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFirstActionPerformed(evt);
            }
        });

        btnPrev.setBackground(new java.awt.Color(51, 255, 255));
        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_rewind.png"))); // NOI18N
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(51, 255, 255));
        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_fast_forward.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnLast.setBackground(new java.awt.Color(51, 255, 255));
        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons_end.png"))); // NOI18N
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });

        lblTimKiem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_search_32px.png"))); // NOI18N
        lblTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lblTimKiemKeyPressed(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        tblGridViewNV.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblGridViewNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Nhân Viên", "Tên Nhân Viên", "Tài Khoản", "Mật Khẩu", "Giới Tính", "Số Điện Thoại", "Email", "Địa Chỉ", "Vai Trò", "Hinh"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridViewNV.setGridColor(new java.awt.Color(255, 255, 255));
        tblGridViewNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewNVMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridViewNV);

        lblTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTaiKhoan.setText("Tài Khoản:");

        txtTaiKhoan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtTaiKhoan.setToolTipText("Tên tài khoản gồm tên và viết tắt của họ và tên đệm");

        btnXuatExcel.setBackground(new java.awt.Color(153, 255, 153));
        btnXuatExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_microsoft_excel_2019_24px.png"))); // NOI18N
        btnXuatExcel.setText("Excel");
        btnXuatExcel.setToolTipText("Xuất sang Excel");
        btnXuatExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnXuatExcel))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnFirst)
                                .addGap(18, 18, 18)
                                .addComponent(btnPrev)
                                .addGap(18, 18, 18)
                                .addComponent(btnNext)
                                .addGap(18, 18, 18)
                                .addComponent(btnLast)))))
                .addContainerGap())
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblTaiKhoan)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addComponent(lblMatKhau)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addComponent(lblXacNhanMK)
                                .addGap(18, 18, 18)
                                .addComponent(txtXacNhanMK))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblGioiTinh)
                                .addGap(83, 83, 83)
                                .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(325, 325, 325))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSDT)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblVaiTro, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(51, 51, 51)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addComponent(rdoThuKho)
                                        .addGap(42, 42, 42)
                                        .addComponent(rdoNhanVien)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtEmail)
                                            .addComponent(txtSDT))
                                        .addGap(1, 1, 1))
                                    .addComponent(txtDiaChi))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDiaChi, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlContentLayout.createSequentialGroup()
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblMaNV)
                                    .addComponent(lblTenNV))
                                .addGap(47, 47, 47)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(17, 17, 17))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaNV)
                            .addComponent(txtMaNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenNV)
                            .addComponent(txtTenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTaiKhoan)
                            .addComponent(txtTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMatKhau)
                            .addComponent(txtMatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblXacNhanMK)
                            .addComponent(txtXacNhanMK, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGioiTinh)
                            .addComponent(cboGioiTinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSDT)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDiaChi)
                    .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVaiTro)
                    .addComponent(rdoThuKho)
                    .addComponent(rdoNhanVien))
                .addGap(25, 25, 25)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(btnXuatExcel)
                        .addGap(179, 179, 179))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTimKiem, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(9, 9, 9)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        jPanel2.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 88, 840, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:

        this.dispose();

    }//GEN-LAST:event_lblExitMouseClicked

    private void cboGioiTinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboGioiTinhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboGioiTinhActionPerformed

    private void rdoNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdoNhanVienActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdoNhanVienActionPerformed

    private void lblMiniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMiniMouseClicked

    private void tblGridViewNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewNVMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.index = tblGridViewNV.getSelectedRow();
            if (this.index >= 0) {

                this.Edit();

            }
        }

       
    }//GEN-LAST:event_tblGridViewNVMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(Check()==true){
            this.Insert();
        }else{
           
        }
        

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(Check()==true){
                  this.Update();
        }else{
            
        }
  

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.Clear();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        
            this.dispose();
            String chuoi = txtMaNV.getText();
            x.setText(chuoi);   
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnFirstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFirstActionPerformed
        // TODO add your handling code here:
        this.index = 0;
        this.Edit();

    }//GEN-LAST:event_btnFirstActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        // TODO add your handling code here:
        this.index--;
        this.Edit();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:

        this.index++;
        this.Edit();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        // TODO add your handling code here:
        this.index = tblGridViewNV.getRowCount() - 1;
        this.Edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.Load();
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Nhân Viên");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH Nhân Viên");
            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã Nhân Viên");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Họ và tên");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tài Khoản");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Mật Khẩu");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Giới tính");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Email");
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Địa chỉ");
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("SỐ điện thoại");
            cell = row.createCell(9, CellType.STRING);
            cell.setCellValue("Hình");
            cell = row.createCell(10, CellType.STRING);
            cell.setCellValue("Vai Trò");

            NhanVienDAO hocVienService = new NhanVienDAO();

            List<NhanVien> listItem = hocVienService.select();

            for (int i = 0; i < listItem.size(); i++) {
                NhanVien nv = new NhanVien();
                nv = listItem.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(nv.getMaNV());
                row.createCell(2).setCellValue(nv.getHoTen());
                row.createCell(3).setCellValue(nv.getTaiKhoan());
                row.createCell(4).setCellValue(nv.getMatKhau());
                row.createCell(5).setCellValue(nv.isGioiTinh() ? "Nam" : "Nữ");
                row.createCell(6).setCellValue(nv.getEmail());
                row.createCell(7).setCellValue(nv.getDiaChi());
                row.createCell(8).setCellValue(nv.getSoDienThoai());
                row.createCell(9).setCellValue(nv.getHinh());
                row.createCell(10).setCellValue(nv.isVaiTro() ? "Thủ Kho" : "Nhân Viên");

            }

            FileOutputStream out = new FileOutputStream(new File("D:/Nhanvien.xlsx"));
            workbook.write(out);
            DialogHelper.alert(this, "Xuất Thành Công");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_btnXuatExcelActionPerformed

    private void lblTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lblTimKiemKeyPressed
        // TODO add your handling code here:
      
    }//GEN-LAST:event_lblTimKiemKeyPressed

    private void txtTimKiemKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtTimKiemKeyPressed
        // TODO add your handling code here:
          if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Find();
        }
    }//GEN-LAST:event_txtTimKiemKeyPressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NhanVienJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NhanVienJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JComboBox<String> cboGioiTinh;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblMaNV;
    private javax.swing.JLabel lblMatKhau;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTaiKhoan;
    private javax.swing.JLabel lblTenNV;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JLabel lblVaiTro;
    private javax.swing.JLabel lblXacNhanMK;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JRadioButton rdoNhanVien;
    private javax.swing.JRadioButton rdoThuKho;
    private javax.swing.JTable tblGridViewNV;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JTextField txtMatKhau;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTaiKhoan;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTimKiem;
    private javax.swing.JTextField txtXacNhanMK;
    // End of variables declaration//GEN-END:variables
}
