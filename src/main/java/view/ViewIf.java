/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.ControllerIf;
import controller.Controller;

import javafx.stage.Stage;

/**
 *
 * @author jukka
 */
public interface ViewIf {

    /**
     * Yrityksen nimi
     */
    String BIZNAME = "KeyChain Enterprise Management";
    /**
     * Controller - ilmentymä
     */
    ControllerIf CONTROLLER = new Controller();

    /**
     *
     * @param stage Stage
     * @throws Exception Varauduttava poikkeus
     */
    void start(Stage stage) throws Exception;
    
}
