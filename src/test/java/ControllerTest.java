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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.scene.control.ChoiceBox;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 * Testi -luokka Controller -luokalle
 *
 * @author Tommi
 */

@Ignore
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
        LocalDate aloitusPV = LocalDate.parse("2019-04-01");
        LocalTime aloitusAika = LocalTime.parse("10:00");
        LocalDate lopetusPV = LocalDate.parse("2019-04-02");
        LocalTime lopetusAika = LocalTime.parse("16:00");

        LocalDateTime aloitus = LocalDateTime.of(aloitusPV, aloitusAika);
        LocalDateTime lopetus = LocalDateTime.of(lopetusPV, lopetusAika);
        Varaukset v = new Varaukset(kay[0], r[1], aloitus, lopetus, "testi", false, "testi", false);
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
    public void ControllerResurssiTestit(){
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
    public void ControllerUtilityTestit(){
        assertFalse("salasananCryptaus: ei onnistunut",
                kont.SalasananCryptaus("asd").equalsIgnoreCase("asd"));
        assertTrue("login: ei onnistunut",
                kont.login(kay[0].getKayttajatunnus(), kay[0].getSalasana()));
        assertFalse("login: pääsi sisään vaikka ei pitänyt",
                kont.login("asdgfds", "äöädrftrewqsxd"));
       
        
    }
    
}
