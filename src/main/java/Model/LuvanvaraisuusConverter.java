/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import javafx.util.StringConverter;

/**
 *
 * @author tmati
 */
public class LuvanvaraisuusConverter extends StringConverter<Integer> {
    
     private String low;
     private String med;
     private String high;
     private Controller kontrolleri;
    public LuvanvaraisuusConverter(Controller kontrolleri){
        this.kontrolleri = kontrolleri;
    }
    
    public LuvanvaraisuusConverter(Controller kontrolleri, String low, String med, String high) {
        this.kontrolleri = kontrolleri;
        this.low = low;
        this.med = med;
        this.high = high;
    }

    @Override
    public String toString(Integer object) {
        switch(object) {
            case 0:
                return low;
            case 1:
                return med;
            case 2:
                return high;
            default:
                break;
        }
        return null;
    }

    @Override
    public Integer fromString(String string) {
        if (string.equals(low)) {
            return 0;
        } else if (string.equals(med)) {
            return 1;
        } else {
            return 2;
        }
    }
}