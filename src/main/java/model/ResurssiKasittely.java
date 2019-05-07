/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.*;
import java.util.Objects;

/**
 * Luokka resurssien käsittelyä varten
 * @author Tommi
 */
public class ResurssiKasittely implements ResurssiKasittelyIf {

    private ControllerIf controller;

    /**
     *  Konstruktori
     * @param controller viite controller -luokkaan
     */
    public ResurssiKasittely(Controller controller) {
        this.controller = controller;
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
