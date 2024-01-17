/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package tubes_warung;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author anand
 */
public class DialogKasir extends javax.swing.JDialog {
    public int totHargaPesananDiskon;
    public int totHargaPesananAsli;
    public int deleteIdPesanan;
    public int editIdPesanan;
    public double diskonDesert;
    public double diskonMainCourse;
    public double diskonAppetizer;
    public int tempId;
    public int id_makanan;
    public int id_minuman;
    private int idAdmin;
    private String roleAdmin;
    private ArrayList<Makanan> makan;
    private ArrayList<Minuman> minum;
    private Object selectedDataMakan;
    private Object selectedDataMinum;
    private Object selectedDataPesanan;
    // private Makanan makan_selected;
    // private Minuman minum_selected;
    /**
     * Creates new form DialogKasir
     * @param parent
     * @param modal
     * @throws java.sql.SQLException
     * @throws java.lang.ClassNotFoundException
     */
    
    public final void set_diskon() throws SQLException, ClassNotFoundException{
        Database db = new Database();
        String cariDiskonDesert = "SELECT * FROM discounts where discounts.namaKategori = 'Desert'";
        String cariDiskonMainCourse = "SELECT * FROM discounts where discounts.namaKategori = 'MainCourse'";
        String cariDiskonAppetizer = "SELECT * FROM discounts where discounts.namaKategori = 'Appetizer'";
        ResultSet rs1 = db.getData(cariDiskonDesert);
        ResultSet rs2 = db.getData(cariDiskonMainCourse);
        ResultSet rs3 = db.getData(cariDiskonAppetizer);
        
        try{
            while (rs1.next()){
                diskonDesert = (double) (100-rs1.getInt(3))/100;
            }
            while (rs2.next()){
                diskonMainCourse = (double) (100-rs2.getInt(3))/100;
            }
            while (rs3.next()){
                diskonAppetizer = (double) (100-rs3.getInt(3))/100;
            }
        }catch (SQLException err){
            JOptionPane.showMessageDialog(null,""+err.getMessage(),"Connection Error",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public DialogKasir(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        jLabel8.setVisible(false);
        jLabel10.setVisible(false);
        set_diskon();
    }
    
    public void setIdRoleLogin(int idAdmin, String role){
        this.idAdmin = idAdmin;
        this.roleAdmin = role;
        
        if(roleAdmin.equals("Admin")){
            jLabel8.setVisible(true);
        }else if(roleAdmin.equals("Kasir")){
            jLabel10.setVisible(true);
        }
    }
    
    public final void loadDataMakan() throws SQLException, ClassNotFoundException{
        Database db = new Database();
        String cariMakan = "SELECT * FROM foods";
        Database.rs = db.getData(cariMakan);
        makan = new ArrayList<>();
        
        try{
            while (Database.rs.next()){
                String kat = Database.rs.getString(5);
                if (kat.equals("Desert")){
                    makan.add(new Makanan(Database.rs.getInt(1), Database.rs.getString(5), Database.rs.getString(2), (int) (Database.rs.getInt(4) * diskonDesert), Database.rs.getInt(3)));
                }else if (kat.equals("Appetizer")){
                    makan.add(new Makanan(Database.rs.getInt(1), Database.rs.getString(5), Database.rs.getString(2), (int) (Database.rs.getInt(4)* diskonAppetizer), Database.rs.getInt(3)));
                }else{
                    makan.add(new Makanan(Database.rs.getInt(1), Database.rs.getString(5), Database.rs.getString(2), (int) (Database.rs.getInt(4) * diskonMainCourse), Database.rs.getInt(3)));
                }            

            }
            while (((DefaultTableModel)TabelMakanan.getModel()).getRowCount()>0){
                ((DefaultTableModel)TabelMakanan.getModel()).removeRow(0);
            }
            for (int i=0; i<makan.size(); i++){
                ((DefaultTableModel)TabelMakanan.getModel()).addRow(new Object[]{
                    makan.get(i).getNama(),
                    makan.get(i).getHarga(),
                    makan.get(i).getStok(),
                    makan.get(i).getKategori()
                });
            }
        }catch (SQLException err){
            JOptionPane.showMessageDialog(null,""+err.getMessage(),"Connection Error",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public final void loadDataMinum() throws SQLException, ClassNotFoundException{
        Database db = new Database();
        String cariMinum = "SELECT * FROM drinks";
        Database.rs = db.getData(cariMinum);
        minum = new ArrayList<>();
        
        try{
            while (Database.rs.next()){
                minum.add(new Minuman(Database.rs.getInt(1), Database.rs.getString(2), Database.rs.getInt(4), Database.rs.getInt(3)));

            }
            while (((DefaultTableModel)TabelMinuman.getModel()).getRowCount()>0){
                ((DefaultTableModel)TabelMinuman.getModel()).removeRow(0);
            }
            for (int i=0; i<minum.size(); i++){
                ((DefaultTableModel)TabelMinuman.getModel()).addRow(new Object[]{
                    minum.get(i).getNama(),
                    minum.get(i).getHarga(),
                    minum.get(i).getStok(),
                });
            }
        }catch (SQLException err){
            JOptionPane.showMessageDialog(null,""+err.getMessage(),"Connection Error",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public final void loadDataPesanan() throws SQLException, ClassNotFoundException{
        try {
            Database db = new Database();
            int hargaAsli = 0;
            boolean cekMinum = false;
            totHargaPesananDiskon = 0;
            totHargaPesananAsli = 0;
            String sql = "SELECT chooses.id, chooses.id_book, chooses.id_makanan, chooses.id_minuman, " +
                         "foods.namaMakanan AS nama_makanan, foods.hargaMakanan AS harga_makanan, " +
                         "drinks.hargaMinuman AS harga_minuman, drinks.namaMinuman AS nama_minuman, " +
                         "foods.kategori AS kategori, chooses.jumlah " +
                         "FROM chooses " +
                         "LEFT JOIN foods ON chooses.id_makanan = foods.id " +
                         "LEFT JOIN drinks ON chooses.id_minuman = drinks.id " +
                         "WHERE chooses.id_book = " + tempId + ";";

            ResultSet rs = db.getData(sql);
            DefaultTableModel model = (DefaultTableModel) TabelPesanan.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int idMakanan = rs.getInt("id_makanan");
                String namaProduk,kategori;
                int hargaProduk, banyaknya;

                if ((rs.wasNull() || idMakanan == 0) && cekMinum == false) {
                    namaProduk = rs.getString("nama_minuman");
                    hargaAsli = rs.getInt("harga_minuman");
                    hargaProduk = rs.getInt("harga_minuman");
                } else {
                    namaProduk = rs.getString("nama_makanan");
                    kategori = rs.getString("kategori");
                    hargaAsli = rs.getInt("harga_makanan");
                    if (kategori.equals("Desert")){
                        hargaProduk = (int) (rs.getInt("harga_makanan") * diskonDesert);
                    }else if(kategori.equals("Appetizer")){
                        hargaProduk = (int) (rs.getInt("harga_makanan")* diskonAppetizer);
                    }else{
                        hargaProduk = (int) (rs.getInt("harga_makanan")* diskonMainCourse);
                    }
                }

                banyaknya = rs.getInt("jumlah");
                int jumlahHargaAsli = hargaAsli * banyaknya;
                int jumlahHargaDiskon = hargaProduk * banyaknya;
                totHargaPesananDiskon += jumlahHargaDiskon;
                totHargaPesananAsli += jumlahHargaAsli;
                model.addRow(new Object[]{namaProduk, hargaProduk, banyaknya, jumlahHargaDiskon});
            }

            jLabel9.setText(""+totHargaPesananAsli+"");
            jLabel13.setText(""+totHargaPesananDiskon+"");
            db.close();

        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TabelMakanan = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        TabelMinuman = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        TambahMinumanButton = new javax.swing.JButton();
        TambahMakananButton = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        TabelPesanan = new javax.swing.JTable();
        BayarButton = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        SubmitNameCustButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        EditButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        jLabel1.setText("LIST MAKANAN");

        jLabel2.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        jLabel2.setText("LIST MINUMAN");

        TabelMakanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Makanan", "Harga", "Stok", "Kategori"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelMakanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMakananMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(TabelMakanan);

        TabelMinuman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Minuman", "Harga", "Stok"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelMinuman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelMinumanMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(TabelMinuman);

        jLabel3.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel3.setText("KASIR");

        TambahMinumanButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        TambahMinumanButton.setText("Tambah Minuman");
        TambahMinumanButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahMinumanButtonActionPerformed(evt);
            }
        });

        TambahMakananButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        TambahMakananButton.setText("Tambah Makanan");
        TambahMakananButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TambahMakananButtonActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Banyaknya :");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Banyaknya :");

        TabelPesanan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nama Produk", "Harga Produk", "Banyaknya", "Jumlah Harga"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        TabelPesanan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TabelPesananMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(TabelPesanan);

        BayarButton.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        BayarButton.setText("BAYAR");
        BayarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BayarButtonActionPerformed(evt);
            }
        });

        jLabel6.setText("NAMA                  :");

        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });

        SubmitNameCustButton.setText("OK");
        SubmitNameCustButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SubmitNameCustButtonActionPerformed(evt);
            }
        });

        jLabel7.setText("TOTAL HARGA  :");

        EditButton.setText("EDIT");
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });

        DeleteButton.setText("DELETE");
        DeleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DeleteButtonActionPerformed(evt);
            }
        });

        jLabel11.setText("Perubahan Qty :");

        jLabel8.setText("<--BACK");
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel10.setText("<--LOGOUT");
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });

        jLabel12.setText("TOTAL HARGA  SETELAH DISKON :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField5)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BayarButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SubmitNameCustButton, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel12)
                            .addComponent(jLabel13))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addGap(94, 94, 94)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TambahMakananButton, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(TambahMinumanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(118, 118, 118))))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jTextField2)
                    .addComponent(TambahMakananButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TambahMinumanButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(SubmitNameCustButton))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(DeleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BayarButton, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TambahMakananButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahMakananButtonActionPerformed
        try {
            Database db = new Database();
            if (selectedDataMakan != null){
                int id_makanan = 0;
                int stok_makanan = 0;
                String sql = "SELECT `id` FROM `foods` WHERE namaMakanan = '" + selectedDataMakan + "';";
                ResultSet rs = db.getData(sql);
                
                System.out.println(selectedDataMakan);

                while (rs.next()) {
                    id_makanan = rs.getInt("id");
                }
                
                int jumlah = Integer.parseInt(jTextField2.getText());
                
//                sql = "INSERT INTO chooses (id_book, id_makanan, jumlah) VALUES (" + tempId + "," + id_makanan + "," + jumlah + ")";
//                db.query(sql);

                sql = "SELECT * FROM chooses WHERE id_book = " + tempId + " AND id_makanan = " + id_makanan + ";";
                ResultSet existingData = db.getData(sql);
                
                sql = "SELECT `stokMakanan` FROM `foods` WHERE namaMakanan = '" + selectedDataMakan + "';";
                ResultSet rStok = db.getData(sql);
                while (rStok.next()) {
                    stok_makanan = rStok.getInt("stokMakanan");
                }

                if (existingData.next()) {
                    int existingJumlah = existingData.getInt("jumlah");
                    jumlah += existingJumlah;
                    if(jumlah <= stok_makanan){
                       sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_makanan = " + id_makanan + ";";
                        db.query(sql);
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }
                    
//                    sql = "UPDATE `foods` SET `stokMakanan` = "+ stok_makanan +" WHERE id = "+ id_makanan +"";
//                    db.query(sql);

                    
                    
                } else {
                    if(jumlah <= stok_makanan){
                       sql = "INSERT INTO chooses (id_book, id_makanan, jumlah) VALUES (" + tempId + "," + id_makanan + "," + jumlah + ")";
                        db.query(sql);
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    
//                    sql = "UPDATE `foods` SET `stokMakanan` = "+ jumlah +" WHERE id = "+ id_makanan +"";
//                    db.query(sql);
                }

                db.close();
                jTextField2.setText("");
                this.loadDataPesanan();
                this.loadDataMakan();
            }else{
                JOptionPane.showMessageDialog(null,"silahkan pilih makanan..","Error system",JOptionPane.WARNING_MESSAGE);
            }
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }//GEN-LAST:event_TambahMakananButtonActionPerformed

    private void TambahMinumanButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TambahMinumanButtonActionPerformed
        try {
            Database db = new Database();
            if (selectedDataMinum != null) {
                int id_minuman = 0;
                int stok_minuman = 0;
                String sql = "SELECT `id` FROM `drinks` WHERE namaMinuman = '" + selectedDataMinum + "';";
                ResultSet rs = db.getData(sql);

                while (rs.next()) {
                    id_minuman = rs.getInt("id");
                }

                int jumlah = Integer.parseInt(jTextField1.getText());

                sql = "SELECT * FROM chooses WHERE id_book = " + tempId + " AND id_minuman = " + id_minuman + ";";
                ResultSet existingData = db.getData(sql);
                
                sql = "SELECT `stokMinuman` FROM `drinks` WHERE namaMinuman = '" + selectedDataMakan + "';";
                ResultSet rStok = db.getData(sql);
                
                while (rStok.next()) {
                    stok_minuman = rs.getInt("stokMinuman");
                }
                
                if (existingData.next()) {
                    int existingJumlah = existingData.getInt("jumlah");
                    jumlah += existingJumlah;
                    if(jumlah <= stok_minuman){
                        stok_minuman = stok_minuman + existingJumlah - jumlah;
                        sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_minuman = " + id_minuman + ";";
                        db.query(sql); 
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }

                    
                                     
//                    sql = "UPDATE `drinks` SET `stokMinuman` = `"+ stok_minuman +"` WHERE id = `"+ id_minuman +"`";
//                    db.query(sql);

                } else {
                    if(jumlah <= stok_minuman){
                        sql = "INSERT INTO chooses (id_book, id_minuman, jumlah) VALUES (" + tempId + "," + id_minuman + "," + jumlah + ")";
                        db.query(sql);; 
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }
                    
                    
//                    sql = "UPDATE `drinks` SET `stokMinuman` = `"+ jumlah +"` WHERE id = `"+ id_minuman +"`";
//                    db.query(sql);
                }

                db.close();
                jTextField1.setText("");
                this.loadDataPesanan();
                this.loadDataMinum();
            } else {
                JOptionPane.showMessageDialog(null, "Silahkan pilih minuman..", "Error system", JOptionPane.WARNING_MESSAGE);
            }
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_TambahMinumanButtonActionPerformed

    private void TabelMakananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMakananMouseClicked
        int selectedMakan = TabelMakanan.getSelectedRow();
        if (selectedMakan != -1) {
            selectedDataMakan = TabelMakanan.getValueAt(selectedMakan,0);
            System.out.println("Selected Makan: " + selectedDataMakan);
        }
    }//GEN-LAST:event_TabelMakananMouseClicked

    private void TabelMinumanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelMinumanMouseClicked
        int selectedMinum = TabelMinuman.getSelectedRow();
        if (selectedMinum != -1){
            selectedDataMinum = TabelMinuman.getValueAt(selectedMinum, 0);
            System.out.println("Selected Minum: " + selectedDataMinum);
        }
    }//GEN-LAST:event_TabelMinumanMouseClicked

    private void SubmitNameCustButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SubmitNameCustButtonActionPerformed
        try {
            Database db = new Database();
            String namaPemesan = jTextField4.getText();
            Pesanan pesan = new Pesanan(namaPemesan, 0);

            String sql = "INSERT INTO bookings (nama, hargaTotal) VALUES ('"+pesan.getNama()+"','"+pesan.getHargaTotal()+"')";
            db.query(sql);
            
            sql = "SELECT MAX(id) FROM `bookings`";
            ResultSet rs = db.getData(sql);
            while(rs.next()){
                tempId= rs.getInt("MAX(id)");
            }
            
            db.close();
            loadDataMakan();
            loadDataMinum();
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_SubmitNameCustButtonActionPerformed

    private void TabelPesananMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TabelPesananMouseClicked
        int selectedPesanan = TabelPesanan.getSelectedRow();
        if (selectedPesanan != -1){
            selectedDataPesanan = TabelPesanan.getValueAt(selectedPesanan, 0);
            System.out.println("Selected Pesan: " + selectedDataPesanan);
        }
    }//GEN-LAST:event_TabelPesananMouseClicked

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        try {
            Database db = new Database();
            int idMakan = 0, idMinum = 0;
            
            String selectedDataPesan = selectedDataPesanan.toString();

            String sql1 = "SELECT `id` FROM `foods` WHERE namaMakanan = '"+selectedDataPesan+"'";
            ResultSet rs1 = db.getData(sql1);

            String sql2 = "SELECT `id` FROM `drinks` WHERE namaMinuman = '"+selectedDataPesan+"';";
            ResultSet rs2 = db.getData(sql2);

            while(rs1.next()){
                idMakan= rs1.getInt("id");
            }

            while(rs2.next()){
                idMinum= rs2.getInt("id");
            }
            
            if(idMakan == 0){
                deleteIdPesanan = idMinum;
                String sql = "DELETE FROM chooses WHERE id_book = "+tempId+" AND id_minuman = "+ deleteIdPesanan +"";
                db.query(sql);
            }else{
                deleteIdPesanan = idMakan;
                String sql = "DELETE FROM chooses WHERE id_book = "+tempId+" AND id_makanan = "+ deleteIdPesanan +"";
                db.query(sql);
            }

            rs1.close();
            rs2.close();
            db.close();
            this.loadDataPesanan();
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        try {
            Database db = new Database();
            int idMakan = 0, idMinum = 0;
            int stok_makanan = 0, stok_minuman = 0;
            
            
            int jumlah = Integer.parseInt(jTextField5.getText());
            
            if (selectedDataPesanan != null){
                
                String selectedDataPesan = selectedDataPesanan.toString();

                String sql1 = "SELECT `id`, `stokMakanan` FROM `foods` WHERE namaMakanan = '"+selectedDataPesan+"'";
                ResultSet rs1 = db.getData(sql1);

                String sql2 = "SELECT `id`, `stokMinuman` FROM `drinks` WHERE namaMinuman = '"+selectedDataPesan+"';";
                ResultSet rs2 = db.getData(sql2);

                while(rs1.next()){
                    idMakan= rs1.getInt("id");
                    stok_makanan = rs1.getInt("stokMakanan");
                }

                while(rs2.next()){
                    idMinum= rs2.getInt("id");
                    stok_minuman = rs2.getInt("stokMinuman");
                }

                if(idMakan == 0){
                    editIdPesanan = idMinum;
                    if(jumlah <= stok_minuman){
                        String sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_minuman = " + editIdPesanan + "";
                        db.query(sql);
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }
                    
                }else{
                    editIdPesanan = idMakan;
                    if(jumlah <= stok_makanan){
                        String sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_makanan = " + editIdPesanan + "";
                        db.query(sql);
                    }else{
                        JOptionPane.showMessageDialog(null, "Stok tidak cukup!!!", "Error system", JOptionPane.WARNING_MESSAGE);
                    }
                   
                }

                rs1.close();
                rs2.close();
                jTextField5.setText("");
                this.loadDataPesanan();
            }else{
                JOptionPane.showMessageDialog(null,"silahkan pilih makanan..","Error system",JOptionPane.WARNING_MESSAGE);
            }
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_EditButtonActionPerformed

    private void BayarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BayarButtonActionPerformed
        int nominalBayar = Integer.parseInt(jTextField3.getText());
        int totPembayaranAsli = Integer.parseInt(jLabel9.getText());
        int totPembayaranDiskon = Integer.parseInt(jLabel13.getText());
        
        int hasilDiskon = nominalBayar - totPembayaranDiskon;
        int selisih = totPembayaranAsli - totPembayaranDiskon;
        if (hasilDiskon < 0){
            JOptionPane.showMessageDialog(null,"Nominal pembayaran kurang..","Kasir",JOptionPane.WARNING_MESSAGE);
        }else{
            Database db = null;
            String sql;
            System.out.println(tempId);
            
            try {
                db = new Database();
                sql = "UPDATE `bookings` SET hargaTotal = "+totPembayaranDiskon+" WHERE id = "+ tempId +"";
                System.out.println(sql);
                db.query(sql);
                sql = "SELECT chooses.id, chooses.id_book, chooses.id_makanan, chooses.id_minuman, " +
                         "foods.namaMakanan AS nama_makanan, foods.hargaMakanan AS harga_makanan, foods.stokMakanan AS stok_makanan, " +
                         "drinks.hargaMinuman AS harga_minuman, drinks.namaMinuman AS nama_minuman, drinks.stokMinuman AS stok_minuman," +
                         "foods.kategori AS kategori, chooses.jumlah " +
                         "FROM chooses " +
                         "LEFT JOIN foods ON chooses.id_makanan = foods.id " +
                         "LEFT JOIN drinks ON chooses.id_minuman = drinks.id " +
                         "WHERE chooses.id_book = " + tempId + ";";

                ResultSet rs = db.getData(sql);
                while (rs.next()) {
                    int idMakanan = rs.getInt("id_makanan");
                    int idProduk, tempStok = 0;
                    int hargaProduk, banyaknya;

                    if (rs.wasNull() || idMakanan == 0) {
                        idProduk = rs.getInt("id_minuman");
                        tempStok = rs.getInt("stok_minuman");
                        banyaknya = rs.getInt("jumlah");
                        sql =  "UPDATE `drinks` SET stokMinuman = "+ (tempStok -banyaknya) +" WHERE id = '"+ idProduk +"'";
                        System.out.println(sql);

                    } else {
                        idProduk = idMakanan;
                        tempStok = rs.getInt("stok_makanan");
                        banyaknya = rs.getInt("jumlah");
                        sql =  "UPDATE `foods` SET stokMakanan = "+ (tempStok -banyaknya) +" WHERE id = '"+ idProduk +"'";
                        System.out.println(sql);
                    }
                    db.query(sql);

                }
                db.close();
            } catch (SQLException ex) {
                Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            JOptionPane.showMessageDialog(null,"Kembalian : "+hasilDiskon+"\nAnda telah menghemat : "+ selisih +"","Kasir",JOptionPane.WARNING_MESSAGE);
            this.dispose();
            
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            DialogKasir newK = null;
            try {
                newK = new DialogKasir(parentFrame, true);
            } catch (SQLException ex) {
                Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
            }
            newK.setIdRoleLogin(idAdmin, roleAdmin);
            newK.setLocationRelativeTo(null);
            newK.setVisible(true);
        }
    }//GEN-LAST:event_BayarButtonActionPerformed

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        this.dispose();
            
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        AdminChoose newChoose;
        newChoose = new AdminChoose(parentFrame, true);
        newChoose.setIdRoleLogin(idAdmin, roleAdmin);
        newChoose.setLocationRelativeTo(null);
        newChoose.setVisible(true);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        this.dispose();

        LoginForm login = new LoginForm();
        login.setLocationRelativeTo(null);
        login.setVisible(true);
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

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
            java.util.logging.Logger.getLogger(DialogKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DialogKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DialogKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DialogKasir.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DialogKasir dialog = null;
                try {
                    dialog = new DialogKasir(new javax.swing.JFrame(), true);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
                }
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BayarButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JButton SubmitNameCustButton;
    private javax.swing.JTable TabelMakanan;
    private javax.swing.JTable TabelMinuman;
    private javax.swing.JTable TabelPesanan;
    private javax.swing.JButton TambahMakananButton;
    private javax.swing.JButton TambahMinumanButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
