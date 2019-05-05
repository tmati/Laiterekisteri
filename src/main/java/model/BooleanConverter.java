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
     * Muuttaa boolean-arvot selkolukuisiksi.
     * 
     */
    public BooleanConverter(){
    }
    
    public BooleanConverter (String trueString, String falseString) {
        this.trueString = trueString;
        this.falseString = falseString;
    }
    
    public String toString(Boolean object) {
        if (object) {
            return trueString;
        }
        return falseString;
    }

     @Override
    public Boolean fromString(String string) {
        return string.equals(trueString);
    }
}
