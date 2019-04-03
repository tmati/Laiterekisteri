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
    
    public BooleanConverter(Controller kontrolleri){
        this.kontrolleri = kontrolleri;
    }
    
    public String toString(Boolean object) {
        if (object) {
            return "Varattavissa";
        }
        return "Ei varattavissa";
    }

     @Override
    public Boolean fromString(String string) {
        return string.equals("Varattavissa");
    }
}
