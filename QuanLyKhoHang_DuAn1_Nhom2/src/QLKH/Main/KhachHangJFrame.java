package QLKH.Main;

import QLKH.Dao.KhachHangDAO;
import QLKH.Helper.DialogHelper;
import QLKH.Helper.ShareHelper;
import QLKH.Model.KhachHang;
import QLKH.Model.NhanVien;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class KhachHangJFrame extends javax.swing.JFrame {

    /**
     * Creates new form KhachHangJFrame
     */
    int index = 0;
    KhachHangDAO KH = new KhachHangDAO();
    boolean t = false;

    public KhachHangJFrame() {
        initComponents();
        setLocationRelativeTo(this);
        this.Load();
        this.setStatus(true);
    }
    JTextField x;

    public KhachHangJFrame(JTextField x) {
        this();
        this.x = x;
        t = true;
    }

    void Load() {
        DefaultTableModel model = (DefaultTableModel) tblGridViewKH.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = KH.select();
            for (KhachHang nv : list) {
                Object[] row = {
                    nv.getMaKH(),
                    nv.getTenKH(),
                    nv.isGioiTinh() ? "Nam" : "Nữ",
                    nv.getEmail(),
                    nv.getSoDienThoai(),
                    nv.getDiaChi(),
                    nv.getHinh(),};
                model.addRow(row);
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModel(KhachHang model) {
        txtMaKH.setText(model.getMaKH());
        txtTenKH.setText(model.getTenKH());
        rdoNam.setSelected(model.isGioiTinh());
        rdoNu.setSelected(!model.isGioiTinh());
        txtEmail.setText(model.getEmail());
        txtDiaChi.setText(model.getDiaChi());
        txtSDT.setText(model.getSoDienThoai() + "");

        if (model.getHinh() != null) {
            lblHinh.setIcon(ShareHelper.readLogo(model.getHinh()));
        }
        tblGridViewKH.setRowSelectionInterval(index, index);
    }

    KhachHang getModel() {
        KhachHang model = new KhachHang();
        model.setMaKH(txtMaKH.getText());
        model.setTenKH(txtTenKH.getText());
        model.setGioiTinh(rdoNam.isSelected());
        model.setEmail(txtEmail.getText());
        model.setDiaChi(txtDiaChi.getText());
        model.setSoDienThoai(txtSDT.getText());
        model.setHinh(lblHinh.getToolTipText());

        return model;
    }

    void setStatus(boolean insertable) {
        txtMaKH.setEditable(insertable);
        btnAdd.setEnabled(true && t);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblGridViewKH.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    void Edit() {
        try {
            String makh = (String) tblGridViewKH.getValueAt(this.index, 0);
            KhachHang model = KH.findById(makh);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            //DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void selectImage() {
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.setDialogTitle("Chọn Hình Khách Hàng");
        if (FileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File file = FileChooser.getSelectedFile();
            if (ShareHelper.saveLogo(file)) {
                // Hiển thị hình lên form
                lblHinh.setIcon(ShareHelper.readLogo(file.getName()));
                lblHinh.setToolTipText(file.getName());
            }

        }
    }

    void Clear() {
        this.setModel(new KhachHang());
        this.setStatus(true);
        lblHinh.setIcon(null);
    }

    void Insert() {
        KhachHang model = getModel();
        try {
            KH.insert(model);
            this.Load();
            this.Clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    void Update() {
        KhachHang model = getModel();
        try {
            KH.update(model);
            this.Load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
            System.out.println(e);
        }
    }

    void Delete() {

        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa Khách Hàng này?")) {
            String manv = txtMaKH.getText();
            try {
                KH.delete(manv);
                this.Load();
                this.Clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }

    public void Find() {

        try {
            String ma = txtTimKiem.getText();
            List<KhachHang> list = KH.select();
            boolean find = false;
            for (KhachHang kh : list) {
                if (ma.equalsIgnoreCase(kh.getMaKH()) || (ma.contains(kh.getTenKH()))) {
                    index = list.indexOf(kh);
                    this.setModel(kh);
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

    public boolean Check() {

        try {
            String ma = txtMaKH.getText();
            KhachHang model;
            model = KH.findById(ma);
            if (txtTenKH.getText().length() == 0) {
                DialogHelper.alert(this, "Tên Khách Hàng KHÔNG được để trống!");
                txtTenKH.requestFocus();
                return false;
            }
            /*else if (!txtTenKH.getText().matches("[a-z A-Z]+")) {
                DialogHelper.alert(this, "Tên Khách Hàng chỉ chứa alphabet và ký tự trắng!");
                txtTenKH.requestFocus();
                return false;
            }*/
            if (!rdoNam.isSelected() && !rdoNu.isSelected()) {
                DialogHelper.alert(this, "Bạn phải chọn Giới Tính! ");
                return false;

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

            if (txtDiaChi.getText().length() == 0) {
                DialogHelper.alert(this, "Địa chỉ KHÔNG được để trống!");
                txtDiaChi.requestFocus();
                return false;

            }
            if (lblHinh.getIcon() == null) {
                DialogHelper.alert(this, "Bạn phải chọn Hình!");
                return false;
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi Định Dạng");
        }
        return true;

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
        jPanel1 = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        lblLogoName = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblMaKH = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        lblTenKH = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        lblGioiTinh = new javax.swing.JLabel();
        rdoNam = new javax.swing.JRadioButton();
        rdoNu = new javax.swing.JRadioButton();
        lblEmail = new javax.swing.JLabel();
        txtEmail = new javax.swing.JTextField();
        lblSDT = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblDiaChi = new javax.swing.JLabel();
        txtDiaChi = new javax.swing.JTextField();
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
        txtTimKiem = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblGridViewKH = new javax.swing.JTable();
        btnXuatExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogo.setBackground(new java.awt.Color(255, 51, 51));
        pnlLogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoName.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoName.setText("Khách Hàng");
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
        pnlLogo.add(lblMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 0, 50, -1));

        jPanel1.add(pnlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 90));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));

        lblMaKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaKH.setText("Mã Khách Hàng:");

        txtMaKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaKH.setEnabled(false);

        lblTenKH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenKH.setText("Tên Khách Hàng:");

        txtTenKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblGioiTinh.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblGioiTinh.setText("Giới Tính:");

        buttonGroup1.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNam.setText("Nam");

        buttonGroup1.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rdoNu.setText("Nữ");

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblEmail.setText("Email:");

        txtEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEmailActionPerformed(evt);
            }
        });

        lblSDT.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSDT.setText("Số Điện Thoại:");

        txtSDT.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblDiaChi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDiaChi.setText("Địa Chỉ:");

        txtDiaChi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblHinh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(255, 51, 0), new java.awt.Color(51, 255, 51), java.awt.Color.yellow, new java.awt.Color(153, 0, 153)));
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
        lblTimKiem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblTimKiemMouseClicked(evt);
            }
        });

        txtTimKiem.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        txtTimKiem.setBorder(null);
        txtTimKiem.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtTimKiemKeyPressed(evt);
            }
        });

        tblGridViewKH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblGridViewKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Khách Hàng", "Tên Khách Hàng", "Giới Tính", "Email", "Số Điện Thoại", "Địa Chỉ", "Hinh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridViewKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewKHMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblGridViewKH);

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
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTenKH)
                            .addComponent(lblGioiTinh)
                            .addComponent(lblSDT)
                            .addComponent(lblDiaChi)
                            .addComponent(lblMaKH)
                            .addComponent(lblEmail))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(rdoNam)
                                .addGap(18, 18, 18)
                                .addComponent(rdoNu))
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtTenKH)
                                .addComponent(txtMaKH)
                                .addComponent(txtEmail)
                                .addComponent(txtSDT)
                                .addComponent(txtDiaChi, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnXuatExcel)))
                .addContainerGap())
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlContentLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                            .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jSeparator1)
                                .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(0, 0, Short.MAX_VALUE))
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
                            .addComponent(btnLast)))
                    .addContainerGap()))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaKH)
                            .addComponent(txtMaKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenKH)
                            .addComponent(txtTenKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(21, 21, 21)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rdoNam)
                            .addComponent(rdoNu)
                            .addComponent(lblGioiTinh))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblEmail)
                            .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSDT)
                            .addComponent(txtSDT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDiaChi)
                            .addComponent(txtDiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
                .addComponent(btnXuatExcel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlContentLayout.createSequentialGroup()
                    .addGap(309, 309, 309)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(lblTimKiem, javax.swing.GroupLayout.Alignment.TRAILING)))
                        .addGroup(pnlContentLayout.createSequentialGroup()
                            .addGap(2, 2, 2)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnPrev, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnLast, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(310, 310, 310)))
        );

        jPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 840, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void txtEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEmailActionPerformed

    private void lblTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTimKiemMouseClicked
        // TODO add your handling code here:
        this.Load();
    }//GEN-LAST:event_lblTimKiemMouseClicked

    private void lblMiniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMiniMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if (Check() == true) {
            this.Insert();
        } else {

        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if (Check() == true) {
            this.Update();
        } else {

        }

    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        this.Clear();
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        this.dispose();
        String chuoi = txtMaKH.getText();
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
        this.index = tblGridViewKH.getRowCount() - 1;
        this.Edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void tblGridViewKHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewKHMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.index = tblGridViewKH.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.Edit();

            }

        }

    }//GEN-LAST:event_tblGridViewKHMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        this.Load();
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Khách Hàng");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("DANH SÁCH KHÁCH HÀNG");
            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã Khách Hàng");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Họ và tên");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Giới tính");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Email");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Địa chỉ");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("SỐ điện thoại");
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Hình");
            KhachHangDAO kh = new KhachHangDAO();
            List<KhachHang> listItem = kh.select();
            for (int i = 0; i < listItem.size(); i++) {
                KhachHang nv = new KhachHang();
                nv = listItem.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(nv.getMaKH());
                row.createCell(2).setCellValue(nv.getTenKH());
                row.createCell(3).setCellValue(nv.isGioiTinh() ? "Nam" : "Nữ");
                row.createCell(4).setCellValue(nv.getEmail());
                row.createCell(5).setCellValue(nv.getDiaChi());
                row.createCell(6).setCellValue(nv.getSoDienThoai());
                row.createCell(7).setCellValue(nv.getHinh());
            }
            FileOutputStream out = new FileOutputStream(new File("D:/khachhang.xlsx"));
            workbook.write(out);
            DialogHelper.alert(this, "Xuất Thành Công");
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnXuatExcelActionPerformed

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
            java.util.logging.Logger.getLogger(KhachHangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(KhachHangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(KhachHangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(KhachHangJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new KhachHangJFrame().setVisible(true);
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblGioiTinh;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblMaKH;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private javax.swing.JTable tblGridViewKH;
    private javax.swing.JTextField txtDiaChi;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
