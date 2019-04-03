/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Controller;
import java.time.LocalDateTime;

/**
 * Luokka tietokannan varaus -taulun tarkistusta varten.
 * Tarkistaa jos varaus on aktiivinen sillä hetkellä ja päivittää tietokantaa sen mukaan.
 * @author Tommi
 */
public class VarausAktiivisuusTarkistus {

    private VarauksetDAO_IF dao;
    /**
     * Konstruktori
     * @param dao viittaus varausDAO:oon
     */
    public VarausAktiivisuusTarkistus(VarauksetDAO_IF dao) {
        this.dao = dao;
    }

    /**
     * Käy läpi kaikki varaukset tietokannasta ja päivittää niiden aktiivisuuden oikeaksi
     * Jos tietokannassa palautettu = true tarkoittaa, että varaus ei ole aktiivinen ja päinvastoin.
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
}
