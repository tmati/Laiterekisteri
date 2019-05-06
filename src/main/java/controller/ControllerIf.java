/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.util.List;
import javafx.scene.control.ChoiceBox;
import javafx.util.Callback;
import model.BooleanConverter;
import model.Kayttaja;
import model.Resurssit;
import model.Varaukset;

/**
 *
 * @author jukka
 */
public interface ControllerIf {

    /**
     * Palauttaa datepickerille muokatut päivät.
     *
     * @param varaukset varaukset joila on varaukset tietyihin päiville.
     * @param today mistä päivästä eteenpäin voi päiviä valita.
     * @return Callbackin jossa on muokatuja päiviä.
     */
    Callback dayCellFactory(Varaukset[] varaukset, LocalDate today);

    /**
     * Hakee modelista BooleanConverter-ilmentymän
     *
     * @return BooleanConverter-olio
     */
    BooleanConverter getBoolConv();

    /**
     * Tuo tiedostosta tekstiä sillä kielellä mikä maa on
     * @param mihin mistä kohtaaa tiedostosta otetaan tietoja
     * @return Stringin joka on halutulla kielellä jos ei löydy antaa nullin
     */
    String getConfigTeksti(String mihin);

    /**
     * Kutsuu VarausKasittely.getVarausAikaString
     *
     * @param v Varaus, jonka tiedoista string kasataan sähköpostia varten
     * @return String, jossa näkyy varattavan laitteen nimi ja varauksen
     * ajankohta.
     */
    String getVarausAikaString(Varaukset v);

    /**
     * Siirtää ArrayListasta varaus alkiot varaus Array:hin.
     *
     * @param aVaraukset Varaus ArrayListasta josta halutaan tehdä array.
     * @return Varaukset Array:na.
     */
    Varaukset[] getVarausTaulukko(List<Varaukset> aVaraukset);

    /**
     * kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @return Palauttaa taulukon kaikista käyttäjistä
     */
    Kayttaja[] haeKaikkiKayttajat();

    /**
     * Kutsuu ResurssiAccessObject.readResurssit()
     *
     * @return palauttaa taulukon kaikista resurssi -olioista
     */
    Resurssit[] haeKaikkiResurssit();

    /**
     * Kutsuu Controller.tarkistaVarausAKtiivisuudet() Kutsuu
     * VarausAccessObject.readVaraukset()
     *
     * @return palauttaa taulukon kaikista varaus -olioista
     */
    Varaukset[] haeKaikkiVaraukset();

    /**
     * Kutsuu VarausKasittely.haeKasittelemattomat()
     *
     * @return taulukko käsittelemättömistä varaus -oloista.
     */
    Varaukset[] haeKasittelemattomatVaraukset();

    /**
     * Kutsuu KayttajaAccessObject.readKayttaja()
     *
     * @param id haetun käyttäjän id
     * @return palauttaa käyttäjä-olion
     */
    Kayttaja haeKayttaja(int id);

    /**
     * kutsuu VarausKasittely.haeKayttajanVaraukset()
     *
     * @param kayttaja kayttaja -olio jonka varaukset haetaan
     * @return palauttaa taulukon kaikista käyttäjän varaus -olioista
     */
    Varaukset[] haeKayttajanVaraukset(Kayttaja kayttaja);

    /**
     * Kutsuu VarausAccessObject.readVaraus()
     *
     * @param id haetun varauksen id
     * @return palauttaa varaus-olion
     */
    Varaukset haeVaraus(int id);

    /**
     * Kutsuu Sahkoposti.lähetäSähköposti()
     *
     * @param vastaanottaja Sahkopostiosoite johon lähetetään
     * @param viesti lähettettävä viesti
     * @return true jos lähetys onnistuu
     *
     */
    boolean lahetaSahkoposti(String vastaanottaja, String viesti);

    /**
     * Tarkistaa modelista käyttäjänimen ja salasanan.
     *
     * @param userName käyttäjänimi
     * @param passWord salasana
     * @return true jos kirjautumistiedot oikein
     */
    boolean login(String userName, String passWord);

    /**
     * Kutsuu KayttajaAccessObject.createKayttaja()
     *
     * @param kayttaja tietokantaan vietävä kayttaja-olio
     * @return palauttaa true jos käyttäjän vienti tietokantaan onnistui
     */
    boolean luoKayttaja(Kayttaja kayttaja);

    /**
     * Kutsuu ResurssiAccessObject.createResurssi()
     *
     * @param r tietokantaan vietävä resurssi -olio
     * @return palauttaa true jos resurssin vienti tietokantaan onnistui
     */
    boolean luoResurssi(Resurssit r);

    /**
     * Kutsuu VarausAccessObject.createVaraus()
     *
     * @param v tietokantaan vietävä varaus -olio
     * @return palauttaa true jos varauksen vienti tietokantaan onnistui
     */
    boolean luoVaraus(Varaukset v);

