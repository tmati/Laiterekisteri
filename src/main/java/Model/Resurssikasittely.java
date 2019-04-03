/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import java.util.Objects;

/**
 * Luokka resurssien käsittelyä varten
 * @author Tommi
 */
public class Resurssikasittely {

    Controller controller;

    /**
     *  Konstruktori
     * @param c viite controller -luokkaan
     */
    public Resurssikasittely(Controller c) {
        this.controller = c;
    }

    /**
     *  Poistaa kaikki haluttuun resurssiin liittyvät varaukset
     * @param r Resurssi jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    
    public boolean poistaResurssinVaraukset(Resurssit r) {
        Varaukset[] varaukset = controller.haeKaikkiVaraukset();
        boolean tarkistus = true;
        for (Varaukset v : varaukset) {
            if (Objects.equals(v.getResurssit().getId(), r.getId())) {
                System.out.println("testi");
                boolean tulos = controller.poistaVaraus(v.getId());
                if (!tulos) {
                    tarkistus = tulos;
                }
            }
        }
        return tarkistus;
    }
}
