/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.facade;

import client.application.ConnectionJDBC;
import common.domain.Post;
import common.domain.Topic;
import common.domain.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 * @author Aleksander Wojcik
 */
public class FacadeJDBC implements common.api.Service {

    private static final Logger logger = Logger.getLogger(FacadeJDBC.class.getName());
    private Connection conn;
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");;

    private FacadeJDBC() {
        
        this.conn = new ConnectionJDBC().getConnectionJDBC();
    }

    private static volatile FacadeJDBC instance = null;

    public static FacadeJDBC getInstance() {
        if (instance == null) {
            synchronized (FacadeJDBC.class) {
                if (instance == null) {
                    instance = new FacadeJDBC();
                }
            }
        }
        return instance;
    }

    @Override
    public int logIn(String login, String passwd) {
        int ind = -1;
        try {
            String query = "select user_id from "+User.TABLE+" where login = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, passwd);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ind = rs.getInt(1);
            }
        } catch (SQLException e) {
            logger.info("sthWentWrong");
            e.printStackTrace();
        }
        return ind;
    }

    @Override
    public int register(String login, String password, String firstName, String lastName, String email) {
        int ind = -1;
        try {
            String query;
            PreparedStatement ps;
            StringBuilder sb = new StringBuilder();
            sb
                    .append("select count(*) from "+User.TABLE+" where "+User.TABLE+".login = ")
                    .append("'")
                    .append(login)
                    .append("'");
            query = sb.toString();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery(query);
            rs.next();
            int result = rs.getInt(1);
            if (result != 0) {
                return -2;
            }
            query = "insert into" + User.INSERT + "values(?,?,?,?,?)";
            ps = conn.prepareStatement(query);
//            ps.setInt(1, ind);
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setString(3, firstName);
            ps.setString(4, lastName);
            ps.setString(5, email);
            ind = ps.executeUpdate();
            StringBuilder sb2 = new StringBuilder();
            sb2.append(bundle.getString("changed"))
                    .append(ind)
                    .append(bundle.getString("records"));
            logger.info(sb2.toString());
        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return ind;
    }

    @Override
    public int addTopic(int user_id, String title, String description) {
        int ind = -1;
        try {
            String query;
//            query = "select count(*) from topic";
//            Statement stmt = conn.createStatement();
//            ResultSet rs = stmt.executeQuery(query);
//            rs.next();
//            ind = rs.getInt(1) + 1;
            query = "insert into " + Topic.INSERT + " values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
//            ps.setInt(1, ind);
            ps.setInt(1, user_id);
            ps.setString(2, title);
            ps.setInt(3, 0);
            ps.setString(4, description);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return ind;
    }

    @Override
    public int addPost(int user_id, int topic_id, String content) {
        int ind = -1;
        try {
            String query;


            query = "insert into " + Post.INSERT + "values(?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(query);
//            ps.setInt(1, ind);
            ps.setInt(1, user_id);
            ps.setInt(2, topic_id);
//            ps.setString(4, "default");//?? to jest do daty
            ps.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            ps.setString(4, content);
            ps.executeUpdate();

            query = "update "+Topic.TABLE+" set last_post=? where topic_id=?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, ind);
            ps.setInt(2, topic_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return ind;
    }

    @Override
    public Post[] loadPosts(int topicId) {
        Post[] posts = null;
        try {
            String query = "select * from "+Post.TABLE+" where topic_id= " + topicId;
            Statement stmt = conn.createStatement();
            logger.info(topicId + ": topicId");
            ResultSet rs = stmt.executeQuery(query);
            Post post;
            List<Post> p = new ArrayList<>();
            while (rs.next()) {
                int post_id = rs.getInt(1);
                int autor_id = rs.getInt(2);
                int topic_id = rs.getInt(3);
                Date date = rs.getDate(4);
                String content = rs.getString(5);
                post = new Post(post_id, autor_id, topic_id, date, content);
                p.add(post);
            }
            posts = new Post[p.size()];
            int i = 0;
            for (Iterator<Post> it = p.iterator(); it.hasNext();) {
                post = it.next();
                posts[i] = post;
                i++;
            }
            logger.info(Arrays.toString(posts));
        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Topic[] loadTopics() {
        Topic[] top = null;
        try {
            String query = "select * from "+Topic.TABLE;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            Topic topic;
            List<Topic> t = new ArrayList<Topic>();
            while (rs.next()) {
                int id = rs.getInt(1);
                int id_autor = rs.getInt(2);
                String title = rs.getString(3);
                int last_post = rs.getInt(4);
                String content = rs.getString(5);
                topic = new Topic(id, id_autor, title, last_post, content);
                t.add(topic);
            }
            top = new Topic[t.size()];
            int i = 0;
            for (Topic topic1 : t) {
                top[i] = topic1;
                i++;
            }
        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return top;
    }

    @Override
    public User getUser(int userId) {
        User user = null;
        try {
            String query = "select * from "+User.TABLE+" where user_id=" + userId;
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String login = rs.getString(2);
            String password = rs.getString(3);
            String firstname = rs.getString(4);
            String lastname = rs.getString(5);
            String email = rs.getString(6);
            user = new User(userId, login, password, firstname, lastname, email);
            logger.info(user.toString());
        } catch (SQLException e) {
            logger.info(bundle.getString("sthWentWrong"));
            e.printStackTrace();
        }
        return user;
    }
}
