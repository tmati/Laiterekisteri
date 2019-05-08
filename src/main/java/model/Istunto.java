/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


package model;
import controller.*;
import java.util.logging.Logger;
/**
 *
 * @author Tommi
 */
public class Istunto implements IstuntoIF {
    /**
     * Staattinen loggeri, jota kaikki luokat käyttävät.
     */
    public static final Logger LOGGER = Logger.getAnonymousLogger();
    /**
     * Viittaus controller instanssiin
     */
    private ControllerIf controller;
    
    /**
     * Yrityksen nimi
     */
    private String bizname = "KeyChain Enterprise Management";
   
    /**
     * Sovelluksen käytäjän tiedot.
     *
     */
    private Kayttaja loggedIn = null;

    /**
     * Käyttäjän varausten tarkastelua varten tallennettava olio
     */
    private Kayttaja selected = null;

    /**
     * Varaukseen vietävä resurssi
     */
    private Resurssit booking = null;

    public Istunto(Controller controller){
        this.controller = controller;
    }
    
    @Override
    public Resurssit getBooking(){
        return booking;
    }
    
    @Override
    public void setBooking(Resurssit booking){
        this.booking = booking;
    }
    
    @Override
    public Kayttaja getLoggedIn(){
        return this.loggedIn;
    }
    
    @Override
    public void setLoggedIn(Kayttaja loggedIn){
        this.loggedIn = loggedIn;
    }
    
    @Override
    public Kayttaja getSelected(){
        return this.selected;
    }
    
    @Override
    public void setSelected(Kayttaja selected){
        this.selected = selected;
    }
    
    @Override
    public String getBizname(){
        return this.bizname;
    }
    
}
