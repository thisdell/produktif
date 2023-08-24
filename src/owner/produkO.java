/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.event.KeyEvent;
import main.koneksi;
import main.Rupiah;

/**
 *
 * @author LENOVO
 */
public class produkO extends javax.swing.JFrame {

    DefaultTableModel tabModel;
    ResultSet RsProduk = null;

    /**
     * Creates new form produkO
     */
    public produkO() {
        initComponents();
        setTitle("Data Produk");
        showTabel();
        HeaderColumn();
    }

    public void showTabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Harga");
        tbl.addColumn("Stok");

        tbl_produk.setModel(tbl);
        try {
            String sql = "SELECT * FROM produk";
            Connection con = (Connection) koneksi.configDB();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("id_produk"),
                    res.getString("nama_produk"),
                    Rupiah.convertToRupiah(Integer.parseInt(res.getString("harga"))),
                    res.getString("stok")
                });
                tbl_produk.setModel(tbl);
                HeaderColumn();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah" + e);
        }
    }

    private void showData(String idProduk) {
        try {
            String query = "SELECT * FROM produk WHERE id_produk = '" + idProduk + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.txt_id.setText(r.getString("id_produk"));
                this.txt_nama.setText(r.getString("nama_produk"));
                this.txt_harga.setText(r.getString("harga"));
                this.txt_stok.setText(r.getString("stok"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    private void cariData(String key) {
        // mencari data 
        try {
            Object[] judul_kolom = {"Id Produk", "Nama Produk", "Harga", "Stok"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tbl_produk.setModel(tabModel);

            java.sql.Connection conn = (java.sql.Connection) koneksi.configDB();
            Statement stt = conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            //query pilih data dari produk
            RsProduk = stt.executeQuery("SELECT * FROM produk WHERE id_produk LIKE '%" + key + "%' OR nama_produk LIKE '%" + key + "%' OR harga LIKE '%" + key + "%'"
                    + "OR stok LIKE '%" + key + "%'");
            while (RsProduk.next()) {
                Object[] data = {
                    //data yang dapat dicari 
                    RsProduk.getString("id_produk"),
                    RsProduk.getString("nama_produk"),
                    RsProduk.getString("harga"),
                    RsProduk.getString("stok"),};
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_produk.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);
        tbl_produk.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
//        tbl_produk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        ((DefaultTableCellRenderer) tbl_produk.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    private void load() {
        this.txt_id.setText("");
        this.txt_nama.setText("");
        this.txt_harga.setText("");
        this.txt_stok.setText("");
        this.showTabel();
//        id_produk();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_produk = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_harga = new javax.swing.JTextField();
        txt_stok = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();
        btn_beranda = new javax.swing.JButton();
        lbl_rp = new javax.swing.JLabel();
        btn_supplier = new javax.swing.JButton();
        btn_karyawan = new javax.swing.JButton();
        btn_member = new javax.swing.JButton();
        btn_taransaksi = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        lbl_rp2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_produk.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_produk.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_produkMouseClicked(evt);
            }
        });
        tbl_produk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_produkKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_produk);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 470, 990, 180));

        txt_id.setBackground(new java.awt.Color(0,0,0,0)
        );
        txt_id.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_id.setForeground(new java.awt.Color(255, 255, 255));
        txt_id.setBorder(null);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(548, 150, 260, 30));

        txt_nama.setBackground(new java.awt.Color(0,0,0,0));
        txt_nama.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(255, 255, 255));
        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(548, 208, 260, 30));

        txt_harga.setBackground(new java.awt.Color(0,0,0,0));
        txt_harga.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_harga.setForeground(new java.awt.Color(255, 255, 255));
        txt_harga.setBorder(null);
        txt_harga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_hargaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 150, 200, -1));

        txt_stok.setBackground(new java.awt.Color(0,0,0,0));
        txt_stok.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        txt_stok.setForeground(new java.awt.Color(255, 255, 255));
        txt_stok.setBorder(null);
        txt_stok.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_stokActionPerformed(evt);
            }
        });
        getContentPane().add(txt_stok, new org.netbeans.lib.awtextra.AbsoluteConstraints(1003, 208, 240, 30));

        btn_tambah.setBackground(new java.awt.Color(0,0,0,0));
        btn_tambah.setBorder(null);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 280, 170, 50));

        btn_edit.setBackground(new java.awt.Color(0,0,0,0));
        btn_edit.setForeground(new java.awt.Color(255, 255, 255));
        btn_edit.setBorder(null);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 280, 120, 50));

        btn_hapus.setBackground(new java.awt.Color(0,0,0,0));
        btn_hapus.setForeground(new java.awt.Color(255, 255, 255));
        btn_hapus.setBorder(null);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(820, 280, 140, 50));

        btn_clean.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_clean.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        btn_clean.setForeground(new java.awt.Color(255, 255, 255));
        btn_clean.setBorder(null);
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 280, 190, 50));

        btn_beranda.setBackground(new java.awt.Color(0,0,0,0));
        btn_beranda.setBorder(null);
        btn_beranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_berandaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_beranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, 140, 50));

        lbl_rp.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        lbl_rp.setForeground(new java.awt.Color(255, 255, 255));
        getContentPane().add(lbl_rp, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 147, 35, -1));

        btn_supplier.setBackground(new java.awt.Color(0,0,0,0));
        btn_supplier.setBorder(null);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 270, 150, 40));

        btn_karyawan.setBackground(new java.awt.Color(0,0,0,0));
        btn_karyawan.setBorder(null);
        btn_karyawan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_karyawanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 340, 160, 40));

        btn_member.setBackground(new java.awt.Color(0,0,0,0));
        btn_member.setBorder(null);
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memberActionPerformed(evt);
            }
        });
        getContentPane().add(btn_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 403, 150, 50));

        btn_taransaksi.setBackground(new java.awt.Color(0,0,0,0));
        btn_taransaksi.setBorder(null);
        btn_taransaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_taransaksiActionPerformed(evt);
            }
        });
        getContentPane().add(btn_taransaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 170, 50));

        btn_laporan.setBackground(new java.awt.Color(0,0,0,0));
        btn_laporan.setBorder(null);
        btn_laporan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_laporanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 160, 40));

        btn_keluar.setBackground(new java.awt.Color(0,0,0,0));
        btn_keluar.setBorder(null);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 600, 130, 50));

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });
        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cariKeyTyped(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 424, 170, 30));

        lbl_rp2.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_rp2.setFont(new java.awt.Font("Segoe UI", 1, 23)); // NOI18N
        lbl_rp2.setForeground(new java.awt.Color(255, 255, 255));
        lbl_rp2.setText("Rp");
        getContentPane().add(lbl_rp2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1002, 148, 30, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/produkO.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, -1, 740));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void txt_hargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_hargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_hargaActionPerformed

    private void txt_stokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_stokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_stokActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        String id_produk = this.txt_id.getText(),
                nama_produk = this.txt_nama.getText(),
                harga = this.txt_harga.getText(),
                stok = this.txt_stok.getText();
        if (this.txt_id.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Id produk harus diisi!");
        } else if (this.txt_nama.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama produk harus diisi!");
        } else if (this.txt_harga.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Harga produk harus diisi!");
        } else if (this.txt_stok.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Stok harus diisi!");
        } else {

            try {
                String sql = "INSERT INTO produk VALUES (?, ?, ?, ?)";
                Connection conn = (Connection) koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, id_produk);
                pst.setString(2, nama_produk);
                pst.setString(3, harga);
                pst.setString(4, stok);

                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "ID Produk atau nama produk sudah ada ");
