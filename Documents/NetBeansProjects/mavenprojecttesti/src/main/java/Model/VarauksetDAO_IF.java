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
public interface VarauksetDAO_IF {

    public abstract boolean createVaraus(Varaukset resurssi);

    public abstract Varaukset readVaraus(int id);

    public abstract Varaukset[] readVaraukset();

    public abstract boolean updateVaraus(Varaukset varaus);

    public abstract boolean deleteVaraus(int id);

}
