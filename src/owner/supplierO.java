/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.koneksi;

/**
 *
 * @author LENOVO
 */
public class supplierO extends javax.swing.JFrame {

    DefaultTableModel tabModel;
    ResultSet RsSupplier = null;

    /**
     * Creates new form supplierO
     */
    public supplierO() {
        initComponents();
        setTitle("Data Supplier");
        showTabel();
        HeaderColumn();
        getID();
    }

    public void showTabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Supplier");
        tbl.addColumn("Nama Supplier");
        tbl.addColumn("Alamat");
        tbl.addColumn("No Telp");

        tbl_supplier.setModel(tbl);
        try {
            String sql = "SELECT * FROM supplier";
            Connection con = (Connection) koneksi.configDB();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("id_supplier"),
                    res.getString("nama_supplier"),
                    res.getString("alamat"),
                    res.getString("no_telp")
                });
                tbl_supplier.setModel(tbl);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah" + e);
        }
    }

    private void showData(String idSupplier) {
        try {
            String query = "SELECT * FROM supplier WHERE id_supplier = '" + idSupplier + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.txt_id.setText(r.getString("id_supplier"));
                this.txt_nama.setText(r.getString("nama_supplier"));
                this.txt_alamat.setText(r.getString("alamat"));
                this.txt_notelp.setText(r.getString("no_telp"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void getID() {
        try {
            String sql = "SELECT id_supplier FROM supplier order by id_supplier desc";
            Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String idS = rs.getString("id_supplier").substring(3);
                String AN = "" + (Integer.parseInt(idS) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "0";
                } else if (AN.length() == 3) {
                    Nol = "";
                }

                txt_id.setText("SUP" + Nol + AN);
            } else {
                txt_id.setText("SUP01");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
    }

    private void cariData(String key) {
        // mencari data 
        try {
            Object[] judul_kolom = {"Id Supplier", "Nama Supplier", "Alamat", "No. Telp"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tbl_supplier.setModel(tabModel);

            java.sql.Connection conn = (java.sql.Connection) koneksi.configDB();
            Statement stt = conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            //query pilih data dari produk
            RsSupplier = stt.executeQuery("SELECT * FROM supplier WHERE id_supplier LIKE '%" + key + "%' OR nama_supplier LIKE '%" + key + "%' OR alamat LIKE '%" + key + "%' OR no_telp LIKE '%" + key + "%'");
            while (RsSupplier.next()) {
                Object[] data = {
                    //data yang dapat dicari 
                    RsSupplier.getString("id_supplier"),
                    RsSupplier.getString("nama_supplier"),
                    RsSupplier.getString("alamat"),
                    RsSupplier.getString("no_telp")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tbl_supplier.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    private void load() {
        this.txt_id.setText("");
        this.txt_nama.setText("");
        this.txt_alamat.setText("");
        this.txt_notelp.setText("");
        this.showTabel();
//        id_supplier();
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
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_supplier = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_notelp = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();
        btn_beranda = new javax.swing.JButton();
        btn_produk = new javax.swing.JButton();
        btn_karyawan = new javax.swing.JButton();
        btn_member = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_supplier.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_supplierMouseClicked(evt);
            }
        });
        tbl_supplier.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_supplierKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_supplier);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(302, 467, 980, 210));

        txt_id.setBackground(new java.awt.Color(0,0,0,0));
        txt_id.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_id.setForeground(new java.awt.Color(255, 255, 255));
        txt_id.setBorder(null);
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 140, 270, 40));

        txt_nama.setBackground(new java.awt.Color(0,0,0,0));
        txt_nama.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(255, 255, 255));
        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 203, 270, 30));

        txt_alamat.setBackground(new java.awt.Color(0,0,0,0));
        txt_alamat.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_alamat.setForeground(new java.awt.Color(255, 255, 255));
        txt_alamat.setBorder(null);
        getContentPane().add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 143, 270, 30));

        txt_notelp.setBackground(new java.awt.Color(0,0,0,0));
        txt_notelp.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_notelp.setForeground(new java.awt.Color(255, 255, 255));
        txt_notelp.setBorder(null);
        txt_notelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_notelpActionPerformed(evt);
            }
        });
        getContentPane().add(txt_notelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 200, 270, 30));

        btn_tambah.setBackground(new java.awt.Color(0,0,0,0));
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 280, 150, 50));

        btn_edit.setBackground(new java.awt.Color(0,0,0,0));
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, 120, 50));

        btn_hapus.setBackground(new java.awt.Color(0,0,0,0));
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 280, 150, 50));

        btn_clean.setBackground(new java.awt.Color(0,0,0,0));
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 280, 200, 50));

        btn_beranda.setBackground(new java.awt.Color(0,0,0,0));
        btn_beranda.setBorder(null);
        btn_beranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_berandaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_beranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, 140, 40));

        btn_produk.setBackground(new java.awt.Color(0,0,0,0));
        btn_produk.setBorder(null);
        btn_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_produkActionPerformed(evt);
            }
        });
        getContentPane().add(btn_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 140, 40));

        btn_karyawan.setOpaque(false);
        btn_karyawan.setContentAreaFilled(false);
        btn_karyawan.setBackground(new java.awt.Color(0,0,0,0));
        btn_karyawan.setBorder(null);
        btn_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 160, 50));

        btn_member.setBackground(new java.awt.Color(0,0,0,0));
        btn_member.setBorder(null);
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memberActionPerformed(evt);
            }
        });
        getContentPane().add(btn_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 150, 40));

        btn_transaksi.setBackground(new java.awt.Color(0,0,0,0));
        btn_transaksi.setBorder(null);
        btn_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(btn_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 480, 160, 40));

        btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        btn_laporan.setBorder(null);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 150, 40));

        btn_keluar.setBackground(new java.awt.Color(0,0,0,0));
        btn_keluar.setBorder(null);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, 140, 40));

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cariKeyTyped(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 420, 170, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/supplierO.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_notelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_notelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_notelpActionPerformed

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        if (this.txt_notelp.getText().length() > 13) {
            JOptionPane.showMessageDialog(this, "No telepon tidak boleh lebih dari 13");
            return;
        }
        try {
            String id_supplier = this.txt_id.getText(),
                    nama_supplier = this.txt_nama.getText(),
                    alamat = this.txt_alamat.getText(),
                    no_telp = this.txt_notelp.getText();

            System.out.println("ID Supplier : " + id_supplier);
            String sql = String.format("UPDATE supplier SET nama_supplier = '%s',alamat = '%s', no_telp = '%s' WHERE id_supplier = '%s'", nama_supplier, alamat, no_telp, id_supplier);

            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            this.showData(id_supplier);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        this.load();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        String id_supplier = this.txt_id.getText(),
                nama_supplier = this.txt_nama.getText(),
                alamat = this.txt_alamat.getText(),
                no_telp = this.txt_notelp.getText();
        if (this.txt_notelp.getText().length() > 13) {
            JOptionPane.showMessageDialog(this, "No telp tidak boleh lebih dari 13");
        } else if (this.txt_id.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Id supplier harus diisi!");
        } else if (this.txt_nama.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama supplier harus diisi!");
        } else if (this.txt_alamat.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Alamat harus diisi!");
        } else if (this.txt_notelp.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nomor telepon harus diisi!");
        } else {

            try {
                String sql = "INSERT INTO supplier VALUES (?, ?, ?, ?)";
                Connection conn = (Connection) koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, id_supplier);
                pst.setString(2, nama_supplier);
                pst.setString(3, alamat);
                pst.setString(4, no_telp);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
            }
            this.load();
        }

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int status;
        status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data?",
                "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (status) {
            case JOptionPane.YES_OPTION: 
            try {
                String id_supplier = this.txt_id.getText(),
                        sql = "DELETE FROM supplier WHERE id_supplier = '" + id_supplier + "'";

                Connection conn = (Connection) koneksi.configDB();
                Statement stat = conn.createStatement();
                // mengecek apakah data pembeli berhasil terhapus atau tidak
                if (stat.executeUpdate(sql) > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    // mengupdate tabel
                    //                this.showData(idK);
                    this.showTabel();
                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus!");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        }
        this.load();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        this.txt_nama.setText("");
        this.txt_alamat.setText("");
        this.txt_notelp.setText("");
//        id_supplier();
//        tbl_supplier();
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_karyawanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_karyawanActionPerformed
        new karyawanO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_karyawanActionPerformed

    private void btn_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_produkActionPerformed
        new produkO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_produkActionPerformed

    private void btn_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_keluarActionPerformed
        if (JOptionPane.showConfirmDialog(null, "Yakin ingin keluar?", "PERINGATAN",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) { 
             new main.landing_page().setVisible(true);
                     this.dispose();
            } else {
        }
    }//GEN-LAST:event_btn_keluarActionPerformed

    private void btn_berandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_berandaActionPerformed
        new dashboardO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_berandaActionPerformed

    private void btn_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memberActionPerformed
        new memberO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_memberActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        new menu_transaksiO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_transaksiActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        new menu_laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        // cari data
        String key = txt_cari.getText();
        System.out.println(key);
        if (key != "") {
            cariData(key);
        } else {
            load();
        }
    }//GEN-LAST:event_txt_cariKeyReleased

    private void txt_cariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyTyped
        if ((txt_cari.getText() + evt.getKeyChar()).length() > 29) {
            evt.consume();
        }
    }//GEN-LAST:event_txt_cariKeyTyped

    private void tbl_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_supplierMouseClicked
        this.showData(this.tbl_supplier.getValueAt(this.tbl_supplier.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbl_supplierMouseClicked

    private void tbl_supplierKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_supplierKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.showData(this.tbl_supplier.getValueAt(this.tbl_supplier.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_tbl_supplierKeyPressed

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
            java.util.logging.Logger.getLogger(supplierO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(supplierO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(supplierO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(supplierO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new supplierO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_beranda;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_karyawan;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_produk;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_supplier;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_notelp;
    // End of variables declaration//GEN-END:variables
}
