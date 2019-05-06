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
public interface SahkopostiIf {

    /**
     * Metodi joka kutsuu sähköpostin lähettähää säikeessä.
     * Vähentää viivettä joka tulisi ilman säiettä
     * @param vastaanottaja vastaanottajan sähköposti
     * @param viesti lähetettävä viesti
     * @return true jos lähetys onnistuu
     */
    boolean lahetaSahkoposti(String vastaanottaja, String viesti);
    
}
