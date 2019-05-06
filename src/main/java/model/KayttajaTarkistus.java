/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerIf;

/**
 * Luokka käyttäjän tietojen tarkistukseen
 *
 * @author Tommi
 */
public class KayttajaTarkistus implements KayttajaTarkistusIf {

    private ControllerIf kontrolleri;

    /**
     * Konstruktori
     *
     * @param kontrolleri viittaus controlleriin
     */
    public KayttajaTarkistus(ControllerIf kontrolleri) {
        this.kontrolleri = kontrolleri;
    }

    /**
     * Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja
     *
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */
    @Override
    public boolean usernameTarkastus(String tarkastettavaSyote) {
        Kayttaja[] kayttajat = kontrolleri.haeKaikkiKayttajat();
        for (Kayttaja kayttaja : kayttajat) {
            if (kayttaja.getKayttajatunnus().equals(tarkastettavaSyote)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja
     *
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */
    @Override
    public boolean emailTarkastus(String tarkastettavaSyote) {
        Kayttaja[] kayttajat = kontrolleri.haeKaikkiKayttajat();
        for (Kayttaja kayttaja : kayttajat) {
            if (kayttaja.getSahkoposti().equals(tarkastettavaSyote)) {
                return false;
            }
        }
        return true;
    }
}
