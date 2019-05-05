/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Rajapinta Resurssi DAO:lle
 *
 * @author Tommi
 */
public interface ResurssitDAOIF {

    /**
     * Vie resurssin tietokantaan
     *
     * @param resurssi resurssi joka viedään tietokantaan
     * @return true jos tietokantaan vienti onnistui
     */
    public abstract boolean createResurssi(Resurssit resurssi);

    /**
     * Lukee resurssin tietokannasta id:n perusteella.
     *
     * @param id resurssin id
     * @return haettu resurssi -olio
     */
    public abstract Resurssit readResurssi(int id);

    /**
     * Lukee kaikki resurssit tietokannasta
     *
     * @return taulukko kaikista resursseista
     */
    public abstract Resurssit[] readResurssit();

    /**
     * Päivittää resurssin tietokantaan
     *
     * @param resurssi päivitettävä resurssi
     * @return true jos resurssin päivitys onnistui
     */
    public abstract boolean updateResurssi(Resurssit resurssi);

    /**
     * Poistaa resurssin id:n avulla tietokannasta
     *
     * @param id poistettavan resurssin id
     * @return true jos resurssin poisto onnistui
     */
    public abstract boolean deleteResurssi(int id);
}
