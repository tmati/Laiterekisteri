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
    private boolean onnistuu = true;
    
    
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
     * kertoo onko varaus mahdollinen edellisten varauksten kanssa.
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
                    if (varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() > startTime.getHour() && varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()) {
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()) {
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }else if (varaukset.getLoppuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getLoppuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        onnistuu = false;
                    } else if (varaukset.getAlkuAika().getDayOfMonth() > startDate.getDayOfMonth() && varaukset.getAlkuAika().getDayOfMonth() < endDate.getDayOfMonth()) {
                        onnistuu = false;
                    }
                }else if(varaukset.getAlkuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getAlkuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getAlkuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        onnistuu = false;
                    }else if(varaukset.getAlkuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        if (varaukset.getAlkuAika().getHour() < endTime.getHour()) {
                            onnistuu = false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() == endDate.getMonthValue() || varaukset.getLoppuAika().getMonthValue() == startDate.getMonthValue() ){
                    if(varaukset.getLoppuAika().getDayOfMonth() == endDate.getDayOfMonth()){
                        onnistuu = false;
                    }else if(varaukset.getLoppuAika().getDayOfMonth() == startDate.getDayOfMonth()){
                        if (varaukset.getLoppuAika().getHour() > startTime.getHour()) {
                            onnistuu = false;
                        }
                    }
                }else if(varaukset.getLoppuAika().getMonthValue() < endDate.getMonthValue() && varaukset.getLoppuAika().getMonthValue() > startDate.getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getMonthValue() < endDate.getMonthValue() && varaukset.getAlkuAika().getMonthValue() > startDate.getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getDayOfMonth() > varaukset.getLoppuAika().getDayOfMonth() && varaukset.getAlkuAika().getMonthValue() == varaukset.getLoppuAika().getMonthValue() ){
                    onnistuu = false;
                }else if(varaukset.getAlkuAika().getMonthValue() > varaukset.getLoppuAika().getMonthValue() ){
                    onnistuu = false;
                }
            }
        //}
        return onnistuu;
    }
    
    /**
     * Medoti joka muutaa Varauksetr ArrayListan Arrayhin.
     * @param aVaraukset muutettava ArrayList
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
