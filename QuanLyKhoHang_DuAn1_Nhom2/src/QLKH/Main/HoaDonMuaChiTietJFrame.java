package QLKH.Main;

import QLKH.Dao.HoaDonMuaHangChiTietDAO;
import QLKH.Helper.DateHelper;
import QLKH.Helper.DialogHelper;
import QLKH.Model.HoaDonMuaHang;
import QLKH.Model.HoaDonMuaHangChiTiet;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import QLKH.Dao.HoaDonMuaHangDAO;
import QLKH.Model.HangHoa;
import QLKH.Dao.HangHoaDAO;
import QLKH.Model.HoaDonBanHang;
import QLKH.Model.HoaDonBanHangChiTiet;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class HoaDonMuaChiTietJFrame extends javax.swing.JFrame {

    /**
     * Creates new form HoaDonBanHangChiTietJFrame
     */
    int index = 0;
    HoaDonMuaHangChiTietDAO dao = new HoaDonMuaHangChiTietDAO();
    HoaDonMuaHangDAO daoHD = new HoaDonMuaHangDAO();
    HangHoaDAO daoHH = new HangHoaDAO();

    public HoaDonMuaChiTietJFrame() {
        initComponents();
        setLocationRelativeTo(this);
    }
    JTextField x;

    public HoaDonMuaChiTietJFrame(String y, Date z) {
        this();
        this.txtMaHDMua.setText(y);
        this.DateNgayMua.setDate(z);
    }

    void Clear() {
        this.setModel(new HoaDonMuaHangChiTiet());
        this.setStatus(true);
    }

    void setModelHH(HangHoa model) {
        // txtMaHDBan.setText(model.getMaHDBan());
        //  txtMaHH.setText(model.getMaHH());
        txtTenHang.setText(model.getTenHH());
        //  txtSoLuong.setText(model.getSoLuong() + "");
        //  cbDonVi.setSelectedItem(model.getDonVi());
        // DateNgayBan.setDate(model.getNgayBan());
        // txtDonGia.setText(model.getDonGia() + "");
        // txtGhiChu.setText(model.getGhiChu());
    }

    void EditHH() {
        try {
            String maHH = (String) txtMaHH.getText();
            HangHoa model = daoHH.findById(maHH);
            if (model != null) {

                this.setModelHH(model);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void setModelMHD(HoaDonMuaHang model) {
        DateNgayMua.setDate(model.getNgayMua());
    }

    void EditMHD() {
        try {
            String maMHD = txtMaHDMua.getText();
            HoaDonMuaHang model = daoHD.findById(maMHD);
            if (model != null) {
                this.setModelMHD(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    void Load() {
        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        model.setRowCount(0);
        try {
            List<HoaDonMuaHangChiTiet> list = dao.select();
            for (HoaDonMuaHangChiTiet hd : list) {

                Object[] row = {
                    hd.getMaHDMua(),
                    hd.getMaHH(),
                    hd.getTenHang(),
                    hd.getSoLuong(),
                    hd.getDonVi(),
                    DateHelper.toString(hd.getNgayMua()),
                    hd.getDonGia(),
                    hd.getGhiChu(),};

                model.addRow(row);
            }

        } catch (Exception e) {
            //DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void Load1() {

        DefaultTableModel model = (DefaultTableModel) tblGridView.getModel();
        model.setRowCount(0);
        try {
            String maHDMua = txtMaHDMua.getText();
            List<HoaDonMuaHangChiTiet> list = dao.findById1(maHDMua);
            for (HoaDonMuaHangChiTiet hd : list) {

                Object[] row = {
                    hd.getMaHDMua(),
                    hd.getMaHH(),
                    hd.getTenHang(),
                    hd.getSoLuong(),
                    hd.getDonVi(),
                    DateHelper.toString(hd.getNgayMua()),
                    hd.getDonGia(),
                    hd.getGhiChu(),};

                model.addRow(row);
            }

        } catch (Exception e) {
            //DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void Insert() {
        HoaDonMuaHangChiTiet model = getModel();
        // DialogHelper.alert(this, ""+model.getNgayMua() +"");
        try {
            dao.Insert(model);
            this.Load();
            this.Clear();

            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }

    }

    void Update() {
        HoaDonMuaHangChiTiet model = getModel();
        try {
            dao.Update(model);
            this.Load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
            System.out.println(e);
        }
    }

    void setModel(HoaDonMuaHangChiTiet model) {
        txtMaHDMua.setText(model.getMaHDMua());
        txtMaHH.setText(model.getMaHH());
        txtTenHang.setText(model.getTenHang());
        txtSoLuong.setText(model.getSoLuong() + "");
        cbDonVi.setSelectedItem(model.getDonVi());
        DateNgayMua.setDate(model.getNgayMua());
        txtDonGia.setText(model.getDonGia() + "");
        txtGhiChu.setText(model.getGhiChu());
        tblGridView.setRowSelectionInterval(index, index);
    }

    HoaDonMuaHangChiTiet getModel() {
        HoaDonMuaHangChiTiet model = new HoaDonMuaHangChiTiet();
        model.setMaHDMua(txtMaHDMua.getText());
        model.setMaHH(txtMaHH.getText());
        model.setTenHang(txtTenHang.getText());
        model.setSoLuong(Integer.parseInt(txtSoLuong.getText()));
        model.setDonVi((String) cbDonVi.getSelectedItem());
        model.setNgayMua(DateNgayMua.getDate());
        model.setDonGia(Double.parseDouble(txtDonGia.getText()));
        model.setGhiChu(txtGhiChu.getText());
        return model;
    }

    void setStatus(boolean insertable) {
        txtMaHDMua.setEditable(insertable);
        btnAdd.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblGridView.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }

    void Edit() {
        try {
            String manv = (String) tblGridView.getValueAt(this.index, 0);
            HoaDonMuaHangChiTiet model = dao.findById(manv);
            if (model != null) {
                this.setModel(model);
                this.setStatus(false);
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
        }
    }

    public void Find() {

        try {
            String ma = txtTimKiem.getText();
            List<HoaDonMuaHangChiTiet> list = dao.select();
            boolean find = false;
            for (HoaDonMuaHangChiTiet HDMCT : list) {
                if (ma.equalsIgnoreCase(HDMCT.getMaHDMua()) || (ma.contains(HDMCT.getTenHang()))) {
                    index = list.indexOf(HDMCT);
                    this.setModel(HDMCT);
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
            String MaHDMua = txtMaHDMua.getText();
            HoaDonMuaHangChiTiet model;
            model = dao.findById(MaHDMua);
                 if (txtSoLuong.getText().length() == 0) {
                DialogHelper.alert(this, "số lượng KHÔNG được để trống!");
                txtSoLuong.requestFocus();
                return false;
            } else if (!txtSoLuong.getText().matches("[0-9]+")) {
                DialogHelper.alert(this, "Sai định dạng");
                txtSoLuong.requestFocus();
                return false;
            } else if (!(Integer.parseInt(txtSoLuong.getText()) > 0)) {
                DialogHelper.alert(this, "số lượng Lớn Hơn 0");
                txtSoLuong.requestFocus();
                return false;
            }
            if (txtDonGia.getText().length() == 0) {
                DialogHelper.alert(this, "Đơn Giá KHÔNG được để trống!");
                txtDonGia.requestFocus();
                return false;
            } else if (!txtDonGia.getText().matches("[0-9]+")) {
                DialogHelper.alert(this, "Sai định dạng");
                txtDonGia.requestFocus();
                return false;
            } else if (!(Integer.parseInt(txtDonGia.getText()) > 0)) {
                DialogHelper.alert(this, "Đơn Giá Lớn Hơn 0");
                txtDonGia.requestFocus();
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

        jPanel1 = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        lblLogoName = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        pnlContent = new javax.swing.JPanel();
        lblMaHDBan = new javax.swing.JLabel();
        txtMaHDMua = new javax.swing.JTextField();
        lblMaHH = new javax.swing.JLabel();
        txtMaHH = new javax.swing.JTextField();
        lblTenhang = new javax.swing.JLabel();
        txtTenHang = new javax.swing.JTextField();
        lblNgayBan = new javax.swing.JLabel();
        DateNgayMua = new com.toedter.calendar.JDateChooser();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtGhiChu = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGridView = new javax.swing.JTable();
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
        lblSoLuong = new javax.swing.JLabel();
        txtSoLuong = new javax.swing.JTextField();
        lblDonVi = new javax.swing.JLabel();
        cbDonVi = new javax.swing.JComboBox<>();
        lblDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnBack1 = new javax.swing.JButton();
        btnXuatExcel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(840, 788));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogo.setBackground(new java.awt.Color(255, 51, 51));
        pnlLogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoName.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoName.setText("Hóa Đơn Mua Hàng Chi Tiết");
        pnlLogo.add(lblLogoName, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, -1, 60));

        lblExit.setBackground(new java.awt.Color(255, 255, 255));
        lblExit.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        lblExit.setForeground(new java.awt.Color(255, 255, 255));
        lblExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_cancel_48px.png"))); // NOI18N
        lblExit.setToolTipText("Exit");
        lblExit.setName("Exit"); // NOI18N
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

        jPanel1.add(pnlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 90));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));

        lblMaHDBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHDBan.setText("Mã Hóa Đơn Mua:");

        txtMaHDMua.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaHDMua.setEnabled(false);
        txtMaHDMua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHDMuaActionPerformed(evt);
            }
        });

        lblMaHH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHH.setText("Mã Hàng Hóa:");

        txtMaHH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaHH.setEnabled(false);
        txtMaHH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtMaHHMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txtMaHHMouseEntered(evt);
            }
        });
        txtMaHH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMaHHActionPerformed(evt);
            }
        });

        lblTenhang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenhang.setText("Tên Hàng:");

        txtTenHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblNgayBan.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblNgayBan.setText("Ngày Mua:");

        DateNgayMua.setDateFormatString("yyyy-MM-dd\n");
        DateNgayMua.setEnabled(false);

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel6.setText("Ghi Chú:");

        txtGhiChu.setColumns(20);
        txtGhiChu.setRows(5);
        jScrollPane1.setViewportView(txtGhiChu);

        tblGridView.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblGridView.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hóa Đơn Mua", "Mã Hàng Hóa", "Tện Hàng", "Số Lượng", "Đơn Vị", "Ngày Mua", "Đơn Giá", "Ghi Chú"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Double.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridView.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGridView);

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

        lblSoLuong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblSoLuong.setText("Số Lượng:");

        lblDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDonVi.setText("Đơn Vị:");

        cbDonVi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Bộ" }));

        lblDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn Giá:");

        btnBack.setText("BACK");
        btnBack.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBackMouseClicked(evt);
            }
        });
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnBack1.setText("BACK");
        btnBack1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnBack1MouseClicked(evt);
            }
        });
        btnBack1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBack1ActionPerformed(evt);
            }
        });

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
                    .addComponent(jScrollPane2)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addComponent(btnSave, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                                .addComponent(btnFirst)
                                .addGap(18, 18, 18)
                                .addComponent(btnPrev))
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(lblTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSeparator1)
                                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createSequentialGroup()
                                .addComponent(btnNext)
                                .addGap(18, 18, 18)
                                .addComponent(btnLast))
                            .addComponent(btnXuatExcel, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addContainerGap())
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDonGia)
                    .addComponent(lblDonVi)
                    .addComponent(lblSoLuong)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(pnlContentLayout.createSequentialGroup()
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblMaHH)
                                            .addComponent(lblTenhang)
                                            .addComponent(lblMaHDBan))
                                        .addGap(18, 18, 18))
                                    .addGroup(pnlContentLayout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                    .addComponent(lblNgayBan)
                                    .addGap(61, 61, 61)))
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(DateNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 469, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(txtSoLuong, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)
                                    .addComponent(txtTenHang, javax.swing.GroupLayout.Alignment.LEADING))
                                .addGroup(pnlContentLayout.createSequentialGroup()
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(txtMaHH, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                                        .addComponent(txtMaHDMua, javax.swing.GroupLayout.Alignment.LEADING))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(btnBack)
                                        .addComponent(btnBack1))))
                            .addGap(172, 172, 172))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaHDBan)
                            .addComponent(txtMaHDMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaHH)
                            .addComponent(txtMaHH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack1))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenhang)
                            .addComponent(txtTenHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSoLuong)
                            .addComponent(txtSoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDonVi)
                            .addComponent(cbDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblNgayBan))
                    .addComponent(DateNgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDonGia)
                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
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
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblTimKiem))
                    .addComponent(txtTimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(btnXuatExcel)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 840, 700));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblExitMouseClicked
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_lblExitMouseClicked

    private void lblMiniMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMiniMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblMiniMouseClicked

    private void tblGridViewMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewMouseClicked
        // TODO add your handling code here:
        if (evt.getClickCount() == 1) {
            this.index = tblGridView.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.Edit();
            }

        }


    }//GEN-LAST:event_tblGridViewMouseClicked

    private void lblTimKiemMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblTimKiemMouseClicked
        // TODO add your handling code here:
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_lblTimKiemMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        if (txtMaHDMua.getText() == null) {
            this.Load();
        } else {
            this.Load1();
        }
        this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

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
        this.index = tblGridView.getRowCount() - 1;
        this.Edit();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        // TODO add your handling code here:
        if(Check()==true){
            this.Update();
        }else{
            
        }
        
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void txtMaHHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaHHMouseClicked
        // TODO add your handling code here:
        ShowHangHoaJFrame x = new ShowHangHoaJFrame(txtMaHH, txtTenHang);
        x.setVisible(true);
    }//GEN-LAST:event_txtMaHHMouseClicked

    private void txtMaHHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtMaHHMouseEntered
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMaHHMouseEntered

    private void btnBackMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBackMouseClicked
        // TODO add your handling code here:
        this.dispose();
        HoaDonMuaHangJFrame x = new HoaDonMuaHangJFrame();
        x.setVisible(true);

    }//GEN-LAST:event_btnBackMouseClicked

    private void btnBack1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnBack1MouseClicked
        // TODO add your handling code here:
        this.dispose();
        ShowHangHoaJFrame x = new ShowHangHoaJFrame();
        x.setVisible(true);

    }//GEN-LAST:event_btnBack1MouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
        if(Check()==true){
              this.Insert();
        }else{
            
        }
      
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtMaHDMuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHDMuaActionPerformed
        // TODO add your handling code here:
        this.EditMHD();
    }//GEN-LAST:event_txtMaHDMuaActionPerformed

    private void txtMaHHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMaHHActionPerformed
        // TODO add your handling code here:
        this.EditHH();
    }//GEN-LAST:event_txtMaHHActionPerformed

    private void btnBack1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBack1ActionPerformed
        // TODO add your handling code here:
        this.dispose();
        ShowHangHoaJFrame x = new ShowHangHoaJFrame();
        x.setVisible(true);
    }//GEN-LAST:event_btnBack1ActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        this.dispose();
        HoaDonMuaHangJFrame x = new HoaDonMuaHangJFrame();
        x.setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnXuatExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatExcelActionPerformed
        // TODO add your handling code here:
        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet spreadsheet = workbook.createSheet("Hóa Đơn Mua Hàng Chi Tiết");

            XSSFRow row = null;
            Cell cell = null;

            row = spreadsheet.createRow((short) 2);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("HÓA ĐƠN MUA HÀNG CHI TIẾT");
            row = spreadsheet.createRow((short) 3);
            row.setHeight((short) 500);
            cell = row.createCell(0, CellType.STRING);
            cell.setCellValue("STT");
            cell = row.createCell(1, CellType.STRING);
            cell.setCellValue("Mã Hóa Đơn Mua Chi Tiết");
            cell = row.createCell(2, CellType.STRING);
            cell.setCellValue("Mã Hàng Hóa");
            cell = row.createCell(3, CellType.STRING);
            cell.setCellValue("Tên Hàng ");
            cell = row.createCell(4, CellType.STRING);
            cell.setCellValue("Số Lượng");
            cell = row.createCell(5, CellType.STRING);
            cell.setCellValue("Đơn Vị");
            cell = row.createCell(6, CellType.STRING);
            cell.setCellValue("Ngày Mua");
            cell = row.createCell(7, CellType.STRING);
            cell.setCellValue("Đơn Giá");
            cell = row.createCell(8, CellType.STRING);
            cell.setCellValue("Ghi Chú");
            HoaDonMuaHangChiTietDAO hh = new HoaDonMuaHangChiTietDAO();
            List<HoaDonMuaHangChiTiet> listItem = hh.select();
            for (int i = 0; i < listItem.size(); i++) {
                HoaDonMuaHangChiTiet nv = new HoaDonMuaHangChiTiet();
                nv = listItem.get(i);
                row = spreadsheet.createRow((short) 4 + i);
                row.setHeight((short) 400);
                row.createCell(0).setCellValue(i + 1);
                row.createCell(1).setCellValue(nv.getMaHDMua());
                row.createCell(2).setCellValue(nv.getMaHH());
                row.createCell(3).setCellValue(nv.getTenHang());
                row.createCell(4).setCellValue(nv.getSoLuong());
                row.createCell(5).setCellValue(nv.getDonVi());
                row.createCell(6).setCellValue(nv.getNgayMua().toString());
                row.createCell(7).setCellValue(nv.getDonGia());
                row.createCell(8).setCellValue(nv.getGhiChu());
            }
            FileOutputStream out = new FileOutputStream(new File("D:/HoaDonMuaHangChiTiet.xlsx"));
            workbook.write(out);
            DialogHelper.alert(this, "Xuất File Thành Công");
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

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteActionPerformed

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
            java.util.logging.Logger.getLogger(HoaDonMuaChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HoaDonMuaChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HoaDonMuaChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HoaDonMuaChiTietJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new HoaDonMuaChiTietJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.toedter.calendar.JDateChooser DateNgayMua;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnBack1;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnFirst;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JButton btnXuatExcel;
    private javax.swing.JComboBox<String> cbDonVi;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonVi;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblMaHDBan;
    private javax.swing.JLabel lblMaHH;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblNgayBan;
    private javax.swing.JLabel lblSoLuong;
    private javax.swing.JLabel lblTenhang;
    private javax.swing.JLabel lblTimKiem;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JTable tblGridView;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextArea txtGhiChu;
    private javax.swing.JTextField txtMaHDMua;
    private javax.swing.JTextField txtMaHH;
    private javax.swing.JTextField txtSoLuong;
    private javax.swing.JTextField txtTenHang;
    private javax.swing.JTextField txtTimKiem;
    // End of variables declaration//GEN-END:variables
}
