

import controller.Controller;
import controller.ControllerIf;
import java.time.LocalDateTime;
import model.BooleanConverter;
import model.Kayttaja;
import model.LanguageText;
import model.LuvanvaraisuusConverter;
import model.ResurssiKasittely;
import model.Resurssit;
import model.VarauksenAikaLaskuri;
import model.Varaukset;
import model.VarauksetAccessObject;
import model.VarauksetDAOIF;
import model.VarausKasittely;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Ignore;
import org.junit.Test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author Tommi
 */



public class ModelTest {

    private static ControllerIf controller = Controller.getInstance();
    private static Kayttaja k;
    private static Resurssit res;
    private static Varaukset var;
    private static Varaukset var1;
    private VarauksetDAOIF dao = new VarauksetAccessObject();
    private VarausKasittely vk = new VarausKasittely(Controller.getInstance(), dao);

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
        ResurssiKasittely rk = new ResurssiKasittely(Controller.getInstance());
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
        LuvanvaraisuusConverter l = new LuvanvaraisuusConverter("0", "1", "2");
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
        BooleanConverter b = new BooleanConverter("true", "false");
        
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
        assertTrue("Virhe 30 päivän kuukauksissa",
               val.paivaKesto(LocalDateTime.of(2015, 11, 6, 0, 0), LocalDateTime.of(2015, 12, 6, 0, 0)) == 30);
        assertTrue("Virhe toisessa kuukauksissa",
               val.paivaKesto(LocalDateTime.of(2015, 2, 6, 0, 0), LocalDateTime.of(2015, 3, 6, 0, 0)) == 28);
        assertTrue("Virhe karkausvuosissa",
               val.paivaKesto(LocalDateTime.of(2016, 2, 6, 0, 0), LocalDateTime.of(2016, 3, 6, 0, 0)) == 29);
        assertTrue("Virhe 31 kuukauksissa",
               val.paivaKesto(LocalDateTime.of(2016, 1, 6, 0, 0), LocalDateTime.of(2016, 2, 6, 0, 0)) == 31);
        assertTrue("Virhe saman kuukauden aikana",
               val.paivaKesto(LocalDateTime.of(2016, 2, 6, 0, 0), LocalDateTime.of(2016, 2, 26, 0, 0)) == 20);
        assertTrue("Virhe vuoden laskuissa",
               val.paivaKesto(LocalDateTime.of(2015, 12, 6, 0, 0), LocalDateTime.of(2016, 1, 6, 0, 0)) == 31);
    }
}
