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
public interface LanguageTextIf {

    /**
     * getteri kielelle, joka käytössä
     * @return kieli, joka valittuna
     */
    String getMaa();

    /**
     * Palautaa tekstin propertien tiedostosta whichtextin avulla
     * @param whichText Kertoo mille osalle pitää antaa tekstin
     * @return tekstin halutulle osalle. Palautaa tyhjän Stringin jos whichText ei osoita mitään tiedostosta.
     */
    String getText(String whichText);

    /**
     * Muutaa kieltä, jolla tiedostosta haetaan.
     * @param maa hyväksyttyä stringejä ovat fi, en ja por ja se kertoo mille kielelle käyttöliittymä asetetaan.
     */
    void setMaa(String maa);
    
}
