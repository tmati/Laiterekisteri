/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Luokka varausten käsittelyä varten
 *
 * @author Tommi
 */
public class VarausKasittely {

    private Controller controller;
    private VarauksetDAO_IF dao;

    /**
     * Konstruktori
     *
     * @param dao viittaus varausDAO:oon
     */
    public VarausKasittely(VarauksetDAO_IF dao, Controller c) {
        this.dao = dao;
        this.controller = c;
    }

    /**
     * Käy läpi kaikki varaukset tietokannasta ja päivittää niiden aktiivisuuden
     * oikeaksi Jos tietokannassa palautettu = true tarkoittaa, että varaus ei
     * ole aktiivinen ja päinvastoin.
     *
     * @return true kun tietokanta on käyty läpi
     */
    public boolean tarkistaAktiivisuudet() {
        LocalDateTime aika = LocalDateTime.now();
        Varaukset[] varaukset = dao.readVaraukset();
        for (Varaukset v : varaukset) {
            if (aika.isAfter(v.getAlkuAika()) && aika.isBefore(v.getLoppuAika()) && v.getHyvaksytty()) {
                if (!v.isPalautettu()) {
                    v.setPalautettu(true);
                    dao.updateVaraus(v);
                }
            } else if (v.isPalautettu()) {
                v.setPalautettu(false);
                dao.updateVaraus(v);
            }
        }
        return true;
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
        for (Varaukset v : kaikkiV) {

            if (Objects.equals(v.getKayttaja().getId(), k.getId())) {

                list.add(v);
            }
        }
        Varaukset[] varaukset = list.toArray(new Varaukset[list.size()]);
        return varaukset;
    }

    /**
     * Poistaa kayttajan kaikki varaukset
     *
     * @param id Kayttaja, jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */

    public boolean poistaKayttajanVaraukset(int id) {
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
