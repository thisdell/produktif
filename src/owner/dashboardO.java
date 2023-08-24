/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
public class dashboardO extends javax.swing.JFrame {

    /**
     * Creates new form dashboard_O
     */
    public dashboardO() {
        initComponents();
        setTitle("Dashboard Owner");
        id_karyawan(login.namaUser);
        tanggal();
        PendapatanBulanan();
        PengeluaranBulanan();
        pieChart();
       
    }
    
    public void tanggal(){
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        System.out.println(date);
        String tgl = date.toString();
        
        Calendar c = Calendar.getInstance();
        int tanggal = c.get(Calendar.DAY_OF_MONTH),
            bulan = c.get(Calendar.MONTH),
            tahun = c.get(Calendar.YEAR);
        
        String txt = getNamaHari(c.get(Calendar.DAY_OF_WEEK)) + ", " +
                tanggal + " " + getNamaBulan(bulan)+ " "  + tahun;
        
        this.lbl_tgl.setText(txt);
    }
    
    private Calendar kalender = Calendar.getInstance();
    
    public String getNamaHari(int dayOfWeek){
        switch(kalender.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY: return "Minggu";
            case Calendar.MONDAY: return "Senin";
            case Calendar.TUESDAY: return "Selasa";
            case Calendar.WEDNESDAY: return "Rabu";
            case Calendar.THURSDAY: return "Kamis";
            case Calendar.FRIDAY: return "Jumat";
            case Calendar.SATURDAY: return "Sabtu";
            default: return "null";
        }
    }
    
    public String getNamaBulan(int bulan){
        switch(bulan){
            case Calendar.JANUARY: return "Januari";
            case Calendar.FEBRUARY: return "Februari";
            case Calendar.MARCH: return "Maret";
            case Calendar.APRIL: return "April";
            case Calendar.MAY: return "Mei";
            case Calendar.JUNE: return "Juni";
            case Calendar.JULY: return "Juli";
            case Calendar.AUGUST: return "Agustus";
            case Calendar.SEPTEMBER: return "September";
            case Calendar.OCTOBER: return "Oktober";
            case Calendar.NOVEMBER: return "November";
            case Calendar.DECEMBER: return "Desember";
            default: return "null";
        }
    }
    
    private void id_karyawan(String username){
        try{
            String query = "SELECT * FROM karyawan WHERE username = '"+username+"'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);
            
            if(r.next()){
                this.lbl_nama.setText(r.getString("nama_karyawan"));
            }
            
        }catch(SQLException ex){
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } 
    }
    
    public void PendapatanBulanan() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        System.out.println(date);
        String tgl = date.toString();
        
        try {
            String sql = "SELECT SUM(harga_diskon) AS total FROM tr_jual WHERE month(tgl_transaksi) = "
                    + "'"+ tgl.substring(5, 7) +"' ";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                if( res.getString("total") == null){
                    this.lbl_pendapatan.setText(main.Rupiah.convertToRupiah(0));
                } else {
                   this.lbl_pendapatan.setText(main.Rupiah.convertToRupiah(Integer.parseInt(res.getString("total")))); 
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "SALAH "+e);
        }
    }

    public void PengeluaranBulanan() {
        long millis = System.currentTimeMillis();
        Date date = new Date(millis);
        System.out.println(date);
        String tgl = date.toString();
        
        try {
            String sql = "SELECT SUM(total_harga) AS total FROM tr_beli WHERE month(tgl_transaksi) = "
                    + "'"+ tgl.substring(5, 7) +"' ";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet res = st.executeQuery(sql);
            if (res.next()) {
                if( res.getString("total") == null){
                    this.lbl_pengeluaran.setText(main.Rupiah.convertToRupiah(0));
                } else {
                   this.lbl_pengeluaran.setText(main.Rupiah.convertToRupiah(Integer.parseInt(res.getString("total")))); 
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "SALAH "+e);
        }
    }
    
    public static ChartPanel framePieChart;
    
    public void pieChart() {
        try {
            String sql = "SELECT MONTH(`tgl_transaksi`) AS bulan, YEAR(`tgl_transaksi`) AS tahun, SUM(harga_diskon) AS `total` from `tr_jual` GROUP BY MONTH(`tgl_transaksi`);";
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
            JFreeChart chartPie = ChartFactory.createPieChart3D("Pendapatan Perbulan",
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

        btn_profil = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_produk = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_karyawan = new javax.swing.JButton();
        btn_member = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        lbl_nama = new javax.swing.JLabel();
        lbl_tgl = new javax.swing.JLabel();
        lbl_pendapatan = new javax.swing.JLabel();
        lbl_pengeluaran = new javax.swing.JLabel();
        panel_chart = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_profil.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_profil.setBorder(null);
        btn_profil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_profilActionPerformed(evt);
            }
        });
        getContentPane().add(btn_profil, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 10, 100, 90));

        btn_transaksi.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_transaksi.setBorder(null);
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 480, 190, 40));

        btn_produk.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_produk.setBorder(null);
        btn_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_produkActionPerformed(evt);
            }
        });
        getContentPane().add(btn_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 160, 40));

        btn_supplier.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_supplier.setBorder(null);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 170, 40));

        btn_karyawan.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_karyawan.setBorder(null);
        btn_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 180, 50));

        btn_member.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_member.setBorder(null);
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memberActionPerformed(evt);
            }
        });
        getContentPane().add(btn_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 160, 40));

        btn_laporan.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_laporan.setBorder(null);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 150, 40));

        btn_keluar.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_keluar.setBorder(null);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, 150, 40));

        lbl_nama.setBackground(new java.awt.Color(255, 255, 255));
        lbl_nama.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        lbl_nama.setForeground(new java.awt.Color(84, 153, 84));
        lbl_nama.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 70, 170, 30));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 25)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(84, 153, 58));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 150, 300, 40));

        lbl_pendapatan.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_pendapatan.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lbl_pendapatan.setForeground(new java.awt.Color(84, 153, 58));
        lbl_pendapatan.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pendapatan.setText("jLabel2");
        getContentPane().add(lbl_pendapatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, 300, 70));

        lbl_pengeluaran.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_pengeluaran.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        lbl_pengeluaran.setForeground(new java.awt.Color(84, 153, 58));
        lbl_pengeluaran.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_pengeluaran.setText("jLabel3");
        getContentPane().add(lbl_pengeluaran, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 500, 300, 80));
        getContentPane().add(panel_chart, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 210, 570, 400));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/berandaO.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_profilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_profilActionPerformed
        new profilO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_profilActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        new menu_transaksiO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_transaksiActionPerformed

    private void btn_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_produkActionPerformed
        new produkO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_produkActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        new supplierO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_karyawanActionPerformed
        new karyawanO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_karyawanActionPerformed

    private void btn_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memberActionPerformed
        new memberO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_memberActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        new menu_laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
         if (JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?", "PERINGATAN",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { 
             new main.landing_page().setVisible(true);
                     this.dispose();
            } else {
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

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
            java.util.logging.Logger.getLogger(dashboardO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(dashboardO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(dashboardO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(dashboardO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new dashboardO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_karyawan;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_produk;
    private javax.swing.JButton btn_profil;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel lbl_nama;
    private javax.swing.JLabel lbl_pendapatan;
    private javax.swing.JLabel lbl_pengeluaran;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JPanel panel_chart;
    // End of variables declaration//GEN-END:variables
}
