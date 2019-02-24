/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Tommi
 */
public interface KayttajaDAO_IF {

    public abstract boolean createKayttaja(Kayttaja kayttaja);

    public abstract Kayttaja readKayttaja(int id);

    public abstract Kayttaja[] readKayttajat();

    public abstract boolean updateKayttaja(Kayttaja kayttaja);

    public abstract boolean deleteKayttaja(int id);
    

}
