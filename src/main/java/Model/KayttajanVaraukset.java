/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Tommi
 */
public class KayttajanVaraukset {
    
    /**
     *
     */
    public KayttajanVaraukset(){
        
    }
    Set<Varaukset> s = new HashSet<>(); 

    /**
     *
     * @param k
     * @param c
     * @return
     */
    public Varaukset[] haeKayttajanVaraukset(Kayttaja k, Controller c){
        
        Varaukset kaikkiV[] = c.haeKaikkiVaraukset();
        ArrayList<Varaukset> list = new ArrayList<>();
        int j = 0;
        for(Varaukset v : kaikkiV){
            if(v.getKayttaja().getId() == k.getId()){
                list.add(v);
            }
        }
        Varaukset[] varaukset = list.toArray(new Varaukset[list.size()]);
       
        return varaukset;
    }
}
