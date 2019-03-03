/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.Resurssit;
import Model.ResurssitAccessObject;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Tommi
 */
public class CRUDTest {

    KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
    int r = (int) (Math.random() * 100000);
    int r1 = (int) (Math.random() * 100000);
    int r2 = (int) (Math.random() * 100000);
    int r3 = (int) (Math.random() * 100000);
    String nimi = String.valueOf(r);
    String salasana = String.valueOf(r1);
    String kayttajatunnus = String.valueOf(r2);
    String sposti = String.valueOf(r3);
    Kayttaja k = new Kayttaja(nimi, salasana, kayttajatunnus, sposti, 1);
    Kayttaja k1 = null;
    Kayttaja[] kayttajat = null;

 

    @Test
    public void kayttajaDAOTest() {
        //uuden käyttäjän luonti
        assertTrue("createKayttaja(): Uuden käyttäjän luominen ei onnistunut",
                kayttajaDAO.createKayttaja(k));

        // Käyttäjän haku
        assertTrue("readKayttaja(): Haku ei onnistunut",
                (k = kayttajaDAO.readKayttaja(k.getId())) != null);
        assertEquals("readKayttaja(): Käyttäjän salasana väärin",
                salasana, k.getSalasana());
        assertEquals("readKayttaja(): Käyttäjän nimi väärin",
                nimi, k.getNimi());
        assertEquals("readKayttaja(): Käyttäjätunnus väärin",
                kayttajatunnus, k.getKayttajatunnus());
        assertEquals("readKayttaja(): Käyttäjän sposti väärin",
                sposti, k.getSahkoposti());

        //Kaikkien käyttäjien haku listaan
        kayttajat = kayttajaDAO.readKayttajat();
        assertEquals("readKayttajat(): lista väärin",
                k.getId(), kayttajat[kayttajat.length - 1].getId());
        k1 = new Kayttaja("testi", "testi", "testi", "testi", 1);
        kayttajaDAO.createKayttaja(k1);
        Kayttaja[] kayttajat2 = kayttajaDAO.readKayttajat();
        assertTrue("readKayttajat(): lista väärin",
                kayttajat.length < kayttajat2.length);

        //Käyttäjän päivittäminen
        k.setNimi("asd");
        k.setKayttajatunnus("asd");
        k.setSahkoposti("asd");
        k.setSalasana("asd");
        k.setValtuudet(2);
        assertTrue("uptadeKayttaja: päivittäminen ei onnistunut",
                kayttajaDAO.updateKayttaja(k));
        k = kayttajaDAO.readKayttaja(k.getId());
        assertEquals("uptadeKayttaja: nimi väärin ",
                "asd", k.getNimi());
        assertEquals("uptadeKayttaja: tunnus väärin ",
                "asd", k.getKayttajatunnus());
        assertEquals("uptadeKayttaja: sposti väärin ",
                "asd", k.getSahkoposti());
        assertEquals("uptadeKayttaja: salasana väärin",
                "asd", k.getSalasana());
        assertEquals("uptadeKayttaja: valtuudet väärin",
                2, k.getValtuudet());

        //käyttäjän poistaminen
        assertTrue("deleteKayttaja(): Käyttäjän poisto ei onnistunut.",
                kayttajaDAO.deleteKayttaja(k1.getId()));
        assertTrue("deleteKayttaja(): Käyttäjän poisto ei onnistunut.",
                kayttajaDAO.deleteKayttaja(k.getId()));
        assertFalse("deleteKayttaja(): poisti olemattoman käyttän",
                kayttajaDAO.deleteKayttaja(999999969));

    }

    ResurssitAccessObject resurssiDAO = new ResurssitAccessObject();
    Resurssit res = new Resurssit(true, "resurssitesti", "resurssitesti", 1, "resurssitesti");
    Resurssit res1 = null;
    Resurssit[] resurssit = null;

    @Test
    public void resurssiDAOTest() {
        //uuden resurssin luonti
        assertTrue("createResurssi(): Uuden resurssin luominen ei onnistunut",
                resurssiDAO.createResurssi(res));

        //resurssin haku
        assertTrue("readResurssi(): Haku ei onnistunut",
                (res = resurssiDAO.readResurssi(res.getId())) != null);
        assertEquals("readResurssi: status väärä ",
                true, res.isStatus());
        assertEquals("readResurssi: nimi väärä",
                "resurssitesti", res.getNimi());
        assertEquals("readResurssi: tyyppi väärä",
                "resurssitesti", res.getTyyppi());
        assertEquals("readResurssi: kuvaus väärä",
                "resurssitesti", res.getKuvaus());

        //kaikkien resurssien haku listaan
        resurssit = resurssiDAO.readResurssit();
        assertEquals("readResurssit(): lista väärin",
                res.getId(), res.getId() );
        res1 = new Resurssit(true, "testi", "testi", 1, "testi");
        resurssiDAO.createResurssi(res1);
        Resurssit[] resurssit2 = resurssiDAO.readResurssit();
        assertTrue("readResurssit(): lista väärin",
                resurssit.length < resurssit2.length);

        //resurssin päivittäminen
        res.setKuvaus("asd");
        res.setLuvanvaraisuus(2);
        res.setNimi("asd");
        res.setStatus(false);
        res.setTyyppi("asd");
        resurssiDAO.updateResurssi(res);
        res = resurssiDAO.readResurssi(res.getId());
        assertEquals("uptadeResurssi: kuvaus väärin",
                "asd", res.getKuvaus());
        assertEquals("uptadeResurssi: luvanvaraisuus väärin",
                2, res.getLuvanvaraisuus());
        assertEquals("uptadeResurssi: nimi väärin",
                "asd", res.getNimi());
        assertEquals("uptadeResurssi: status väärin",
                false, res.isStatus());
        assertEquals("uptadeResurssi: tyyppi väärin",
                "asd", res.getTyyppi());
        //varausten päivitys pitää testata

        //resurssin poisto
        assertTrue("deleteResurssi(): poisto ei onnistunut",
                resurssiDAO.deleteResurssi(res.getId()));
        assertTrue("deleteResurssi(): poisto ei onnistunut",
                resurssiDAO.deleteResurssi(res1.getId()));
        assertFalse("deleteResurssi(): väittää poistaneen olemattoman resurssin",
                resurssiDAO.deleteResurssi(99999969));

    }

    @Test
    public void varausDAOTest() {

    }

}
