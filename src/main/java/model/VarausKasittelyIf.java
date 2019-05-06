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
public interface VarausKasittelyIf {

    /**
     * Kasaa stringin varauksen aikatiedoista sähköpostin lähetystä varten.
     *
     * @param varaus Varaus -olio
     * @return String, jossa näkyy varattavan laitteen nimi ja varauksen
     * ajankohta.
     */
    String getVarausAikaString(Varaukset varaus);

    /**
     * Hakee kaikki varaukset, joissa hyvaksytty -status on false ja laittaa ne
     * taulukkoon. False tarkoittaa sitä, että varausta ei ole käsitelty
     *
     * @return taulukko käsittelemättömistä varauksista.
     */
    Varaukset[] haeKasittelemattomat();

    /**
     * Siirtää halutun käyttäjän varaukset taulukkoon
     *
     * @param k käyttäjä jonka varaukset halutaan
     * @return taulukko käyttäjän varauksista
     */
    Varaukset[] haeKayttajanVaraukset(Kayttaja k);

    /**
     * Poistaa kayttajan kaikki varaukset
     *
     * @param id Kayttaja, jonka varaukset poistetaan
     * @return true jos poisto onnistui
     */
    boolean poistaKayttajanVaraukset(int id);

    /**
     * Käy läpi kaikki varaukset tietokannasta ja päivittää niiden aktiivisuuden
     * oikeaksi. Jos tietokannassa palautettu = true tarkoittaa, että varaus ei
     * ole aktiivinen ja päinvastoin.
     *
     * @return true kun tietokanta on käyty läpi
     */
    boolean tarkistaAktiivisuudet();

    /**
     *  Tarkistaa onko varauksen alkamis päivämäärä jo mennyt
     * @param varaus tarkastettava varaus
     * @return true jos varauksen alkuaika on jo mennyt
     */
    boolean tarkistaOnkoVarausAlkanut(Varaukset varaus);
    
}
