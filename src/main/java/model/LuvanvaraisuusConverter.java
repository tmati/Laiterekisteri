/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.util.StringConverter;

/**
 *
 * @author tmati
 */
public class LuvanvaraisuusConverter extends StringConverter<Integer> {
    
     private String low;
     private String med;
     private String high;
     
    /**
     * LuvanvaraisuusConverterin construktrio joka ottaa low, med, high arvot(String)
     * @param low arvo joka palautetaan kun annetaan 0 toString metodilla
     * @param med arvo joka palautetaan kun annetaan 1 toString metodilla
     * @param high arvo joka palautetaan kun annetaan 2 toString metodilla
     */
    public LuvanvaraisuusConverter(String low, String med, String high) {
        this.low = low;
        this.med = med;
        this.high = high;
    }

    /**
     * Antaa stringin lukua vastaan hyväksyy luvut 0, 1 ja 2
     * @param object luku jota käytäen annetaan String
     * @return Stringin tai nullin jos int on eri kuin 1, 2 tai 0
     */
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

    /**
     * Palautaa luvun stringiä vastaan
     * @param string stringin josta halutaan sitä vastaavan luvun
     * @return luvun 0-2 josta 2 on default
     */
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