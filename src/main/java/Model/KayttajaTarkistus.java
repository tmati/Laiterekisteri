/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;

/**
 *
 * @author Tommi
 */
public class KayttajaTarkistus {

    private Controller kontrolleri;

    public KayttajaTarkistus(Controller kontrolleri) {
        this.kontrolleri = kontrolleri;
    }

    /**
     * Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */
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
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */    
    public boolean emailTarkastus(String tarkastettavaSyote) {
        Kayttaja[] kayttajat = kontrolleri.haeKaikkiKayttajat();
        for (Kayttaja kayttaja : kayttajat) {
            if (kayttaja.getSahkoposti().equals(tarkastettavaSyote)){
                return false;
            }
        }
        return true;
    }
}
