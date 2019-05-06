/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author jukka
 */
public interface ResurssiKasittelyIf {

    /**
     *  Poistaa kaikki haluttuun resurssiin liittyvät varaukset
     * @param r Resurssi jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    boolean poistaResurssinVaraukset(Resurssit r);
    
}
