/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerIf;
import view.View;


/**
 * Tarkistus luokka sisään kirjautumista varten
 *
 * @author tmati
 */
public class LoginUtils implements LoginUtilsIf {

    private ControllerIf kontrolleri;

    /**
     * Konstruktori LoginUtils luokalle
     * @param kontrolleri controlleri jota tämä luokka käyttää
     */
    public LoginUtils(ControllerIf kontrolleri) {
        this.kontrolleri = kontrolleri;
    }

    /**
     * Sisäänkirjautuminen
     *
     * @param userName username-kentän sisältö
     * @param passWord password-kentän sisältö
     * @return true jos käyttäjä/salasanapari on oikea.
     */
    @Override
    public boolean loginProcess(String userName, String passWord) {
        //kontrolleri = new Controller();
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
