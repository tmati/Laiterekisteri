/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Controller;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Luokka varausten käsittelyä varten
 *
 * @author Tommi
 */
public class VarausKasittely {
    
    private final Controller controller;
    private final VarauksetDAOIF dao;

    /**
     * Konstruktori
     *
     * @param dao viittaus varausDAO:oon
     * @param c viittaus Controlleriin
     */
    public VarausKasittely(VarauksetDAOIF dao, Controller c) {
        this.dao = dao;
        this.controller = c;
    }

    /**
     * Käy läpi kaikki varaukset tietokannasta ja päivittää niiden aktiivisuuden
     * oikeaksi. Jos tietokannassa palautettu = true tarkoittaa, että varaus ei
     * ole aktiivinen ja päinvastoin.
     *
     * @return true kun tietokanta on käyty läpi
     */
    public boolean tarkistaAktiivisuudet() {
        for (Varaukset v : dao.readVaraukset()) {
            if (LocalDateTime.now().isAfter(v.getAlkuAika()) && LocalDateTime.now().isBefore(v.getLoppuAika()) && v.getHyvaksytty()) {
                if (!v.isPalautettu()) {
                    v.setPalautettu(true);
                    dao.updateVaraus(v);
                }
            } else if (v.isPalautettu()) {
                v.setPalautettu(false);
                controller.lahetaSahkoposti(v.getKayttaja().getSahkoposti(), controller.getVarausAikaString(v)
                        + " " + controller.getConfigTeksti("emailReservaEnd"));
                dao.updateVaraus(v);
            } else if (!v.getHyvaksytty() && LocalDateTime.now().isAfter(v.getAlkuAika())) {
                controller.lahetaSahkoposti(v.getKayttaja().getSahkoposti(), controller.getVarausAikaString(v)
                        + " " + controller.getConfigTeksti("emailReservaFail"));
                controller.poistaVaraus(v.getId());
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
        
        Varaukset[] kaikkiV = controller.haeKaikkiVaraukset();
        ArrayList<Varaukset> list = new ArrayList<>();
        for (Varaukset v : kaikkiV) {
            if (Objects.equals(v.getKayttaja().getId(), k.getId())) {
                list.add(v);
            }
        }
        return list.toArray(new Varaukset[list.size()]);
    }

    /**
     * Poistaa kayttajan kaikki varaukset
     *
     * @param id Kayttaja, jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    public boolean poistaKayttajanVaraukset(int id) {
        boolean tarkistus = true;
        for (Varaukset v : controller.haeKaikkiVaraukset()) {
            if (v.getKayttaja().getId() == id) {
                boolean tulos = controller.poistaVaraus(v.getId());
                if (!tulos) {
                    tarkistus = tulos;
                }
            }
        }
        return tarkistus;
    }

    /**
     * Hakee kaikki varaukset, joissa hyvaksytty -status on false ja laittaa ne
     * taulukkoon. False tarkoittaa sitä, että varausta ei ole käsitelty
     *
     * @return taulukko käsittelemättömistä varauksista.
     */
    public Varaukset[] haeKasittelemattomat() {
        ArrayList<Varaukset> list = new ArrayList<>();
        for (Varaukset v : controller.haeKaikkiVaraukset()) {
            if (!v.getHyvaksytty()) {
                list.add(v);
            }
        }
        return list.toArray(new Varaukset[list.size()]);
    }
    /**
     *  Tarkistaa onko varauksen alkamis päivämäärä jo mennyt
     * @param varaus tarkastettava varaus
     * @return true jos varauksen alkuaika on jo mennyt
     */
    public boolean tarkistaOnkoVarausAlkanut(Varaukset varaus) {
        return (varaus.getAlkuAika().isBefore(LocalDateTime.now()));
    }

    /**
     * Kasaa stringin varauksen aikatiedoista sähköpostin lähetystä varten.
     *
     * @param varaus Varaus -olio
     * @return String, jossa näkyy varattavan laitteen nimi ja varauksen
     * ajankohta.
     */

    public String getVarausAikaString(Varaukset varaus) {
        return controller.getConfigTeksti("emailReservationTime") + varaus.getNimi() + " " + controller.getConfigTeksti("forTime")+ " " + varaus.getAlkuAika().getHour() + "." + varaus.getAlkuAika().getDayOfMonth() + "."
                + varaus.getAlkuAika().getYear() + "-" + varaus.getLoppuAika().getHour() + "." + varaus.getLoppuAika().getDayOfMonth()
                + "." + varaus.getLoppuAika().getYear();

    }
}
