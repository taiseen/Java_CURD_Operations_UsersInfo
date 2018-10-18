package main;

import dbConnection.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import net.proteanit.sql.DbUtils;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainPanel extends JFrame {

    Connection conn;
    PreparedStatement pst;
    ResultSet rs;
    String gender;
    //int userID;

    // PreparedStatement : Used for ==> sending data into DataBase OR for matching 
    // ResultSet : Used for ==> get result from DataBase 
    public MainPanel() {
        initComponents();
        // Conncetion Stublished..............
        conn = ConnectionDB.getConnection();
        // constantely connected with class & Database
        loadTable();
    }

    //#######################################################################################
    //#######################################################################################  
    // Read all row's from database & load into Table 
    private void loadTable() {
        try {
            String query = "SELECT uID, uName , uEmail, uUserName, uAge, uContact, uGender, uHight, uAddress FROM users";

            pst = conn.prepareStatement(query);
            rs = pst.executeQuery();

            dataTable_.setModel(DbUtils.resultSetToTableModel(rs));

            pst.close();
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //#######################################################################################
    //#######################################################################################  
    private void load_table_row_in_cell() {
        try {
            int selected_row = dataTable_.getSelectedRow();

            String id = (dataTable_.getModel().getValueAt(selected_row, 0)).toString();
            //String QUERY = "SELECT * FROM users WHERE uID = '" + (selected_row) + "'";
            String QUERY = "SELECT * FROM users WHERE uID = '" + (id) + "'";

            pst = conn.prepareStatement(QUERY);
            rs = pst.executeQuery();

            while (rs.next()) {
                //userID = rs.getInt("uID");
                textField_id.setText(rs.getString("uID"));
                textField_Name.setText(rs.getString("uName"));
                textField_Email.setText(rs.getString("uEmail"));
                textField_UserName.setText(rs.getString("uUserName"));
                textField_Age.setText(rs.getString("uAge"));
                textField_Contact.setText(rs.getString("uContact"));
                textField_Height.setText(rs.getString("uHight"));

                //###################################################
                //###################################################
                gender = rs.getString("uGender");
                if (gender.equals("Male")) {
                    jRadioButtonMale.setSelected(true);
                } else {
                    jRadioButtonFemale.setSelected(true);
                }
                //###################################################
                //###################################################
                String city = rs.getString("uAddress");
                jComboBox_Address.setSelectedItem(city);
                //###################################################
                //###################################################
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            System.out.println("from inside : load_table_row_in_cell() method...");
            e.printStackTrace();
        }

    }

    //#######################################################################################
    //#######################################################################################  
    private void update_cell() {
        try {
            /*
            String QUERY = "UPDATE users SET uName = '" + textField_Name.getText()
                    + "', uEmail= '" + textField_Email.getText() 
                    + "', uUserName = '" + textField_UserName.getText()
                    + "', uAge= '" + textField_Age.getText()
                    + "', uContact= '" + textField_Contact.getText() 
                    + "', uGender= '" + gender //############# Radio Button updation not work
                    + "', uHight= '" + textField_Height.getText()
                    + "', uAddress= '" + jComboBox_Address.getSelectedItem()
                    +"' WHERE uID = '"+ userID +"'"; 
             */
            String QUERY = "UPDATE users SET uName=?, uEmail=?, uUserName=?, uAge=?, uContact=?, uGender=?, uHight=?, uAddress=? WHERE uID=?";
            pst = conn.prepareStatement(QUERY);

            //##################################################################
            pst.setString(1, textField_Name.getText());
            pst.setString(2, textField_Email.getText());
            pst.setString(3, textField_UserName.getText());
            pst.setString(4, textField_Age.getText());
            pst.setString(5, textField_Contact.getText());

            //##################################################################
            // Radio Button System.....
            if (jRadioButtonMale.isSelected()) {
                gender = jRadioButtonMale.getText().toString();
            } else if (jRadioButtonFemale.isSelected()) {
                gender = jRadioButtonFemale.getText().toString();
            } else {
                JOptionPane.showMessageDialog(null, "Select Gender");
            }
            pst.setString(6, gender);
            //##################################################################

            pst.setString(7, textField_Height.getText());
            pst.setString(8, jComboBox_Address.getSelectedItem().toString());

            // fatch user ID .....
            pst.setString(9, textField_id.getText());
            //##################################################################

            pst.executeUpdate();
            pst.close();
            JOptionPane.showMessageDialog(null, "Data Update Successfuly.....");
            loadTable();

        } catch (Exception e) {
            System.out.println("from inside : update_cell() method...");
            e.printStackTrace();
        }
    }

    //#######################################################################################
    //####################################################################################### 
    private void clear_values() {
        // cleare all input option's... 
        textField_id.setText(null);
        textField_Name.setText(null);
        textField_Email.setText(null);
        textField_UserName.setText(null);
        textField_Age.setText(null);
        textField_Contact.setText(null);

        // cleare radio button group
        buttonGroup1.clearSelection();

        textField_Height.setText(null);

        // cleare ComboBox
        jComboBox_Address.setSelectedIndex(0);

    }

    //#######################################################################################
    //####################################################################################### 
    private void delete_selected_row() {
        try {
            int action = JOptionPane.showConfirmDialog(null, "Are You Sure want to Delete?", "Delete", JOptionPane.YES_NO_CANCEL_OPTION);
            if (action == 0) {
                String query = "DELETE FROM users WHERE uID = ?";
                pst = conn.prepareStatement(query);

                // fatch user ID .....
                pst.setString(1, textField_id.getText());
                //##################################################################
                pst.execute();
                JOptionPane.showMessageDialog(null, "Delete Successfuly...");
                pst.close();
                loadTable();
                clear_values();
            }
        } catch (Exception e) {
            System.out.println("from inside : delete_selected_row() method...");
            e.printStackTrace();
        }
    }

    //#######################################################################################
    //####################################################################################### 
    private void delete_all_rows_from_table() {
        try {
            int action = JOptionPane.showConfirmDialog(null, "All Row's Going to be Delete... \nAre You Sure want to Delete All Row's?", "Delete all Row's...", JOptionPane.YES_NO_OPTION);
            if (action == 0) {
                
                // The DROP TABLE statement is used to drop an existing table in a database.
                //String query = "DELETE FROM users";
                
                // The TRUNCATE TABLE statement is used to delete the data inside a table, but not the table itself.
                String query = "TRUNCATE TABLE users"; 
                pst = conn.prepareStatement(query);
                pst.execute();
                JOptionPane.showMessageDialog(null, "Delete All Row's Successfuly...");
                pst.close();
                loadTable();
                clear_values();
            }
        } catch (Exception e) {
            System.out.println("from inside : delete_selected_row() method...");
            e.printStackTrace();
        }
    }

    //#######################################################################################
    //####################################################################################### 
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        textField_Name = new javax.swing.JTextField();
        textField_Email = new javax.swing.JTextField();
        textField_UserName = new javax.swing.JTextField();
        textField_Age = new javax.swing.JTextField();
        textField_Contact = new javax.swing.JTextField();
        jRadioButtonMale = new javax.swing.JRadioButton();
        jRadioButtonFemale = new javax.swing.JRadioButton();
        textField_Height = new javax.swing.JTextField();
        jComboBox_Address = new javax.swing.JComboBox<>();
        Update_Button_ = new javax.swing.JButton();
        Add_Button_ = new javax.swing.JButton();
        Clear_Button_ = new javax.swing.JButton();
        textField_id = new javax.swing.JTextField();
        Log_Out_Button = new javax.swing.JButton();
        Delete_all_Button_ = new javax.swing.JButton();
        Delete_Button_ = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        dataTable_ = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 51, 51));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(204, 204, 204));

        textField_Name.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_Name.setToolTipText("Name");

        textField_Email.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_Email.setToolTipText("Email");

        textField_UserName.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_UserName.setToolTipText("User Name");

        textField_Age.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_Age.setToolTipText("Age");

        textField_Contact.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_Contact.setToolTipText("Contact");

        buttonGroup1.add(jRadioButtonMale);
        jRadioButtonMale.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jRadioButtonMale.setText("Male");
        jRadioButtonMale.setToolTipText("Gender");
        jRadioButtonMale.setActionCommand("Female");

        buttonGroup1.add(jRadioButtonFemale);
        jRadioButtonFemale.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jRadioButtonFemale.setText("Female");
        jRadioButtonFemale.setToolTipText("Gender");

        textField_Height.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_Height.setToolTipText("Height");

        jComboBox_Address.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jComboBox_Address.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Dhaka", "Rajshahi", "Khulna", "Chapai", "Nattor", "Bogura", "Borishal", "Jhalokhati", "Chadpur", "Dinajpur" }));
        jComboBox_Address.setToolTipText("Address");

        Update_Button_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Update_Button_.setText("Update...");
        Update_Button_.setToolTipText("Update Specific Row");
        Update_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Update_Button_MouseClicked(evt);
            }
        });

        Add_Button_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Add_Button_.setText("Add");
        Add_Button_.setToolTipText("Add New Record (UNDER CONSTRUCTION)");
        Add_Button_.setEnabled(false);
        Add_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Add_Button_MouseClicked(evt);
            }
        });

        Clear_Button_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Clear_Button_.setText("Clear");
        Clear_Button_.setToolTipText("Clear Data From Cell");
        Clear_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Clear_Button_MouseClicked(evt);
            }
        });

        textField_id.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        textField_id.setToolTipText("id Number from DataBase");
        textField_id.setEnabled(false);

        Log_Out_Button.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Log_Out_Button.setText("Log Out");
        Log_Out_Button.setToolTipText("Hope See You Soon...");
        Log_Out_Button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Log_Out_ButtonMouseClicked(evt);
            }
        });

        Delete_all_Button_.setBackground(new java.awt.Color(255, 153, 153));
        Delete_all_Button_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Delete_all_Button_.setText("Delete all!");
        Delete_all_Button_.setToolTipText("Delete All Record's");
        Delete_all_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Delete_all_Button_MouseClicked(evt);
            }
        });

        Delete_Button_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        Delete_Button_.setText("Delete");
        Delete_Button_.setToolTipText("Delete One Record");
        Delete_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Delete_Button_MouseClicked(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(102, 102, 102));
        jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton1.setText("Change Password");
        jButton1.setToolTipText("Select a Row From Table & Change Password... (UNDER CONSTRUCTION)");
        jButton1.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textField_Contact, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(textField_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(textField_id, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox_Address, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(textField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(textField_Age))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(textField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jRadioButtonMale)))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jRadioButtonFemale, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textField_Height))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 119, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(Add_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(8, 8, 8)
                            .addComponent(Clear_Button_, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addComponent(Delete_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(Delete_all_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Update_Button_)
                        .addGap(55, 55, 55))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Log_Out_Button)
                        .addGap(58, 58, 58))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(textField_Name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jRadioButtonMale)
                    .addComponent(jRadioButtonFemale)
                    .addComponent(Clear_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Add_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textField_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField_Age, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField_Height, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textField_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox_Address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textField_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(textField_Contact, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(Update_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Delete_all_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Delete_Button_, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Log_Out_Button, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 3, Short.MAX_VALUE))))
        );

        dataTable_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        dataTable_.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Name", "Email", "User Name"
            }
        ));
        dataTable_.getTableHeader().setReorderingAllowed(false);
        dataTable_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataTable_MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dataTable_);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void dataTable_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataTable_MouseClicked

        load_table_row_in_cell();

        int row = dataTable_.getSelectedRow() ;
        System.out.println( row + " - Number Row is Selected...");
    }//GEN-LAST:event_dataTable_MouseClicked

    private void Add_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Add_Button_MouseClicked

    }//GEN-LAST:event_Add_Button_MouseClicked

    private void Update_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Update_Button_MouseClicked
        update_cell();
    }//GEN-LAST:event_Update_Button_MouseClicked

    private void Delete_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Delete_Button_MouseClicked
        delete_selected_row();

    }//GEN-LAST:event_Delete_Button_MouseClicked

    private void Delete_all_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Delete_all_Button_MouseClicked
        delete_all_rows_from_table();
    }//GEN-LAST:event_Delete_all_Button_MouseClicked

    private void Clear_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Clear_Button_MouseClicked
        clear_values();
    }//GEN-LAST:event_Clear_Button_MouseClicked

    private void Log_Out_ButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Log_Out_ButtonMouseClicked
       this.setVisible(false);
       LoginForm lf = new LoginForm();
       lf.setVisible(true);
       
    }//GEN-LAST:event_Log_Out_ButtonMouseClicked

    //#######################################################################################
    //#######################################################################################  
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
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainPanel.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Add_Button_;
    private javax.swing.JButton Clear_Button_;
    private javax.swing.JButton Delete_Button_;
    private javax.swing.JButton Delete_all_Button_;
    private javax.swing.JButton Log_Out_Button;
    private javax.swing.JButton Update_Button_;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable dataTable_;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox_Address;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton jRadioButtonFemale;
    private javax.swing.JRadioButton jRadioButtonMale;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField textField_Age;
    private javax.swing.JTextField textField_Contact;
    private javax.swing.JTextField textField_Email;
    private javax.swing.JTextField textField_Height;
    private javax.swing.JTextField textField_Name;
    private javax.swing.JTextField textField_UserName;
    private javax.swing.JTextField textField_id;
    // End of variables declaration//GEN-END:variables
}
