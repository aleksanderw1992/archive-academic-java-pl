/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksander Wojcik
 */
public class ConnectionJDBC {

    private static final Logger logger = Logger.getLogger(ConnectionJDBC.class.getName());
private java.util.ResourceBundle bundle=java.util.ResourceBundle.getBundle("Bundle");

    private String driver;
    private String databaseUrl;
    private String user;
    private String password;

    public Connection getConnectionJDBC() {
        Connection conn = null;
        this.loadConfigurationFromProperties();
        try {
            Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(databaseUrl, user, password);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e) {
            final String message = bundle.getString("failedToConnectToDatabase");
            logger.log(Level.SEVERE, message, e);
        }
        return conn;
    }
    private void loadConfigurationFromProperties(){
        Properties properties = new Configuration().properties;
        this.driver=properties.getProperty("driver", "com.mysql.jdbc.Driver");
        this.databaseUrl=properties.getProperty("databaseUrl", "jdbc:mysql://db4free.net:3306/awojforum");
        this.password=properties.getProperty("password", "alanga123xx");
        this.user=properties.getProperty("user", "horahora789Qxx");
    }

}
