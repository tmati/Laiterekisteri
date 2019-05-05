/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Rajapinta Varauksen DAO:lle
 *
 * @author Tommi
 */
public interface VarauksetDAOIF {

    /**
     * Vie varauksen tietokantaan
     *
     * @param varaus varaus -olio joka viedään tietokantaan
     * @return true jos varauksen vienti onnistui
     */
    public abstract boolean createVaraus(Varaukset varaus);

    /**
     * Hakee varauksen tietokannasta id:n avulla
     *
     * @param id varauksen id
     * @return varaus -olio
     */
    public abstract Varaukset readVaraus(int id);

    /**
     * Lukee kaikki varaukset tietokannasta.
     *
     * @return taulukko kaikista varauksista
     */
    public abstract Varaukset[] readVaraukset();

    /**
     * Päivittää varauksen tietokantaan
     *
     * @param varaus varaus, jota päivitetään
     * @return true jos päivitys onnistui
     */
    public abstract boolean updateVaraus(Varaukset varaus);

    /**
     * Poistaa varauksen tietokannasta id:n perusteella
     *
     * @param id varauksen id
     * @return true jos poisto onnistui
     */
    public abstract boolean deleteVaraus(int id);

}