    /**
     * Kutsuu VarausKasittely.tarkistaOnkoVarausAlkanut
     *
     * @param varaus tarkistettava varaus
     * @return true jos varauksen alkamisaika on mennyt jo
     */
    boolean onkoVarausAlkanut(Varaukset varaus);

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
    boolean onnistuu(List<Varaukset> aVaraukset, ChronoLocalDateTime endDate, ChronoLocalDateTime startDate);

    /**
     * Vie paivat varauksen kesto laskuriin ja tuo sen jälkeen, kuinka monta
     * päivää varaus kestää.
     *
     * @param alkupvm milloin varaus alkaa
     * @param loppumispvm milloin varaus loppuu
     * @return alkupvm ja loppupvm erotuksen
     */
    int paivaLaskuri(LocalDateTime alkupvm, LocalDateTime loppumispvm);

    /**
     * Kutsuu KayttajaAccessObject.uptadeKayttaja()
     *
     * @param kayttaja päivitettävä kayttaja -olio
     * @return palauttaa true jos käyttäjän päivitys tietokantaan onnistui
     */
    boolean paivitaKayttaja(Kayttaja kayttaja);

    /**
     * Kutsuu ResurssiAccessObject.updateResurssi()
     *
     * @param r päivitettävä resurssi -olio
     * @return palauttaa true jos resurssin päivitys tietokantaan onnistui
     */
    boolean paivitaResurssi(Resurssit r);

    /**
     * Kutsuu VarausAccessObject.updateVaraus()
     *
     * @param v päivitettävä varaus -olio
     * @return palauttaa true jos varauksen päivitys tietokantaan onnistui
     */
    boolean paivitaVaraus(Varaukset v);

    /**
     * Kutsuu SalasananPalautus.palautaSalasana()
     *
     * @param email Sähköposti palautetaan
     * @return true jos palautus onnistui
     */
    boolean palautaSalasana(String email);

    /**
     * Kutsuu VarausKasittely.poistaKayttajanVaraukset() Kutsuu
     * KayttajaAccessObject.deleteKayttaja()
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poista tietokannasta onnistui
     */
    boolean poistaKayttaja(int id);

    /**
     * Kutsuu ResurssiKasittely.poisteResurssinVaraukset() Kutsuu
     * ResurssiAccessObject.deleteResurssi()
     *
     * @param r tietokannasta poistettava resurssi
     * @return palauttaa true jos resurssin poisto tietokannasta onnistui
     */
    boolean poistaResurssi(Resurssit r);

    /**
     * Kutsuu VarausAccessObject.deleteVaraus()
     *
     * @param id tietokannasta poistettavan varauksen id
     * @return palauttaa true jos varauksen poisto tietokannasta onnistui
     */
    boolean poistaVaraus(int id);

    /**
     * Kutsuu PoistaBtnToiminnot.varauksetPoistaBtn()
     *
     * @param toDelete poistettava varaus
     * @return true jos poisto onnistuu
     */
    boolean poistaVarausBtnToiminto(Varaukset toDelete);

    /**
     * Kutsuu ChoiceboxUtils.tulkitseChoiceBox()
     *
     * @param cb choice box elementti jota tulkitaan
     * @return choice boxia vastaavan kokonais luvun
     */
    int readCb(ChoiceBox cb);

    /**
     * Vie parametrit KalenterinTarvitsematToimenpiteet() luokalle, jossa
    resurssiId:n avulla varaus Arraysta tehdään ArrayListan jossa on vain sen
    resursin varaukset.
     *
     * @param resurssiId Halutun resursin Id.
     * @param varaukset Varaus array josta halutaan saada resursin varaukset.
     * @return ArrayListan jossa on resursin varaukset Arraysta.
     */
    List<Varaukset> resurssinVaraukset(int resurssiId, Varaukset[] varaukset);

    /**
     * Kutsuu PasswordConverterInterface.passwordConverter()
     *
     * @param password Encryptattava salasana.
     * @return Palautaa encryptattatun salasanan.
     */
    String salasananCryptaus(String password);

    /**
     * Asettaa LanguageTextin Maa parametrin käy vain fi, en, por tai pt
     * @param maa mihin kieleen vaihdetaan
     */
    void setMaa(String maa);

    /**
     * Kutsuu KayttajaTarkistus.emailTarkistus
     *
     * @param email tarkistettava sähköposti
     * @return palauttaa true jos sähköpostia ei löydy tietokannasta
     */
    boolean tarkistaEmail(String email);

    /**
     * Kutsuu KayttajaTarkistus.tarkistaUsername()
     *
     * @param username tarkistettava käyttäjätunnus
     * @return palauttaa true jos käyttäjätunnusta ei löydy tietokannasta
     */
    boolean tarkistaUsername(String username);

    /**
     * Kutsuu VarausKasittely.tarkistaAktiivisuudet()
     *
     * @return true jos onnistuu
     */
    boolean tarkistaVarausAktiivisuudet();
    
}
