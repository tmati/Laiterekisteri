/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import javafx.util.StringConverter;

/**
 * Käsittelee true-false arvoja käyttäjäystävällisemmäksi
 * @author tmati
 */
public class BooleanConverter extends StringConverter<Boolean> {
    
     private Controller kontrolleri;
     String TrueString;
     String FalseString;
    /**
     * Muuttaa boolean-arvot selkolukuisiksi.
     * @param kontrolleri 
     */
    public BooleanConverter(Controller kontrolleri){
        this.kontrolleri = kontrolleri;
    }
    
    public BooleanConverter (Controller kontrolleri, String TrueString, String FalseString) {
        this.kontrolleri = kontrolleri;
        this.TrueString = TrueString;
        this.FalseString = FalseString;
    }
    
    public String toString(Boolean object) {
        if (object) {
            return TrueString;
        }
        return FalseString;
    }

     @Override
    public Boolean fromString(String string) {
        return string.equals(TrueString);
    }
}
