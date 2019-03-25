/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import View.View;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 * Tarkistus luokka sisään kirjautumista varten
 *
 * @author tmati
 */
public class LoginUtils {

    private Controller kontrolleri;

    public LoginUtils(Controller kontrolleri) {
        this.kontrolleri = kontrolleri;
    }

    /**
     * Sisäänkirjautuminen
     *
     * @param userName username-kentän sisältö
     * @param passWord password-kentän sisältö
     * @return true jos käyttäjä/salasanapari on oikea.
     */
    public boolean loginProcess(String userName, String passWord) {
        kontrolleri = new Controller();
        boolean found = false;
        Kayttaja[] users = kontrolleri.haeKaikkiKayttajat();
        for (Kayttaja user : users) {
            if (user.getKayttajatunnus().equals(userName) && user.getSalasana().equals(passWord)) {
                View.loggedIn = user;
                return true;
            }
        }
        return found;
    }
}
