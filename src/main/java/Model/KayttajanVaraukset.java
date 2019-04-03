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
 * Luokka käyttäjän varausten hakua ja käsittelyä varten
 *
 * @author Tommi
 */
public class KayttajanVaraukset {

    private Controller controller;

    /**
     * Konstruktori
     * @param c viittaus controlleriin
     */
    public KayttajanVaraukset(Controller c) {
        this.controller = c;
    }

    /**
     * Siirtää halutun käyttäjän varaukset taulukkoon
     *
     * @param k käyttäjä jonka varaukset halutaan
     * @return taulukko käyttäjän varauksista
     */
    public Varaukset[] haeKayttajanVaraukset(Kayttaja k) {

        Varaukset kaikkiV[] = controller.haeKaikkiVaraukset();
        ArrayList<Varaukset> list = new ArrayList<>();
        int j = 0;
        for (Varaukset v : kaikkiV) {
            if (v.getKayttaja().getId() == k.getId()) {
                list.add(v);
            }
        }
        Varaukset[] varaukset = list.toArray(new Varaukset[list.size()]);
        return varaukset;
    }
    /**
     * Poistaa kayttajan kaikki varaukset
     * @param id Kayttaja, jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    
    public boolean poistaKayttajanVaraukset(int id){
        Varaukset[] varaukset = controller.haeKaikkiVaraukset();
        boolean tarkistus = true;
        for (Varaukset v : varaukset) {
            if (v.getKayttaja().getId() == id) {
                boolean tulos = controller.poistaVaraus(v.getId());
                if (!tulos) {
                    tarkistus = tulos;
                }
            }
        }
        return tarkistus;
    }
}
