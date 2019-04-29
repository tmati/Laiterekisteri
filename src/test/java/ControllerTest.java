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
        assertTrue("paivaLaskuri: paivien erotus väärin",
                kont.paivaLaskuri(LocalDateTime.now(), LocalDateTime.now().plusDays(2)) == 2);
    }
    
  
    
}
