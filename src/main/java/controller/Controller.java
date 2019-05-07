package controller;

import model.DayCellFactory;
import model.Resurssit;
import model.ResurssiKasittely;
import model.VarausKasittely;
import model.Kayttaja;
import model.ResurssitAccessObject;
import model.VarauksetAccessObject;
import model.Sahkoposti;
import model.BooleanConverter;
import model.ChoiceboxUtils;
import model.Varaukset;
import model.PasswordConverter;
import model.LanguageText;
import model.KayttajaAccessObject;
import model.PoistaBtnToiminnot;
import model.VarauksenAikaLaskuri;
import model.KayttajaTarkistus;
import model.VarauksenAikaLaskuriInterface;
import model.LoginUtils;
import model.PasswordConverterInterface;
import model.KalenterinTarvitsematToimenpiteet;
import model.SalasananPalautus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import javafx.scene.control.ChoiceBox;
import javafx.util.Callback;
import model.ChoiceboxUtilsIf;
import model.DayCellFactoryIf;
import model.Istunto;
import model.IstuntoIF;
import model.KalenterinTarvitsematToimenpiteetIf;
import model.KayttajaDAOIF;
import model.KayttajaTarkistusIf;
import model.LanguageTextIf;
import model.LoginUtilsIf;
import model.PoistaBtnToiminnotIf;
import model.ResurssiKasittelyIf;
import model.ResurssitDAOIF;
import model.SahkopostiIf;
import model.SalasananPalautusIf;
import model.VarauksetDAOIF;
import model.VarausKasittelyIf;

/**
 * Controlleri
 *
 * @author Tommi
 */
public class Controller implements ControllerIf {
    private static Controller instance = null;
    private final KayttajaDAOIF kayttajaDAO;
    private final ResurssitDAOIF resurssiDAO;
    private final VarauksetDAOIF varausDAO;
    private final KayttajaTarkistusIf kayttajaTarkistus;
    private final PasswordConverterInterface crypter;
    private final LoginUtilsIf login;
    private final ChoiceboxUtilsIf cbutils;
    private final DayCellFactoryIf cellfactory;
    private final VarauksenAikaLaskuriInterface aikalaskuri;
    private final VarausKasittelyIf varausKasittely;
    private final ResurssiKasittelyIf resurssikasittely;
    private final KalenterinTarvitsematToimenpiteetIf kalenteriApu;
    private final SahkopostiIf sahkoposti;
    private final SalasananPalautusIf salasananPalautus;
    private final PoistaBtnToiminnotIf poistaBtnToiminnot;
    private final LanguageTextIf tekstit;
    private final IstuntoIF istunto;
    

    /**
     * Controllerin konstruktio
     * 
     */
    private Controller(){        
        tekstit = LanguageText.getInstance();
        kayttajaDAO = new KayttajaAccessObject();
        kayttajaTarkistus = new KayttajaTarkistus(this);
        resurssiDAO = new ResurssitAccessObject();
        varausDAO = new VarauksetAccessObject();
        login = new LoginUtils(this);
        cbutils = new ChoiceboxUtils(this);
        cellfactory = new DayCellFactory();
        aikalaskuri = new VarauksenAikaLaskuri();
        varausKasittely = new VarausKasittely(this,varausDAO);
        resurssikasittely = new ResurssiKasittely(this);
        kalenteriApu = new KalenterinTarvitsematToimenpiteet();
        sahkoposti = new Sahkoposti(this);
        salasananPalautus = new SalasananPalautus(this);
        poistaBtnToiminnot = new PoistaBtnToiminnot(this);
        crypter = new PasswordConverter();
        istunto = new Istunto(this);
    }
    
    public static Controller getInstance() {
        if(instance == null){
            instance = new Controller();
        }
        return instance;
    }

    /**
     * Kutsuu PasswordConverterInterface.passwordConverter()
     *
     * @param password Encryptattava salasana.
     * @return Palautaa encryptattatun salasanan.
     */
    @Override
    public String salasananCryptaus(String password) {
        return crypter.passwordConverter(password);
    }

    /**
     * kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @return Palauttaa taulukon kaikista käyttäjistä
     */
    @Override
    public Kayttaja[] haeKaikkiKayttajat() {
        return kayttajaDAO.readKayttajat();
    }

    /**
     * Kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @param id haetun käyttäjän id
     * @return palauttaa käyttäjä-olion
     */
    @Override
    public Kayttaja haeKayttaja(int id) {
        return kayttajaDAO.readKayttaja(id);
    }

