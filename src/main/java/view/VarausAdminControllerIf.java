/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jukka
 */
public interface VarausAdminControllerIf extends Initializable {

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url URL
     * @param rb ResourceBundle
     */
    void initialize(URL url, ResourceBundle rb);

    /*
     * Kirjaa käyttäjän ulos.
     * @param event Painikkeen klikkaus
     * @throws IOException IOException
     */
    void logout(MouseEvent event) throws IOException;

    /**
     * Päivittää napin ulkonäön.
     * @param event Painikkeen klikkaus.
     */
    void updateBtnPainettu(MouseEvent event);
    
}
