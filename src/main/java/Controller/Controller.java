/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.ResurssitAccessObject;
import Model.VarauksetAccessObject;
import Model.KayttajaTarkistus;

/**
 * Controlleri
 * @author Tommi
 */
public class Controller {

    private KayttajaAccessObject kayttajaDAO;
    private ResurssitAccessObject resurssiDAO;
    private VarauksetAccessObject varausDAO;
    private KayttajaTarkistus kayttajaTarkistus;

    /**
     *Controllerin konstruktio
     */
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
    }
    
    /**
     * Hakee modelista kaikki käytäjät
     * @return
     */
    public Kayttaja[] haeKaikkiKayttajat(){
        return kayttajaDAO.readKayttajat();
    }
    
    /**
     * hakee modelista käytäjän
     * @param id
     * @return
     */
    public Kayttaja haeKayttaja(int id){
        return kayttajaDAO.readKayttaja(id);
    }
    
    /**
     * vie uuden käytäjän modeliin
     * @param kayttaja
     * @return
     */
    public boolean vieUusiKayttajaTietokantaan(Kayttaja kayttaja){
        return kayttajaDAO.createKayttaja(kayttaja);
    }
    
    /**
     * vie käytäjän modeliin
     * @param kayttaja
     * @return
     */
    public boolean paivitaKayttaja(Kayttaja kayttaja){
        return kayttajaDAO.updateKayttaja(kayttaja);
    }
    
    /**
     * vie käytäjän poistettavaksi
     * @param id
     * @return
     */
    public boolean poistaKayttaja(int id){
        return kayttajaDAO.deleteKayttaja(id);
    }
    
    /**
     * tarkistaa modelista käyttäjätunnuksen.
     * @param username
     * @return
     */
    public boolean tarkistaUsername(String username){
        return kayttajaTarkistus.usernameTarkastus(username);
    }
    
    /**
     * tarkistaa modelista sähköpostin.
     * @param email
     * @return
     */
    public boolean tarkistaEmail(String email){
        return kayttajaTarkistus.emailTarkastus(email);
    }
    
    
    
    
    
    

}
