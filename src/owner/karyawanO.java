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
public class karyawanO extends javax.swing.JFrame {

    DefaultTableModel tabModel;
    ResultSet RsKaryawan = null;

    /**
     * Creates new form karyawanO
     */
    public karyawanO() {
        initComponents();
        setTitle("Data Karyawan");
        showTabel();
        HeaderColumn();
    }
    
    public void showTabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Karyawan");
        tbl.addColumn("Username");
        tbl.addColumn("Nama Karyawan");
        tbl.addColumn("No Telp");
        tbl.addColumn("Alamat");
        tbl.addColumn("Level User");

        tbl_karyawan.setModel(tbl);
        try {
            String sql = "SELECT * FROM karyawan";
            Connection con = (Connection) koneksi.configDB();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("id_karyawan"),
                    res.getString("username"),
                    res.getString("nama_karyawan"),
                    res.getString("no_telp"),
                    res.getString("alamat"),
                    res.getString("level_user")});
                tbl_karyawan.setModel(tbl);

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah" + e);
        }
    }

    private void showData(String idKaryawan) {
        try {
            String query = "SELECT * FROM karyawan WHERE id_karyawan = '" + idKaryawan + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.txt_id.setText(r.getString("id_karyawan"));
                this.txt_username.setText(r.getString("username"));
                this.txt_nama.setText(r.getString("nama_karyawan"));
                this.txt_notelp.setText(r.getString("no_telp"));
                this.txt_alamat.setText(r.getString("alamat"));
                this.txt_password.setText(r.getString("password"));
                this.txt_leveluser.setSelectedItem(r.getString("level_user"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    private boolean isOwner(String idKy) {
        try {
            String sql = "SELECT level_user FROM karyawan WHERE id_karyawan = '" + idKy + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet res = stat.executeQuery(sql);
            System.out.println(sql);

            if (res.next()) {
                String level = res.getString("level_user");
                return level.equalsIgnoreCase("owner");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "gagal hapus data");
        }
        return false;
    }

    private void cariData(String key) {
        // mencari data 
        try {
            Object[] judul_kolom = {"Id Karyawan", "Username", "Nama Karyawan", "No. Telp", "Alamat"};
            tabModel = new DefaultTableModel(null, judul_kolom);
            tbl_karyawan.setModel(tabModel);

            java.sql.Connection conn = (java.sql.Connection) koneksi.configDB();
            Statement stt = conn.createStatement();
            tabModel.getDataVector().removeAllElements();
            //query pilih data dari produk
            RsKaryawan = stt.executeQuery("SELECT * FROM karyawan WHERE id_karyawan LIKE '%" + key + "%' OR username LIKE '%" + key + "%' OR nama_karyawan LIKE '%" + key + "%' OR no_telp LIKE '%" + key + "%' OR alamat LIKE '%" + key + "%' OR password LIKE '%" + key + "%' OR level_user LIKE '%" + key + "%'");
            while (RsKaryawan.next()) {
                Object[] data = {
                    //data yang dapat dicari 
                    RsKaryawan.getString("id_karyawan"),
                    RsKaryawan.getString("username"),
                    RsKaryawan.getString("nama_karyawan"),
                    RsKaryawan.getString("no_telp"),
                    RsKaryawan.getString("alamat"),
                    RsKaryawan.getString("password"),
                    RsKaryawan.getString("level_user")
                };
                tabModel.addRow(data);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        ((DefaultTableCellRenderer) tbl_karyawan.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    private void load() {
        this.txt_id.setText("");
        this.txt_username.setText("");
        this.txt_nama.setText("");
        this.txt_notelp.setText("");
        this.txt_alamat.setText("");
        this.txt_password.setText("");
        this.txt_leveluser.setSelectedItem("");
        this.showTabel();
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
        tbl_karyawan = new javax.swing.JTable();
        txt_id = new javax.swing.JTextField();
        txt_username = new javax.swing.JTextField();
        txt_nama = new javax.swing.JTextField();
        txt_notelp = new javax.swing.JTextField();
        txt_alamat = new javax.swing.JTextField();
        txt_password = new javax.swing.JTextField();
        txt_leveluser = new javax.swing.JComboBox<>();
        btn_daftar = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_clean = new javax.swing.JButton();
        btn_beranda = new javax.swing.JButton();
        btn_produk = new javax.swing.JButton();
        btn_supplier = new javax.swing.JButton();
        btn_member = new javax.swing.JButton();
        btn_transaksi = new javax.swing.JButton();
        btn_laporan = new javax.swing.JButton();
        btn_keluar = new javax.swing.JButton();
        txt_cari = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_karyawan.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_karyawan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_karyawanMouseClicked(evt);
            }
        });
        tbl_karyawan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_karyawanKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_karyawan);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 510, 990, 160));

        txt_id.setBackground(new java.awt.Color(0,0,0,0));
        txt_id.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_id.setForeground(new java.awt.Color(255, 255, 255));
        txt_id.setText("kkkk");
        txt_id.setBorder(null);
        txt_id.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_idActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 138, 230, 30));

        txt_username.setBackground(new java.awt.Color(0,0,0,0));
        txt_username.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_username.setForeground(new java.awt.Color(255, 255, 255));
        txt_username.setBorder(null);
        getContentPane().add(txt_username, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 184, 230, 30));

        txt_nama.setBackground(new java.awt.Color(0,0,0,0));
        txt_nama.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_nama.setForeground(new java.awt.Color(255, 255, 255));
        txt_nama.setBorder(null);
        txt_nama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_namaActionPerformed(evt);
            }
        });
        getContentPane().add(txt_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 230, 230, 30));

        txt_notelp.setBackground(new java.awt.Color(0,0,0,0));
        txt_notelp.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_notelp.setForeground(new java.awt.Color(255, 255, 255));
        txt_notelp.setBorder(null);
        txt_notelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_notelpActionPerformed(evt);
            }
        });
        getContentPane().add(txt_notelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 277, 230, 30));

        txt_alamat.setBackground(new java.awt.Color(0,0,0,0));
        txt_alamat.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_alamat.setForeground(new java.awt.Color(255, 255, 255));
        txt_alamat.setBorder(null);
        txt_alamat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_alamatActionPerformed(evt);
            }
        });
        getContentPane().add(txt_alamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 132, 230, 30));

        txt_password.setBackground(new java.awt.Color(0,0,0,0));
        txt_password.setFont(new java.awt.Font("Segoe UI", 1, 21)); // NOI18N
        txt_password.setForeground(new java.awt.Color(255, 255, 255));
        txt_password.setBorder(null);
        getContentPane().add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(1010, 180, 230, 30));

        txt_leveluser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Owner", "Karyawan" }));
        txt_leveluser.setBorder(null);
        getContentPane().add(txt_leveluser, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 230, 250, 30));

        btn_daftar.setBackground(new java.awt.Color(0,0,0,0));
        btn_daftar.setBorder(null);
        btn_daftar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_daftarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_daftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 340, 160, 40));

        btn_edit.setBackground(new java.awt.Color(0,0,0,0));
        btn_edit.setBorder(null);
        btn_edit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editActionPerformed(evt);
            }
        });
        getContentPane().add(btn_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 340, 130, 40));

        btn_hapus.setBackground(new java.awt.Color(0,0,0,0));
        btn_hapus.setBorder(null);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 340, 140, 40));

        btn_clean.setBackground(new java.awt.Color(0,0,0,0));
        btn_clean.setForeground(new java.awt.Color(255, 255, 255));
        btn_clean.setBorder(null);
        btn_clean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cleanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_clean, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 340, 190, 40));

        btn_beranda.setBackground(new java.awt.Color(0,0,0,0));
        btn_beranda.setBorder(null);
        btn_beranda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_berandaActionPerformed(evt);
            }
        });
        getContentPane().add(btn_beranda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 160, 40));

        btn_produk.setBackground(new java.awt.Color(0,0,0,0));
        btn_produk.setBorder(null);
        btn_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_produkActionPerformed(evt);
            }
        });
        getContentPane().add(btn_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, 140, 40));

        btn_supplier.setBackground(new java.awt.Color(0,0,0,0));
        btn_supplier.setBorder(null);
        btn_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 150, 40));

        btn_member.setBackground(new java.awt.Color(0,0,0,0));
        btn_member.setBorder(null);
        btn_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_memberActionPerformed(evt);
            }
        });
        getContentPane().add(btn_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, 150, 50));

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
        getContentPane().add(btn_laporan, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 543, 150, 50));

        btn_keluar.setBackground(new java.awt.Color(0,0,0,0));
        btn_keluar.setBorder(null);
        btn_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_keluarActionPerformed(evt);
            }
        });
        getContentPane().add(btn_keluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 610, 130, 40));

        txt_cari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cariKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_cariKeyTyped(evt);
            }
        });
        getContentPane().add(txt_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 465, 180, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/karyawanO.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_idActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_idActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_idActionPerformed

    private void txt_notelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_notelpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_notelpActionPerformed

    private void txt_alamatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_alamatActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_alamatActionPerformed

    private void txt_namaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_namaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_namaActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        int status;
        status = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data?", "Confirm", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
        String idK = this.txt_id.getText(),
                sql = "DELETE FROM karyawan WHERE id_karyawan = '" + idK + "'";
        if (this.isOwner(idK)) {
            System.out.println("LEVEL : " + this.isOwner(idK));
            JOptionPane.showMessageDialog(this, "Owner tidak bisa dihapus!");
            return;
        }

        switch (status) {
            case JOptionPane.YES_OPTION:
            try {
                Connection conn = (Connection) koneksi.configDB();
                Statement stat = conn.createStatement();
                if (stat.executeUpdate(sql) > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
                    this.txt_id.setText("");
                    this.txt_username.setText("");
                    this.txt_nama.setText("");
                    this.txt_notelp.setText("");
                    this.txt_alamat.setText("");
                    this.txt_password.setText("");
                    this.txt_leveluser.setSelectedItem("");

                } else {
                    JOptionPane.showMessageDialog(this, "Data gagal dihapus!");
                }

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }

            break;
        }
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void btn_daftarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_daftarActionPerformed
        String id_karyawan = this.txt_id.getText(),
                username = this.txt_username.getText(),
                nama_karyawan = this.txt_nama.getText(),
                no_telp = this.txt_notelp.getText(),
                alamat = this.txt_alamat.getText(),
                password = this.txt_password.getText(),
                level_user = this.txt_leveluser.getSelectedItem().toString();
        if (this.txt_notelp.getText().length() > 13) {
            JOptionPane.showMessageDialog(this, "No telp tidak boleh lebih dari 13");
        } else if (txt_id.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Id karyawan harus diisi!");
        } else if (this.txt_username.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Username harus diisi!");
        } else if (this.txt_nama.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama karyawan harus diisi!");
        } else if (txt_notelp.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nomor telepon harus diisi!");
        } else if (this.txt_alamat.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Alamat harus diisi!");
        } else if (txt_password.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Password harus diisi!");
        } else if (this.txt_leveluser.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(this, "Level user harus diisi!");
        } else {

            try {
                String sql = "INSERT INTO karyawan VALUES (?, ?, ?, ?, ?, ?, ?)";
                Connection conn = (Connection) koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, id_karyawan);
                pst.setString(2, username);
                pst.setString(3, nama_karyawan);
                pst.setString(4, no_telp);
                pst.setString(5, alamat);
                pst.setString(6, password);
                pst.setString(7, level_user);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "ID Karyawan atau nama karyawan sudah terdaftar!");
            }
            this.load();
    }//GEN-LAST:event_btn_daftarActionPerformed
    }
    private void btn_editActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editActionPerformed
        if (this.txt_notelp.getText().length() > 13) {
            JOptionPane.showMessageDialog(this, "No telepon tidak boleh lebih dari 13");
            return;
        }
        try {
            String idK = this.txt_id.getText(),
                    usernameK = this.txt_username.getText(),
                    namaK = this.txt_nama.getText(),
                    notelpK = this.txt_notelp.getText(),
                    alamatK = this.txt_alamat.getText(),
                    passwordK = this.txt_password.getText(),
                    leveluserK = this.txt_leveluser.getSelectedItem().toString(),
                    sql = String.format("UPDATE karyawan SET username = '%s', nama_karyawan = '%s', no_telp = '%s', alamat = '%s', password = '%s', level_user = '%s' WHERE id_karyawan = '%s'", usernameK, passwordK, namaK, notelpK, alamatK, leveluserK, idK);

            System.out.println("ID Karyawan : " + idK);

            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            stat.executeUpdate(sql);
            JOptionPane.showMessageDialog(this, "Data berhasil diedit!");
            this.showData(idK);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        this.load();
    }//GEN-LAST:event_btn_editActionPerformed

    private void btn_cleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cleanActionPerformed
        this.txt_id.setText("");
        this.txt_username.setText("");
        this.txt_nama.setText("");
        this.txt_notelp.setText("");
        this.txt_alamat.setText("");
        this.txt_leveluser.setSelectedItem("");
        this.txt_password.setText("");
