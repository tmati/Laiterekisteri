/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Luokka jonka tarkoitus on käydä config.properties.txt tiedoston läpi ja hakee tietyt kohdat sieltä stringinä muille.
 * @author jukka
 */
public class LanguageText {
    
    private static LanguageText INSTANCE = null;
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
        if(INSTANCE == null){
            INSTANCE = new LanguageText();
            INSTANCE.PropertiesConstructor();
        }
        return INSTANCE;
    }
    
    /**
     * avaa tiedoston propertiesselle
     */
    private void PropertiesConstructor(){
        try{
            properties.load(new FileInputStream("config.properties.txt"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    /**
     * Palautaa tekstin propertien tiedostosta whichtextin avulla
     * @param whichText Kertoo mille osalle pitää antaa tekstin
     * @return tekstin halutulle osalle. Palautaa tyhjän Stringin jos whichText ei osoita mitään tiedostosta.
     */
    public String getText(String whichText){
        return properties.getProperty(whichText.concat(maa));
    }
    
    /**
     * Muutaa kieltä tiedostosta otetaan.
     * @param maa hyväksyttyä stringejä ovat fi, en ja por ja se kertoo miksi kieleksi se laitaa ne tekstit.
     */
    public void setMaa(String maa){
        if("fi".equals(maa) || "en".equals(maa) || "por".equals(maa)){
            this.maa = maa;
        }
    }
}