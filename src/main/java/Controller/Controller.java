
package Controller;

import Model.*;
import java.io.IOException;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.scene.control.ChoiceBox;
import javafx.util.Callback;
import javax.mail.MessagingException;

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
    private LoginUtils login;
    private ChoiceboxUtils cbutils;
    private BooleanConverter BoolConv;
    private DayCellFactory cellfactory;
    private VarauksenAikaLaskuriInterface aikalaskuri;
    private VarausKasittely varausKasittely;
    private Resurssikasittely resurssikasittely;
    private Kalenterin_tarvitsemat_toimenpiteet kalenteriApu;
    private Sahkoposti sahkoposti;
    private SalasananPalautus salasananPalautus;

    /**
     * Controllerin konstruktio
     */
    public Controller() {
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
        resurssiDAO = new ResurssitAccessObject();
        varausDAO = new VarauksetAccessObject();
        login = new LoginUtils(this);
        cbutils = new ChoiceboxUtils(this);
        BoolConv = new BooleanConverter(this);
        cellfactory = new DayCellFactory();
        aikalaskuri = new VarauksenAikaLaskuri();
        varausKasittely = new VarausKasittely(varausDAO, this);
        resurssikasittely = new Resurssikasittely(this);
        kalenteriApu = new Kalenterin_tarvitsemat_toimenpiteet();
        sahkoposti = new Sahkoposti();
        salasananPalautus = new SalasananPalautus(this);
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
     * Kutsuu VarausKasittely.poistaKayttajanVaraukset()
     * Kutsuu KayttajaAccessObject.deleteKayttaja()
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poista tietokannasta onnistui
     */
    public boolean poistaKayttaja(int id){
        varausKasittely.poistaKayttajanVaraukset(id);
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
     * Kutsuu Controller.tarkistaVarausAKtiivisuudet()
     * Kutsuu VarausAccessObject.readVaraukset()
     *
     * @return palauttaa taulukon kaikista varaus -olioista
     */
    public Varaukset[] haeKaikkiVaraukset(){
        this.tarkistaVarausAktiivisuudet();
        return varausDAO.readVaraukset();
    }
    
    /**
     * Kutsuu VarausKasittely.tarkistaAktiivisuudet()
     * @return true jos onnistuu
     */
    public boolean tarkistaVarausAktiivisuudet(){
        return varausKasittely.tarkistaAktiivisuudet();
    }

    /**
     * kutsuu VarausKasittely.haeKayttajanVaraukset()
     *
     * @param kayttaja kayttaja -olio jonka varaukset haetaan
     * @return palauttaa taulukon kaikista käyttäjän varaus -olioista
     */
    public Varaukset[] haeKayttajanVaraukset(Kayttaja kayttaja){
        return varausKasittely.haeKayttajanVaraukset(kayttaja);
    }

    /**
     * Kutsuu ResurssiKasittely.poisteResurssinVaraukset()
     * Kutsuu ResurssiAccessObject.deleteResurssi()
     *
     * @param r tietokannasta poistettava resurssi
     * @return palauttaa true jos resurssin poisto tietokannasta onnistui
     */
    public boolean poistaResurssi(Resurssit r){
        resurssikasittely.poistaResurssinVaraukset(r);
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
        return login.loginProcess(userName, this.SalasananCryptaus(passWord));
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
     * @param today mistä päivästä eteenpäin voi päiviä valita.
     * @return Callbackin jossa on muokatuja päiviä.
     */
    public Callback dayCellFactory(Varaukset[] varaukset, LocalDate today){
        return cellfactory.dayCellFactory(this, varaukset,today);
    }
    
    /**
     * Tulkitsee boolean-arvon sisältävän choiceboxin. Pyyntö model-luokkaan.
     * @param cb Tulkittava choicebox
     * @return True/false
     */
    public boolean readBoolCb(String cb) {
        return cbutils.tulkitseBooleanBox(cb);
    }
    
    /**
     * Hakee modelista BooleanConverter-ilmentymän
     * @return BooleanConverter-olio
     */
    public BooleanConverter getBoolConv() {
        return new BooleanConverter(this);
    }
    
        /**
     * Siirtää ArrayListasta varaus alkiot varaus Array:hin.
     * @param aVaraukset Varaus ArrayListasta josta halutaan tehdä array.
     * @return Varaukset Array:na.
     */
    public Varaukset[] getVaraus(ArrayList<Varaukset> aVaraukset){
        return kalenteriApu.getVaraus(aVaraukset);
    }
    
    /**
     * Vie parametrit Kalenterin_tarvitsemat_toimenpiteet() luokalle, jossa resurssiId:n avulla varaus Arraysta tehdään ArrayListan jossa on vain sen resursin varaukset.
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Varaus array josta halutaan saada resursin varaukset.
     * @return ArrayListan jossa on resursin varaukset Arraysta.
     */
    public ArrayList<Varaukset> ResursinVaraukset(int resurssiId, Varaukset[] varaukset){
        return kalenteriApu.ResursinVaraukset(resurssiId, varaukset);
    }
    
    /**
     * Vie parametrit Kalenterin_tarvitsemat_toimenpiteet lu9okalle parametrit. Se kertoo jos varaus sillä ajan hetkellä on mahdollista. Kun verrataan muihin tuoteen varauksiin.
     * @param aVaraukset ArrayLista varattavan tuoteen varauksista.
     * @param endDate Milloin tuleva varaus loppuu. (päivä)
     * @param startDate Milloin tuleva varaus alkaa. (päivä)
     * @return true jos vraus on mahdollista ja falsen jos varaus menee toisen varauksen päälle.
     */
    public boolean Onnistuu(ArrayList<Varaukset> aVaraukset, ChronoLocalDateTime endDate, ChronoLocalDateTime startDate){
        return kalenteriApu.Onnistuu(aVaraukset, endDate, startDate);
    }
    
    
    /**
     * Kutsuu VarausKasittely.haeKasittelemattomat()
     * @return taulukko käsittelemättömistä varaus -oloista.
     */
    public Varaukset[] haeKasittelemattomatVaraukset(){
        return varausKasittely.haeKasittelemattomat();
    }
    
    /**
     * Kutsuu Sahkoposti.sendEmail()
     * @param vastaanottaja Sahkopostiosoite johon lähetetään
     * @param viesti lähettettävä viesti
     * @return true jos lähetys onnistuu
     * 
     */
    public boolean lahetaSahkoposti(String vastaanottaja, String viesti){
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        emailExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                   sahkoposti.sendEmail(vastaanottaja, viesti);
                } catch (Exception e) {
                    System.out.println("säie fail" + e);
                }
            }
        });
        emailExecutor.shutdown(); // it is very important to shutdown your non-singleton ExecutorService.
        return true;
    }
    
    /**
     * Kutsuu VarausKasittely.getVarausAikaString
     * @param V Varaus, jonka tiedoista string kasataan sähköpostia varten
     * @return String, jossa näkyy varattavan laitteen nimi ja varauksen ajankohta.
     */
    public String getVarausAikaString(Varaukset V){
        return varausKasittely.getVarausAikaString(V);
    }
    
    /**
     * Kutsuu SalasananPalautus.palautaSalasana()
     * @param email Sähköposti palautetaan
     * @return true jos palautus onnistui
     */
    public boolean palautaSalasana(String email){
        return salasananPalautus.palautaSalasana(email);
    }
    
    /**
     * Kutsuu VarausKasittely.tarkistaOnkoVarausAlkanut
     * @param varaus tarkistettava varaus
     * @return true jos varauksen alkamisaika on mennyt jo
     */
    public boolean OnkoVarausAlkanut(Varaukset varaus){
        return varausKasittely.tarkistaOnkoVarausAlkanut(varaus);
    }
    
    
}