//        id_karyawan();
//        tabelKaryawan();
    }//GEN-LAST:event_btn_cleanActionPerformed

    private void btn_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_produkActionPerformed
        new produkO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_produkActionPerformed

    private void btn_laporanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_laporanActionPerformed
        new menu_laporan().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_laporanActionPerformed

    private void btn_berandaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_berandaActionPerformed
        new dashboardO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_berandaActionPerformed

    private void btn_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_supplierActionPerformed
        new supplierO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_supplierActionPerformed

    private void btn_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_memberActionPerformed
        new memberO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_memberActionPerformed

    private void btn_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transaksiActionPerformed
        new menu_transaksiO().setVisible(true);
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

    private void tbl_karyawanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_karyawanMouseClicked
        this.showData(this.tbl_karyawan.getValueAt(this.tbl_karyawan.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbl_karyawanMouseClicked

    private void tbl_karyawanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_karyawanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            this.showData(this.tbl_karyawan.getValueAt(this.tbl_karyawan.getSelectedRow(), 0).toString());
        }
    }//GEN-LAST:event_tbl_karyawanKeyPressed

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
            java.util.logging.Logger.getLogger(karyawanO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(karyawanO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(karyawanO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(karyawanO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new karyawanO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_beranda;
    private javax.swing.JButton btn_clean;
    private javax.swing.JButton btn_daftar;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_keluar;
    private javax.swing.JButton btn_laporan;
    private javax.swing.JButton btn_member;
    private javax.swing.JButton btn_produk;
    private javax.swing.JButton btn_supplier;
    private javax.swing.JButton btn_transaksi;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tbl_karyawan;
    private javax.swing.JTextField txt_alamat;
    private javax.swing.JTextField txt_cari;
    private javax.swing.JTextField txt_id;
    private javax.swing.JComboBox<String> txt_leveluser;
    private javax.swing.JTextField txt_nama;
    private javax.swing.JTextField txt_notelp;
    private javax.swing.JTextField txt_password;
    private javax.swing.JTextField txt_username;
    // End of variables declaration//GEN-END:variables
}
