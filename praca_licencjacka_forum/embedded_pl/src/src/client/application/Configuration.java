/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package client.application;

import client.facade.FacadesEnum;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aleksander Wojcik
 */
public class Configuration {
    public static final String PATH = "./resources/config.properties";
    private static final Logger logger = Logger.getLogger(Configuration.class.getName());
            protected Properties properties;
    protected Configuration (){
        properties = new Properties();
        try {
            final InputStream stream = new FileInputStream(PATH);
            properties.load(stream);
        } catch (IOException iOException) {
            logger.log(Level.SEVERE, iOException.getMessage());
        }
    }
    protected FacadesEnum getFacade(){
        final String property = properties.getProperty("fasada", "JDBC").trim();
        if ("JDBC".equals(property)) return FacadesEnum.FasadaJDBC;
        return FacadesEnum.FasadaRMI;
    }
}
