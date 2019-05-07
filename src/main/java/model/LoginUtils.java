/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;



/**
 * Tarkistus luokka sisään kirjautumista varten
 *
 * @author tmati
 */
public class LoginUtils implements LoginUtilsIf {

    private ControllerIf controller;

 /**
     * Konstruktori LoginUtils luokalle
     * @param controller controlleri jota tämä luokka käyttää
     */
    public LoginUtils(Controller controller) {
        this.controller = controller;

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
        Kayttaja[] users = controller.haeKaikkiKayttajat();
        for (Kayttaja user : users) {
            if (user.getKayttajatunnus().equals(userName) && user.getSalasana().equals(passWord)) {
                controller.setLoggedIn(user);
                return true;
            }
        }
        return found;
    }
}
