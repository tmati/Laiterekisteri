/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;

import java.time.LocalDateTime;
import java.util.Set;
import javafx.scene.control.ChoiceBox;
import javafx.util.Callback;

/**
 * Controlleri
 *
 * @author Tommi
 */
public class Controller {

    private KayttajaDAO_IF kayttajaDAO;
    private ResurssitDAO_IF resurssiDAO;
    private VarauksetDAO_IF varausDAO;
    private KayttajaTarkistus kayttajaTarkistus;
    private PasswordConverterInterface crypter = new PasswordConverter();
    private KayttajanVaraukset KV;
    private LoginUtils login;
    private ChoiceboxUtils cbutils;
    private BooleanConverter BoolConv;
    private DayCellFactory cellfactory;
    private VarauksenAikaLaskuriInterface aikalaskuri;

    /**
     * Controllerin konstruktio
     */
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
        resurssiDAO = new ResurssitAccessObject();
        varausDAO = new VarauksetAccessObject();
        KV = new KayttajanVaraukset(this);
        login = new LoginUtils(this);
        cbutils = new ChoiceboxUtils(this);
        BoolConv = new BooleanConverter(this);
        cellfactory = new DayCellFactory();
        aikalaskuri = new VarauksenAikaLaskuri();
       }

    /**
     * Kutsuu PasswordConverterInterface.passwordConverter()
     *
     * @param password Encryptattava salasana.
     * @return Palautaa encryptattatun salasanan.
     */
    public String SalasananCryptaus(String password) {
        return crypter.passwordConverter(password);
    }

    /**
     * kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @return Palauttaa taulukon kaikista käyttäjistä
     */
    public Kayttaja[] haeKaikkiKayttajat() {
        return kayttajaDAO.readKayttajat();
    }

    /**
     * Kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @param id haetun käyttäjän id
     * @return palauttaa käyttäjä-olion
     */
    public Kayttaja haeKayttaja(int id) {
        return kayttajaDAO.readKayttaja(id);
    }

    /**
     * Kutsuu KayttajaAccessObject.createKayttaja()
     *
     * @param kayttaja tietokantaan vietävä kayttaja-olio
     * @return palauttaa true jos käyttäjän vienti tietokantaan onnistui
     */
    public boolean luoKayttaja(Kayttaja kayttaja) {
        return kayttajaDAO.createKayttaja(kayttaja);
    }

    /**
     * Kutsuu KayttajaAccessObject.uptadeKayttaja()
     *
     * @param kayttaja päivitettävä kayttaja -olio
     * @return palauttaa true jos käyttäjän päivitys tietokantaan onnistui
     */
    public boolean paivitaKayttaja(Kayttaja kayttaja) {
        return kayttajaDAO.updateKayttaja(kayttaja);
    }

    /**
     * Kutsuu KayttajaAccessObject.deleteKayttaja()
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poista tietokannasta onnistui
     */
    public boolean poistaKayttaja(int id) {
        return kayttajaDAO.deleteKayttaja(id);
    }

    /**
     * Kutsuu KayttajaTarkistus.tarkistaUsername()
     *
     * @param username tarkistettava käyttäjätunnus
     * @return palauttaa true jos käyttäjätunnusta ei löydy tietokannasta
     */
    public boolean tarkistaUsername(String username) {
        return kayttajaTarkistus.usernameTarkastus(username);
    }

    /**
     * Kutsuu KayttajaTarkistus.emailTarkistus
     *
     * @param email tarkistettava sähköposti
     * @return palauttaa true jos sähköpostia ei löydy tietokannasta
     */
    public boolean tarkistaEmail(String email) {
        return kayttajaTarkistus.emailTarkastus(email);
    }

    /**
     * Kutsuu ResurssiAccessObject.readResurssit()
     *
     * @return palauttaa taulukon kaikista resurssi -olioista
     */
    public Resurssit[] haeKaikkiResurssit() {
        return resurssiDAO.readResurssit();
    }

    /**
     * Kutsuu VarausAccessObject.readVaraukset()
     *
     * @return palauttaa taulukon kaikista varaus -olioista
     */
    public Varaukset[] haeKaikkiVaraukset() {
        return varausDAO.readVaraukset();
    }

    /**
     * kutsuu KayttajanVaraukset.haeKayttajanVaraukset()
     *
     * @param kayttaja kayttaja -olio jonka varaukset haetaan
     * @return palauttaa taulukon kaikista käyttäjän varaus -olioista
     */
    public Varaukset[] haeKayttajanVaraukset(Kayttaja kayttaja) {
        return KV.haeKayttajanVaraukset(kayttaja);
    }

    /**
     * Kutsuu ResurssiAccessObject.deleteResurssi()
     *
     * @param r tietokannasta poistettava resurssi
     * @return palauttaa true jos resurssin poisto tietokannasta onnistui
     */
    public boolean poistaResurssi(Resurssit r) {
        return resurssiDAO.deleteResurssi(r.getId());
    }

    /**
     * Kutsuu ResurssiAccessObject.updateResurssi()
     *
     * @param r päivitettävä resurssi -olio
     * @return palauttaa true jos resurssin päivitys tietokantaan onnistui
     */
    public boolean paivitaResurssi(Resurssit r) {
        return resurssiDAO.updateResurssi(r);
    }

    /**
     * Kutsuu VarausAccessObject.updateVaraus()
     *
     * @param v päivitettävä varaus -olio
     * @return palauttaa true jos varauksen päivitys tietokantaan onnistui
     */
    public boolean paivitaVaraus(Varaukset v) {
        return varausDAO.updateVaraus(v);
    }

    /**
     * Kutsuu VarausAccessObject.createVaraus()
     *
     * @param v tietokantaan vietävä varaus -olio
     * @return palauttaa true jos varauksen vienti tietokantaan onnistui
     */
    public boolean luoVaraus(Varaukset v) {
        return varausDAO.createVaraus(v);
    }

    /**
     * Kutsuu ResurssiAccessObject.createResurssi()
     *
     * @param r tietokantaan vietävä resurssi -olio
     * @return palauttaa true jos resurssin vienti tietokantaan onnistui
     */
    public boolean luoResurssi(Resurssit r) {
        return resurssiDAO.createResurssi(r);
    }

    /**
     * Tarkistaa modelista käyttäjänimen ja salasanan.
     *
     * @param userName käyttäjänimi
     * @param passWord salasana
     * @return true jos kirjautumistiedot oikein
     */
    public boolean login(String userName, String passWord) {
        return login.loginProcess(userName, passWord);
    }


    /**
     * Kutsuu VarausAccessObject.readVaraus()
     *
     * @param id haetun varauksen id
     * @return palauttaa varaus-olion
     */
    public Varaukset haeVaraus(int id) {
        return varausDAO.readVaraus(id);
    }

    /**
     * Kutsuu VarausAccessObject.deleteVaraus()
     *
     * @param id tietokannasta poistettavan varauksen id
     * @return palauttaa true jos varauksen poisto tietokannasta onnistui
     */
    public boolean poistaVaraus(int id) {
        return varausDAO.deleteVaraus(id);
    }
    
    
      /**
     * Kutsuu ChoiceboxUtils.tulkitseChoiceBox()
     *
     * @param cb choice box elementti jota tulkitaan
     * @return choice boxia vastaavan kokonais luvun
     */
    public int readCb(ChoiceBox cb) {
        return cbutils.tulkitseChoiceBox(cb);

    }
    
    /**
     * Vie paivat varauksen kesto laskuriin ja tuo sen jälkeen, kuinka monta päivää varaus kestää.
     * @param alkupvm milloin varaus alkaa
     * @param loppumispvm milloin varaus loppuu
     * @return alkupvm ja loppupvm erotuksen
     */
    public int paivaLaskuri(LocalDateTime alkupvm, LocalDateTime loppumispvm){
       return aikalaskuri.PaivaKesto(alkupvm, loppumispvm);
    }
    
    /**
     * Palauttaa datepickerille muokatut päivät.
     * @param varaukset varaukset joila on varaukset tietyihin päiville.
     * @return Callbackin jossa on muokatuja päiviä.
     */
    public Callback dayCellFactory(Varaukset[] varaukset){
        return cellfactory.dayCellFactory(this, varaukset);
    }
    public boolean readBoolCb(String cb) {
        return cbutils.tulkitseBooleanBox(cb);
    }
    public BooleanConverter getBoolConv() {
        return new BooleanConverter(this);
    }
}