//                JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
            }
            this.load();
    }//GEN-LAST:event_btn_tambahActionPerformed
    }

    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        try {
            String id_produk = this.txt_id.getText(),
                    nama_produk = this.txt_nama.getText(),
                    harga = this.txt_harga.getText(),
                    stok = this.txt_stok.getText();

            System.out.println("ID Produk : " + id_produk);
            String sql = String.format("UPDATE produk SET nama_produk = '%s', harga = '%s', stok = '%s' WHERE id_produk = '%s'", nama_produk, harga, stok, id_produk);

            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            this.showData(id_produk);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        this.load();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int status;
        status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data?",
                "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        switch (status) {
            case JOptionPane.YES_OPTION: 
            try {
                String id_produk = this.txt_id.getText(),
                        sql = "DELETE FROM produk WHERE id_produk = '" + id_produk + "'";

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
        this.txt_id.setText("");
        this.txt_nama.setText("");
        this.txt_harga.setText("");
        this.txt_stok.setText("");
//        id_produk();
//        tbl_produk();
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_berandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_berandaActionPerformed
        new dashboardO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_berandaActionPerformed

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

    private void btn_taransaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_taransaksiActionPerformed
        new menu_transaksiO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_taransaksiActionPerformed

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

    private void txt_cariKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cariKeyReleased
        //cari data yg akan dicari
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

    private void tbl_produkMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_produkMouseClicked
        this.showData(this.tbl_produk.getValueAt(this.tbl_produk.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbl_produkMouseClicked

    private void tbl_produkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_produkKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.showData(this.tbl_produk.getValueAt(this.tbl_produk.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_tbl_produkKeyPressed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_cariActionPerformed

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
            java.util.logging.Logger.getLogger(produkO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(produkO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(produkO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(produkO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new produkO().setVisible(true);
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
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JButton btn_taransaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbl_rp;
    private javax.swing.JLabel lbl_rp2;
    private javax.swing.JTable tbl_produk;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_harga;
    private javax.swing.JTextField txt_id;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_stok;
    // End of variables declaration//GEN-END:variables
}
