/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author jukka
 */
public interface KayttajaAdminControllerIf extends Initializable {

    /**
     * Initializes the CONTROLLER class.
     *
     * @param url url
     * @param rb resourcebundle
     */
    void initialize(URL url, ResourceBundle rb);

    /**
     * Logout. Palauttaa käyttäjän kirjautumisnäkymään ja kirjaa tämän ulos.
     *
     * @param event MouseEvent
     * @throws IOException IOException
     */
    @FXML
    void logout(MouseEvent event) throws IOException;

    /**
     * Päivittää käyttäjä -taulun.
     *
     */
    @FXML
    void updateBtnPainettu();
    
}
