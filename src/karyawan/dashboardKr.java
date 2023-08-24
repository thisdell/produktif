/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package karyawan;

import java.awt.Dimension;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JOptionPane;
import main.koneksi;
import main.login;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author LENOVO
 */
public class dashboardKr extends javax.swing.JFrame {

    /**
     * Creates new form dashboardK
     */
    public dashboardKr() {
        initComponents();
        setTitle("Dashboard Karyawan");
        tanggal();
        id_karyawan(login.namaUser);
        produkFavorit();
        produkTerjual();
        pieChart();
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

        String txt = getNamaHari(c.get(Calendar.DAY_OF_WEEK)) + ", "
                + tanggal + " " + getNamaBulan(bulan) + " " + tahun;

        this.lbl_tgl.setText(txt);
    }

    private Calendar kalender = Calendar.getInstance();

    public String getNamaHari(int dayOfWeek) {
        switch (kalender.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                return "Minggu";
            case Calendar.MONDAY:
                return "Senin";
            case Calendar.TUESDAY:
                return "Selasa";
            case Calendar.WEDNESDAY:
                return "Rabu";
            case Calendar.THURSDAY:
                return "Kamis";
            case Calendar.FRIDAY:
                return "Jumat";
            case Calendar.SATURDAY:
                return "Sabtu";
            default:
                return "null";
        }
    }

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

    private void id_karyawan(String username) {
        try {
            String query = "SELECT * FROM karyawan WHERE username = '" + username + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.lbl_nama.setText(r.getString("nama_karyawan"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void produkFavorit() {
        try {
            String sql = "SELECT produk.nama_produk FROM detail_tr_jual "
                    + "INNER JOIN produk ON detail_tr_jual.id_produk = produk.id_produk "
                    + "GROUP BY detail_tr_jual.id_produk "
                    + "ORDER BY COUNT(*) DESC LIMIT 1";

            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);

            if (res.next()) {
                String namaProduk = res.getString("nama_produk");
                this.lbl_favorit.setText(namaProduk);
            } else {
                this.lbl_favorit.setText("Tidak ada produk favorit");
            }

            res.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "SALAH " + e);
        }
    }

    public void produkTerjual() {
        try {
            String sql = "SELECT SUM(total_item) AS total FROM tr_jual WHERE MONTH(tgl_transaksi) = MONTH(CURRENT_DATE())";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                if (res.getString("total") == null) {
                    this.lbl_terjual.setText("0");
                } else {
                    this.lbl_terjual.setText(String.format("%,d", res.getInt("total")).replaceAll(",", ".") + " item");
                }
            }
            res.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "SALAH " + e);
        }
    }

    public static ChartPanel framePieChart;

    public void pieChart() {
        try {
            String sql = "SELECT MONTH(`tgl_transaksi`) AS bulan, YEAR(`tgl_transaksi`) AS tahun, SUM(total_item) AS `total` from `tr_jual` GROUP BY MONTH(`tgl_transaksi`);";
            DefaultPieDataset pieSet = new DefaultPieDataset();
            Connection conn = (Connection) koneksi.configDB();
            PreparedStatement prp = conn.prepareStatement(sql);
            ResultSet rs = prp.executeQuery(sql);
            while (rs.next()) {
                System.out.println("data");
                if (rs.getString("bulan").equals(String.valueOf(1))) {
                    pieSet.setValue("Januari " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(2))) {
                    pieSet.setValue("Febuary " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(3))) {
                    pieSet.setValue("Maret " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(4))) {
                    pieSet.setValue("April " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(5))) {
                    pieSet.setValue("Mei " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(6))) {
                    pieSet.setValue("Juni " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(7))) {
                    pieSet.setValue("Juli " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(8))) {
                    pieSet.setValue("Agustus " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(9))) {
                    pieSet.setValue("September " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(10))) {
                    pieSet.setValue("October " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(11))) {
                    pieSet.setValue("November " + rs.getString("tahun"), rs.getDouble("total"));
                } else if (rs.getString("bulan").equals(String.valueOf(12))) {
                    pieSet.setValue("Desember " + rs.getString("tahun"), rs.getDouble("total"));
                }
            }
            JFreeChart chartPie = ChartFactory.createPieChart3D("Produk Terjual Perbulan",
                    pieSet, true, true, false);
            ChartPanel framePieChart = new ChartPanel(chartPie);
            framePieChart.setPreferredSize(new Dimension(570, 400));
            panel_chart.add(framePieChart);

        } catch (SQLException e) {
            e.printStackTrace();
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

        lbl_tgl = new javax.swing.JLabel();
        lbl_terjual = new javax.swing.JLabel();
        lbl_favorit = new javax.swing.JLabel();
        lbl_nama = new javax.swing.JLabel();
        panel_chart = new javax.swing.JPanel();
        btn_produk = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_member = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        btn_profil = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(84, 153, 58));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_tgl.setText("jLabel2");
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 300, 50));

        lbl_terjual.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_terjual.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lbl_terjual.setForeground(new java.awt.Color(84, 153, 58));
        lbl_terjual.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_terjual.setText("jLabel2");
        getContentPane().add(lbl_terjual, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 300, 80));

        lbl_favorit.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_favorit.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        lbl_favorit.setForeground(new java.awt.Color(84, 153, 58));
        lbl_favorit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_favorit.setText("jLabel2");
        getContentPane().add(lbl_favorit, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 510, 300, 70));

        lbl_nama.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_nama.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        lbl_nama.setForeground(new java.awt.Color(84, 153, 58));
        lbl_nama.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 70, 170, 30));
        getContentPane().add(panel_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 570, 400));

        btn_produk.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_produk.setBorder(null);
        btn_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_produkActionPerformed(evt);
            }
        });
        getContentPane().add(btn_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 140, 40));

        btn_supplier.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_supplier.setBorder(null);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 150, 40));

        btn_member.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_member.setBorder(null);
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memberActionPerformed(evt);
            }
        });
        getContentPane().add(btn_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 150, 40));

        btn_transaksi.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_transaksi.setBorder(null);
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 400, 160, 50));

        btn_keluar.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_keluar.setBorder(null);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 130, 40));

        btn_profil.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_profil.setBorder(null);
        btn_profil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_profilActionPerformed(evt);
            }
        });
        getContentPane().add(btn_profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 10, 80, 80));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/berandaKr.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_produkActionPerformed
        new produkKr().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_produkActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        new supplierKr().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memberActionPerformed
        new memberKr().setVisible(true);
        this.dispose();
                
    }//GEN-LAST:event_btn_memberActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        new menu_transaksiKr().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_transaksiActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
       if (JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?", "PERINGATAN",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { 
             new main.landing_page().setVisible(true);
                     this.dispose();
            } else {
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_profilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_profilActionPerformed
        new profilK().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_profilActionPerformed

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
            java.util.logging.Logger.getLogger(dashboardKr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardKr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardKr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardKr.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardKr().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_produk;
    private javax.swing.JButton btn_profil;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_favorit;
    private javax.swing.JLabel lbl_nama;
    private javax.swing.JLabel lbl_terjual;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JPanel panel_chart;
    // End of variables declaration//GEN-END:variables
}
