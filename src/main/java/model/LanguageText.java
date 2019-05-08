/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;

/**
 * Luokka jonka tarkoitus on käydä config.properties.txt tiedoston läpi ja hakee tietyt kohdat sieltä stringinä muille.
 * @author jukka
 */
public class LanguageText implements LanguageTextIf {
    
    private static LanguageText instance = null;
    private final Properties properties = new Properties();
    private String maa = "en";
    
    /**
     * Yksityinen konstruktori jotta toteutaisi singletonin periaatetta
     */
    private LanguageText() {
        
    }
    
    /**
     * Palautaa itsensä ja luo itsensä ja muodostaa yhteyden tiedostoon jos ei ole jo aikaisemmin tehty.
     * @return Instansin LanguageText luokasta
     */
    public static LanguageText getInstance() {
        if(instance == null){
            instance = new LanguageText();
            instance.propertiesConstructor();
        }
        return instance;
    }
    
    /**
     * avaa tiedoston propertiesselle
     */
    private void propertiesConstructor(){
        try{
            properties.load(new FileInputStream("config.properties.txt"));
        }catch(IOException e){
              Istunto.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }
    
    /**
     * Palautaa tekstin propertien tiedostosta whichtextin avulla
     * @param whichText Kertoo mille osalle pitää antaa tekstin
     * @return tekstin halutulle osalle. Palautaa tyhjän Stringin jos whichText ei osoita mitään tiedostosta.
     */
    @Override
    public String getText(String whichText){
        return properties.getProperty(whichText.concat(maa));
    }
    
    /**
     * Muutaa kieltä, jolla tiedostosta haetaan.
     * @param maa hyväksyttyä stringejä ovat fi, en ja por ja se kertoo mille kielelle käyttöliittymä asetetaan.
     */
    @Override
    public void setMaa(String maa){
        if("fi".equals(maa) || "en".equals(maa) || "por".equals(maa)){
            this.maa = maa;
        }else if("pt".equals(maa)){
            this.maa = "pt";
        }
       
    }
    
    /**
     * getteri kielelle, joka käytössä
     * @return kieli, joka valittuna
     */
    @Override
    public String getMaa(){
        return maa;
    }
}
