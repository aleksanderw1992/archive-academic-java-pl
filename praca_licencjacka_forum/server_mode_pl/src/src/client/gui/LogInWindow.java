package client.gui;

import javax.swing.JOptionPane;

/**
 *
 * @author Aleksander Wojcik
 */
public class LogInWindow extends javax.swing.JDialog {

    private MainWindow mw = null;
    String login = null;
    String passwd = null;
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");;

    public LogInWindow(java.awt.Frame parent, MainWindow mw, boolean modal) {
        super(parent, modal);
        
        this.mw = mw;
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        super.setLocationRelativeTo(parent);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbLogin = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        tfLogin = new javax.swing.JTextField();
        pfPassword = new javax.swing.JPasswordField();
        btOk = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("logInToForum")); // NOI18N
        setResizable(false);

        lbLogin.setText(bundle.getString("login:")); // NOI18N

        lbPassword.setText(bundle.getString("password:")); // NOI18N

        tfLogin.setToolTipText(bundle.getString("typeInYourLogin")); // NOI18N

        pfPassword.setToolTipText(bundle.getString("typeInYourPasswd")); // NOI18N

        btOk.setText(bundle.getString("ok")); // NOI18N
        btOk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btOkActionPerformed(evt);
            }
        });

        btCancel.setText(bundle.getString("cancel")); // NOI18N
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btOk)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbPassword)
                            .addComponent(lbLogin))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(pfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lbLogin)
                    .addComponent(tfLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(lbPassword))
                .addGap(32, 32, 32)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btOk)
                    .addComponent(btCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btCancelActionPerformed

private void btOkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btOkActionPerformed
    try {
        //weryfikacja logowania
        login = tfLogin.getText();
        passwd = new String(pfPassword.getPassword());
        int id = 0;
        id = mw.getService().logIn(login, passwd);
        if (id >= 0) {
            mw.onLogin(id);
        } else {
            JOptionPane.showMessageDialog(null, bundle.getString("invalidLoginOrPasswd"));
        }
        dispose();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, bundle.getString("error"));

    }

}//GEN-LAST:event_btOkActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btOk;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tfLogin;
    // End of variables declaration//GEN-END:variables
}
