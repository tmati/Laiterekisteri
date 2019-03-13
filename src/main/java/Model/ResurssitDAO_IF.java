/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Rajapinta Resurssi DAO:n
 * @author Tommi
 */
public interface ResurssitDAO_IF {

    /**
     *
     * @param resurssi
     * @return
     */
    public abstract boolean createResurssi(Resurssit resurssi);

    /**
     *
     * @param id
     * @return
     */
    public abstract Resurssit readResurssi(int id);

    /**
     *
     * @return
     */
    public abstract Resurssit[] readResurssit();

    /**
     *
     * @param resurssi
     * @return
     */
    public abstract boolean updateResurssi(Resurssit resurssi);

    /**
     *
     * @param id
     * @return
     */
    public abstract boolean deleteResurssi(int id);
}
