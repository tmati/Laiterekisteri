/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Kayttaja;
import Model.KayttajaAccessObject;

/**
 *
 * @author Tommi
 */
public class KayttajaController {

    private KayttajaAccessObject kayttajaDAO;

    public KayttajaController() {
        kayttajaDAO = new KayttajaAccessObject();
    }
    
    public Kayttaja[] haeKaikkiKayttajat(){
        return kayttajaDAO.readKayttajat();
    }
    
    public Kayttaja haeKayttaja(int id){
        return kayttajaDAO.readKayttaja(id);
    }
    
    

}
