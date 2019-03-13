/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 * Rajapinta Varauksen DAO:sta
 * @author Tommi
 */
public interface VarauksetDAO_IF {

    /**
     *
     * @param varaus
     * @return
     */
    public abstract boolean createVaraus(Varaukset varaus);

    /**
     *
     * @param id
     * @return
     */
    public abstract Varaukset readVaraus(int id);

    /**
     *
     * @return
     */
    public abstract Varaukset[] readVaraukset();

    /**
     *
     * @param varaus
     * @return
     */
    public abstract boolean updateVaraus(Varaukset varaus);

    /**
     *
     * @param id
     * @return
     */
    public abstract boolean deleteVaraus(int id);

}
