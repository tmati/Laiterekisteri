/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.JUnitTestiluokka;
import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.ResurssitAccessObject;
import Model.VarauksetAccessObject;
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
public class CRUDTest {

    KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
    VarauksetAccessObject varausDAO = new VarauksetAccessObject();
    ResurssitAccessObject resurssiDAO = new ResurssitAccessObject();

    Kayttaja k = new Kayttaja("jUnitTesti", "jUnitTesti", "jUnitTesti", "jUnitTesti", 1);
    Kayttaja[] kayttajat = null;

    @Test
    public void CreateKayttajaTest() {
        boolean testi = kayttajaDAO.createKayttaja(k);
        assertEquals(true, testi);
    }

    @Test
    public void ReadKayttajaTest() {
        Kayttaja haettu = kayttajaDAO.readKayttaja("jUnitTesti");
        assertEquals(k.getNimi(), haettu.getNimi());
        assertEquals(k.getId(), haettu.getId());
    }

    @Test
    public void ReadKayttajatTest() {
        kayttajat = kayttajaDAO.readKayttajat();
        assertEquals(kayttajat[kayttajat.length].getId(), k.getId());
    }

    @Test
    public void UptadeKayttajaTest() {
        Kayttaja k1 = kayttajaDAO.readKayttaja("jUnitTesti");
        k1.setValtuudet(2);
        boolean tulos = kayttajaDAO.updateKayttaja(k1);
        int id = kayttajaDAO.readKayttaja("jUnitTesti").getId();
        assertEquals(true, tulos);
        assertEquals(2, id);
    }

    @Test
    public void DeleteKayttajaTest() {
        boolean tulos = kayttajaDAO.deleteKayttaja("jUnitTesti");
        assertEquals(true, tulos);
    }

    @Test
    public void nakoisTesti() {

        JUnitTestiluokka t = new JUnitTestiluokka();
        Kayttaja k = t.testi(1);
        Kayttaja k2 = t.testi(2);

        assertEquals("Jokke", k.getNimi());
        assertEquals("Jakke", k2.getNimi());

    }

}
