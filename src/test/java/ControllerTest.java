/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.Controller;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tommi
 */
public class ControllerTest {

    Controller kont = new Controller();
    KayttajaAccessObject kDAO = new KayttajaAccessObject();
    @Test
    public void Controllertestit() {
        Kayttaja k = new Kayttaja("contTest","contTest","contTest","contTest",1);
        assertTrue("vieKayttajatietokantaan: ei onnistunut",
                kont.vieUusiKayttajaTietokantaan(k));
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

}
