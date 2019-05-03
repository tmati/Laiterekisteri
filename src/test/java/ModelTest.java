/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Controller.Controller;
import Model.BooleanConverter;
import Model.Kayttaja;
import Model.LanguageText;
import Model.LuvanvaraisuusConverter;
import Model.ResurssiKasittely;
import Model.Resurssit;
import Model.VarauksenAikaLaskuri;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import Model.VarauksetDAO_IF;
import Model.VarausKasittely;
import java.time.LocalDateTime;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tommi
 */
public class ModelTest {

    private static Controller controller = new Controller();
    private static Kayttaja k;
    private static Resurssit res;
    private static Varaukset var;
    private static Varaukset var1;
    private VarauksetDAO_IF dao = new VarauksetAccessObject();
    private VarausKasittely vk = new VarausKasittely(dao, controller);

    @BeforeClass
    public static void setUpClass() {
        k = new Kayttaja("contTest", "contTest", "rtrhj", "keychaintesti@gmail.com", 1);
        res = new Resurssit(true, "ctesti", "ctesti", 1, "ctesti");
        var = new Varaukset(k, res, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), "testi", false, "testi", true);
        var1 = new Varaukset(k, res, LocalDateTime.now().minusDays(2), LocalDateTime.now().minusDays(1), "testi", true, "testi", true);
        controller.luoResurssi(res);
        controller.luoKayttaja(k);
        controller.luoVaraus(var);
        controller.luoVaraus(var1);
    }

    @AfterClass
    public static void tearDownClass() {
        controller.poistaVaraus(var.getId());
        controller.poistaVaraus(var1.getId());
        controller.poistaResurssi(res);
        controller.poistaKayttaja(k.getId());
    }

    @Test
    public void tarkistaAktiivisuudetTesti() {
        vk.tarkistaAktiivisuudet();
        var.setHyvaksytty(false);
        controller.paivitaVaraus(var);
        //varauksen palautettu muuttaa falseksi
        vk.tarkistaAktiivisuudet();
        assertFalse("Varauksen palautettu status piti olla false",
                var.isPalautettu());

        var.setHyvaksytty(false);

        Varaukset var2 = new Varaukset(k, res, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(5), "testi", true, "testi", false);
        controller.luoVaraus(var2);
        //käsittelemättömien varausten haku
        assertTrue("haeKasittelemattomat",
                vk.haeKasittelemattomat() != null);

        assertTrue("tarkistaAktiisuudet",
                vk.tarkistaAktiivisuudet());
    }

    @Test
    public void poistaResurssinVaraukset() {
        ResurssiKasittely rk = new ResurssiKasittely(controller);
        assertTrue("poistaResurssinVaraukset: poisto ei onnistunut",
                rk.poistaResurssinVaraukset(res));
    }

    @Test
    public void poistaKayttäjanVaraukset() {
        Varaukset varausvar3 = new Varaukset(k, res, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "testi", true, "testi", true);
        controller.luoVaraus(var);
        k = controller.haeKayttaja(k.getId());
        assertTrue("poistaKayttajanVaraukset: ei onnistunut",
                vk.poistaKayttajanVaraukset(k.getId()));
    }

    @Test
    public void setMaagetMaaTesti() {
        LanguageText.getInstance().setMaa("fi");
        assertTrue("getMaa: väärä kieli",
                LanguageText.getInstance().getMaa().equals("fi"));
    }

    @Test
    public void luvanVaraisuusConverterTest() {
        LuvanvaraisuusConverter l = new LuvanvaraisuusConverter(controller);
        l = new LuvanvaraisuusConverter(controller, "0", "1", "2");
        assertTrue("toString: väärä",
                l.toString(0).equals("0"));
        assertTrue("toString: väärä",
                l.toString(1).equals("1"));
        assertTrue("toString: väärä",
                l.toString(2).equals("2"));
        assertTrue("toString: väärä",
                l.toString(6) == null);

        assertTrue("fromString: väärä",
                l.fromString("0") == 0);
        assertTrue("fromString: väärä",
                l.fromString("1") == 1);
        assertTrue("fromString: väärä",
                l.fromString("2") == 2);
    }

    @Test
    public void booleanConverterTest() {
        BooleanConverter b = new BooleanConverter(controller, "true", "false");
        
        assertTrue("toString: väärä",
                b.toString(false).equals("false"));
        assertTrue("toString: väärä",
                b.toString(true).equals("true"));
        
        assertTrue("fromString: väärä",
                b.fromString("true"));
    }
    
    @Test
    public void varausAikaLaskuriTest(){
        VarauksenAikaLaskuri val = new VarauksenAikaLaskuri();
        
        
    }
}