    /**
     * Kutsuu KayttajaAccessObject.createKayttaja()
     *
     * @param kayttaja tietokantaan vietävä kayttaja-olio
     * @return palauttaa true jos käyttäjän vienti tietokantaan onnistui
     */
    @Override
    public boolean luoKayttaja(Kayttaja kayttaja) {
        return kayttajaDAO.createKayttaja(kayttaja);
    }

    /**
     * Kutsuu KayttajaAccessObject.uptadeKayttaja()
     *
     * @param kayttaja päivitettävä kayttaja -olio
     * @return palauttaa true jos käyttäjän päivitys tietokantaan onnistui
     */
    @Override
    public boolean paivitaKayttaja(Kayttaja kayttaja) {
        return kayttajaDAO.updateKayttaja(kayttaja);
    }

    /**
     * Kutsuu VarausKasittely.poistaKayttajanVaraukset() Kutsuu
     * KayttajaAccessObject.deleteKayttaja()
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poista tietokannasta onnistui
     */
    @Override
    public boolean poistaKayttaja(int id) {
        varausKasittely.poistaKayttajanVaraukset(id);
        return kayttajaDAO.deleteKayttaja(id);
    }

    /**
     * Kutsuu KayttajaTarkistus.tarkistaUsername()
     *
     * @param username tarkistettava käyttäjätunnus
     * @return palauttaa true jos käyttäjätunnusta ei löydy tietokannasta
     */
    @Override
    public boolean tarkistaUsername(String username) {
        return kayttajaTarkistus.usernameTarkastus(username);
    }

    /**
     * Kutsuu KayttajaTarkistus.emailTarkistus
     *
     * @param email tarkistettava sähköposti
     * @return palauttaa true jos sähköpostia ei löydy tietokannasta
     */
    @Override
    public boolean tarkistaEmail(String email) {
        return kayttajaTarkistus.emailTarkastus(email);
    }

    /**
     * Kutsuu ResurssiAccessObject.readResurssit()
     *
     * @return palauttaa taulukon kaikista resurssi -olioista
     */
    @Override
    public Resurssit[] haeKaikkiResurssit() {
        return resurssiDAO.readResurssit();
    }

    /**
     * Kutsuu Controller.tarkistaVarausAKtiivisuudet() Kutsuu
     * VarausAccessObject.readVaraukset()
     *
     * @return palauttaa taulukon kaikista varaus -olioista
     */
    @Override
    public Varaukset[] haeKaikkiVaraukset() {
        this.tarkistaVarausAktiivisuudet();
        return varausDAO.readVaraukset();
    }

    /**
     * Kutsuu VarausKasittely.tarkistaAktiivisuudet()
     *
     * @return true jos onnistuu
     */
    @Override
    public boolean tarkistaVarausAktiivisuudet() {
        return varausKasittely.tarkistaAktiivisuudet();
    }

    /**
     * kutsuu VarausKasittely.haeKayttajanVaraukset()
     *
     * @param kayttaja kayttaja -olio jonka varaukset haetaan
     * @return palauttaa taulukon kaikista käyttäjän varaus -olioista
     */
    @Override
    public Varaukset[] haeKayttajanVaraukset(Kayttaja kayttaja) {
        return varausKasittely.haeKayttajanVaraukset(kayttaja);
    }

    /**
     * Kutsuu ResurssiKasittely.poisteResurssinVaraukset() Kutsuu
     * ResurssiAccessObject.deleteResurssi()
     *
     * @param r tietokannasta poistettava resurssi
     * @return palauttaa true jos resurssin poisto tietokannasta onnistui
     */
    @Override
    public boolean poistaResurssi(Resurssit r) {
        resurssikasittely.poistaResurssinVaraukset(r);
        return resurssiDAO.deleteResurssi(r.getId());
    }

    /**
     * Kutsuu ResurssiAccessObject.updateResurssi()
     *
     * @param r päivitettävä resurssi -olio
     * @return palauttaa true jos resurssin päivitys tietokantaan onnistui
     */
    @Override
    public boolean paivitaResurssi(Resurssit r) {
        return resurssiDAO.updateResurssi(r);
    }

    /**
     * Kutsuu VarausAccessObject.updateVaraus()
     *
     * @param v päivitettävä varaus -olio
     * @return palauttaa true jos varauksen päivitys tietokantaan onnistui
     */
    @Override
    public boolean paivitaVaraus(Varaukset v) {
        return varausDAO.updateVaraus(v);
    }

