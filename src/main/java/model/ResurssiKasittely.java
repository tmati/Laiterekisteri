/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.ControllerIf;
import java.util.Objects;

/**
 * Luokka resurssien käsittelyä varten
 * @author Tommi
 */
public class ResurssiKasittely implements ResurssiKasittelyIf {

    ControllerIf controller;

    /**
     *  Konstruktori
     * @param c viite controller -luokkaan
     */
    public ResurssiKasittely(ControllerIf c) {
        this.controller = c;
    }

    /**
     *  Poistaa kaikki haluttuun resurssiin liittyvät varaukset
     * @param r Resurssi jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    
    @Override
    public boolean poistaResurssinVaraukset(Resurssit r){
        Varaukset[] varaukset = controller.haeKaikkiVaraukset();
        boolean tarkistus = true;
        for (Varaukset v : varaukset) {
            if (Objects.equals(v.getResurssit().getId(), r.getId())) {
                boolean tulos = controller.poistaVaraus(v.getId());
                if (!tulos) {
                    tarkistus = tulos;
                }
            }
        }
        return tarkistus;
    }
}
