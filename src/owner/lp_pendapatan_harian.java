/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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

/**
 *
 * @author LENOVO
 */
public class lp_pendapatan_harian extends javax.swing.JFrame {

    /**
     * Creates new form laporan_pendapatan
     */
    public lp_pendapatan_harian() {
        initComponents();
        setTitle("Laporan Pendapatan Harian");
        show_tabel();
        HeaderColumn();
        tanggal();
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

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_laporan.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tbl_laporan.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tbl_laporan.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);

        ((DefaultTableCellRenderer) tbl_laporan.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public void show_tabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID Transaksi Jual");
        tbl.addColumn("Id Karyawan");
        tbl.addColumn("Id Member");
        tbl.addColumn("Total Item");
        tbl.addColumn("Total Harga");
        tbl.addColumn("Tanggal");
        tbl_laporan.setModel(tbl);
    }

    public void total() {
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tgl = String.valueOf(fm.format(txt_tgl.getDate()));

        try {
            String sql = "SELECT SUM(harga_diskon) as total FROM tr_jual WHERE tgl_transaksi = '" + tgl + "'";
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
            JOptionPane.showMessageDialog(rootPane, "salah" + e);
        }
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_laporan = new javax.swing.JTable();
        txt_tgl = new com.toedter.calendar.JDateChooser();
        btn_cari = new javax.swing.JButton();
        lbl_pendapatan = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JButton();
        btn_bulanan = new javax.swing.JButton();
        btn_eksport = new javax.swing.JButton();
        lbl_tgl = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(62, 257, 1210, 310));

        txt_tgl.setForeground(new java.awt.Color(84, 153, 0));
        txt_tgl.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        getContentPane().add(txt_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 203, 210, 30));

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
        getContentPane().add(lbl_pendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 619, 160, 40));

        btn_kembali.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_kembali.setBorder(null);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 80, 40));

        btn_bulanan.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_bulanan.setBorder(null);
        btn_bulanan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_bulananActionPerformed(evt);
            }
        });
        getContentPane().add(btn_bulanan, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 143, 200, 40));

        btn_eksport.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_eksport.setBorder(null);
        btn_eksport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eksportActionPerformed(evt);
            }
        });
        getContentPane().add(btn_eksport, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 620, 120, 40));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 150, 180, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/laporan_pendapatan harian.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        String tampilan = "yyyy-MM-dd";
        SimpleDateFormat fm = new SimpleDateFormat(tampilan);
        String tgl = String.valueOf(fm.format(txt_tgl.getDate()));

        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("ID Transaksi Jual");
        tbl.addColumn("Id Karyawan");
        tbl.addColumn("Id Member");
        tbl.addColumn("Total Item");
        tbl.addColumn("Total Harga");
        tbl.addColumn("Tanggal");
        tbl_laporan.setModel(tbl);
        try {
            String sql = "select * from tr_jual WHERE tgl_transaksi = '" + tgl + "'";
            Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("id_tr_jual"),
                    res.getString("id_karyawan"),
                    res.getString("id_member"),
                    res.getString("total_item"),
                    main.Rupiah.convertToRupiah(Integer.parseInt(res.getString("harga_diskon"))),
                    res.getString("tgl_transaksi"),});
                tbl_laporan.setModel(tbl);
                total();
                HeaderColumn();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah " + e);
        }
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        new menu_laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_bulananActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_bulananActionPerformed
        new lp_pendapatan_bulanan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_bulananActionPerformed

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
            java.util.logging.Logger.getLogger(lp_pendapatan_harian.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_harian.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_harian.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(lp_pendapatan_harian.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new lp_pendapatan_harian().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_bulanan;
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_eksport;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_pendapatan;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JTable tbl_laporan;
    private com.toedter.calendar.JDateChooser txt_tgl;
    // End of variables declaration//GEN-END:variables
}
