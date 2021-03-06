package client.gui;

import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksander Wojcik
 */
public class RegisterWindow extends javax.swing.JDialog {

    private static final Logger logger = Logger.getLogger(RegisterWindow.class.getName());
    /**
     * Creates new form RegisterWindow
     */
    private MainWindow mw;
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");;
    public RegisterWindow(MainWindow mw, boolean modal) {
        super(mw, modal);
        
        this.mw = mw;
        initComponents();
        super.setLocationRelativeTo(mw);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbFirstname = new javax.swing.JLabel();
        lbLastname = new javax.swing.JLabel();
        lbLogin = new javax.swing.JLabel();
        lbPassword = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        tfFirstname = new javax.swing.JTextField();
        tfLastname = new javax.swing.JTextField();
        tfLogin = new javax.swing.JTextField();
        tfEmail = new javax.swing.JTextField();
        btCreateAccount = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        lbRequired = new javax.swing.JLabel();
        pfPassword = new javax.swing.JPasswordField();
        pfConfirmPassword = new javax.swing.JPasswordField();
        lbConfirmPassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("register")); // NOI18N
        setResizable(false);

        lbFirstname.setText(bundle.getString("firstName:")); // NOI18N

        lbLastname.setText(bundle.getString("surname:")); // NOI18N

        lbLogin.setText(bundle.getString("login:")); // NOI18N

        lbPassword.setText(bundle.getString("passwd:")); // NOI18N

        lbEmail.setText(bundle.getString("email:")); // NOI18N

        btCreateAccount.setText(bundle.getString("ok")); // NOI18N
        btCreateAccount.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCreateAccountActionPerformed(evt);
            }
        });

        btCancel.setText(bundle.getString("cancel")); // NOI18N
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        lbRequired.setText(bundle.getString("requiredField")); // NOI18N

        lbConfirmPassword.setText(bundle.getString("confirmPasswd")); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btCreateAccount)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel))
                    .addComponent(lbRequired, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbLogin)
                            .addComponent(lbPassword)
                            .addComponent(lbLastname)
                            .addComponent(lbFirstname)
                            .addComponent(lbConfirmPassword)
                            .addComponent(lbEmail))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(tfFirstname, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(tfLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(tfLastname, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(pfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE)
                            .addComponent(pfConfirmPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbFirstname)
                    .addComponent(tfFirstname, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfLastname, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(lbLastname))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbLogin)
                    .addComponent(tfLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbPassword)
                    .addComponent(pfPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pfConfirmPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(lbConfirmPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                    .addComponent(lbEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbRequired)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btCancel)
                    .addComponent(btCreateAccount))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
// TODO add your handling code here:
    dispose();
}//GEN-LAST:event_btCancelActionPerformed

    // sprawdz, czy podano mail w prawidlowym formacie
private void btCreateAccountActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCreateAccountActionPerformed
    String name = tfFirstname.getText();
    String login = tfLogin.getText();
    String lastname = tfLastname.getText();
    String passwd = String.valueOf(pfPassword.getPassword());
    String cpasswd = String.valueOf(pfConfirmPassword.getPassword());
    String email = tfEmail.getText();
    boolean ok = true;
    if (!passwd.equals(cpasswd)) {
        JOptionPane.showMessageDialog(null, bundle.getString("passwdWronglyConfirmed"));
        ok = false;
    }
    StringTokenizer st = new StringTokenizer(email, "@");
    if (st.countTokens() != 2) {
        JOptionPane.showMessageDialog(null, bundle.getString("wrongEmail"));
        ok = false;
    }
    if (ok) {
        try {
            int register = mw.getService().register(login, passwd, name, lastname, email);
            switch (register) {
                case -1:
                    JOptionPane.showMessageDialog(null, bundle.getString("serversError"));

                    break;
                case -2:
                    JOptionPane.showMessageDialog(null, bundle.getString("userAlreadyExistsWithLogin"));
                    break;
                default:
                    dispose();
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, bundle.getString("youClicked"));
            logger.log(Level.SEVERE, null, ex);
        }
    }

}//GEN-LAST:event_btCreateAccountActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btCreateAccount;
    private javax.swing.JLabel lbConfirmPassword;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbFirstname;
    private javax.swing.JLabel lbLastname;
    private javax.swing.JLabel lbLogin;
    private javax.swing.JLabel lbPassword;
    private javax.swing.JLabel lbRequired;
    private javax.swing.JPasswordField pfConfirmPassword;
    private javax.swing.JPasswordField pfPassword;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfFirstname;
    private javax.swing.JTextField tfLastname;
    private javax.swing.JTextField tfLogin;
    // End of variables declaration//GEN-END:variables
}
