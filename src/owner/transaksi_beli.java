/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.koneksi;
import main.login;
import static main.login.namaUser;
//import static owner.transaksi_jual.lbl_harga;
//import static owner.transaksi_jual.lbl_nama;
//import static owner.transaksi_jual.txt_id_produk;
//import static owner.transaksi_jual.txt_jumlah;

/**
 *
 * @author LENOVO
 */
public class transaksi_beli extends javax.swing.JFrame {

    private String id_supplier, jenis_brg, nama_brg;
    private Object tabelTr;
    private int harga, jumlah, total_harga;

    /**
     * Creates new form transaksi_beli
     */
    public transaksi_beli() {
        initComponents();
        setTitle("Transaksi Beli");
        tanggal();
        showTabel();
        HeaderColumn();
        id_karyawan(login.namaUser);
        id_tr_beli();
    }

    public void tanggal() {
        long millis = System.currentTimeMillis();
        Date date = new java.sql.Date(millis);
        System.out.println(date);
        String tgl = date.toString();

        Calendar c = java.util.Calendar.getInstance();
        int tanggal = c.get(Calendar.DAY_OF_MONTH),
                bulan = c.get(Calendar.MONTH),
                tahun = c.get(Calendar.YEAR);

        this.lbl_tgl.setText(tgl);
    }

    public void pilihDashboard() {
        String Username = login.getUsernamee();
        String Password = login.getPassword();
        System.out.println("INI USERNAME ...." + Username);
        System.out.println(Password);
        try {
            String sql = "SELECT * FROM karyawan WHERE username=" + "'" + Username + "'" + " AND password='" + Password + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement s = conn.createStatement();
            ResultSet r = s.executeQuery(sql);
            System.out.println("Koneksi Berhasil!!");

            if (r.next()) {
                namaUser = r.getString("username");
//                JOptionPane.showMessageDialog(null, "Login berhasil");
                if (r.getString("level_user").equalsIgnoreCase("owner")) {
                    System.out.println("Owner");
                    new menu_transaksiO().setVisible(true);
                    this.dispose();
                } else {
                    new karyawan.menu_transaksiKr().setVisible(true);
                    System.out.println("Karyawan");
                    this.dispose();
                }

            } else {
                System.out.println("GAGAL KE DASHBOARD");
            }
        } catch (Exception e) {
            System.out.println("GAGAL KE DASHBOARD");

        }

    }

    private DefaultTableModel tbl;

    public void showTabel() {

        tbl = new DefaultTableModel() {
            public boolean isCellEditable(int row, int col) {
                if (col == 3) { //columnIndex: the column you want to make it editable
                    return true;
                } else {
                    return false;
                }
            }
        };

        tbl.addColumn("Id Transaksi Beli");
        tbl.addColumn("Id Supplier");
        tbl.addColumn("Jenis Barang");
        tbl.addColumn("Nama Barang");
        tbl.addColumn("Harga");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Total");

        tbl_trBeli.setModel(tbl);

    }

