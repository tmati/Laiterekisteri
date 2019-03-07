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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.AfterClass;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Tommi
 */
public class CRUDTest {

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
        KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
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
        assertTrue("readKayttajat(): lista haku ei onnistunut",
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
                kayttajaDAO.deleteKayttaja(k.getId()));

        assertTrue("deleteKayttaja(): Käyttäjän poisto ei onnistunut.",
                kayttajaDAO.deleteKayttaja(k1.getId()));

        assertFalse("deleteKayttaja(): poisti olemattoman käyttän",
                kayttajaDAO.deleteKayttaja(999999969));

    }

    Resurssit res = new Resurssit(true, "resurssitesti", "resurssitesti", 1, "resurssitesti");
    Resurssit res1 = null;
    Resurssit[] resurssit = null;

    @Test
    public void resurssiDAOTest() {
        ResurssitAccessObject resurssiDAO = new ResurssitAccessObject();
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

        //resurssin poisto
        assertTrue("deleteResurssi(): poisto ei onnistunut",
                resurssiDAO.deleteResurssi(res.getId()));
        assertTrue("deleteResurssi(): poisto ei onnistunut",
                resurssiDAO.deleteResurssi(res1.getId()));

        assertFalse("deleteResurssi(): väittää poistaneen olemattoman resurssin",
                resurssiDAO.deleteResurssi(99999969));

    }

    LocalDate aloitusPV = LocalDate.parse("2019-04-01");
    LocalTime aloitusAika = LocalTime.parse("10:00");
    LocalDate lopetusPV = LocalDate.parse("2019-04-02");
    LocalTime lopetusAika = LocalTime.parse("16:00");

    LocalDateTime aloitus = LocalDateTime.of(aloitusPV, aloitusAika);
    LocalDateTime lopetus = LocalDateTime.of(lopetusPV, lopetusAika);

    @Test
    public void varausDAOTest() {
        KayttajaAccessObject kayttajaDAO = new KayttajaAccessObject();
        ResurssitAccessObject resurssiDAO = new ResurssitAccessObject();
        VarauksetAccessObject varausDAO = new VarauksetAccessObject();
        Kayttaja kayttaja = new Kayttaja("varaustesti", "varaustesti", "varaustesti", "varaustesti", 1);
        Resurssit resurssi = new Resurssit(true, "varaustesti", "varaustesti", 1, "varaustesti");
        kayttajaDAO.createKayttaja(kayttaja);
        resurssiDAO.createResurssi(resurssi);
        Varaukset varaus = new Varaukset(kayttaja, resurssi, aloitus, lopetus, "varaustesti", false, "varaustesti");
        Varaukset var = null;

        //uuden varauksen luominen
        assertTrue("createVaraus(): Uuden resurssin luominen ei onnistunut",
                varausDAO.createVaraus(varaus));

        //Varauksen haku
        assertTrue("readVaraus: Haku ei onnistunut",
                (var = varausDAO.readVaraus(varaus.getId())) != null);
        assertEquals("readVaraus: id väärä",
                var.getId(), varaus.getId());
        assertEquals("readVaraus: alkuAika väärä",
                var.getAlkuAika(), varaus.getAlkuAika());
        assertEquals("readVaraus: loppuAika väärä",
                var.getLoppuAika(), varaus.getLoppuAika());
        assertEquals("readVaraus: kuvaus väärä",
                var.getKuvaus(), varaus.getKuvaus());
        assertEquals("readVaraus: palautuksen status väärä",
                var.isPalautettu(), varaus.isPalautettu());
        assertEquals("readVaraus: nimi väärä",
                var.getNimi(), varaus.getNimi());
        assertEquals("readVaraus: käyttäjä väärä",
                var.getKayttaja().getId(), varaus.getKayttaja().getId());
        assertEquals("readVaraus: resurssi väärä",
                var.getResurssit().getId(), var.getResurssit().getId());

        //Kaikkien varauksien haku listaan
        Varaukset[] varaukset = varausDAO.readVaraukset();
        Varaukset var1 = new Varaukset(kayttaja, resurssi, aloitus, lopetus, "varaustesti1", false, "varaustesti1");
        varausDAO.createVaraus(var1);
        Varaukset[] varaukset1 = varausDAO.readVaraukset();
        assertTrue("readVaraukset(): lista ei päivittynyt oikein",
                varaukset.length < varaukset1.length);

        //Varauksen päivittäminen
        aloitusAika = LocalTime.parse("11:00");
        lopetusAika = LocalTime.parse("14:00");
        LocalDateTime aloitus = LocalDateTime.of(aloitusPV, aloitusAika);
        LocalDateTime lopetus = LocalDateTime.of(lopetusPV, lopetusAika);
        var.setAlkuAika(aloitus);
        var.setLoppuAika(lopetus);
        var.setHyvaksytty(false);
        var.setKuvaus("asd");
        var.setNimi("varaustestiä");
        var.setPalautettu(true);
        assertTrue("updateVaraus: päivitys ei onnistunut",
                varausDAO.updateVaraus(var));
        varaus = varausDAO.readVaraus(var.getId());
        assertEquals("updateVaraus: aloitusAika väärin",
                varaus.getAlkuAika(), var.getAlkuAika());
        assertEquals("updateVaraus: lopetusAika väärin",
                varaus.getLoppuAika(), var.getLoppuAika());
        assertEquals("updateVaraus: id väärin",
                varaus.getId(), var.getId());
        assertEquals("updateVaraus: kuvaus väärin",
                varaus.getKuvaus(), var.getKuvaus());
        assertEquals("updateVaraus: palautettu status väärin",
                varaus.isPalautettu(), var.isPalautettu());
        assertEquals("updateVaraus: nimi väärin",
                varaus.getNimi(), var.getNimi());
        assertEquals("updateVaraus: hyvaksytty status väärin",
                varaus.getHyvaksytty(), var.getHyvaksytty());

        System.out.println(varaus.getKayttaja().getNimi());

        //varauksen poisto
        assertTrue("deleteVaraus: varauksen poisto ei onnistunut",
                varausDAO.deleteVaraus(var.getId()));
        assertTrue("deleteVaraus: varauksen poisto ei onnistunut",
                varausDAO.deleteVaraus(var1.getId()));
        kayttajaDAO.deleteKayttaja(kayttaja.getId());
        resurssiDAO.deleteResurssi(resurssi.getId());

    }

}
