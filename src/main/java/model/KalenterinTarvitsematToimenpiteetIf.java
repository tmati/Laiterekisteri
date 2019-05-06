/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.time.chrono.ChronoLocalDateTime;
import java.util.List;

/**
 *
 * @author jukka
 */
public interface KalenterinTarvitsematToimenpiteetIf {

    /**
     * Metodi, joka muuttaa Varaukset ArrayListan Arrayhin.
     *
     * @param aVaraukset muutettava ArrayList lista yhden resurssin kaikista
     * varauksista.
     * @return varaus Array
     */
    Varaukset[] getVarausTaulukko(List<Varaukset> aVaraukset);

    /**
     * Kertoo onko varaus mahdollinen edellisten varausten kanssa.
     *
     * @param aVaraukset Resursin muut varaukset.
     * @param endDate Uuden varauksen loppupäivä.
     * @param startDate Uuden varauksen aloituspäivä.
     * @return Truen jos ei ole päälekäisyyksiä varaksen kohdalla ja Fasle jos
     * on.
     */
    boolean onnistuu(List<Varaukset> aVaraukset, ChronoLocalDateTime endDate, ChronoLocalDateTime startDate);

    /**
     * Antaa valitusta resurssin kaikki varaukset ArrayListana.
     *
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Kaikki varaukset.
     * @return ArrayListan jossa on kaikki resursin varaukset.
     */
    List<Varaukset> resurssinVaraukset(int resurssiId, Varaukset[] varaukset);
    
}
