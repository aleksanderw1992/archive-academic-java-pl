/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package client.facade;

import common.api.Service;

/**
 *
 * @author Aleksander Wojcik
 */
public class Facade {

    private FacadesEnum facade;

    public void setFacade(FacadesEnum facade) {
        this.facade = facade;
    }

    public Service getService() {
        Service intf = null;
        switch (facade) {
            case FasadaJDBC:
                intf = FacadeJDBC.getInstance();
                break;
            case FasadaRMI:
                intf = FacadeRMI.getInstance();

                break;
            default:
                intf = FacadeJDBC.getInstance();
                break;
        }
        return intf;
    }
}
