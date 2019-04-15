/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 *
 * @author jukka
 */
public class Kalenterin_tarvitsemat_toimenpiteet {
    
    
    /**
     * Antaa valitusta resursin kaikki varaukset ArrayListana.
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Kaikki varaukset.
     * @return ArrayListan jossa on kaikki resursin varaukset.
     */
    public ArrayList<Varaukset> ResursinVaraukset(int resurssiId, Varaukset[] varaukset){
        ArrayList<Varaukset> aVaraukset = new ArrayList<Varaukset>();
        for(int i=0; i<varaukset.length; i++){
            if(varaukset[i].getResurssit().getId() == resurssiId){
                aVaraukset.add(varaukset[i]);   
                System.out.println("Valitun resursin Id = " + resurssiId + " varauksen Id =  " + varaukset[i].getResurssit().getId());

            }
        }
        
        return aVaraukset;
        
    }
    
    /**
     * Kertoo onko varaus mahdollinen edellisten varausten kanssa.
     * @param aVaraukset Resursin muut varaukset.
     * @param endDate Uuden varauksen loppupäivä.
     * @param startDate Uuden varauksen aloituspäivä.
     * @param endTime Uuden varauksen lopetusaika.
     * @param startTime Uuden varauksen aloitamisaika
     * @return Truen jos ei ole päälekäisyyksiä varaksen kohdalla ja Fasle jos on.
     */
    public boolean Onnistuu(ArrayList<Varaukset> aVaraukset, LocalDate endDate, LocalDate startDate, LocalTime endTime, LocalTime startTime){
        for (Varaukset varaukset : aVaraukset) {
            //if (varaukset.getAlkuAika().getMonthValue() <= endDate.getMonthValue() || varaukset.getLoppuAika().getDayOfMonth() >= startDate.getDayOfMonth()) {
                if (varaukset.getAlkuAika().getMonthValue() == startDate.getMonthValue() && varaukset.getAlkuAika().getMonthValue() == endDate.getMonthValue() && varaukset.getLoppuAika().getMonthValue() == startDate.getMonthValue()) {
                    if(startDate.getDayOfMonth() == endDate.getDayOfMonth() && startTime.getHour() > endTime.getHour()){
                        return false;    
                    }else if(startDate.getDayOfMonth() > endDate.getDayOfMonth()){
                        return false;    
                    }
                    if (varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() > startTime.getHour() && varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            return false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()) {
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            return false;
                        }
                    }else if (varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            return false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getLoppuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        return false;
                    } else if (varaukset.getAlkuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        return false;
                    }
                }else if(varaukset.getAlkuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getAlkuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        return false;
                    }else if(varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            return false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getLoppuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getLoppuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        return false;
                    }else if(varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            return false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() < endDate.getMonthValue() && varaukset.getLoppuAika().getMonthValue() > startDate.getMonthValue() ){
                    return false;
                }else if(startDate.getDayOfMonth() > endDate.getDayOfMonth() && startDate.getMonthValue() == endDate.getMonthValue() ){
                    return false;
                }else if(startDate.getMonthValue() > endDate.getMonthValue() ){
                    return false;
                }else if(startTime.getHour() > endTime.getHour() && startDate.getMonthValue() == endDate.getMonthValue() ){
                    return false;
                }
            }
        //}
        return true;
    }
    
    /**
     * Metodi, joka muuttaa Varaukset ArrayListan Arrayhin.
     * @param aVaraukset muutettava ArrayList lista yhden resurssin kaikista varauksista.
     * @return varaus Array
     */
    public Varaukset[] getVaraus(ArrayList<Varaukset> aVaraukset){
        Varaukset[] varaus = new Varaukset[aVaraukset.size()];
                for(int i=0; i<aVaraukset.size(); i++){
                    varaus[i] = aVaraukset.get(i);
                }
        return varaus;
    }
}
