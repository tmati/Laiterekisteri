/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Rajapinta käyttäjä DAO:lle
 *
 * @author Tommi
 */
public interface KayttajaDAO_IF {

    /**
     * Vie uuden käyttäjän tietokantaan
     *
     * @param kayttaja kayttaja joka viedään tietokantaan
     * @return palauttaa true jos käyttäjän vienti tietokantaan onnistui
     */
    public abstract boolean createKayttaja(Kayttaja kayttaja);

    /**
     * Hakee käyttäjän tietokannasta id:n avulla
     *
     * @param id käyttäjän id
     * @return palauttaa kayttaja -olion
     */
    public abstract Kayttaja readKayttaja(int id);

    /**
     * Lukee kaikki käyttäjät tietokannasta.
     *
     * @return palauttaa taulukon kaikista käyttäjistä
     */
    public abstract Kayttaja[] readKayttajat();

    /**
     * Päivittää käyttäjän tietokantaan
     *
     * @param kayttaja päivitettävä käyttäjä
     * @return palauttaa true jos käyttäjän päivitys onnistui
     */
    public abstract boolean updateKayttaja(Kayttaja kayttaja);

    /**
     * Poistaa käytäjän tietokannasta id:n perusteella.
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poisto onnistui
     */
    public abstract boolean deleteKayttaja(int id);

}
