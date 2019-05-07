/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Tommi
 */
public interface IstuntoIF {

    String getBizname();

    Resurssit getBooking();

    Kayttaja getLoggedIn();

    Kayttaja getSelected();

    void setBooking(Resurssit booking);

    void setLoggedIn(Kayttaja loggedIn);

    void setSelected(Kayttaja selected);
    
}
