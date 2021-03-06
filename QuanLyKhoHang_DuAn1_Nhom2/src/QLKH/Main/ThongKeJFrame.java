
package QLKH.Main;

import QLKH.Dao.ThongKeDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.jdbc.JDBCCategoryDataset;

/**
 *
 * @author Admin
 */

public class ThongKeJFrame extends javax.swing.JFrame {

    /**
     * Creates new form ThongKeJFrame
     */    
      private static String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static String dburl = "jdbc:sqlserver://localhost;database=QuanLiKhoHang";
    private static String username = "sa";
    private static String password = "12345";
    ThongKeDAO dao=new ThongKeDAO();
    public ThongKeJFrame() {
        initComponents();
        setLocationRelativeTo(this);
        FillTableKhachHang();
        FillTableNhanVien();
        FillTableHangHoa();
    }
 void FillTableKhachHang() {
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getKhachHang();
        for (Object[] row : list) {
            model.addRow(row);
        }
        
    }
  void FillTableNhanVien() {
        DefaultTableModel model = (DefaultTableModel) tblNhanVien.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getNhanVien();
        for (Object[] row : list) {
            model.addRow(row);
        }
        
    }
   void FillTableHangHoa() {
        DefaultTableModel model = (DefaultTableModel) tblHangHoa.getModel();
        model.setRowCount(0);
        List<Object[]> list = dao.getHangHoa();
        for (Object[] row : list) {
            model.addRow(row);
        }
        
    }
    void ThongKeKhachHang() throws SQLException{
      
       Connection connection = DriverManager.getConnection(dburl, username, password);
       try{
          String sql = "{call sp_ThongKeKhachHang}";//
           JDBCCategoryDataset Data=new JDBCCategoryDataset(connection, sql);
      JFreeChart chart=ChartFactory.createBarChart3D("Thống Kê Khách Hàng", "Mã Khách Hàng", "Doanh Thu", Data, PlotOrientation.VERTICAL, false,true,true);
           BarRenderer r=null;
           CategoryPlot plot=null;
           r=new BarRenderer();
           ChartFrame frame=new ChartFrame("Thống Kê Khách Hàng", chart);
           frame.setVisible(true);
           frame.setSize(400,650);
       }catch(Exception e){
           
       }
    }
      void ThongKeNhanVien() throws SQLException{
      
       Connection connection = DriverManager.getConnection(dburl, username, password);
       try{
          String sql = "{call sp_ThongKeNhanVien}";//
           JDBCCategoryDataset Data=new JDBCCategoryDataset(connection, sql);
      JFreeChart chart=ChartFactory.createBarChart3D("Thống Kê Nhân Viên", "Mã Nhân Viên", "Doanh Thu", Data, PlotOrientation.VERTICAL, false,true,true);
           BarRenderer r=null;
           CategoryPlot plot=null;
           r=new BarRenderer();
           ChartFrame frame=new ChartFrame("Thống Kê Nhân Viên", chart);
           frame.setVisible(true);
           frame.setSize(400,650);
       }catch(Exception e){
           
       }
    }
         void ThongKeDoanhThu() throws SQLException{
      
       Connection connection = DriverManager.getConnection(dburl, username, password);
       try{
          String sql = "{call sp_ThongKeDoanhThu}";//
           JDBCCategoryDataset Data=new JDBCCategoryDataset(connection, sql);
      JFreeChart chart=ChartFactory.createBarChart3D("Thống Kê Doanh Thu", "Mã Hàng Hóa", "Tổng Tiền", Data, PlotOrientation.VERTICAL, false,true,true);
           BarRenderer r=null;
           CategoryPlot plot=null;
           r=new BarRenderer();
           ChartFrame frame=new ChartFrame("Thống Kê Doanh Thu", chart);
           frame.setVisible(true);
           frame.setSize(400,650);
       }catch(Exception e){
           
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

        jPanel1 = new javax.swing.JPanel();
        pnlLogo = new javax.swing.JPanel();
        lblLogoName = new javax.swing.JLabel();
        lblExit = new javax.swing.JLabel();
        lblMini = new javax.swing.JLabel();
        pnlThongKe = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlKhachHang = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKhachHang = new javax.swing.JTable();
        btnBieuDo = new javax.swing.JButton();
        pnlKhachHang1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNhanVien = new javax.swing.JTable();
        btnBieuDO = new javax.swing.JButton();
        pnlKhachHang2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHangHoa = new javax.swing.JTable();
        btnChart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlLogo.setBackground(new java.awt.Color(255, 51, 51));
        pnlLogo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblLogoName.setFont(new java.awt.Font("Segoe UI Black", 1, 36)); // NOI18N
        lblLogoName.setForeground(new java.awt.Color(255, 255, 255));
        lblLogoName.setText("Thống Kê");
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
        pnlLogo.add(lblExit, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 0, -1, -1));

        lblMini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/icons8_minus_48px.png"))); // NOI18N
        lblMini.setToolTipText("Miniform");
        lblMini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMiniMouseClicked(evt);
            }
        });
        pnlLogo.add(lblMini, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 0, -1, -1));

        jPanel1.add(pnlLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 852, 90));

        pnlThongKe.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Khách Hàng", "Mã Hàng Hóa", "Mã Hóa Đơn Bán", "Số Lương Bán", "Doanh Thu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblKhachHang);
        if (tblKhachHang.getColumnModel().getColumnCount() > 0) {
            tblKhachHang.getColumnModel().getColumn(3).setResizable(false);
        }

        btnBieuDo.setText("Biểu Đồ");
        btnBieuDo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBieuDoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlKhachHangLayout = new javax.swing.GroupLayout(pnlKhachHang);
        pnlKhachHang.setLayout(pnlKhachHangLayout);
        pnlKhachHangLayout.setHorizontalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachHangLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnBieuDo)))
                .addContainerGap())
        );
        pnlKhachHangLayout.setVerticalGroup(
            pnlKhachHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHangLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBieuDo)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("KHÁCH HÀNG", pnlKhachHang);

        tblNhanVien.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Mã Nhân Viên", "Mã Hàng Hóa", "Mã Hóa Đơn Bán", "Số Lương Bán", "Doanh Thu"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblNhanVien);
        if (tblNhanVien.getColumnModel().getColumnCount() > 0) {
            tblNhanVien.getColumnModel().getColumn(3).setResizable(false);
        }

        btnBieuDO.setText("Biểu Đồ");
        btnBieuDO.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBieuDOActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlKhachHang1Layout = new javax.swing.GroupLayout(pnlKhachHang1);
        pnlKhachHang1.setLayout(pnlKhachHang1Layout);
        pnlKhachHang1Layout.setHorizontalGroup(
            pnlKhachHang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachHang1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBieuDO)
                .addContainerGap())
            .addGroup(pnlKhachHang1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 827, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlKhachHang1Layout.setVerticalGroup(
            pnlKhachHang1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHang1Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBieuDO)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Nhân Viên", pnlKhachHang1);

        tblHangHoa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Mã Hàng Hóa", "Số Lượng Mua", "Số Lượng Bán", "Số Lượng Tồn", "Tổng Tiền Nhập", "Tổng Tiền Xuất", "Lợi Nhuận"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Float.class, java.lang.Float.class, java.lang.Float.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblHangHoa);
        if (tblHangHoa.getColumnModel().getColumnCount() > 0) {
            tblHangHoa.getColumnModel().getColumn(6).setResizable(false);
        }

        btnChart.setText("Biểu Đồ");
        btnChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlKhachHang2Layout = new javax.swing.GroupLayout(pnlKhachHang2);
        pnlKhachHang2.setLayout(pnlKhachHang2Layout);
        pnlKhachHang2Layout.setHorizontalGroup(
            pnlKhachHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHang2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlKhachHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 825, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachHang2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnChart)))
                .addContainerGap())
        );
        pnlKhachHang2Layout.setVerticalGroup(
            pnlKhachHang2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachHang2Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnChart)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Hàng Hóa", pnlKhachHang2);

        pnlThongKe.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 13, 850, 460));

        jPanel1.add(pnlThongKe, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 82, 852, 470));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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

    private void btnBieuDoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBieuDoActionPerformed
          try {
              // TODO add your handling code here:
              this.ThongKeKhachHang();
          } catch (SQLException ex) {
              Logger.getLogger(ThongKeJFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_btnBieuDoActionPerformed

    private void btnBieuDOActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBieuDOActionPerformed
          try {
              // TODO add your handling code here:
              this.ThongKeNhanVien();
          } catch (SQLException ex) {
              Logger.getLogger(ThongKeJFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_btnBieuDOActionPerformed

    private void btnChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChartActionPerformed
          try {
              // TODO add your handling code here:
              this.ThongKeDoanhThu();
          } catch (SQLException ex) {
              Logger.getLogger(ThongKeJFrame.class.getName()).log(Level.SEVERE, null, ex);
          }
    }//GEN-LAST:event_btnChartActionPerformed

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
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ThongKeJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ThongKeJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBieuDO;
    private javax.swing.JButton btnBieuDo;
    private javax.swing.JButton btnChart;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblExit;
    private javax.swing.JLabel lblLogoName;
    private javax.swing.JLabel lblMini;
    private javax.swing.JPanel pnlKhachHang;
    private javax.swing.JPanel pnlKhachHang1;
    private javax.swing.JPanel pnlKhachHang2;
    private javax.swing.JPanel pnlLogo;
    private javax.swing.JPanel pnlThongKe;
    private javax.swing.JTable tblHangHoa;
    private javax.swing.JTable tblKhachHang;
    private javax.swing.JTable tblNhanVien;
    // End of variables declaration//GEN-END:variables
}