    /**
     * Kutsuu VarausAccessObject.createVaraus()
     *
     * @param v tietokantaan vietävä varaus -olio
     * @return palauttaa true jos varauksen vienti tietokantaan onnistui
     */
    @Override
    public boolean luoVaraus(Varaukset v) {
        return varausDAO.createVaraus(v);
    }

    /**
     * Kutsuu ResurssiAccessObject.createResurssi()
     *
     * @param r tietokantaan vietävä resurssi -olio
     * @return palauttaa true jos resurssin vienti tietokantaan onnistui
     */
    @Override
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
    @Override
    public boolean login(String userName, String passWord) {
        return login.loginProcess(userName, this.salasananCryptaus(passWord));
    }

    /**
     * Kutsuu VarausAccessObject.readVaraus()
     *
     * @param id haetun varauksen id
     * @return palauttaa varaus-olion
     */
    @Override
    public Varaukset haeVaraus(int id) {
        return varausDAO.readVaraus(id);
    }

    /**
     * Kutsuu VarausAccessObject.deleteVaraus()
     *
     * @param id tietokannasta poistettavan varauksen id
     * @return palauttaa true jos varauksen poisto tietokannasta onnistui
     */
    @Override
    public boolean poistaVaraus(int id) {
        return varausDAO.deleteVaraus(id);
    }

    /**
     * Kutsuu ChoiceboxUtils.tulkitseChoiceBox()
     *
     * @param cb choice box elementti jota tulkitaan
     * @return choice boxia vastaavan kokonais luvun
     */
    @Override
   public int readCb(ChoiceBox cb) {
        return cbutils.tulkitseChoiceBox(cb);

   }

    /**
     * Vie paivat varauksen kesto laskuriin ja tuo sen jälkeen, kuinka monta
     * päivää varaus kestää.
     *
     * @param alkupvm milloin varaus alkaa
     * @param loppumispvm milloin varaus loppuu
     * @return alkupvm ja loppupvm erotuksen
     */
    @Override
    public int paivaLaskuri(LocalDateTime alkupvm, LocalDateTime loppumispvm) {
        return aikalaskuri.paivaKesto(alkupvm, loppumispvm);
    }

    /**
     * Palauttaa datepickerille muokatut päivät.
     *
     * @param varaukset varaukset joila on varaukset tietyihin päiville.
     * @param today mistä päivästä eteenpäin voi päiviä valita.
     * @return Callbackin jossa on muokatuja päiviä.
     */
    @Override
    public Callback dayCellFactory(Varaukset[] varaukset, LocalDate today) {
        return cellfactory.dayCellFactory(this,varaukset, today);
    }

    /**
     * Hakee modelista BooleanConverter-ilmentymän
     *
     * @return BooleanConverter-olio
     */
    @Override
    public BooleanConverter getBoolConv() {
        return new BooleanConverter();
    }

    /**
     * Siirtää ArrayListasta varaus alkiot varaus Array:hin.
     *
     * @param aVaraukset Varaus ArrayListasta josta halutaan tehdä array.
     * @return Varaukset Array:na.
     */
    @Override
    public Varaukset[] getVarausTaulukko(List<Varaukset> aVaraukset) {
        return kalenteriApu.getVarausTaulukko(aVaraukset);
    }

    /**
     * Vie parametrit KalenterinTarvitsematToimenpiteet() luokalle, jossa
 resurssiId:n avulla varaus Arraysta tehdään ArrayListan jossa on vain sen
 resursin varaukset.
     *
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Varaus array josta halutaan saada resursin varaukset.
     * @return ArrayListan jossa on resursin varaukset Arraysta.
     */
    @Override
    public List<Varaukset> resurssinVaraukset(int resurssiId, Varaukset[] varaukset) {
        return kalenteriApu.resurssinVaraukset(resurssiId, varaukset);
    }

    /**
     * Vie parametrit KalenterinTarvitsematToimenpiteet lu9okalle parametrit.
     * Se kertoo jos varaus sillä ajan hetkellä on mahdollista. Kun verrataan
     * muihin tuoteen varauksiin.
     *
     * @param aVaraukset ArrayLista varattavan tuoteen varauksista.
     * @param endDate Milloin tuleva varaus loppuu. (päivä)
     * @param startDate Milloin tuleva varaus alkaa. (päivä)
     * @return true jos vraus on mahdollista ja falsen jos varaus menee toisen
     * varauksen päälle.
     */
    @Override
    public boolean onnistuu(List<Varaukset> aVaraukset, ChronoLocalDateTime endDate, ChronoLocalDateTime startDate) {
        return kalenteriApu.onnistuu(aVaraukset, endDate, startDate);
    }

