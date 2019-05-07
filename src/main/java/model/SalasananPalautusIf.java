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
public interface SalasananPalautusIf {

    /**
     * lähettää sähköpostin käyttäjälle jonka sähköposti on sama kuin tämän medotin email
     * @param email mihin sähköposti osoiteeseen lähetetään posti
     * @return palautaa truen jos lähetys onnistui
     */
    boolean palautaSalasana(String email);
    
}
