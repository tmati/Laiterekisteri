/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Rajapinta käytäjä DAO:n.
 * @author Tommi
 */
public interface KayttajaDAO_IF {

    /**
     *
     * @param kayttaja
     * @return
     */
    public abstract boolean createKayttaja(Kayttaja kayttaja);

    /**
     *
     * @param id
     * @return
     */
    public abstract Kayttaja readKayttaja(int id);

    /**
     *
     * @return
     */
    public abstract Kayttaja[] readKayttajat();

    /**
     *
     * @param kayttaja
     * @return
     */
    public abstract boolean updateKayttaja(Kayttaja kayttaja);

    /**
     *
     * @param id
     * @return
     */
    public abstract boolean deleteKayttaja(int id);
    

}