    /**
     * Kutsuu VarausKasittely.haeKasittelemattomat()
     *
     * @return taulukko käsittelemättömistä varaus -oloista.
     */
    @Override
    public Varaukset[] haeKasittelemattomatVaraukset() {
        return varausKasittely.haeKasittelemattomat();
    }

    /**
     * Kutsuu Sahkoposti.lähetäSähköposti()
     *
     * @param vastaanottaja Sahkopostiosoite johon lähetetään
     * @param viesti lähettettävä viesti
     * @return true jos lähetys onnistuu
     *
     */
    @Override
    public boolean lahetaSahkoposti(String vastaanottaja, String viesti) {
        return sahkoposti.lahetaSahkoposti(vastaanottaja, viesti);
    }

    /**
     * Kutsuu VarausKasittely.getVarausAikaString
     *
     * @param v Varaus, jonka tiedoista string kasataan sähköpostia varten
     * @return String, jossa näkyy varattavan laitteen nimi ja varauksen
     * ajankohta.
     */
    @Override
    public String getVarausAikaString(Varaukset v) {
        return varausKasittely.getVarausAikaString(v);
    }

    /**
     * Kutsuu SalasananPalautus.palautaSalasana()
     *
     * @param email Sähköposti palautetaan
     * @return true jos palautus onnistui
     */
    @Override
    public boolean palautaSalasana(String email) {
        return salasananPalautus.palautaSalasana(email);
    }

    /**
     * Kutsuu VarausKasittely.tarkistaOnkoVarausAlkanut
     *
     * @param varaus tarkistettava varaus
     * @return true jos varauksen alkamisaika on mennyt jo
     */
    @Override
    public boolean onkoVarausAlkanut(Varaukset varaus) {
        return varausKasittely.tarkistaOnkoVarausAlkanut(varaus);
    }

    /**
     * Kutsuu PoistaBtnToiminnot.varauksetPoistaBtn()
     *
     * @param toDelete poistettava varaus
     * @return true jos poisto onnistuu
     */
    @Override
    public boolean poistaVarausBtnToiminto(Varaukset toDelete) {
        return poistaBtnToiminnot.varauksetPoistaBtn(toDelete);
    }

    /**
     * Tuo tiedostosta tekstiä sillä kielellä mikä maa on
     * @param mihin mistä kohtaaa tiedostosta otetaan tietoja
     * @return Stringin joka on halutulla kielellä jos ei löydy antaa nullin
     */
    @Override
    public String getConfigTeksti(String mihin) {
        return tekstit.getText(mihin);
    }

    /**
     * Asettaa LanguageTextin Maa parametrin käy vain fi, en, por tai pt
     * @param maa mihin kieleen vaihdetaan
     */
    @Override
    public void setMaa(String maa){
        tekstit.setMaa(maa);
    }
    
    /**
     * Palauttaa istunto.selected jossa säilytetään taulukosta valittu käyttäjä
     * @return valittu käyttäjä
     */
    @Override
    public Kayttaja getSelected(){
        return istunto.getSelected();
    }
    
    /**
     * Metodi selected käyttäjä asettamiseen, jossa säilytetään taulukosta valittu käyttäjä
     * @param kayttaja valittu käyttäjä
     */
    @Override
    public void setSelected(Kayttaja kayttaja){
        istunto.setSelected(kayttaja);
    }
    
    /**
     * Palauttaa istunto.booking jossa säilytetään taulukosta valittua resurssia
     * @return valittu resurssi
     */
    @Override
    public Resurssit getBooking(){
        return istunto.getBooking();
    }
    
    /**
     * Metodi booking resurssin asettamiseen, jossa säilytetään taulukosta valittu resurssi
     * @param booking valittu resurssi
     */
    @Override
    public void setBooking(Resurssit booking){
        istunto.setBooking(booking);
    }
    
    /**
     * Yrityksen nimeä hakeva medodi, istunto.bizname
     * @return yrityksen nimi
     */
    @Override
    public String getBizname(){
        return istunto.getBizname();
    }
    
    /**
     * Metodi palauttaa istunto.loggedin, jossa säilytetään kirjautunutta käyttäjää
     * @return kirjautunut käyttäjä
     */
    @Override
    public Kayttaja getLoggedIn(){
        return istunto.getLoggedIn();
    }
    
    /**
     * Metodi kirjautuneen käyttäjän asettamiseen
     * @param kayttaja kirjautunut käyttäjä
     */
    @Override
    public void setLoggedIn(Kayttaja kayttaja){
        istunto.setLoggedIn(kayttaja);
    }
    
    
}
