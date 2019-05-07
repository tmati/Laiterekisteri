/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;

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
    
    public SalasananPalautus(Controller controller){
        this.controller = controller;
    }
    
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
    
    private String getRandomSalasana(){
         for (int i = 0; i < 9; i++) { 
            int index = (int)(MERKIT.length() * Math.random()); 
            sb.append(MERKIT.charAt(index)); 
        } 
        return sb.toString(); 
    }
    
}
