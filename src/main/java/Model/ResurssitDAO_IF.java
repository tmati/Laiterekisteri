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
public interface ResurssitDAO_IF {

    public abstract boolean createResurssi(Resurssit resurssi);

    public abstract Resurssit readResurssi(int id);

    public abstract Resurssit[] readResurssit();

    public abstract boolean updateResurssi(Resurssit resurssi);

    public abstract boolean deleteResurssi(int id);
}
