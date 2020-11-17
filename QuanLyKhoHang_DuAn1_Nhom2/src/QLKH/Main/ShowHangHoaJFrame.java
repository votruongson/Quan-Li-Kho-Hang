package QLKH.Main;

import QLKH.Dao.HangHoaDAO;
import QLKH.Dao.LoaiHangDAO;
import QLKH.Helper.DialogHelper;
import QLKH.Helper.ShareHelper;
import QLKH.Model.HangHoa;
import QLKH.Model.KhachHang;
import QLKH.Model.LoaiHang;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ShowHangHoaJFrame extends javax.swing.JFrame {

    /**
     * Creates new form HangHoaJFrame
     */
    int index = 0;
    HangHoaDAO dao = new HangHoaDAO();
    LoaiHangDAO dao1 = new LoaiHangDAO();

    public ShowHangHoaJFrame() {
        initComponents();
        setLocationRelativeTo(this);
    }
      JTextField x;
      JTextField y;
    public ShowHangHoaJFrame(JTextField x,JTextField y){
    this();
    this.x=x;
    this.y=y;
    }

    void Load() {
        DefaultTableModel model = (DefaultTableModel) tblGridViewHH.getModel();
        model.setRowCount(0);
        try {
            List<HangHoa> list = dao.select();
            for (HangHoa hh : list) {
                Object[] row = {
                    hh.getMaHH(),
                    hh.getMaLoai(),
                    hh.getTenHH(),
                    hh.getDonGia(),
                    hh.getDonVi(),
                    hh.getGhiChu(),
                    hh.getHinh(),};
                model.addRow(row);
            }

        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void fillComboBox() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaLoaiHang.getModel();       
        model.removeAllElements();
        try {
            List<LoaiHang> list = dao1.select();
            for (LoaiHang lh : list) {
                model.addElement(lh.getMaLoai());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }void fillComboBox1() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenLoaiHang.getModel();       
        model.removeAllElements();
        try {
            List<LoaiHang> list = dao1.select();
            for (LoaiHang lh : list) {
               // DialogHelper.alert(this,""+lh.getTenLoai()+"");
                model.addElement(lh.getTenLoai());
            }
        } catch (Exception e) {
            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }
    
/*
//    void fillComboBox1() {
//        DefaultComboBoxModel model = (DefaultComboBoxModel) cbxTenHH.getModel();
//        model.removeAllElements();
//        try {
//            List<HangHoa> list = dao.select();
//            for (HangHoa hh : list) {
//                model.addElement(hh);
//            }
//        } catch (Exception e) {
//            DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
//            System.out.println(e);
//        }
//    }
//
//    void SelectComboBox() {
//        LoaiHang lh = (LoaiHang) cbxMaLoaiHang.getSelectedItem();
//        cboTenLoaiHang.setSelectedItem(String.valueOf(lh.getTenLoai()));
//    }

//    void select() {
//        HangHoa hh = (HangHoa) cbxTenHH.getSelectedItem();
//        txtDonGia.setText(String.valueOf(hh.getDonGia()));
//    }
*/
    void setModel(HangHoa model) {
        txtMaHH.setText(model.getMaHH());
        cboMaLoaiHang.setSelectedItem(model.getMaLoai());
        txtTenHH.setText(model.getTenHH());
//        cbxTe.setSelectedItem(dao.findById(model.getTenHH()));
        txtDonGia.setText((String.valueOf(model.getDonGia())));
        cboDonVi.setSelectedItem(model.getDonVi());
   //     txtGhiChu.setText(model.getGhiChu());
        lblHinh.setIcon(null);
         if (model.getHinh() != null) {
          lblHinh.setIcon(ShareHelper.readLogo(model.getHinh()));
         }
         tblGridViewHH.setRowSelectionInterval(index, index);

    }
    LoaiHang getModel1(){
        LoaiHang model = new LoaiHang();
        model.setMaLoai(String.valueOf(cboMaLoaiHang.getSelectedItem()));
        model.setTenLoai(String.valueOf(cboTenLoaiHang.getSelectedItem()));
        return model;
    }
    void setModel1(LoaiHang model){
        cboMaLoaiHang.setSelectedItem(String.valueOf(model.getMaLoai()));
        cboTenLoaiHang.setSelectedItem(String.valueOf(model.getTenLoai()));
    }
    HangHoa getModel() {
        HangHoa model = new HangHoa();
        model.setMaHH(txtMaHH.getText());
        model.setMaLoai(String.valueOf( cboMaLoaiHang.getSelectedItem()));
        model.setTenHH((String.valueOf(txtTenHH.getText())));
        model.setDonGia(txtDonGia.getText());
        model.setDonVi((String) cboDonVi.getSelectedItem());
       // model.setGhiChu(txtGhiChu.getText());
        model.setHinh(lblHinh.getText());
        return model;
    }
/*
    void setStatus(boolean insertable) {
        txtMaHH.setEditable(insertable);
//        btnAdd.setEnabled(insertable);
        btnUpdate.setEnabled(!insertable);
        btnDelete.setEnabled(!insertable);
        boolean first = this.index > 0;
        boolean last = this.index < tblGridViewHH.getRowCount() - 1;
        btnFirst.setEnabled(!insertable && first);
        btnPrev.setEnabled(!insertable && first);
        btnNext.setEnabled(!insertable && last);
        btnLast.setEnabled(!insertable && last);
    }
    */
    void edit1(){
     try {
            String makh = String.valueOf( cboMaLoaiHang.getSelectedItem());
            LoaiHang model = dao1.findById(makh);
            if (model != null) {
                this.setModel1(model);
             //   this.setStatus(false);
            }
        } catch (Exception e) {
            //  DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
}
    void Edit() {
        try {
            String makh = (String) tblGridViewHH.getValueAt(this.index, 0);
            HangHoa model = dao.findById(makh);
            if (model != null) {
                this.setModel(model);
              //  this.setStatus(false);
            }
        } catch (Exception e) {
            //  DialogHelper.alert(this, "Lỗi truy vấn dữ liệu!");
            System.out.println(e);
        }
    }

    void selectImage() {
        JFileChooser FileChooser = new JFileChooser();
        FileChooser.setDialogTitle("Chọn Hình Hàng Hóa");
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
        this.setModel(new HangHoa());
       // this.setStatus(true);
    }
/*
    void InsertLH() {
        LoaiHang model = getModelLH();
        try {
            dao1.insert(model);
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    void setModelLH(LoaiHang model) {
        txtMaLoai.setText( String.valueOf(model.getMaLoai()));
        txtTenLoai.setText(String.valueOf(model.getTenLoai()));
    }

    LoaiHang getModelLH() {
        LoaiHang model = new LoaiHang();
        model.setMaLoai((txtMaLoai.getText()));
        model.setTenLoai(txtTenLoai.getText());
        return model;
    }

    void DeleteLH() {
        if (DialogHelper.confirm(this, "Bạn thực sự muốn xóa Khách Hàng này?")) {
            String manv = txtTenLoai.getText();
            try {
                dao1.delete(manv);
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
            }
        }
    }
*/
    void Insert() {
        HangHoa model = getModel();
        try {
            dao.insert(model);
            this.Load();
            this.Clear();
            DialogHelper.alert(this, "Thêm mới thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Thêm mới thất bại!");
            System.out.println(e);
        }
    }

    void Update() {
        HangHoa model = getModel();
        try {
            dao.update(model);
            this.Load();
            DialogHelper.alert(this, "Cập nhật thành công!");
        } catch (Exception e) {
            DialogHelper.alert(this, "Cập nhật thất bại!");
            System.out.println(e);
        }
    }

    void Delete() {

        if (DialogHelper.confirm(this, "Bạn thực sự muốn XÓA hàng hóa này?")) {
            String manv = txtMaHH.getText();
            try {
                dao.delete(manv);
                this.Load();
                this.Clear();
                DialogHelper.alert(this, "Xóa thành công!");
            } catch (Exception e) {
                DialogHelper.alert(this, "Xóa thất bại!");
                System.out.println(e);
            }
        }
    }
    /*
    public void Find() {

        try {
            String ma = txtTimKiem.getText();
            List<HangHoa> list = dao.select();
            boolean find = false;
            for (HangHoa hh : list) {
                if (ma.equalsIgnoreCase(hh.getMaHH()) || (ma.contains(hh.getTenHH()))) {
                    index = list.indexOf(hh);
                    this.setModel(hh);
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
            String ma = txtMaHH.getText();
           HangHoa model;
            model = dao.findById(ma);
              if (txtMaLoai.getText().length() == 0) {
                DialogHelper.alert(this, "Tên Khách Hàng KHÔNG được để trống!");
                txtMaLoai.requestFocus();
                return false;
            } else if (!txtMaLoai.getText().matches("[a-z A-Z]+")) {
                DialogHelper.alert(this, "Tên Khách Hàng chỉ chứa alphabet và ký tự trắng!");
                txtMaLoai.requestFocus();
                return false;
            }
            if (txtTenHH.getText().length() == 0) {
                DialogHelper.alert(this, "Tên Khách Hàng KHÔNG được để trống!");
                txtTenHH.requestFocus();
                return false;
            } else if (!txtTenHH.getText().matches("[a-z A-Z]+")) {
                DialogHelper.alert(this, "Tên Khách Hàng chỉ chứa alphabet và ký tự trắng!");
                txtTenHH.requestFocus();
                return false;
            }
              if (txtDonGia.getText().length() == 0) {
                DialogHelper.alert(this, "Đơn Giá KHÔNG được để trống!");
                txtTenHH.requestFocus();
                return false;
            } else if (!txtDonGia.getText().matches("[0-9]+")) {
                DialogHelper.alert(this, "Sai định dạng");
                txtDonGia.requestFocus();
                return false;
            }else if(Integer.parseInt(txtDonGia.getText())>0){
                 DialogHelper.alert(this, "Đơn Giá Lớn Hơn 0");
                txtDonGia.requestFocus();
                return false;
            }
                 if (txtGhiChu.getText().length() == 0) {
                DialogHelper.alert(this, "Ghi Chú KHÔNG được để trống!");
                txtGhiChu.requestFocus();
                return false;
            } else if (!txtGhiChu.getText().matches("[a-z A-Z]+")) {
                DialogHelper.alert(this, "Ghi Chú chỉ chứa alphabet và ký tự trắng!");
                txtGhiChu.requestFocus();
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
 */
    
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
        lblMaHH = new javax.swing.JLabel();
        txtMaHH = new javax.swing.JTextField();
        lblMaLoaiHang = new javax.swing.JLabel();
        lblTenHH = new javax.swing.JLabel();
        lblDonVi = new javax.swing.JLabel();
        lblHinh = new javax.swing.JLabel();
        cboDonVi = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblGridViewHH = new javax.swing.JTable();
        lblDonGia = new javax.swing.JLabel();
        txtDonGia = new javax.swing.JTextField();
        lblTenLoaiHang = new javax.swing.JLabel();
        cboTenLoaiHang = new javax.swing.JComboBox<>();
        cboMaLoaiHang = new javax.swing.JComboBox<>();
        txtTenHH = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 51, 255));
        jPanel1.setPreferredSize(new java.awt.Dimension(840, 840));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogo.setBackground(new java.awt.Color(255, 51, 51));
        pnlLogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoName.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoName.setText("Hàng Hóa");
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

        jPanel1.add(pnlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 840, 90));

        pnlContent.setBackground(new java.awt.Color(255, 255, 255));
        pnlContent.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Hàng Hóa", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 16), new java.awt.Color(0, 0, 255))); // NOI18N
        pnlContent.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                pnlContentKeyPressed(evt);
            }
        });

        lblMaHH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaHH.setText("Mã Hàng Hóa:");

        txtMaHH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtMaHH.setEnabled(false);

        lblMaLoaiHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblMaLoaiHang.setText("Mã Loại Hàng:");

        lblTenHH.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenHH.setText("Tên Hàng Hóa:");

        lblDonVi.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDonVi.setText("Đơn Vị:");

        lblHinh.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, java.awt.Color.red, java.awt.Color.green, java.awt.Color.yellow, java.awt.Color.magenta));
        lblHinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblHinhMouseClicked(evt);
            }
        });

        cboDonVi.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboDonVi.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Thùng", "Cái", " " }));

        tblGridViewHH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tblGridViewHH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã Hàng Hóa", "Mã Loại", "Tên Hàng Hóa", "Đơn Giá", "Đơn Vị", "Ghi Chú", "Hinh"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblGridViewHH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblGridViewHHMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblGridViewHH);

        lblDonGia.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblDonGia.setText("Đơn Giá:");

        txtDonGia.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblTenLoaiHang.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        lblTenLoaiHang.setText("Tên Loại Hàng");

        cboTenLoaiHang.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cboTenLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboTenLoaiHangActionPerformed(evt);
            }
        });

        cboMaLoaiHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaLoaiHangActionPerformed(evt);
            }
        });

        txtTenHH.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jButton1.setText("add HH");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlContentLayout = new javax.swing.GroupLayout(pnlContent);
        pnlContent.setLayout(pnlContentLayout);
        pnlContentLayout.setHorizontalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblMaHH)
                                .addComponent(lblTenHH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lblMaLoaiHang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblTenLoaiHang)
                            .addComponent(lblDonGia))
                        .addGap(18, 38, Short.MAX_VALUE)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(cboMaLoaiHang, 0, 486, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(cboTenLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                                    .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblDonVi)
                                    .addGap(35, 35, 35)
                                    .addComponent(cboDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(txtMaHH, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                            .addComponent(txtTenHH))
                        .addGap(18, 18, 18)
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlContentLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        pnlContentLayout.setVerticalGroup(
            pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlContentLayout.createSequentialGroup()
                .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaHH)
                            .addComponent(txtMaHH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblMaLoaiHang)
                            .addComponent(cboMaLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenHH)
                            .addComponent(txtTenHH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTenLoaiHang)
                            .addComponent(cboTenLoaiHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlContentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDonGia)
                            .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblDonVi)
                            .addComponent(cboDonVi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(pnlContentLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblHinh, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        jPanel1.add(pnlContent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 90, 840, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 840, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
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

    private void lblHinhMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHinhMouseClicked
        // TODO add your handling code here:
        selectImage();
    }//GEN-LAST:event_lblHinhMouseClicked

    private void tblGridViewHHMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblGridViewHHMouseClicked
        // TODO add your handling code here:
      try{
          if (evt.getClickCount() == 1) {
            this.index = tblGridViewHH.rowAtPoint(evt.getPoint());
            if (this.index >= 0) {
                this.Edit();
                this.edit1();

            }
        }
           
                       
           
      } catch(Exception e) {
           e.printStackTrace();
      }
    }//GEN-LAST:event_tblGridViewHHMouseClicked

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        fillComboBox();
     fillComboBox1();
        this.Load();
     //   this.setStatus(true);
    }//GEN-LAST:event_formWindowOpened

    private void cboMaLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaLoaiHangActionPerformed
        // TODO add your handling code here:
//        this.SelectComboBox();
    }//GEN-LAST:event_cboMaLoaiHangActionPerformed

    private void pnlContentKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_pnlContentKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_pnlContentKeyPressed

    private void cboTenLoaiHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTenLoaiHangActionPerformed
        // TODO add your handling code here:
       // SelectComboBox();
    }//GEN-LAST:event_cboTenLoaiHangActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        
                  this.dispose();
        String chuoi = txtMaHH.getText();
        x.setText(chuoi);            
        String chuoi1 = txtTenHH.getText();
        y.setText(chuoi1);
            
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ShowHangHoaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ShowHangHoaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ShowHangHoaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ShowHangHoaJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ShowHangHoaJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cboDonVi;
    private javax.swing.JComboBox<LoaiHang> cboMaLoaiHang;
    private javax.swing.JComboBox<String> cboTenLoaiHang;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDonGia;
    private javax.swing.JLabel lblDonVi;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblHinh;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblMaHH;
    private javax.swing.JLabel lblMaLoaiHang;
    private javax.swing.JLabel lblMini;
    private javax.swing.JLabel lblTenHH;
    private javax.swing.JLabel lblTenLoaiHang;
    private javax.swing.JPanel pnlContent;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JTable tblGridViewHH;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtMaHH;
    private javax.swing.JTextField txtTenHH;
    // End of variables declaration//GEN-END:variables
}
