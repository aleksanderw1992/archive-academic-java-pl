/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.facade;

import common.domain.Post;
import common.domain.Topic;
import common.domain.User;
import server.Server;
import java.net.MalformedURLException;
import java.rmi.*;

/**
 *
 * @author Aleksander Wojcik
 */
public class FacadeRMI implements common.api.Service {

    private static volatile FacadeRMI instance = null;
    private static Server server = null;
    
    private FacadeRMI() {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new RMISecurityManager());
        }
        try {
            server = (Server) Naming.lookup("//localhost/RmiServer");
        } catch (NotBoundException | MalformedURLException | RemoteException e) {
            try {
                server = new Server();
            } catch (RemoteException remoteException) {
            }
        }
    }

    public static FacadeRMI getInstance() {
        if (instance == null) {
            synchronized (FacadeRMI.class) {
                if (instance == null) {
                    instance = new FacadeRMI();
                }
            }
        }
        return instance;
    }

    @Override
    public int logIn(String login, String passwd) throws java.rmi.RemoteException, Exception {
        return server.logIn(login, passwd);
    }

    @Override
    public int register(String login, String password, String imie, String nazwisko, String email) throws java.rmi.RemoteException, Exception {
        return server.register(login, password, imie, nazwisko, email);
    }

    @Override
    public int addTopic(int user_id, String tytul, String opis) throws java.rmi.RemoteException, Exception {
        return server.addTopic(user_id, tytul, opis);
    }

    @Override
    public int addPost(int user_id, int topic_id, String tresc) throws java.rmi.RemoteException, Exception {
        return server.addPost(user_id, topic_id, tresc);
    }

    @Override
    public Post[] loadPosts(int temat_id) throws java.rmi.RemoteException, Exception {
        return server.loadPosts(temat_id);
    }

    @Override
    public Topic[] loadTopics() throws java.rmi.RemoteException, Exception {
        return server.loadTopics();
    }

    @Override
    public User getUser(int user_id) throws java.rmi.RemoteException, Exception {
        return server.getUser(user_id);
    }

}
