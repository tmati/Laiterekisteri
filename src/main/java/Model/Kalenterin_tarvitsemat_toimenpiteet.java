/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author jukka
 */
public class Kalenterin_tarvitsemat_toimenpiteet {

    /**
     * Antaa valitusta resurssin kaikki varaukset ArrayListana.
     *
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Kaikki varaukset.
     * @return ArrayListan jossa on kaikki resursin varaukset.
     */
    public ArrayList<Varaukset> resurssinVaraukset(int resurssiId, Varaukset[] varaukset) {
        ArrayList<Varaukset> aVaraukset = new ArrayList<Varaukset>();
        for (int i = 0; i < varaukset.length; i++) {
            if (varaukset[i].getResurssit().getId() == resurssiId) {
                aVaraukset.add(varaukset[i]);
            }
        }

        return aVaraukset;

    }

    /**
     * Kertoo onko varaus mahdollinen edellisten varausten kanssa.
     *
     * @param aVaraukset Resursin muut varaukset.
     * @param endDate Uuden varauksen loppupäivä.
     * @param startDate Uuden varauksen aloituspäivä.
     * @return Truen jos ei ole päälekäisyyksiä varaksen kohdalla ja Fasle jos
     * on.
     */
    public boolean Onnistuu(ArrayList<Varaukset> aVaraukset, ChronoLocalDateTime endDate, ChronoLocalDateTime startDate) {
        if (startDate.isAfter(endDate)) {
            return false;
        }
        if (startDate.isBefore(LocalDateTime.now())) {
            return false;
        }
        for (Varaukset varaus : aVaraukset) {
            if (startDate.isBefore(varaus.getAlkuAika()) && endDate.isBefore(varaus.getLoppuAika())) {
            } else if (startDate.isAfter(varaus.getAlkuAika()) && endDate.isAfter(varaus.getLoppuAika())) {
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * Metodi, joka muuttaa Varaukset ArrayListan Arrayhin.
     *
     * @param aVaraukset muutettava ArrayList lista yhden resurssin kaikista
     * varauksista.
     * @return varaus Array
     */
    public Varaukset[] getVarausTaulukko(ArrayList<Varaukset> aVaraukset) {
        Varaukset[] varaus = new Varaukset[aVaraukset.size()];
        for (int i = 0; i < aVaraukset.size(); i++) {
            varaus[i] = aVaraukset.get(i);
        }
        return varaus;
    }
}
