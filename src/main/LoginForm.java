package main;

import dbConnection.ConnectionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class LoginForm extends javax.swing.JFrame {

    Connection conn = null;
    // Used for ==> sending data into DataBase OR for matching 
    PreparedStatement pst = null;
    // Used for ==> get result from DataBase 
    ResultSet rs = null;

    public LoginForm() {
        initComponents();
        //####################################
        //####################################
        //####################################
        // Conncetion Stublished..............
        conn = ConnectionDB.getConnection();

    }

    // Login function
    public void login() {
        try {
            String querry = "SELECT * FROM users WHERE uUserName = ? AND uPass = ?";
            pst = conn.prepareStatement(querry);

            pst.setString(1, jTextField_UserName.getText());
            pst.setString(2, jPasswordField.getText());

            rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successfil...");
                // Linking with another page 
                MainPanel pl = new MainPanel();
                pl.setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Login Failed...", "ERROR!", JOptionPane.ERROR_MESSAGE);
                jTextField_UserName.setText(null);
                jPasswordField.setText(null);
            }
            pst.close();
            rs.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Login Error...");
        }
    }

    //#######################################################################################
    //#######################################################################################
    //#######################################################################################    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Main = new javax.swing.JPanel();
        Login_Button_ = new javax.swing.JButton();
        jTextField_UserName = new javax.swing.JTextField();
        jPasswordField = new javax.swing.JPasswordField();
        SignupPageLink_ = new javax.swing.JLabel();
        jLabel_TimeShow = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Login_Button_.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        Login_Button_.setText("Login...");
        Login_Button_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Login_Button_MouseClicked(evt);
            }
        });

        jTextField_UserName.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jTextField_UserName.setToolTipText("User Name");

        jPasswordField.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jPasswordField.setToolTipText("Password");

        SignupPageLink_.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SignupPageLink_.setText("Signup Here...");
        SignupPageLink_.setToolTipText("Create a New Profile...");
        SignupPageLink_.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        SignupPageLink_.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SignupPageLink_MouseClicked(evt);
            }
        });

        jLabel_TimeShow.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel_TimeShow.setText("Time Show ");

        javax.swing.GroupLayout jPanel_MainLayout = new javax.swing.GroupLayout(jPanel_Main);
        jPanel_Main.setLayout(jPanel_MainLayout);
        jPanel_MainLayout.setHorizontalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_MainLayout.createSequentialGroup()
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_MainLayout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel_MainLayout.createSequentialGroup()
                                .addComponent(SignupPageLink_)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Login_Button_))
                            .addGroup(jPanel_MainLayout.createSequentialGroup()
                                .addComponent(jTextField_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel_MainLayout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel_TimeShow)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        jPanel_MainLayout.setVerticalGroup(
            jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_MainLayout.createSequentialGroup()
                .addContainerGap(39, Short.MAX_VALUE)
                .addComponent(jLabel_TimeShow)
                .addGap(38, 38, 38)
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField_UserName, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Login_Button_)
                    .addComponent(SignupPageLink_))
                .addGap(39, 39, 39))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_Main, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Login_Button_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Login_Button_MouseClicked
        login();
        System.out.println("clicked...");
    }//GEN-LAST:event_Login_Button_MouseClicked

    private void SignupPageLink_MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SignupPageLink_MouseClicked
        this.setVisible(false);
        SignupForm sf = new SignupForm();
        sf.setVisible(true);
    }//GEN-LAST:event_SignupPageLink_MouseClicked

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
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login_Button_;
    private javax.swing.JLabel SignupPageLink_;
    private javax.swing.JLabel jLabel_TimeShow;
    private javax.swing.JPanel jPanel_Main;
    private javax.swing.JPasswordField jPasswordField;
    private javax.swing.JTextField jTextField_UserName;
    // End of variables declaration//GEN-END:variables
}
