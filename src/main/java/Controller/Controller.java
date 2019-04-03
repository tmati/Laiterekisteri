/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;


import Model.*;
import javafx.scene.control.ChoiceBox;



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
    private PasswordConverterInterface salasananVaihto = new PasswordConverter();
    private KayttajanVaraukset KV;
    private LoginUtils login;
    private ChoiceboxUtils cbutils;
    private BooleanConverter BoolConv;

    /**
     * Controllerin konstruktio
     */
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
        resurssiDAO = new ResurssitAccessObject();
        varausDAO = new VarauksetAccessObject();
        KV = new KayttajanVaraukset();
        login = new LoginUtils(this);
        cbutils = new ChoiceboxUtils(this);
        BoolConv = new BooleanConverter(this);
       }

    /**
     * Kutsuu PasswordConverterInterface.passwordConverter()
     *
     * @param password Encryptattava salasana.
     * @return Palautaa encryptattatun salasanan.
     */
    public String salasananVaihto(String password) {
        return salasananVaihto.passwordConverter(password);
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
     * @param kayttaja kayttaja -olio jonka varaukset haetaan
     * @return palauttaa taulukon kaikista käyttäjän varaus -olioista
     */
    public Varaukset[] haeKayttajanVaraukset(Kayttaja kayttaja) {
        return KV.haeKayttajanVaraukset(kayttaja, this);
    }

    /**
     * Kutsuu ResurssiAccessObject.deleteResurssi()
     * @param r tietokannasta poistettava resurssi
     * @return palauttaa true jos resurssin poisto tietokannasta onnistui
     */
    public boolean poistaResurssi(Resurssit r) {
        return resurssiDAO.deleteResurssi(r.getId());
    }

    /**
     *Kutsuu ResurssiAccessObject.updateResurssi()
     * @param r päivitettävä resurssi -olio
     * @return palauttaa true jos resurssin päivitys tietokantaan onnistui
     */
    public boolean paivitaResurssi(Resurssit r){
        return resurssiDAO.updateResurssi(r);
    }
    
    /**
     * Kutsuu VarausAccessObject.updateVaraus()
     * @param v päivitettävä varaus -olio
     * @return palauttaa true jos varauksen päivitys tietokantaan onnistui
     */
    public boolean paivitaVaraus(Varaukset v){
        return varausDAO.updateVaraus(v);
    }
    
    /**
     * Kutsuu VarausAccessObject.createVaraus()
     * @param v tietokantaan vietävä varaus -olio
     * @return palauttaa true jos varauksen vienti tietokantaan onnistui
     */
    public boolean luoVaraus(Varaukset v){
        return varausDAO.createVaraus(v);
    }
    
    /**
     * Kutsuu ResurssiAccessObject.createResurssi()
     * @param r tietokantaan vietävä resurssi -olio
     * @return palauttaa true jos resurssin vienti tietokantaan onnistui
     */
    public boolean luoResurssi(Resurssit r){
        return resurssiDAO.createResurssi(r);
    }
    
        /**
     * Tarkistaa modelista käyttäjänimen ja salasanan.
     * @param userName
     * @param passWord
     * @return 
     */
    public boolean login(String userName, String passWord) {
        return login.loginProcess(userName, passWord);
    }
    
    public int readCb(ChoiceBox cb) {
        return cbutils.tulkitseChoiceBox(cb);
    }
    public boolean readBoolCb(String cb) {
        return cbutils.tulkitseBooleanBox(cb);
    }
    public BooleanConverter getBoolConv() {
        return new BooleanConverter(this);
    }
}
