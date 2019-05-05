/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.LocalDateTime;

/**
 * Luokan tarkoitus on laskea päivien erotuksen käytämällä kahta eri LocalDateTime muutujaa.
 * @author jukka
 */
public interface VarauksenAikaLaskuriInterface {
    
    /**
     * Laskee kuinka paljon päiviä on alkupvm ja paatymispvm välillä.
     * @param alkupvm mistä päivät alkavat päivät.
     * @param paatymispvm mihin päivät loppuvat.
     * @return palautaa näiden kahden päivien erotukset asiakaalle.
     */
    public int paivaKesto(LocalDateTime alkupvm, LocalDateTime paatymispvm);
    
}
