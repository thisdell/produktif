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
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import main.koneksi;
import main.login;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
//import static owner.tr_jual.lbl_harga;
//import static owner.tr_jual.lbl_nama;
//import static owner.tr_jual.txt_id_produk;
//import static owner.tr_jual.txt_jumlah;

/**
 *
 * @author LENOVO
 */
public class tr_jual extends javax.swing.JFrame {

    private String id_produk, nama_produk;
    private int harga, jumlah, total_harga;

    /**
     * Creates new form tr_jual
     */
    public tr_jual() {
        initComponents();
        setTitle("Transaksi Jual");
        tanggal();
        showTabel();
        id_tr_jual();
        id_karyawan(login.namaUser);
        HeaderColumn();
//        showData();
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

    public void id_tr_jual() {
        try {
            String sql = "SELECT id_tr_jual FROM tr_jual ORDER by id_tr_jual DESC";
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

                lbl_id_transaksi.setText("TRJ" + Nol + AN);
            } else {
                lbl_id_transaksi.setText("TRJ001");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
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

    public void HeaderColumn() {
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();

        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        tbl_trJual.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
        tbl_trJual.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tbl_trJual.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
//        tbl_produk.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);

        ((DefaultTableCellRenderer) tbl_trJual.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public int getTotalJumlahBarang() {
        int ttlJumlah = 0;
        for (int i = 0; i < this.tbl_trJual.getRowCount(); i++) {
            ttlJumlah += Integer.parseInt(this.tbl_trJual.getValueAt(i, 3).toString());
        }

        return ttlJumlah;
    }

    public static String namaUser;

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
        tbl.addColumn("Id Transaksi Jual");
        tbl.addColumn("ID Produk");
        tbl.addColumn("Nama Produk");
        tbl.addColumn("Jumlah");
        tbl.addColumn("Harga");
        tbl.addColumn("Total");
        tbl_trJual.setModel(tbl);
    }

    public void showData() {
        int row = this.tbl_trJual.getSelectedRow();
        // mendapatkan data menu
        this.id_produk = this.tbl_trJual.getValueAt(row, 1).toString();
        this.nama_produk = this.tbl_trJual.getValueAt(row, 2).toString();
        this.jumlah = Integer.parseInt(this.tbl_trJual.getValueAt(row, 3).toString());
        this.harga = Integer.parseInt(this.tbl_trJual.getValueAt(row, 4).toString());
        this.total_harga = Integer.parseInt(this.tbl_trJual.getValueAt(row, 5).toString());

        // menampilkan data menu
        this.txt_id_produk.setText(this.id_produk);
        this.lbl_nama.setText(this.nama_produk);
        this.txt_jumlah.setText(Integer.toString(this.jumlah));
        this.lbl_harga.setText(Integer.toString(this.harga));
        this.lbl_total_harga.setText(Integer.toString(this.total_harga));
    }

    public void totalJumlah() {
        int jumlahBaris = tbl_trJual.getRowCount();
        int hargaBarang = 0;
        int totalJumlah = 0;
        for (int i = 0; i < jumlahBaris; i++) {
            hargaBarang = Integer.parseInt(tbl_trJual.getValueAt(i, 4).toString());
            totalJumlah = totalJumlah + hargaBarang;//dapatkan harga barang
        }
        System.out.println("Barang yang dibeli = " + totalJumlah);
        System.out.println("harga barang = " + hargaBarang);
        totalbarang.setText("" + totalJumlah);
        totalbarang.setVisible(false);
    }

    public void totalBiaya() {
        int jumlahBaris = tbl_trJual.getRowCount();
        int totalBiaya = 0;
        int jumlahBarang, hargaBarang;
        for (int i = 0; i < jumlahBaris; i++) {
            jumlahBarang = Integer.parseInt(tbl_trJual.getValueAt(i, 3).toString());
            hargaBarang = Integer.parseInt(tbl_trJual.getValueAt(i, 4).toString());
            totalBiaya = totalBiaya + (jumlahBarang * hargaBarang);
        }

        //format rupiah pada table
        String idr1 = String.valueOf(totalBiaya);
        lbl_total_harga.setText(idr1);
        lbl_harga_diskon.setText(idr1);
        this.totalhargadiambil = totalBiaya;
        totalhargabrg.setText("" + totalBiaya);
        totalhargabrg.setVisible(false);
    }

    public int totalhargadiambil;

    private void kosong() {
        DefaultTableModel model = (DefaultTableModel) tbl_trJual.getModel();
        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    public void clear() {
        //menghapus data setelah data telah berhasil disimpan
        txt_id_member.setText("");
        lbl_harga_diskon.setText("");
        lbl_total_harga.setText("");
        txt_tunai.setText("");
        lbl_kembalian.setText("");
//        tbl_trJual();
    }

    public void clear2() {
        txt_id_produk.setText("");
        lbl_nama.setText("");
        lbl_harga.setText("");
        txt_jumlah.setText("");
    }

    public void tambahTransaksi() {
        int jumlah, harga, total;

        jumlah = Integer.valueOf(txt_jumlah.getText());
        harga = Integer.valueOf(lbl_harga.getText());
        total = jumlah * harga;

        lbl_total_harga.setText(String.valueOf(total));
        lbl_harga_diskon.setText(String.valueOf(total));
        System.out.println("TESTING");

        totalJumlah();
        load();
        totalBiaya();
        clear2();
        txt_id_produk.requestFocus();
    }

    private void cetakNota(Map parameter) throws SQLException {
        try {
            JasperDesign jasperDesign = JRXmlLoader.load("src\\report\\NotaJual.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
            JasperPrint jPrint = JasperFillManager.fillReport(jasperReport, parameter, main.koneksi.configDB());
            JasperViewer.viewReport(jPrint);

        } catch (JRException ex) {
        }
    }

    public void load() {
        DefaultTableModel model = (DefaultTableModel) tbl_trJual.getModel();
        model.addRow(new Object[]{
            this.lbl_id_transaksi.getText(),
            this.txt_id_produk.getText(),
            this.lbl_nama.getText(),
            this.txt_jumlah.getText(),
            this.lbl_harga.getText(),
            this.lbl_total_harga.getText(),
            this.txt_id_member.getText(),
            this.lbl_diskon5persen.getText()});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        totalhargabrg = new javax.swing.JTextField();
        totalbarang = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_trJual = new javax.swing.JTable();
        lbl_tgl = new javax.swing.JLabel();
        lbl_id_transaksi = new javax.swing.JLabel();
        lbl_id_karyawan = new javax.swing.JLabel();
        btn_kembali = new javax.swing.JButton();
        txt_id_produk = new javax.swing.JTextField();
        lbl_nama = new javax.swing.JLabel();
        lbl_harga = new javax.swing.JLabel();
        lbl_rp_harga = new javax.swing.JLabel();
        btn_cari = new javax.swing.JButton();
        btn_tambah = new javax.swing.JButton();
        btn_hapus = new javax.swing.JButton();
        txt_jumlah = new javax.swing.JTextField();
        lbl_total_harga = new javax.swing.JLabel();
        lbl_rp_subtotal = new javax.swing.JLabel();
        lbl_harga_diskon = new javax.swing.JLabel();
        lbl_rp = new javax.swing.JLabel();
        txt_id_member = new javax.swing.JTextField();
        lbl_diskon5persen = new javax.swing.JLabel();
        btn_simpan = new javax.swing.JButton();
        txt_tunai = new javax.swing.JTextField();
        lbl_kembalian = new javax.swing.JLabel();
        lbl_rp_kembalian = new javax.swing.JLabel();
        lbl_rp_tunai = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();

        totalhargabrg.setText("jTextField1");

        totalbarang.setText("jTextField1");

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
        tbl_trJual.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_trJualKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_trJual);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(22, 340, 1300, 230));

        lbl_tgl.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_tgl.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_tgl.setForeground(new java.awt.Color(255, 255, 255));
        lbl_tgl.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_tgl, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 50, 150, 30));

        lbl_id_transaksi.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_id_transaksi.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_id_transaksi.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id_transaksi.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_id_transaksi, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 50, 160, 30));

        lbl_id_karyawan.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_id_karyawan.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lbl_id_karyawan.setForeground(new java.awt.Color(255, 255, 255));
        lbl_id_karyawan.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        getContentPane().add(lbl_id_karyawan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 50, 170, 30));