    public void showData() {
        int row = this.tbl_trBeli.getSelectedRow();
        // mendapatkan data menu
        this.id_supplier = this.tbl_trBeli.getValueAt(row, 1).toString();
        this.jenis_brg = this.tbl_trBeli.getValueAt(row, 2).toString();
        this.nama_brg = this.tbl_trBeli.getValueAt(row, 3).toString();
        this.jumlah = Integer.parseInt(this.tbl_trBeli.getValueAt(row, 5).toString());
        this.harga = Integer.parseInt(this.tbl_trBeli.getValueAt(row, 4).toString());
//        this.total_harga = Integer.parseInt(this.tbl_trBeli.getValueAt(row, 6).toString());

        // menampilkan data menu
        this.txt_id_supplier.setText(this.id_supplier);
        this.txt_jenis_brg.setSelectedItem(this.jenis_brg);
        this.txt_nama_brg.setText(this.nama_brg);
        this.txt_jumlah.setText(Integer.toString(this.jumlah));
        this.txt_harga_brg.setText(Integer.toString(this.harga));
//        this.lbl_total_harga.setText(Integer.toString(this.total_harga));

    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_trBeli.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tbl_trBeli.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        tbl_trBeli.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
//        tbl_produk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        ((DefaultTableCellRenderer) tbl_trBeli.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    private void id_karyawan(String username) {
        try {
            String query = "SELECT * FROM karyawan WHERE username = '" + username + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.lbl_id_karyawan.setText(r.getString("id_karyawan"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void id_tr_beli() {
        try {
            String sql = "SELECT id_tr_beli FROM tr_beli ORDER by id_tr_beli DESC";
            Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String idTB = rs.getString("id_tr_beli").substring(3);
                String AN = "" + (Integer.parseInt(idTB) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "00";
                } else if (AN.length() == 2) {
                    Nol = "0";
                } else if (AN.length() == 3) {
                    Nol = "";
                }

                lbl_id_transaksi.setText("TRB" + Nol + AN);
            } else {
                lbl_id_transaksi.setText("TRB001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
    }

    public int getTotalJumlahBarang() {
        int ttlJumlah = 0;
        for (int i = 0; i < this.tbl_trBeli.getRowCount(); i++) {
            ttlJumlah += Integer.parseInt(this.tbl_trBeli.getValueAt(i, 5).toString());
        }

        return ttlJumlah;
    }

    public void totalBiaya() {
        int jumlahBaris = tbl_trBeli.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tbl_trBeli.getValueAt(i, 5).toString());
            hargaBarang = Integer.parseInt(tbl_trBeli.getValueAt(i, 4).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }

        //format rupiah pada table
        String idr1 = String.valueOf(totalBiaya);
        lbl_total_harga.setText(idr1);
        this.totalhargadiambil = totalBiaya;
        totalhargabrg.setText("" + totalBiaya);
        totalhargabrg.setVisible(false);
    }

    public int totalhargadiambil;

    public void tambahTransaksi() {
        int jumlah, harga, total;

        jumlah = Integer.valueOf(txt_jumlah.getText());
        harga = Integer.valueOf(txt_harga_brg.getText());
        total = jumlah * harga;

        lbl_total_harga.setText(String.valueOf(total));

        load();
        totalBiaya();
        clear2();
        txt_id_supplier.requestFocus();
    }

    public void clear() {
        //menghapus data setelah data telah berhasil disimpan
        txt_id_supplier.setText("");
        txt_jenis_brg.setSelectedItem("");
        txt_nama_brg.setText("");
        txt_harga_brg.setText("");
        txt_jumlah.setText("");
        lbl_total_harga.setText("");
    }

    public void clear2() {
        //menghapus data setelah data telah berhasil ditambah
        txt_jenis_brg.setSelectedItem("");
        txt_nama_brg.setText("");
        txt_harga_brg.setText("");
        txt_jumlah.setText("");
    }

    private void kosong() {
        DefaultTableModel model = (DefaultTableModel) tbl_trBeli.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void load() {
        DefaultTableModel model = (DefaultTableModel) tbl_trBeli.getModel();
        model.addRow(new Object[]{
            this.lbl_id_transaksi.getText(),
            this.txt_id_supplier.getText(),
            this.txt_jenis_brg.getSelectedItem(),
            this.txt_nama_brg.getText(),
            this.txt_harga_brg.getText(),
            this.txt_jumlah.getText(),
            this.lbl_total_harga.getText()});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        totalbarang = new javax.swing.JTextField();
        totalhargabrg = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_trBeli = new javax.swing.JTable();
        btn_kembali = new javax.swing.JButton();
        lbl_tgl = new javax.swing.JLabel();
        lbl_id_transaksi = new javax.swing.JLabel();
        lbl_id_karyawan = new javax.swing.JLabel();
        txt_id_supplier = new javax.swing.JTextField();
        txt_jenis_brg = new javax.swing.JComboBox<>();
        txt_nama_brg = new javax.swing.JTextField();
        txt_harga_brg = new javax.swing.JTextField();
        lbl_rp = new javax.swing.JLabel();
        txt_jumlah = new javax.swing.JTextField();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_cari_supplier = new javax.swing.JButton();
        lbl_rp1 = new javax.swing.JLabel();
        lbl_total_harga = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        lbl_pcs = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        totalbarang.setText("jTextField1");

        totalhargabrg.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_trBeli.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_trBeli.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_trBeliMouseClicked(evt);
            }
        });
        tbl_trBeli.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_trBeliKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_trBeli);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, 1300, 210));

        btn_kembali.setBackground(new java.awt.Color(0,0,0,0));
        btn_kembali.setBorder(null);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 70, 50));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 150, 30));

        lbl_id_transaksi.setBackground(new java.awt.Color(0,0,0,0));
        lbl_id_transaksi.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_id_transaksi.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id_transaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_id_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 50, 140, 30));

        lbl_id_karyawan.setBackground(new java.awt.Color(0,0,0,0));
        lbl_id_karyawan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_id_karyawan.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id_karyawan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_id_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 50, 140, 30));

        txt_id_supplier.setBackground(new java.awt.Color(0,0,0,0)
        );
        txt_id_supplier.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_id_supplier.setForeground(new java.awt.Color(84, 153, 58));
        txt_id_supplier.setBorder(null);
        txt_id_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 130, 240, 30));

        txt_jenis_brg.setBackground(new java.awt.Color(0,0,0,0)
        );
        txt_jenis_brg.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_jenis_brg.setForeground(new java.awt.Color(84, 153, 58));
        txt_jenis_brg.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Varian", "Kemasan", " " }));
        txt_jenis_brg.setBorder(null);
        txt_jenis_brg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jenis_brgActionPerformed(evt);
            }
        });
        getContentPane().add(txt_jenis_brg, new org.netbeans.lib.awtextra.AbsoluteConstraints(215, 174, 280, 30));

        txt_nama_brg.setBackground(new java.awt.Color(0,0,0,0)
        );
        txt_nama_brg.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_nama_brg.setForeground(new java.awt.Color(84, 153, 58));
        txt_nama_brg.setBorder(null);
        getContentPane().add(txt_nama_brg, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 218, 280, 30));

        txt_harga_brg.setBackground(new java.awt.Color(0,0,0,0)
        );
        txt_harga_brg.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_harga_brg.setForeground(new java.awt.Color(84, 153, 58));
        txt_harga_brg.setBorder(null);
        getContentPane().add(txt_harga_brg, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 268, 90, -1));

        lbl_rp.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_rp.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp.setText("Rp");
        getContentPane().add(lbl_rp, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, 30, 20));

        txt_jumlah.setBackground(new java.awt.Color(0,0,0,0)

        );
        txt_jumlah.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_jumlah.setForeground(new java.awt.Color(84, 153, 0));
        txt_jumlah.setBorder(null);
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        getContentPane().add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, 60, 30));

        btn_tambah.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_tambah.setBorder(null);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 130, 40));

        btn_hapus.setBackground(new java.awt.Color(0,0,0,0));
        btn_hapus.setBorder(null);
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 310, 130, 30));

        btn_cari_supplier.setBackground(new java.awt.Color(0,0,0,0));
        btn_cari_supplier.setBorder(null);
        btn_cari_supplier.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cari_supplierActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cari_supplier, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 40, 30));

        lbl_rp1.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp1.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        lbl_rp1.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp1.setText("Rp");
        getContentPane().add(lbl_rp1, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 210, 70, 50));

        lbl_total_harga.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_total_harga.setFont(new java.awt.Font("Segoe UI", 1, 43)); // NOI18N
        lbl_total_harga.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_total_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 208, 310, 60));

        btn_simpan.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_simpan.setBorder(null);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1190, 620, 130, 40));

        lbl_pcs.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_pcs.setForeground(new java.awt.Color(84, 153, 58));
        lbl_pcs.setText("Pcs");
        getContentPane().add(lbl_pcs, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 310, -1, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tr_beli.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        if (txt_id_supplier.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id supplier!");
                return;
            }
        if (txt_nama_brg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi nama barang!");
                return;
            }
        if (txt_harga_brg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id harga barang!");
                return;
            }
        if (txt_jumlah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id jumlah!");
                return;
            }
        tambahTransaksi();
    }//GEN-LAST:event_btn_tambahActionPerformed

    private void tbl_trBeliMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_trBeliMouseClicked
        this.showData();
    }//GEN-LAST:event_tbl_trBeliMouseClicked

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        if (txt_id_supplier.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id supplier!");
                return;
            }
        if (txt_nama_brg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi nama barang!");
                return;
            }
        if (txt_harga_brg.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id harga barang!");
                return;
            }
        if (txt_jumlah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi id jumlah!");
                return;
            }
        tambahTransaksi();
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_id_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_supplierActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_supplierActionPerformed

    private void txt_jenis_brgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jenis_brgActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jenis_brgActionPerformed

    private void btn_cari_supplierActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cari_supplierActionPerformed
        new list_supplier(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_btn_cari_supplierActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_trBeli.getModel();
        int row = tbl_trBeli.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        clear2();
    }//GEN-LAST:event_btn_hapusActionPerformed

    private void tbl_trBeliKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_trBeliKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int p = tbl_trBeli.getSelectedRow();

            String idS = tbl_trBeli.getValueAt(p, 1).toString();
            String jenis = tbl_trBeli.getValueAt(p, 2).toString();
            String nama = tbl_trBeli.getValueAt(p, 3).toString();
            String harga = tbl_trBeli.getValueAt(p, 4).toString();
            String jumlah = tbl_trBeli.getValueAt(p, 5).toString();
