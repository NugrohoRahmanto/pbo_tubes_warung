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
    public int totHargaPesanan;
    public int deleteIdPesanan;
    public int editIdPesanan;
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
    public DialogKasir(java.awt.Frame parent, boolean modal) throws SQLException, ClassNotFoundException {
        super(parent, modal);
        initComponents();
        jLabel8.setVisible(false);
        jLabel10.setVisible(false);
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
                makan.add(new Makanan(Database.rs.getInt(1), Database.rs.getString(5), Database.rs.getString(2), Database.rs.getInt(4), Database.rs.getInt(3)));

            }
            while (((DefaultTableModel)jTable1.getModel()).getRowCount()>0){
                ((DefaultTableModel)jTable1.getModel()).removeRow(0);
            }
            for (int i=0; i<makan.size(); i++){
                ((DefaultTableModel)jTable1.getModel()).addRow(new Object[]{
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
            while (((DefaultTableModel)jTable2.getModel()).getRowCount()>0){
                ((DefaultTableModel)jTable2.getModel()).removeRow(0);
            }
            for (int i=0; i<minum.size(); i++){
                ((DefaultTableModel)jTable2.getModel()).addRow(new Object[]{
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
            totHargaPesanan = 0;
            String sql = "SELECT chooses.id, chooses.id_book, chooses.id_makanan, chooses.id_minuman, " +
                         "foods.namaMakanan AS nama_makanan, foods.hargaMakanan AS harga_makanan, " +
                         "drinks.hargaMinuman AS harga_minuman, drinks.namaMinuman AS nama_minuman, " +
                         "chooses.jumlah " +
                         "FROM chooses " +
                         "LEFT JOIN foods ON chooses.id_makanan = foods.id " +
                         "LEFT JOIN drinks ON chooses.id_minuman = drinks.id " +
                         "WHERE chooses.id_book = " + tempId + ";";

            ResultSet rs = db.getData(sql);
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);

            while (rs.next()) {
                int idMakanan = rs.getInt("id_makanan");
                String namaProduk;
                int hargaProduk, banyaknya;

                if (rs.wasNull() || idMakanan == 0) {
                    namaProduk = rs.getString("nama_minuman");
                    hargaProduk = rs.getInt("harga_minuman");
                } else {
                    namaProduk = rs.getString("nama_makanan");
                    hargaProduk = rs.getInt("harga_makanan");
                }

                banyaknya = rs.getInt("jumlah");
                int jumlahHarga = hargaProduk * banyaknya;
                totHargaPesanan += jumlahHarga;
                model.addRow(new Object[]{namaProduk, hargaProduk, banyaknya, jumlahHarga});
            }

            jLabel9.setText(""+totHargaPesanan+"");
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
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();
        jTextField3 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        EDIT = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField5 = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        jLabel1.setText("LIST MAKANAN");

        jLabel2.setFont(new java.awt.Font("Sitka Text", 1, 14)); // NOI18N
        jLabel2.setText("LIST MINUMAN");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jLabel3.setFont(new java.awt.Font("Sitka Text", 1, 24)); // NOI18N
        jLabel3.setText("KASIR");

        jButton1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton1.setText("Tambah Minuman");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jButton2.setText("Tambah Makanan");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel4.setText("Banyaknya :");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jLabel5.setText("Banyaknya :");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
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
        jTable3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable3MouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(jTable3);

        jButton3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 14)); // NOI18N
        jButton3.setText("BAYAR");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel6.setText("NAMA                  :");

        jButton4.setText("OK");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel7.setText("TOTAL HARGA  :");

        EDIT.setText("EDIT");
        EDIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EDITActionPerformed(evt);
            }
        });

        jButton6.setText("DELETE");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
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
                                .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 526, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(14, 14, 14)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel10)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel1)
                                .addGap(102, 102, 102)
                                .addComponent(jLabel3))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jTextField1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 344, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(79, 79, 79))))))
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
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EDIT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))))
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

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            Database db = new Database();
            if (selectedDataMakan != null){
                int id_makanan = 0;
                String sql = "SELECT `id` FROM `foods` WHERE namaMakanan = '" + selectedDataMakan + "';";
                ResultSet rs = db.getData(sql);

                while (rs.next()) {
                    id_makanan = rs.getInt("id");
                }
                
                int jumlah = Integer.parseInt(jTextField2.getText());

                sql = "SELECT * FROM chooses WHERE id_book = " + tempId + " AND id_makanan = " + id_makanan + ";";
                ResultSet existingData = db.getData(sql);
                
                if (existingData.next()) {
                    int existingJumlah = existingData.getInt("jumlah");
                    jumlah += existingJumlah;

                    sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_makanan = " + id_makanan + ";";
                    db.query(sql);
                } else {
                    sql = "INSERT INTO chooses (id_book, id_makanan, jumlah) VALUES (" + tempId + "," + id_makanan + "," + jumlah + ")";
                    db.query(sql);
                }

                jTextField2.setText("");
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
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            Database db = new Database();
            if (selectedDataMinum != null) {
                int id_minuman = 0;
                String sql = "SELECT `id` FROM `drinks` WHERE namaMinuman = '" + selectedDataMinum + "';";
                ResultSet rs = db.getData(sql);

                while (rs.next()) {
                    id_minuman = rs.getInt("id");
                }

                int jumlah = Integer.parseInt(jTextField1.getText());

                sql = "SELECT * FROM chooses WHERE id_book = " + tempId + " AND id_minuman = " + id_minuman + ";";
                ResultSet existingData = db.getData(sql);

                if (existingData.next()) {
                    int existingJumlah = existingData.getInt("jumlah");
                    jumlah += existingJumlah;

                    sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_minuman = " + id_minuman + ";";
                    db.query(sql);
                } else {
                    sql = "INSERT INTO chooses (id_book, id_minuman, jumlah) VALUES (" + tempId + "," + id_minuman + "," + jumlah + ")";
                    db.query(sql);
                }

                jTextField1.setText("");
                this.loadDataPesanan();
            } else {
                JOptionPane.showMessageDialog(null, "Silahkan pilih minuman..", "Error system", JOptionPane.WARNING_MESSAGE);
            }
            db.close();
        } catch (SQLException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DialogKasir.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        int selectedMakan = jTable1.getSelectedRow();
        if (selectedMakan != -1) {
            selectedDataMakan = jTable1.getValueAt(selectedMakan,0);
            System.out.println("Selected Makan: " + selectedDataMakan);
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int selectedMinum = jTable2.getSelectedRow();
        if (selectedMinum != -1){
            selectedDataMinum = jTable2.getValueAt(selectedMinum, 0);
            System.out.println("Selected Minum: " + selectedDataMinum);
        }
        
    }//GEN-LAST:event_jTable2MouseClicked

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
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
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTable3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable3MouseClicked
        int selectedPesanan = jTable3.getSelectedRow();
        if (selectedPesanan != -1){
            selectedDataPesanan = jTable3.getValueAt(selectedPesanan, 0);
            System.out.println("Selected Pesan: " + selectedDataPesanan);
        }
    }//GEN-LAST:event_jTable3MouseClicked

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
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
    }//GEN-LAST:event_jButton6ActionPerformed

    private void EDITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EDITActionPerformed
        try {
            Database db = new Database();
            int idMakan = 0, idMinum = 0;
            
            int jumlah = Integer.parseInt(jTextField5.getText());
            
            if (selectedDataPesanan != null){
                
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
                    editIdPesanan = idMinum;
                    String sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_minuman = " + editIdPesanan + "";
                    db.query(sql);
                }else{
                    editIdPesanan = idMakan;
                    String sql = "UPDATE chooses SET jumlah = " + jumlah + " WHERE id_book = " + tempId + " AND id_makanan = " + editIdPesanan + "";
                    db.query(sql);
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
    }//GEN-LAST:event_EDITActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int nominalBayar = Integer.parseInt(jTextField3.getText());
        int totPembayaran = Integer.parseInt(jLabel9.getText());
        
        int hasil = nominalBayar - totPembayaran;
        if (hasil < 0){
            JOptionPane.showMessageDialog(null,"Nominal pembayaran kurang..","Kasir",JOptionPane.WARNING_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null,"Kembalian : "+hasil+"","Kasir",JOptionPane.WARNING_MESSAGE);
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
    }//GEN-LAST:event_jButton3ActionPerformed

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
    private javax.swing.JButton EDIT;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
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
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