        btn_kembali.setBackground(new java.awt.Color(0,0,0,0));
        btn_kembali.setBorder(null);
        btn_kembali.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_kembaliActionPerformed(evt);
            }
        });
        getContentPane().add(btn_kembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 90, 60));

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
        getContentPane().add(txt_id_produk, new org.netbeans.lib.awtextra.AbsoluteConstraints(262, 133, 200, 27));

        lbl_nama.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_nama.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_nama.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_nama, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 179, 250, 20));

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

        btn_cari.setBackground(new java.awt.Color(0,0,0,0)
        );
        btn_cari.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btn_cariMouseClicked(evt);
            }
        });
        btn_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cariActionPerformed(evt);
            }
        });
        getContentPane().add(btn_cari, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 133, 40, 30));

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
        btn_hapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_hapusActionPerformed(evt);
            }
        });
        getContentPane().add(btn_hapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 260, 140, 40));

        txt_jumlah.setBackground(new java.awt.Color(0,0,0,0));
        txt_jumlah.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        txt_jumlah.setForeground(new java.awt.Color(84, 153, 58));
        txt_jumlah.setBorder(null);
        txt_jumlah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_jumlahActionPerformed(evt);
            }
        });
        txt_jumlah.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_jumlahKeyReleased(evt);
            }
        });
        getContentPane().add(txt_jumlah, new org.netbeans.lib.awtextra.AbsoluteConstraints(218, 267, 120, 20));

        lbl_total_harga.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_total_harga.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_total_harga, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 597, 140, 20));

        lbl_rp_subtotal.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_subtotal.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_subtotal.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_subtotal.setText("Rp");
        getContentPane().add(lbl_rp_subtotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 594, -1, -1));

        lbl_harga_diskon.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_harga_diskon.setFont(new java.awt.Font("Segoe UI", 1, 43)); // NOI18N
        lbl_harga_diskon.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_harga_diskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 171, 280, 70));

        lbl_rp.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp.setFont(new java.awt.Font("Segoe UI", 1, 45)); // NOI18N
        lbl_rp.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp.setText("Rp");
        getContentPane().add(lbl_rp, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 180, 70, 50));

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

        lbl_diskon5persen.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_diskon5persen.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_diskon5persen.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_diskon5persen, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 640, 130, 20));

        btn_simpan.setBackground(new java.awt.Color(0,0,0,0));
        btn_simpan.setBorder(null);
        btn_simpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_simpanActionPerformed(evt);
            }
        });
        getContentPane().add(btn_simpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 610, 130, 40));

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

        lbl_kembalian.setBackground(new java.awt.Color(0,0,0,0)
        );
        lbl_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_kembalian.setForeground(new java.awt.Color(84, 153, 58));
        getContentPane().add(lbl_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(854, 639, 130, 20));

        lbl_rp_kembalian.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_kembalian.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_kembalian.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_kembalian.setText("Rp");
        getContentPane().add(lbl_rp_kembalian, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 636, -1, -1));

        lbl_rp_tunai.setBackground(new java.awt.Color(0,0,0,0));
        lbl_rp_tunai.setFont(new java.awt.Font("Segoe UI", 1, 17)); // NOI18N
        lbl_rp_tunai.setForeground(new java.awt.Color(84, 153, 58));
        lbl_rp_tunai.setText("Rp");
        getContentPane().add(lbl_rp_tunai, new org.netbeans.lib.awtextra.AbsoluteConstraints(822, 594, -1, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/tr_jual.png"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_kembaliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_kembaliActionPerformed
        pilihDashboard();
    }//GEN-LAST:event_btn_kembaliActionPerformed

    private void txt_id_produkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_produkActionPerformed
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

    private void txt_jumlahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_jumlahActionPerformed
        try {
            if (txt_id_produk.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Id produk harus diisi!");
                return;
            }
            if (txt_jumlah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi jumlah!");
                return;
            }
            String total = "SELECT   FROM produk where id_produk = '" + txt_id_produk.getText() + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(total);

            if (rs.next()) {
                int harga = Integer.parseInt(rs.getString("harga"));

//         Ambil stok dari produk yang dipilih
                String stok_query = "SELECT stok FROM produk WHERE id_produk = '" + txt_id_produk.getText() + "'";
                ResultSet stok_rs = st.executeQuery(stok_query);
                if (stok_rs.next()) {
                    int stok = Integer.parseInt(stok_rs.getString("stok"));

//         Cek apakah jumlah melebihi stok yang tersedia
                    int jumlah = 0;
                    double diskon = 0.0;
                    if (!txt_jumlah.getText().isEmpty()) {
                        jumlah = Integer.parseInt(txt_jumlah.getText());
                    }
                    if (jumlah > stok) {
                        JOptionPane.showMessageDialog(null, "Jumlah melebihi stok yang tersedia! Stok yang tersedia adalah " + stok);
                        txt_jumlah.setText("");
                        lbl_total_harga.setText("");
                        return;
                    }

                    tambahTransaksi();
                }

            }
            int Nilai = Integer.valueOf(lbl_harga_diskon.getText());

            if (Nilai <= 50000) {
                txt_id_member.setEnabled(false);
            } else {
                txt_id_member.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }
    }//GEN-LAST:event_txt_jumlahActionPerformed

    private void txt_jumlahKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyPressed

    }//GEN-LAST:event_txt_jumlahKeyPressed

    private void txt_jumlahKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_jumlahKeyReleased
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_jumlahKeyReleased

    private void btn_cariMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cariMouseClicked

    }//GEN-LAST:event_btn_cariMouseClicked

    private void btn_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cariActionPerformed
        new list_produk(this, rootPaneCheckingEnabled).setVisible(true);
    }//GEN-LAST:event_btn_cariActionPerformed

    private void btn_tambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_tambahActionPerformed
        try {
            if (txt_id_produk.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Id produk harus diisi!");
                return;
            }
            if (txt_jumlah.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Harap isi jumlah!");
                return;
            }
            String total = "SELECT harga FROM produk where id_produk = '" + txt_id_produk.getText() + "'";
            Connection conn = (Connection) koneksi.configDB();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(total);

            if (rs.next()) {
                int harga = Integer.parseInt(rs.getString("harga"));

//         Ambil stok dari produk yang dipilih
                String stok_query = "SELECT stok FROM produk WHERE id_produk = '" + txt_id_produk.getText() + "'";
                ResultSet stok_rs = st.executeQuery(stok_query);
                if (stok_rs.next()) {
                    int stok = Integer.parseInt(stok_rs.getString("stok"));

//         Cek apakah jumlah melebihi stok yang tersedia
                    int jumlah = 0;
                    double diskon = 0.0;
                    if (!txt_jumlah.getText().isEmpty()) {
                        jumlah = Integer.parseInt(txt_jumlah.getText());
                    }
                    if (jumlah > stok) {
                        JOptionPane.showMessageDialog(null, "Jumlah melebihi stok yang tersedia! Stok yang tersedia adalah " + stok);
                        txt_jumlah.setText("");
                        lbl_total_harga.setText("");
                        return;
                    }

                    tambahTransaksi();
                }

            }
            int Nilai = Integer.valueOf(lbl_harga_diskon.getText());

            if (Nilai <= 50000) {
                txt_id_member.setEnabled(false);
            } else {
                txt_id_member.setEnabled(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
        }

    }//GEN-LAST:event_btn_tambahActionPerformed

    private void btn_hapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_hapusActionPerformed
        DefaultTableModel model = (DefaultTableModel) tbl_trJual.getModel();
        int row = tbl_trJual.getSelectedRow();
        model.removeRow(row);
        totalBiaya();
        int Nilai = Integer.valueOf(lbl_harga_diskon.getText());

        if (Nilai <= 50000) {
            txt_id_member.setEnabled(false);
        } else {
            txt_id_member.setEnabled(true);
        }

    }//GEN-LAST:event_btn_hapusActionPerformed

    private void tbl_trJualMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_trJualMouseClicked
        this.showData();
    }//GEN-LAST:event_tbl_trJualMouseClicked

    private void tbl_trJualKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_trJualKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            int p = tbl_trJual.getSelectedRow();

            String idP = tbl_trJual.getValueAt(p, 1).toString();
            String nama = tbl_trJual.getValueAt(p, 2).toString();
            String harga = tbl_trJual.getValueAt(p, 3).toString();
            String jumlah = tbl_trJual.getValueAt(p, 4).toString();
            String total_harga = tbl_trJual.getValueAt(p, 5).toString();

            txt_id_produk.setText(idP);
            lbl_nama.setText(nama);
            lbl_harga.setText(harga);
            txt_jumlah.setText(jumlah);
            lbl_total_harga.setText(total_harga);

        }
    }//GEN-LAST:event_tbl_trJualKeyPressed

    private void txt_id_memberActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_id_memberActionPerformed
        if (txt_id_member.getText() == null || txt_id_member.getText().isEmpty()) {
            lbl_diskon5persen.setText("");
        } else {
            try {
                String sql = "SELECT * FROM member WHERE id_member = '" + txt_id_member.getText() + "'";
                Connection conn = (Connection) koneksi.configDB();
                Statement stm = conn.createStatement();
                ResultSet rs = stm.executeQuery(sql);
                if (rs.next()) {
                    // Tampilkan diskon di text field
                    lbl_diskon5persen.setText("5%");

                    // Hitung total setelah diskon
                    int total_harga = Integer.parseInt(lbl_total_harga.getText());
                    int diskon = 5;
                    int jumlah_diskon = (total_harga * diskon) / 100;
                    int harga_diskon = total_harga - (total_harga * diskon) / 100;

                    // Tampilkan total setelah diskon di text field
                    lbl_harga_diskon.setText(Integer.toString(harga_diskon));

                } else {
                    JOptionPane.showMessageDialog(null, "ID member tidak terdaftar");
                    txt_id_member.setText("");
                    lbl_diskon5persen.setText("");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "ERROR " + e.getMessage());
                txt_id_member.setText("");
                lbl_diskon5persen.setText("");
            }
        }
    }//GEN-LAST:event_txt_id_memberActionPerformed

    private void txt_id_memberKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_id_memberKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_id_memberKeyPressed

    private void btn_simpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_simpanActionPerformed
        //        String kembaliannyaa = lbl_kembalian.getText();
        String noTransaksi = lbl_id_transaksi.getText();
        txt_id_member.getText();
        String kodeM = txt_id_member.getText();
        String kodeK = lbl_id_karyawan.getText();
        String tanggal = lbl_tgl.getText().toString();
        System.out.println("TANGGAL AJA = " + tanggal);
        String totalbrg = totalbarang.getText();
        System.out.println("TOTAL BARANG = " + totalbrg);
        String checkBayar = txt_tunai.getText();
        int bayar = 0;

        int bayarpilih;

        String totalhargatext1 = lbl_total_harga.getText();
        String totalhargaclean1 = totalhargatext1.replaceAll("\\D+", "");
        int totalhargaint1 = Integer.parseInt(totalhargaclean1.substring(0, totalhargaclean1.length() - 2));

        if (lbl_harga_diskon.getText().equals("") || lbl_harga_diskon.getText().equals("0")) {
            bayarpilih = totalhargaint1;
        } else {
            bayarpilih = Integer.parseInt(lbl_harga_diskon.getText());

        }
        int hargafix1;

        String totalhargatext = lbl_total_harga.getText();
        String totalhargaclean = totalhargatext.replaceAll("\\D+", "");
        int totalhargaint = Integer.parseInt(totalhargaclean.substring(0, totalhargaclean.length() - 2));
        if (lbl_harga_diskon.getText().equals("0")) {
            hargafix1 = Integer.valueOf(totalhargaint);
        } else {
            hargafix1 = Integer.valueOf(lbl_harga_diskon.getText());
        }

        if (checkBayar.isEmpty()) {

            JOptionPane.showMessageDialog(this, " Harap isi Pembayaran");
        } else {
            bayar = Integer.parseInt(txt_tunai.getText().replace(".", "").trim());
        }

        if (bayar < hargafix1) {

            JOptionPane.showMessageDialog(this, "PEMBAYARAN KURANG");
        } else if (checkBayar.isEmpty()) {

            txt_tunai.setText("");
            JOptionPane.showMessageDialog(this, "Harap isi Pembayaran");

        } else {

            try {
                String sql = "INSERT INTO `tr_jual`(`id_tr_jual`, `id_karyawan`, `id_member`, `total_item`, `total_harga`, `harga_diskon`, `tunai`, `tgl_transaksi`) VALUES "
                        + "('" + noTransaksi + "','" + kodeK + "','" + kodeM + "','" + this.getTotalJumlahBarang() + "','" + lbl_total_harga.getText() + "','" + lbl_harga_diskon.getText() + "','" + txt_tunai.getText() + "','" + tanggal + "')";
                java.sql.Connection con = (Connection) main.koneksi.configDB();
                java.sql.PreparedStatement pst = con.prepareStatement(sql);
                System.out.println("QUERYYY TRANSAKSI = " + sql);
                System.out.println("HARGA DISKON = " + lbl_harga_diskon.getText());
                pst.executeUpdate();
                pst.close();
            } catch (Exception e) {
                System.out.println("simpan penjualan error " + e.getMessage());
            }

            try {
                Connection c = (Connection) main.koneksi.configDB();
                int baris = tbl_trJual.getRowCount();

                for (int i = 0; i < baris; i++) {
                    String sql = "INSERT INTO `detail_tr_jual`(`id_tr_jual`, `id_produk`, `nama_produk`, `jumlah`, `harga`, `total_harga`) VALUES "
                            + "('" + tbl_trJual.getValueAt(i, 0) + "','" + tbl_trJual.getValueAt(i, 1) + "','" + tbl_trJual.getValueAt(i, 2) + "','" + tbl_trJual.getValueAt(i, 3) + "','" + tbl_trJual.getValueAt(i, 4) + "','" + tbl_trJual.getValueAt(i, 5) + "')";
                    System.out.println("SQL DETAIL TRANS = " + sql);
                    PreparedStatement p = c.prepareStatement(sql);
                    p.executeUpdate();
                    p.close();
                }
                JOptionPane.showMessageDialog(this, "Transaksi berhasil disimpan");
            } catch (Exception e) {
                System.out.println("simpan penjualan rinci error" + e.getMessage());
            }

            kosong();
            clear();
            clear2();
            load();
            id_tr_jual();

            Map parameters = new HashMap();
            parameters.put("id_tr_jual", lbl_id_transaksi.getText());
            parameters.put("diskon", lbl_diskon5persen.getText());
            parameters.put("kembalian", lbl_kembalian.getText());
            try {
//                this.dispose();
                this.cetakNota(parameters);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e.getMessage() + " ERROR");
            }

            lbl_id_karyawan.setText(login.getIdK());
            lbl_harga_diskon.setText("");
            lbl_kembalian.setText("");
            lbl_total_harga.setText("");
            txt_id_produk.requestFocus();
            txt_tunai.setText(null);
        }

