/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.Controller;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.Resurssit;
import Model.Varaukset;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Testi -luokka Controller -luokalle
 *
 * @author Tommi
 */
public class ControllerTest {

    Controller kont = new Controller();
    Kayttaja[] kay = kont.haeKaikkiKayttajat();

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
                kont.paivaLaskuri(LocalDateTime.now(), LocalDateTime.now().plusDays(2)) == 2);
        //ressurssin varaukset arraylistinä
        assertTrue("resurssinVaraukset: Arraylist null",
                kont.resurssinVaraukset(kont.haeKaikkiResurssit()[0].getId(), kont.haeKaikkiVaraukset()) != null);
        Resurssit res = new Resurssit(true, "ctesti", "ctesti", 1, "ctesti");
        kont.luoResurssi(res);
         Varaukset var = new Varaukset(kay[0], res, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "testi", false, "testi", true);
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
        
    }
}
