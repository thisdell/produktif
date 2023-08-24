/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package owner;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.Rupiah;
import main.koneksi;

/**
 *
 * @author LENOVO
 */
public class tr_jualO extends javax.swing.JFrame {

    /**
     * Creates new form tr_jualO
     */
    public tr_jualO() {
        initComponents();
        setTitle("Transaksi Jual Owner");
        showTabel();
        id_tr_jual();
        tanggal();
        HeaderColumn();
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

//        String txt = getNamaHari(c.get(Calendar.DAY_OF_WEEK)) + ", " +
//                tanggal + " " + getNamaBulan(bulan) + " "  + tahun;
        this.lbl_tgl.setText(tgl);
    }

    public void showTabel() {
        DefaultTableModel tbl = new DefaultTableModel();
        tbl.addColumn("Id Transaksi Jual");
        tbl.addColumn("ID Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Harga");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Subtotal");
//        tbl.addColumn("Diskon");
//        tbl.addColumn("Total");
//        tbl.addColumn("Tgl Transaksi");

        tbl_trJual.setModel(tbl);
        try {
            String sql = "SELECT * FROM detail_tr_jual";
            Connection con = (Connection) koneksi.configDB();
            Statement st = con.createStatement();
            ResultSet res = st.executeQuery(sql);
            while (res.next()) {
                tbl.addRow(new Object[]{
                    res.getString("id_tr_jual"),
                    res.getString("id_produk"),
                    res.getString("nama_produk"),
                    Rupiah.convertToRupiah(Integer.parseInt(res.getString("harga"))),
                    res.getString("jumlah"),
                    Rupiah.convertToRupiah(Integer.parseInt(res.getString("total_harga")))
//                    res.getString("diskon"),
//                    Rupiah.convertToRupiah(Integer.parseInt(res.getString("total"))),
//                    res.getString("tgl_transaksi")
                });
                tbl_trJual.setModel(tbl);
//                HeaderColumn();

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(rootPane, "salah" + e);
        }
    }

    private void showData(String idDetailTrJual) {
        try {
            String query = "SELECT * FROM detail_tr_jual WHERE id_tr_jual = '" + idDetailTrJual + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement stat = conn.createStatement();
            ResultSet r = stat.executeQuery(query);

            if (r.next()) {
                this.txt_id_transaksi.setText(r.getString("id_tr_jual"));
                this.txt_id_produk.setText(r.getString("id_produk"));
                this.lbl_nama.setText(r.getString("nama_produk"));
                this.txt_jumlah.setText(r.getString("harga"));
                this.txt_jumlah.setText(r.getString("jumlah"));
                this.lbl_subtotal.setText(r.getString("total_harga"));
//                this.lbl_diskon.setText(r.getString("diskon"));
//                this.lbl_total.setText(r.getString("total"));
//                this.lbl_tgl.setText(r.getString("tgl_transaksi"));

            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        }
    }

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_trJual.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tbl_trJual.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tbl_trJual.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//        tbl_produk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        ((DefaultTableCellRenderer) tbl_trJual.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public void id_tr_jual() {
        try {
            String sql = "SELECT id_tr_jual FROM tr_jual order by id_tr_jual desc";
            Connection conn = (Connection) koneksi.configDB();
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(sql);

            if (rs.next()) {
                String idTJ = rs.getString("id_tr_jual").substring(3);
                String AN = "" + (Integer.parseInt(idTJ) + 1);
                String Nol = "";
                if (AN.length() == 1) {
                    Nol = "00";
                } else if (AN.length() == 2) {
                    Nol = "0";
                } else if (AN.length() == 3) {
                    Nol = "";

                }

                txt_id_transaksi.setText("TRJ" + Nol + AN);
            } else {
                txt_id_transaksi.setText("TRJ001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
    }

    public void clear() {
        //menghapus data setelah data telah berhasil disimpan

        lbl_subtotal.setText("");
        txt_id_member.setText("0");
        lbl_diskon.setText("0");
        lbl_total.setText("0");
        txt_tunai.setText("0");
        txt_kembalian.setText("0");
    }
    //menghapus data setelah data berhasil ditambahkan

    public void clear2() {
        txt_id_produk.setText("");
        lbl_nama.setText("");
        txt_jumlah.setText("");
    }

    private void load() {
        this.txt_id_transaksi.setText("");
        this.txt_id_produk.setText("");
        this.lbl_nama.setText("");
        this.txt_jumlah.setText("");
        this.lbl_subtotal.setText("");
        this.lbl_diskon.setText("");
        this.lbl_total.setText("");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_trJual = new javax.swing.JTable();
        txt_id_produk = new javax.swing.JTextField();
        txt_jumlah = new javax.swing.JTextField();
        txt_id_transaksi = new javax.swing.JTextField();
        txt_id_karyawan = new javax.swing.JTextField();
        lbl_rp = new javax.swing.JLabel();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        btn_kembali = new javax.swing.JButton();
        lbl_rp_subtotal = new javax.swing.JLabel();
        txt_id_member = new javax.swing.JTextField();
        lbl_rp_tunai = new javax.swing.JLabel();
        txt_tunai = new javax.swing.JTextField();
        lbl_rp_kembalian = new javax.swing.JLabel();
        txt_kembalian = new javax.swing.JTextField();
        btn_simpan = new javax.swing.JButton();
        btn_cetak = new javax.swing.JButton();
        lbl_nama = new javax.swing.JLabel();
        lbl_tgl = new javax.swing.JLabel();
        lbl_subtotal = new javax.swing.JLabel();
        lbl_diskon = new javax.swing.JLabel();
        lbl_total = new javax.swing.JLabel();
        lbl_harga = new javax.swing.JLabel();
        lbl_rp_harga = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbl_trJual.setModel(new javax.swing.table.DefaultTableModel(
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
        tbl_trJual.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_trJualMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_trJual);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 340, 1300, 230));

        txt_id_produk.setBackground(new java.awt.Color(0,0,0,0));
        txt_id_produk.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_id_produk.setForeground(new java.awt.Color(84, 153, 58));
        txt_id_produk.setBorder(null);
        txt_id_produk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_produkActionPerformed(evt);
            }
        });
        txt_id_produk.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_id_produkKeyPressed(evt);
            }
        });
        getContentPane().add(txt_id_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(282, 133, 220, 27));

        txt_jumlah.setBackground(new java.awt.Color(0,0,0,0));
        txt_jumlah.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_jumlah.setForeground(new java.awt.Color(84, 153, 58));
        txt_jumlah.setBorder(null);
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        getContentPane().add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 267, 120, 20));
        txt_jumlah.getAccessibleContext().setAccessibleDescription("");

        txt_id_transaksi.setBackground(new java.awt.Color(0,0,0,0));
        txt_id_transaksi.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txt_id_transaksi.setForeground(new java.awt.Color(255, 255, 255));
        txt_id_transaksi.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_id_transaksi.setBorder(null);
        txt_id_transaksi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_transaksiActionPerformed(evt);
            }
        });
        getContentPane().add(txt_id_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 52, 170, 30));

        txt_id_karyawan.setBackground(new java.awt.Color(0,0,0,0));
        txt_id_karyawan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        txt_id_karyawan.setForeground(new java.awt.Color(255, 255, 255));
        txt_id_karyawan.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txt_id_karyawan.setBorder(null);
        getContentPane().add(txt_id_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1170, 53, 160, 30));

        lbl_rp.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        lbl_rp.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp.setText("Rp");
        getContentPane().add(lbl_rp, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 180, 70, 50));

        btn_tambah.setBackground(new java.awt.Color(0,0,0,0));
        btn_tambah.setBorder(null);
        btn_tambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_tambahActionPerformed(evt);
            }
        });
        getContentPane().add(btn_tambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 220, 140, 30));

        btn_hapus.setBackground(new java.awt.Color(0,0,0,0));
        btn_hapus.setBorder(null);
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 140, 40));

        btn_kembali.setBackground(new java.awt.Color(0,0,0,0));
        btn_kembali.setBorder(null);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, 60));

        lbl_rp_subtotal.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_subtotal.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_subtotal.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_subtotal.setText("Rp");
        getContentPane().add(lbl_rp_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 594, -1, -1));

        txt_id_member.setBackground(new java.awt.Color(0,0,0,0));
        txt_id_member.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_id_member.setForeground(new java.awt.Color(84, 153, 58));
        txt_id_member.setBorder(null);
        txt_id_member.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_id_memberActionPerformed(evt);
            }
        });
        txt_id_member.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_id_memberKeyPressed(evt);
            }
        });
        getContentPane().add(txt_id_member, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 597, 130, 20));

        lbl_rp_tunai.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_tunai.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_tunai.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_tunai.setText("Rp");
        getContentPane().add(lbl_rp_tunai, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 594, -1, -1));

        txt_tunai.setBackground(new java.awt.Color(0,0,0,0));
        txt_tunai.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_tunai.setForeground(new java.awt.Color(84, 153, 58));
        txt_tunai.setBorder(null);
        txt_tunai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_tunaiActionPerformed(evt);
            }
        });
        getContentPane().add(txt_tunai, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 597, 140, 20));

        lbl_rp_kembalian.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_kembalian.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_kembalian.setText("Rp");
        getContentPane().add(lbl_rp_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 636, -1, -1));

        txt_kembalian.setBackground(new java.awt.Color(0,0,0,0));
        txt_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_kembalian.setForeground(new java.awt.Color(84, 153, 58));
        txt_kembalian.setBorder(null);
        getContentPane().add(txt_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(853, 639, 140, 20));

        btn_simpan.setBackground(new java.awt.Color(0,0,0,0));
        btn_simpan.setBorder(null);
        getContentPane().add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1030, 620, 130, 40));

        btn_cetak.setBackground(new java.awt.Color(0,0,0,0));
        btn_cetak.setBorder(null);
        getContentPane().add(btn_cetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 620, 120, 40));

        lbl_nama.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_nama.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_nama.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 179, 250, 20));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 150, 30));

        lbl_subtotal.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_subtotal.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 597, 140, 20));

        lbl_diskon.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_diskon.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 639, 80, 20));

        lbl_total.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        lbl_total.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_total, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 292, 50));

        lbl_harga.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_harga.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_harga.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 224, 90, 20));

        lbl_rp_harga.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_rp_harga.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_harga.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_harga.setText("Rp");
        getContentPane().add(lbl_rp_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(217, 217, 30, 30));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tr_jual.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1350, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_trJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_trJualMouseClicked
        this.showData(this.tbl_trJual.getValueAt(this.tbl_trJual.getSelectedRow(), 0).toString());
    }//GEN-LAST:event_tbl_trJualMouseClicked

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        try {
            String total = "SELECT harga FROM produk where id_produk = '" + txt_id_produk.getText() + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(total);

            if (rs.next()) {
                int harga = Integer.parseInt(rs.getString("harga"));

                // Ambil stok dari produk yang dipilih
                String stok_query = "SELECT stok FROM produk WHERE id_produk = '" + txt_id_produk.getText() + "'";
                ResultSet stok_rs = st.executeQuery(stok_query);
                if (stok_rs.next()) {
                    int stok = Integer.parseInt(stok_rs.getString("stok"));

                    // Cek apakah jumlah melebihi stok yang tersedia
                    int jumlah = 0;
                    double diskon = 0.0;
                    if (!txt_jumlah.getText().isEmpty()) {
                        jumlah = Integer.parseInt(txt_jumlah.getText());
                    }
                    if (!lbl_diskon.getText().isEmpty()) {
                        diskon = Double.parseDouble(lbl_diskon.getText());
                    }
                    if (jumlah > stok) {
                        JOptionPane.showMessageDialog(null, "Jumlah melebihi stok yang tersedia! Stok yang tersedia adalah " + stok);
                        txt_jumlah.setText("");
                        lbl_subtotal.setText("");
                        lbl_total.setText("");
                        return;
                    }

                    // Hitung subtotal dan total
                    this.lbl_subtotal.setText(String.valueOf(harga * jumlah));
                    this.lbl_total.setText(String.format("%.0f", (harga * jumlah) - (harga * jumlah * diskon)));
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }

    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_id_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_produkActionPerformed
//// mendapatkan nilai id_produk dari txt_id_produk
//        String id_produk = txt_id_produk.getText();
//
//        // membuat instance JDialog list_produk
//        list_produk list_produk = new list_produk(this, true);
//
//        // mengatur properti JDialog seperti judul dan ukuran
//        list_produk.setTitle("List Produk");
//        list_produk.setSize(400, 300);
//
//        // membuat instance JTextField di JDialog list_produk
////        JTextField txt_id_produk_dialog = new JTextField();
////        txt_id_produk_dialog.setBounds(10, 10, 150, 30);
//
//        // mengisi nilai JTextField di JDialog list_produk dengan nilai id_produk dari halaman tr_jualO
//        list_produk.setTextIdProduk(id_produk);
//
////        // menambahkan komponen JTextField ke JDialog list_produk
////        list_produk.add(txt_id_produk_dialog);
//
//        // memunculkan JDialog list_produk
//        list_produk.setVisible(true);

        if (txt_id_produk.getText().equals(" ")) {
            lbl_nama.setText("");
        } else {
            try {
                String sql = "SELECT nama_produk, harga FROM produk WHERE id_produk = '" + txt_id_produk.getText() + "'";
                Connection conn = (Connection) koneksi.configDB();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    lbl_nama.setText(rs.getString("nama_produk"));
                    lbl_harga.setText(rs.getString("harga"));
                } else {
                    lbl_nama.setText("");
                    lbl_harga.setText("");
                    JOptionPane.showMessageDialog(null, "ID produk tidak tersedia");
                    txt_id_produk.setText("");

                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
            }
        }

    }//GEN-LAST:event_txt_id_produkActionPerformed

    private void txt_id_produkKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_id_produkKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_produkKeyPressed

    private void txt_id_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_memberActionPerformed
        if (txt_id_member.getText().isEmpty()) {
            lbl_diskon.setText("");
        } else {
            try {
                double diskon;
                int harga = Integer.parseInt(lbl_harga.getText());
                String sql = "SELECT diskon FROM member WHERE id_member = '" + txt_id_member.getText() + "'";
                Connection conn = (Connection) koneksi.configDB();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    // Ambil nilai diskon dari hasil query
                    diskon = rs.getDouble("diskon");

                    // Tampilkan diskon di text field dalam format persentase
                    lbl_diskon.setText(String.format("%.0f%%", diskon * 100));
                    // Hitung total setelah diskon
                    double total = harga - (harga * diskon);
                    // Tampilkan total setelah diskon di text field
                    harga = Integer.parseInt(lbl_harga.getText());
                    int jumlah = Integer.parseInt(txt_jumlah.getText());
                    this.lbl_subtotal.setText(String.valueOf(harga * jumlah));
                    this.lbl_total.setText(String.format("%.0f", (harga * jumlah) - (harga * jumlah * diskon)));
                } else {
                    JOptionPane.showMessageDialog(null, "ID member tidak terdaftar");
                    txt_id_member.setText("");
                    lbl_diskon.setText("");
                    lbl_total.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
                txt_id_member.setText("");
                lbl_diskon.setText("");
            }
        }
    }//GEN-LAST:event_txt_id_memberActionPerformed

    private void txt_id_memberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_id_memberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_memberKeyPressed

    private void txt_tunaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tunaiActionPerformed
        //menghitung total pembayaran
        int total, tunai, kembalian;

        total = Integer.valueOf(lbl_total.getText());
        tunai = Integer.valueOf(txt_tunai.getText());

        //jika total lebih dari bayar makan akan muncul uang kurang
        if (total > tunai) {
            JOptionPane.showMessageDialog(null, "Uang yang Anda masukkan tidak cukup!");
            txt_tunai.setText("");
        } else {
            kembalian = tunai - total;
            txt_kembalian.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_txt_tunaiActionPerformed

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        new transaksiO().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        String id_transaksi = this.txt_id_transaksi.getText(),
                id_produk = this.txt_id_produk.getText(),
                nama_produk = this.lbl_nama.getText(),
                harga_produk = this.lbl_harga.getText(),
                jumlah = this.txt_jumlah.getText(),
                subtotal = this.lbl_subtotal.getText();

        if (this.txt_id_transaksi.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Id transaksi harus diisi!");
        } else if (this.txt_id_produk.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Id produk harus diisi!");
        } else if (this.lbl_nama.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Nama produk harus diisi!");
        } else if (this.lbl_harga.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Harga produk harus diisi!");
        } else if (this.txt_jumlah.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Jumlah harus diisi!");
        } else if (this.lbl_subtotal.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Subtotal harus diisi!");
        } else {

            try {
                String sql = "INSERT INTO detail_tr_jual (id_tr_jual, id_produk, nama_produk, harga, jumlah, total_harga) VALUES (?, ?, ?, ?, ?, ?)";
                Connection conn = (Connection) koneksi.configDB();
                PreparedStatement pst = conn.prepareStatement(sql);
                pst.setString(1, id_transaksi);
                pst.setString(2, id_produk);
                pst.setString(3, nama_produk);
                pst.setString(4, harga_produk);
                pst.setString(5, jumlah);
                pst.setString(6, subtotal);
                pst.executeUpdate();
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error : " + ex.getMessage());
            }
        }

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void txt_id_transaksiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_transaksiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_transaksiActionPerformed

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
            java.util.logging.Logger.getLogger(tr_jualO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tr_jualO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tr_jualO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tr_jualO.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tr_jualO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cetak;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_diskon;
    private javax.swing.JLabel lbl_harga;
    private javax.swing.JLabel lbl_nama;
    private javax.swing.JLabel lbl_rp;
    private javax.swing.JLabel lbl_rp_harga;
    private javax.swing.JLabel lbl_rp_kembalian;
    private javax.swing.JLabel lbl_rp_subtotal;
    private javax.swing.JLabel lbl_rp_tunai;
    private javax.swing.JLabel lbl_subtotal;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JLabel lbl_total;
    private javax.swing.JTable tbl_trJual;
    private javax.swing.JTextField txt_id_karyawan;
    private javax.swing.JTextField txt_id_member;
    private javax.swing.JTextField txt_id_produk;
    private javax.swing.JTextField txt_id_transaksi;
    private javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_kembalian;
    private javax.swing.JTextField txt_tunai;
    // End of variables declaration//GEN-END:variables
}