//        Map parameters = new HashMap();
//        parameters.put("id_tr_jual", tr_jual.lbl_id_transaksi.getText());
//        parameters.put("diskon", owner.tr_jual.lbl_diskon5persen.getText());
//        parameters.put("kembalian", owner.tr_jual.lbl_kembalian.getText());
//        try {
//            this.dispose();
//            this.cetakNota(parameters);
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e.getMessage() + " ERROR");
//        }

    }//GEN-LAST:event_btn_simpanActionPerformed

    private void txt_tunaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_tunaiActionPerformed
        //menghitung total pembayaran
        int total, tunai, kembalian;

        total = Integer.valueOf(lbl_harga_diskon.getText());
        tunai = Integer.valueOf(txt_tunai.getText());

        //jika total lebih dari bayar makan akan muncul uang kurang
        if (total > tunai) {
            JOptionPane.showMessageDialog(null, "Uang yang Anda masukkan tidak cukup!");
            txt_tunai.setText("");
        } else {
            kembalian = tunai - total;
            lbl_kembalian.setText(String.valueOf(kembalian));
        }
    }//GEN-LAST:event_txt_tunaiActionPerformed

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
            java.util.logging.Logger.getLogger(tr_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tr_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tr_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tr_jual.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tr_jual().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cari;
    private javax.swing.JButton btn_hapus;
    private javax.swing.JButton btn_kembali;
    private javax.swing.JButton btn_simpan;
    private javax.swing.JButton btn_tambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbl_diskon5persen;
    public static javax.swing.JLabel lbl_harga;
    private javax.swing.JLabel lbl_harga_diskon;
    private javax.swing.JLabel lbl_id_karyawan;
    public static javax.swing.JLabel lbl_id_transaksi;
    public static javax.swing.JLabel lbl_kembalian;
    public static javax.swing.JLabel lbl_nama;
    private javax.swing.JLabel lbl_rp;
    private javax.swing.JLabel lbl_rp_harga;
    private javax.swing.JLabel lbl_rp_kembalian;
    private javax.swing.JLabel lbl_rp_subtotal;
    private javax.swing.JLabel lbl_rp_tunai;
    private javax.swing.JLabel lbl_tgl;
    private javax.swing.JLabel lbl_total_harga;
    private javax.swing.JTable tbl_trJual;
    private javax.swing.JTextField totalbarang;
    private javax.swing.JTextField totalhargabrg;
    private javax.swing.JTextField txt_id_member;
    public static javax.swing.JTextField txt_id_produk;
    public static javax.swing.JTextField txt_jumlah;
    private javax.swing.JTextField txt_tunai;
    // End of variables declaration//GEN-END:variables
}
