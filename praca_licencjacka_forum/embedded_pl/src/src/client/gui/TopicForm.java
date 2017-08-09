package client.gui;

import common.domain.Post;
import common.domain.Topic;
import common.utils.StringUtils;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aleksander Wojcik
 */
public class TopicForm extends javax.swing.JFrame {

    private static final Logger logger = Logger.getLogger(TopicForm.class.getName());
    private Topic topic;
    private int userId = -1;
    private MainWindow mw;
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");;

    public TopicForm(Topic topic, MainWindow mw, int userId) {
        
        initComponents();
        this.mw = mw;
        super.setLocationRelativeTo(mw);
        this.topic = topic;
        this.userId = userId;
        String title = topic.getTitle();
        this.lbTitle.setText(title);
        this.setTitle(title);
        this.showPosts();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        sepSeparator = new javax.swing.JSeparator();
        spTopics = new javax.swing.JScrollPane();
        plTopics = new javax.swing.JPanel();
        btAddPost = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        spText = new javax.swing.JScrollPane();
        taText = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Bundle"); // NOI18N
        setTitle(bundle.getString("topic")); // NOI18N
        setMinimumSize(new java.awt.Dimension(530, 500));

        lbTitle.setText(bundle.getString("topicsTitle")); // NOI18N
        lbTitle.setName("lbTitle"); // NOI18N

        sepSeparator.setName("sepSeparator"); // NOI18N

        spTopics.setName("spTopics"); // NOI18N

        plTopics.setName("plTopics"); // NOI18N
        plTopics.setLayout(new java.awt.GridLayout(0, 1));
        spTopics.setViewportView(plTopics);

        btAddPost.setText(bundle.getString("answer")); // NOI18N
        btAddPost.setName("btAddPost"); // NOI18N
        btAddPost.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAddPostActionPerformed(evt);
            }
        });

        btCancel.setText(bundle.getString("cancel")); // NOI18N
        btCancel.setName("btCancel"); // NOI18N
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        spText.setName("spText"); // NOI18N

        taText.setName("taText"); // NOI18N
        spText.setViewportView(taText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(sepSeparator, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(spText, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(lbTitle, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spTopics, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btAddPost, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbTitle)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sepSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 9, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spTopics, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spText, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btAddPost)
                    .addComponent(btCancel))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btAddPostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAddPostActionPerformed
        try {

            this.addPost();
            this.taText.setText("");
            this.showPosts();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                    java.text.MessageFormat.format(bundle.getString("generalException")+" {0}", 
                            new Object[] {e.getMessage()}, 
                            bundle.getString("error"), 
                            JOptionPane.ERROR_MESSAGE));
        }
}//GEN-LAST:event_btAddPostActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
        dispose();
    }//GEN-LAST:event_btCancelActionPerformed

    private void showPosts() {
        try {
            Post[] posts = mw.getService().loadPosts(topic.getTopic_id());
            plTopics.removeAll();
            for (int i = 0; i < posts.length; i++) {
                Post post = posts[i];
                PostPanel pp = new PostPanel(post, i, mw);
                pp.setVisible(true);
                plTopics.add(pp);
                plTopics.setVisible(true);
            }
        } catch (Exception ex) {
            logger.log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, bundle.getString("serversError"));

        }
    }

    private void addPost() {
        String content = this.taText.getText();
        if (!StringUtils.isEmpty(content)) {
            try {
                mw.getService().addPost(userId, topic.getTopic_id(), content);
                this.showPosts();
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null, bundle.getString("serversError"));

            }
        } else {
            JOptionPane.showMessageDialog(null, bundle.getString("postCannotBeEmpty"));

        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAddPost;
    private javax.swing.JButton btCancel;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel plTopics;
    private javax.swing.JSeparator sepSeparator;
    private javax.swing.JScrollPane spText;
    private javax.swing.JScrollPane spTopics;
    private javax.swing.JEditorPane taText;
    // End of variables declaration//GEN-END:variables
}
