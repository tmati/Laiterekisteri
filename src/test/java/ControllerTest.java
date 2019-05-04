/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.Controller;
import Model.Kayttaja;
import Model.Resurssit;
import Model.Varaukset;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Ignore;

/**
 * Testi -luokka Controller -luokalle
 *
 * @author Tommi
 */
public class ControllerTest {

    static Controller kont;
    boolean tulos;
    Kayttaja[] kay = kont.haeKaikkiKayttajat();

    @BeforeClass
    public static void setUpClass() {
        kont = new Controller();

        //lähettää sähköpostin säikeessä, täytyy käydä tarkistamassa manuaalisesti
        kont.lahetaSahkoposti("keychainems@gmail.com", "testi");
    }

    /**
     * Kayttaja -olion käsittelyyn liittyvät testit
     */
    @Test
    public void ControllerKayttajaTestit() {
        Kayttaja k = new Kayttaja("contTest", "contTest", "contTest", "contTest", 1);
        assertTrue("luoKayttaja: ei onnistunut",
                kont.luoKayttaja(k));
        assertTrue("readKayttaja(): Haku ei onnistunut",
                (k = kont.haeKayttaja(k.getId())) != null);
        assertFalse("tarkistaEmail: ei onnistunut",
                kont.tarkistaEmail("contTest"));
        assertFalse("tarkistaUsername: ei onnistunut",
                kont.tarkistaUsername("contTest"));
        assertTrue("tarkistaEmail: ei onnistunut",
                kont.tarkistaEmail("conasdfghjktTest"));
        assertTrue("tarkistaUsername: ei onnistunut",
                kont.tarkistaUsername("conasdfghtTest"));
        k.setValtuudet(2);
        assertTrue("paivitaKayttaja: ei onnistunut",
                kont.paivitaKayttaja(k));
        assertTrue("poistaKayttaja: ei onnistunut",
                kont.poistaKayttaja(k.getId()));
        assertTrue("haeKaikkiKayttajat: ei onnistunut",
                kont.haeKaikkiKayttajat() != null);

    }

    @Test
    public void ControllerVarausTestit() {
        Resurssit[] r = kont.haeKaikkiResurssit();
        LocalDateTime aloitus = LocalDateTime.now();
        aloitus.plusHours(2);;
        LocalDateTime lopetus = aloitus.plusHours(2);
        Varaukset v = new Varaukset(kay[0], r[1], aloitus, lopetus, "testi", false, "testi", true);
        assertTrue("luoVaraus: ei onnistunut",
                kont.luoVaraus(v));
        assertTrue("readVaraus(): Haku ei onnistunut",
                (v = kont.haeVaraus(v.getId())) != null);
        v.setKuvaus("asd");
        assertTrue("paivitaVaraus: ei onnistunut",
                kont.paivitaVaraus(v));
        assertTrue("haeKaikkiVaraukset: ei onnistunut",
                kont.haeKaikkiVaraukset() != null);

        assertTrue("haeKayttajanVaraukset: ei onnistunut",
                kont.haeKayttajanVaraukset(kay[0]).length > 0);
        assertTrue("poistaVaraus: ei onnistunut",
                kont.poistaVaraus(v.getId()));
        //Hae käsittelemättömät varaukset
        Varaukset[] kasit = kont.haeKasittelemattomatVaraukset();
        assertTrue("haeKasittelattomatVaraukset: ei onnistunut - array on null",
                kasit != null);
        for (int i = 0; i < kasit.length; i++) {
            assertFalse("haeKasittelemattomatVaraukset: varaus on jo käsitely",
                    kasit[i].getHyvaksytty());
        }
    }

    @Test
    public void ControllerResurssiTestit() {
        Resurssit r = new Resurssit(true, "ctesti", "ctesti", 1, "ctesti");
        assertTrue("luoResurssi: ei onnistunut",
                kont.luoResurssi(r));
        r.setKuvaus("asd");
        assertTrue("paivitaResurssi: ei onnistunut",
                kont.paivitaResurssi(r));
        assertTrue("poistaResurssi: ei onnistunut",
                kont.poistaResurssi(r));

    }

