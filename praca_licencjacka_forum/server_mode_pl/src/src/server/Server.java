/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import common.domain.Post;
import common.domain.Topic;
import common.domain.User;
import java.net.MalformedURLException;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.Logger;

/**
 *
 * @author Aleksander Wojcik
 */
public class Server extends UnicastRemoteObject implements common.api.Service {

    private static final Logger logger = Logger.getLogger(Server.class.getName());
    private static Connection conn = null;

    public Server() throws RemoteException {
        super(0);
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, Exception, MalformedURLException {

        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection(
                "jdbc:mysql://localhost/test?"
                + "user=monty&password=greatsqldb",
                "myLogin",
                "myPassword");
        try {
            Server server = startServer();
        } finally {
            try {
                conn.close();
            } catch (Throwable ignore) {
            }
        }
    }

    private static Server startServer() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            LocateRegistry.createRegistry(1099);
            logger.info("java RMI registry created.");
        } catch (Exception e) {
            //do nothing, error means registry already exists
            logger.info("java RMI registry already exists.");
        }

        //Instantiate RmiServer
        Server obj = null;
        try {
            obj = new Server();
        } catch (Exception e) {
        }
        try {
            Naming.rebind("//localhost/RmiServer", obj);
            logger.info("PeerServer bound in registry");
        } catch (MalformedURLException e) {
        } catch (Exception e) {
        }
        return obj;
    }

    @Override
    public int logIn(String login, String passwd) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int register(String login, String password, String imie, String nazwisko, String email) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addTopic(int user_id, String tytul, String opis) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addPost(int user_id, int topic_id, String tresc) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Post[] loadPosts(int temat_id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Topic[] loadTopics() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public User getUser(int user_id) throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
