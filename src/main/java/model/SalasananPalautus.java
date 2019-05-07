/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerIf;

/**
 *
 * @author Tommi
 */
public class SalasananPalautus implements SalasananPalautusIf {
    private ControllerIf controller;
    private static final String MERKIT = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz"; 
    private final StringBuilder sb = new StringBuilder(8);
    
    /**
     * SalasananPalautus luokan constructori
     * @param c controlleri jota tämä käyttää
     */
    public SalasananPalautus(ControllerIf c){
        this.controller = c;
    }
    
    /**
     * lähettää sähköpostin käyttäjälle jonka sähköposti on sama kuin tämän medotin email
     * @param email mihin sähköposti osoiteeseen lähetetään posti
     * @return palautaa truen jos lähetys onnistui
     */
    @Override
    public boolean palautaSalasana(String email){
        Kayttaja[] kayttajat = controller.haeKaikkiKayttajat();
        for(Kayttaja k : kayttajat){
            if (k.getSahkoposti().equals(email)){
                String salasana = this.getRandomSalasana();
                k.setSalasana(controller.salasananCryptaus(salasana));
                controller.paivitaKayttaja(k);
                controller.lahetaSahkoposti(k.getSahkoposti(), controller.getConfigTeksti("emailReturnPassword") + salasana
                + controller.getConfigTeksti("emailReturnPasswordCon"));
                return true;
            }
        }
    return false;
    }
    
    /**
     * Luo random generoidun salasanan jonka se palauttaa
     * @return random generoidu salasana
     */
    private String getRandomSalasana(){
         for (int i = 0; i < 9; i++) { 
            int index = (int)(MERKIT.length() * Math.random()); 
            sb.append(MERKIT.charAt(index)); 
        } 
        return sb.toString(); 
    }
    
}