//            String total_harga = tbl_trBeli.getValueAt(p, 6).toString();

            txt_id_supplier.setText(idS);
            txt_jenis_brg.setSelectedItem(jenis);
            txt_nama_brg.setText(nama);
            txt_harga_brg.setText(harga);
            txt_jumlah.setText(jumlah);
//            lbl_total_harga.setText(total_harga);

        }
    }//GEN-LAST:event_tbl_trBeliKeyPressed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        String noTransaksi = lbl_id_transaksi.getText();
        String idSupplier = this.txt_id_supplier.getText();
        String idKaryawan = this.lbl_id_karyawan.getText();
        String tanggal = lbl_tgl.getText().toString().substring(0, 10);
        String jenis_brg = (String) this.txt_jenis_brg.getSelectedItem();
        String nama_brg = this.txt_nama_brg.getText();
        String harga = this.txt_harga_brg.getText();
        String jumlah = this.txt_jumlah.getText();
        String totalbrg = totalbarang.getText();
        String grandtotal = totalhargabrg.getText();

        try {
            String sql = "INSERT INTO `tr_beli`(`id_tr_beli`, `id_karyawan`, `id_supplier`, `total_item`, `total_harga`, `tgl_transaksi`) VALUES "
                    + "('" + noTransaksi + "','" + idKaryawan + "','" + idSupplier + "','" + this.getTotalJumlahBarang() + "','" + lbl_total_harga.getText() + "','" + tanggal + "')";
            java.sql.Connection con = (Connection) main.koneksi.configDB();
            java.sql.PreparedStatement pst = con.prepareStatement(sql);
            System.out.println("QUERYYY TRANSAKSI = " + sql);
            pst.executeUpdate();
            pst.close();
        } catch (Exception e) {
            System.out.println("simpan penjualan error " + e.getMessage());
        }

        try {
            Connection c = (Connection) main.koneksi.configDB();
            int baris = tbl_trBeli.getRowCount();

            for (int i = 0; i < baris; i++) {
                String sql = "INSERT INTO `detail_tr_beli`(`id_tr_beli`, `nama_barang`, `jenis_barang`, `harga`,`jumlah`, `total_harga`) VALUES "
                        + "('" + tbl_trBeli.getValueAt(i, 0) + "','" + tbl_trBeli.getValueAt(i, 3) + "','" + tbl_trBeli.getValueAt(i, 2) + "','" + tbl_trBeli.getValueAt(i, 4) + "','" + tbl_trBeli.getValueAt(i, 5) + "','" + tbl_trBeli.getValueAt(i, 6) + "')";
                System.out.println("SQL DETAIL TRANS = " + sql);
                PreparedStatement p = c.prepareStatement(sql);
                p.executeUpdate();
                p.close();
            }
            JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan");
        } catch (Exception e) {

        }
    }//GEN-LAST:event_btn_simpanActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        this.pilihDashboard();
    }//GEN-LAST:event_btn_kembaliActionPerformed

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
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(transaksi_beli.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new transaksi_beli().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari_supplier;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_id_karyawan;
    private javax.swing.JLabel lbl_id_transaksi;
    private javax.swing.JLabel lbl_pcs;
    private javax.swing.JLabel lbl_rp;
    private javax.swing.JLabel lbl_rp1;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JLabel lbl_total_harga;
    private javax.swing.JTable tbl_trBeli;
    private javax.swing.JTextField totalbarang;
    private javax.swing.JTextField totalhargabrg;
    public static javax.swing.JTextField txt_harga_brg;
    public static javax.swing.JTextField txt_id_supplier;
    public static javax.swing.JComboBox<String> txt_jenis_brg;
    public static javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_nama_brg;
    // End of variables declaration//GEN-END:variables
}
