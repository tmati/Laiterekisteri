/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.util.StringConverter;

/**
 * Käsittelee true-false arvoja käyttäjäystävällisemmäksi
 * @author tmati
 */
public class BooleanConverter extends StringConverter<Boolean> {
    
 
     private String trueString;
     private String falseString;
     
     /**
      * Tyhjä konstruktio
      */
    public BooleanConverter(){
    }
    
     /**
      * BooleanConverterin construktori johon asetetaan kaksi Stringiä jotka muutetaan true tai falseksi
      * @param trueString Stringin jonka arvoksi tulee true
      * @param falseString String jonka arvoksi tulee false
      */
    public BooleanConverter (String trueString, String falseString) {
        this.trueString = trueString;
        this.falseString = falseString;
    }

    
   
    
    /**
     * Antaa booleania vastaava stringin
     * @param object Boolaen arvo josta halutaan sitä vastaava String
     * @return Stringin joka vastaa boolean arvoa
     */
    @Override
    public String toString(Boolean object) {
        if (object) {
            return trueString;
        }
        return falseString;
    }

    /**
     * Vertaa onko string sama kuin true string
     * @param string verratava string
     * @return truen jos on sama kuin tosi String ja false muissa tapauksissa
     */
     @Override
    public Boolean fromString(String string) {
        return trueString.equals(string);
    }
}
