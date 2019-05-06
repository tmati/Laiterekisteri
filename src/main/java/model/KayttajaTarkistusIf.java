/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jukka
 */
public interface KayttajaTarkistusIf {

    /**
     * Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja
     *
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */
    boolean emailTarkastus(String tarkastettavaSyote);

    /**
     * Hae käyttäjät tietokannasta ja vertaa niiden sähköposteja
     *
     * @param tarkastettavaSyote uuden käyttäjän parametri
     * @return true jos uniikki
     */
    boolean usernameTarkastus(String tarkastettavaSyote);
    
}
