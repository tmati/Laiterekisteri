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
 *
 * @author Tommi
 */
public class Controller {

    private KayttajaAccessObject kayttajaDAO;
    private ResurssitAccessObject resurssiDAO;
    private VarauksetAccessObject varausDAO;
    private KayttajaTarkistus kayttajaTarkistus;
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
    }
    
    public Kayttaja[] haeKaikkiKayttajat(){
        return kayttajaDAO.readKayttajat();
    }
    
    public Kayttaja haeKayttaja(int id){
        return kayttajaDAO.readKayttaja(id);
    }
    
    public boolean vieUusiKayttajaTietokantaan(Kayttaja kayttaja){
        return kayttajaDAO.createKayttaja(kayttaja);
    }
    
    public boolean paivitaKayttaja(Kayttaja kayttaja){
        return kayttajaDAO.updateKayttaja(kayttaja);
    }
    
    public boolean poistaKayttaja(int id){
        return kayttajaDAO.deleteKayttaja(id);
    }
    
    public boolean tarkistaUsername(String username){
        return kayttajaTarkistus.usernameTarkastus(username);
    }
    
    public boolean tarkistaEmail(String email){
        return kayttajaTarkistus.emailTarkastus(email);
    }
    
    
    
    

}
