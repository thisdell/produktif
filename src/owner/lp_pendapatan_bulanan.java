/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.koneksi;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author LENOVO
 */
public class lp_pendapatan_bulanan extends javax.swing.JFrame {

    /**
     * Creates new form lp_pendapatan_bulanan
     */
    public lp_pendapatan_bulanan() {
        initComponents();
        setTitle("Laporan Pendapatan Bulanan");
        tanggal();
        show_tabel();
        HeaderColumn();
        tampilGrafik();
    }

    public void tanggal() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        System.out.println(date);
        String tgl = date.toString();

        Calendar c = Calendar.getInstance();
        int tanggal = c.get(Calendar.DAY_OF_MONTH),
                bulan = c.get(Calendar.MONTH),
                tahun = c.get(Calendar.YEAR);

        String txt = tanggal + " " + getNamaBulan(bulan) + " " + tahun;

        this.lbl_tgl.setText(txt);
    }

    private Calendar kalender = Calendar.getInstance();

    public String getNamaBulan(int bulan) {
        switch (bulan) {
            case Calendar.JANUARY:
                return "Januari";
            case Calendar.FEBRUARY:
                return "Februari";
            case Calendar.MARCH:
                return "Maret";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "Mei";
            case Calendar.JUNE:
                return "Juni";
            case Calendar.JULY:
                return "Juli";
            case Calendar.AUGUST:
                return "Agustus";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "Oktober";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "Desember";
            default:
                return "null";
        }
    }

    public void show_tabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Tahun");
        tbl.addColumn("Bulan");
        tbl.addColumn("Item Terjual");
        tbl.addColumn("Total Perbulan");
        tbl_laporan.setModel(tbl);
        lbl_pendapatan.setText(null);
    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_laporan.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tbl_laporan.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);

        ((DefaultTableCellRenderer) tbl_laporan.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public void total() {
        try {
            String sql = "SELECT SUM(harga_diskon) AS total FROM tr_jual WHERE year(tgl_transaksi) ='" + year.getYear() + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                if (rs.getString("total") == null) {
                    this.lbl_pendapatan.setText(main.Rupiah.convertToRupiah(0));
                } else {
                    this.lbl_pendapatan.setText(main.Rupiah.convertToRupiah(Integer.parseInt(rs.getString("total"))));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah " + e);
        }
    }

    private double getPendapatan(int bulan) {
        try {
            Connection c = (Connection) koneksi.configDB();
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery("SELECT SUM(harga_diskon) FROM tr_jual WHERE MONTH(tgl_transaksi) = " + bulan);

            if (r.next()) {
                return r.getDouble(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String getBulan(int bulan) {
        switch (bulan - 1) {
            case Calendar.JANUARY:
                return "Januari";
            case Calendar.FEBRUARY:
                return "Februari";
            case Calendar.MARCH:
                return "Maret";
            case Calendar.APRIL:
                return "April";
            case Calendar.MAY:
                return "Mei";
            case Calendar.JUNE:
                return "Juni";
            case Calendar.JULY:
                return "Juli";
            case Calendar.AUGUST:
                return "Agustus";
            case Calendar.SEPTEMBER:
                return "September";
            case Calendar.OCTOBER:
                return "Oktober";
            case Calendar.NOVEMBER:
                return "November";
            case Calendar.DECEMBER:
                return "Desember";
            default:
                return "null";
        }
    }

    public void tampilGrafik() {

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(this.getPendapatan(1), "Pendapatan Bulan", "Jan");
        dataset.setValue(this.getPendapatan(2), "Pendapatan Bulan", "Feb");
        dataset.setValue(this.getPendapatan(3), "Pendapatan Bulan", "Mar");
        dataset.setValue(this.getPendapatan(4), "Pendapatan Bulan", "Apr");
        dataset.setValue(this.getPendapatan(5), "Pendapatan Bulan", "Mei");
        dataset.setValue(this.getPendapatan(6), "Pendapatan Bulan", "Jun");
        dataset.setValue(this.getPendapatan(7), "Pendapatan Bulan", "Jul");
        dataset.setValue(this.getPendapatan(8), "Pendapatan Bulan", "Agt");
        dataset.setValue(this.getPendapatan(9), "Pendapatan Bulan", "Sep");
        dataset.setValue(this.getPendapatan(10), "Pendapatan Bulan", "Okt");
        dataset.setValue(this.getPendapatan(11), "Pendapatan Bulan", "Nov");
        dataset.setValue(this.getPendapatan(12), "Pendapatan Bulan", "Des");

        JFreeChart barChart = ChartFactory.createBarChart("GRAFIK PENDAPATAN", "BULAN", "TOTAL PENDAPATAN ", dataset, PlotOrientation.VERTICAL, false, true, false);
        CategoryPlot p = barChart.getCategoryPlot();
        CategoryPlot plot = barChart.getCategoryPlot();
        p.setRangeGridlinePaint(Color.RED);

        // Memperbesar ukuran plot area dari chart
        CategoryAxis axis = plot.getDomainAxis();
        axis.setCategoryMargin(0.1);
        axis.setUpperMargin(0.1);
        axis.setLowerMargin(0.1);

        // Menyesuaikan ukuran font 
        CategoryItemRenderer renderer = plot.getRenderer();
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setTickLabelFont(new Font("SansSerif", Font.BOLD, 12));
        renderer.setItemLabelFont(new Font("SansSerif", Font.PLAIN, 12));

        ChartPanel barPanel = new ChartPanel(barChart);
        barPanel.setPreferredSize(new Dimension(520, 370)); 
        panel_chart.removeAll();
        panel_chart.add(barPanel, BorderLayout.CENTER);
        panel_chart.validate();

    }
    
    public void RubahKeExcel(javax.swing.JTable table, File file) {
        try {
            DefaultTableModel Model_Data = (DefaultTableModel) table.getModel();
            FileWriter ObjWriter = new FileWriter(file);
            for (int i = 0; i < Model_Data.getColumnCount(); i++) {
                ObjWriter.write(Model_Data.getColumnName(i) + "\t");
            }

            ObjWriter.write("\n");

            for (int i = 0; i < Model_Data.getRowCount(); i++) {
                for (int j = 0; j < Model_Data.getColumnCount(); j++) {
                    ObjWriter.write(Model_Data.getValueAt(i, j).toString() + "\t");
                }
                ObjWriter.write("\n");
            }

            ObjWriter.close();

        } catch (IOException e) {
            System.out.println(e);
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

        panel_chart = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_laporan = new javax.swing.JTable();
        btn_kembali = new javax.swing.JButton();
        btn_harian = new javax.swing.JButton();
        lbl_tgl = new javax.swing.JLabel();
        btn_cari = new javax.swing.JButton();
        lbl_pendapatan = new javax.swing.JLabel();
        btn_eksport = new javax.swing.JButton();
        year = new com.toedter.calendar.JYearChooser();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(panel_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 200, 520, 370));

        tbl_laporan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tbl_laporan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 250, 600, 320));

        btn_kembali.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_kembali.setBorder(null);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 70, 50));

        btn_harian.setBackground(new java.awt.Color(0,0,0,0));
        btn_harian.setBorder(null);
        btn_harian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_harianActionPerformed(evt);
            }
        });
        getContentPane().add(btn_harian, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 200, 40));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 150, 180, 30));

        btn_cari.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_cari.setBorder(null);
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 40, 40));

        lbl_pendapatan.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_pendapatan.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_pendapatan.setForeground(new java.awt.Color(84, 153, 58));
        lbl_pendapatan.setText("RP50000");
        getContentPane().add(lbl_pendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 630, 160, 30));

        btn_eksport.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_eksport.setBorder(null);
        btn_eksport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eksportActionPerformed(evt);
            }
        });
        getContentPane().add(btn_eksport, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 630, 130, 40));
        getContentPane().add(year, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 203, 210, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/laporan_pendapatan bulanan.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Tahun");
        tbl.addColumn("Bulan");
        tbl.addColumn("Item Terjual");
        tbl.addColumn("Total Perbulan");
        tbl_laporan.setModel(tbl);
        try {
            String sql = "SELECT SUM(harga_diskon) AS totalPerbulan, SUM(total_item) AS itemTerjual ,  MONTH(tgl_transaksi) AS Bulan_ke , YEAR(tgl_transaksi) AS Tahun FROM tr_jual "
                    + "WHERE MONTH(tgl_transaksi) >=1 AND YEAR(tgl_transaksi) = '" + year.getYear() + "' GROUP BY MONTH(tgl_transaksi)";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("Tahun"),
                    this.getBulan(res.getInt("Bulan_ke")),
                    Integer.parseInt(res.getString("itemTerjual")),
                    main.Rupiah.convertToRupiah(Integer.parseInt(res.getString("totalPerbulan")))
                });
                tbl_laporan.setModel(tbl);
                total();
                HeaderColumn();
            }
            if (tbl.getRowCount() == 0) {
        lbl_pendapatan.setText("");
    }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah " + e);
        }
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        new menu_laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_harianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_harianActionPerformed
        new lp_pendapatan_harian().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_harianActionPerformed

    private void btn_eksportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eksportActionPerformed
       JFileChooser path = new JFileChooser();
        path.showOpenDialog(this);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new java.util.Date());
        try {
            File f = path.getSelectedFile();
            String location = f.getAbsolutePath();
            String filename = location + "_" + date + ".xls";
            JOptionPane.showMessageDialog(null, filename);
            File fp = new File(filename);

            RubahKeExcel(tbl_laporan, fp);

            JOptionPane.showMessageDialog(null, "DATA BERHASIL DI EXPORT");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "DATA GAGAL DI EXPORT");
        }
    }//GEN-LAST:event_btn_eksportActionPerformed

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
            java.util.logging.Logger.getLogger(lp_pendapatan_bulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_bulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_bulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_bulanan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new lp_pendapatan_bulanan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_eksport;
    private javax.swing.JButton btn_harian;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_pendapatan;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JPanel panel_chart;
    private javax.swing.JTable tbl_laporan;
    private com.toedter.calendar.JYearChooser year;
    // End of variables declaration//GEN-END:variables
}
