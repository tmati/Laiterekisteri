package View;

import Model.Kayttaja;
import Model.KayttajaAccessObject;
import Model.Resurssit;
import Model.ResurssitAccessObject;
import Model.Varaukset;
import Model.VarauksetAccessObject;
import java.util.Date;

public class MainApp {

    public static void main(String[] args) {
        Date alkamis = new Date();
        Date loppumis = new Date();
        alkamis.setYear(2019);
        loppumis.setYear(2019);
        alkamis.setMonth(2);
        loppumis.setMonth(02);
        Kayttaja k = new Kayttaja("sasd", "sasd", 1);
        Resurssit r = new Resurssit(true, "iLuuri", "Puhelin", 0, "Uusi iLuuri");
        Varaukset v = new Varaukset(k,r,alkamis,loppumis,"tarviin iLuurin", false, "iLuuri varaus");
        
        
       
        KayttajaAccessObject KayttajaDAO = new KayttajaAccessObject();
        ResurssitAccessObject ResurssiDAO = new ResurssitAccessObject();
        VarauksetAccessObject VarausDAO = new VarauksetAccessObject();
        
        KayttajaDAO.createKayttaja(k);
        ResurssiDAO.createResurssi(r);
        VarausDAO.createVaraus(v);
        
        

    }
}
