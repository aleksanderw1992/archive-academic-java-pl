package client.gui;

import common.domain.User;
import common.utils.StringUtils;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksander Wojcik
 */
public class AddNewTopicWindow extends javax.swing.JDialog {

    private User user;
    private MainWindow mw = null;
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");

    public AddNewTopicWindow(MainWindow mw, boolean modal, User user) {
        super(mw, modal);
        
        initComponents();
        this.mw = mw;
        this.user = user;
        super.setLocationRelativeTo(mw);
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        lbTitle = new javax.swing.JLabel();
        lbContnet = new javax.swing.JLabel();
        tfTitle = new javax.swing.JTextField();
        btAddTopic = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        taContent = new javax.swing.JEditorPane();

        jScrollPane2.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("typeInTitleAndConent")); // NOI18N

        lbTitle.setText(bundle.getString("title:")); // NOI18N

        lbContnet.setText(bundle.getString("content:")); // NOI18N

        tfTitle.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tfTitleActionPerformed(evt);
            }
        });

        btAddTopic.setText(bundle.getString("ok")); // NOI18N
        btAddTopic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddTopicActionPerformed(evt);
            }
        });

        btCancel.setText(bundle.getString("cancel")); // NOI18N
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jScrollPane3.setViewportView(taContent);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 314, Short.MAX_VALUE)
                        .addComponent(btAddTopic, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbContnet)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTitle)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(85, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(lbTitle))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(tfTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbContnet)
                .addGap(249, 249, 249)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddTopic)
                    .addComponent(btCancel))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(54, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(48, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddTopicActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddTopicActionPerformed

        if (StringUtils.isEmpty(tfTitle.getText()) || StringUtils.isEmpty(taContent.getText())) {
            JOptionPane.showMessageDialog(this, bundle.getString("typeInNewTitleAndConent"), bundle.getString("error"), JOptionPane.ERROR_MESSAGE);
        } else {
            addTopic();
            dispose();
        }
    }//GEN-LAST:event_btAddTopicActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btCancelActionPerformed

    private void tfTitleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tfTitleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tfTitleActionPerformed

    public void addTopic() {
        String title = tfTitle.getText();
        String zawartosc = taContent.getText();
        if (!StringUtils.isEmpty(title)) {
        mw.addTopic(title, zawartosc);
        }else{
            JOptionPane.showMessageDialog(this, bundle.getString("titleCannotBeEmpty"), bundle.getString("error"), JOptionPane.ERROR_MESSAGE);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddTopic;
    private javax.swing.JButton btCancel;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lbContnet;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JEditorPane taContent;
    private javax.swing.JTextField tfTitle;
    // End of variables declaration//GEN-END:variables
}