    @Test
    public void ControllerUtilityTestit() {
        //Salasanan cryptaus
        assertFalse("salasananCryptaus: ei onnistunut",
                kont.SalasananCryptaus("asd").equalsIgnoreCase("asd"));
        //Login
        assertTrue("login: ei onnistunut",
                kont.login("testi", "testi"));
        assertFalse("login: pääsi sisään vaikka ei pitänyt",
                kont.login("asdgfds", "äöädrftrewqsxd"));
        //Varaus aktiivisuuden tarkastus
        assertTrue("tarkistaVarausAktiivisuudet: ei onnistunut",
                kont.tarkistaVarausAktiivisuudet());
        //Paivalaskuri
        assertTrue("paivaLaskuri: paivien erotus väärin",
                kont.paivaLaskuri(LocalDateTime.now(), LocalDateTime.now().plusYears(2).plusMonths(1).plusDays(1).plusHours(1)) != 0);
        //ressurssin varaukset arraylistinä
        assertTrue("resurssinVaraukset: Arraylist null",
                kont.resurssinVaraukset(kont.haeKaikkiResurssit()[0].getId(), kont.haeKaikkiVaraukset()) != null);
        Resurssit res = new Resurssit(true, "ctesti", "ctesti", 1, "ctesti");
        kont.luoResurssi(res);
        Kayttaja k = new Kayttaja("contTest", "contTest", "rtrhj", "keychaintesti@gmail.com", 1);
        kont.luoKayttaja(k);
        Varaukset var = new Varaukset(k, res, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "testi", false, "testi", true);
        ChronoLocalDateTime eka = LocalDateTime.now().plusDays(1);
        ChronoLocalDateTime toka = LocalDateTime.now().plusDays(2);
        //Tarkistus, että varaus ei mene olemassa olevan varauksen päälle
        assertTrue("onnistuu: tarkistus epäonnistui, piti tulla true",
                kont.Onnistuu(kont.resurssinVaraukset(res.getId(), kont.haeKaikkiVaraukset()), toka, eka));
        //varauspäivä on loppupv jälkeen
        assertFalse("onnistuu: tarkistus epäonnistui, piti tulla false",
                kont.Onnistuu(kont.resurssinVaraukset(res.getId(), kont.haeKaikkiVaraukset()), eka, toka));
        //Aloitus pv menneisyydessä
        ChronoLocalDateTime men = LocalDateTime.now().minusDays(1);
        assertFalse("onnistuu: tarkistus epäonnistui, piti tulla false",
                kont.Onnistuu(kont.resurssinVaraukset(res.getId(), kont.haeKaikkiVaraukset()), eka, men));
        //varaus toisen kanssa päällekkäin
        Varaukset t = kont.haeKaikkiVaraukset()[0];
        eka = t.getAlkuAika();
        toka = t.getLoppuAika();
        assertFalse("onnistuu: tarkistus epäonnistui, piti tulla false",
                kont.Onnistuu(kont.resurssinVaraukset(t.getResurssit().getId(), kont.haeKaikkiVaraukset()), toka, eka));
        //varausTaulukon luonti
        assertTrue("getVarausTaulukko: haku ei onnistunut",
                kont.getVarausTaulukko(kont.resurssinVaraukset(t.getResurssit().getId(), kont.haeKaikkiVaraukset())) != null);
        kont.poistaResurssi(res);

        //booleanConverter haku
        assertTrue("getBoolConv: null",
                 kont.getBoolConv() != null);

        //Kieliasetusten haku
        assertTrue("getConfigTeksti: haku ei onnistunut",
                kont.getConfigTeksti("logoutInfo") != null);
        assertTrue("getConfigTeksti: haku ei onnistunut",
                kont.getConfigTeksti("") == null);

        //Onko varaus alkanut tarkistus
        assertFalse("onkoVarausAlkanut: varaus ei ole alkanut, piti tulla false",
                kont.onkoVarausAlkanut(var));
        var.setAlkuAika(LocalDateTime.now().minusDays(1));
        assertTrue("onkoVarausAlkanut: varaus on alkanut, piti tulla true",
                kont.onkoVarausAlkanut(var));

        //Salasanan palautus
        assertTrue("palautaSalasana: piti tulla true",
                kont.palautaSalasana("keychaintesti@gmail.com"));
        assertFalse("palautaSalasana: piti tulla false",
                kont.palautaSalasana("asdfghjkljhgfdsa"));
        //poistaa testi käyttäjän
        kont.poistaKayttaja(k.getId());

        //kasattu string sähköpostia varten
        assertTrue("getVarausAikaString: string ei ollut oikeanlainen",
                kont.getVarausAikaString(t).equals(kont.getConfigTeksti("emailReservationTime") + t.getNimi() + " " + kont.getConfigTeksti("forTime")+ " " + t.getAlkuAika().getHour() + "." + t.getAlkuAika().getDayOfMonth() + "."
                + t.getAlkuAika().getYear() + "-" + t.getLoppuAika().getHour() + "." + t.getLoppuAika().getDayOfMonth()
                + "." + t.getLoppuAika().getYear()));

        //poistaa testi käyttäjän
        kont.poistaKayttaja(k.getId());

        //DayCellFacotry joka paöauttaa datepickerille muokatutpäivät
        assertTrue("dayCellFactory: ei onnistunut",
                kont.dayCellFactory(kont.haeKaikkiVaraukset(), LocalDate.now()) != null);

    }
}
