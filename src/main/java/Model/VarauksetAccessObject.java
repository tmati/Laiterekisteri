/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Varaus DAO
 *
 * @author Tommi
 */
public class VarauksetAccessObject implements VarauksetDAO_IF {

    SessionFactory sf = null;

    /**
     * Konstuktori hakee sessionfactoryn
     */
    public VarauksetAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vie varauksen tietokantaan
     *
     * @param varaus varaus -olio joka viedään tietokantaan
     * @return true jos varauksen vienti onnistui
     */
    @Override
    public boolean createVaraus(Varaukset varaus) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            tran = s.beginTransaction();
            s.saveOrUpdate(varaus);
            tran.commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            s.close();
        }
        return true;

    }

    /**
     * Hakee varauksen tietokannasta id:n avulla
     *
     * @param id varauksen id
     * @return varaus -olio
     */
    @Override
    public Varaukset readVaraus(int id) {
        Session s = sf.openSession();
        Transaction transaktio = null;

        s = sf.openSession();
        s.beginTransaction();
        Varaukset haettu = new Varaukset();
        try {
            s.load(haettu, id);
            Hibernate.initialize(haettu.getKayttaja());
            Hibernate.initialize(haettu.getResurssit());
            s.getTransaction().commit();
        } catch (Exception e) {
            throw e;
        } finally {
            s.close();
        }
        return haettu;
    }

    /**
     * Lukee kaikki varaukset tietokannasta.
     *
     * @return taulukko kaikista varauksista
     */
    @Override
    public Varaukset[] readVaraukset() {
        Session s = sf.openSession();
        Transaction tran = null;
        Varaukset[] varaukset = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Varaukset> result = s.createQuery("from Varaukset").list();
            varaukset = result.toArray(new Varaukset[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            s.close();
        }
        return varaukset;
    }

    /**
     * Päivittää varauksen tietokantaan
     *
     * @param varaus varaus, jota päivitetään
     * @return true jos päivitys onnistui
     */
    @Override
    public boolean updateVaraus(Varaukset varaus) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Varaukset päivitettävä = (Varaukset) s.get(Varaukset.class, varaus.getId());
            if (päivitettävä != null) {
                päivitettävä.setHyvaksytty(varaus.getHyvaksytty());
                päivitettävä.setKayttaja(varaus.getKayttaja());
                päivitettävä.setResurssit(varaus.getResurssit());
                päivitettävä.setAlkupvm(varaus.getAlkupvm());
                päivitettävä.setPaattymispvm(varaus.getPaattymispvm());
                päivitettävä.setKuvaus(varaus.getKuvaus());
                päivitettävä.setPalautettu(varaus.isPalautettu());
                päivitettävä.setNimi(varaus.getNimi());
                s.saveOrUpdate(päivitettävä);
            } else {
                System.out.println("Ei löytynyt päivitettävää");
            }
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            s.close();
        }
        return true;
    }

    /**
     * Poistaa varauksen tietokannasta id:n perusteella
     *
     * @param id varauksen id
     * @return true jos poisto onnistui
     */
    public boolean deleteVaraus(int id) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Varaukset valittu = readVaraus(id);
            Varaukset poistettava = (Varaukset) s.get(Varaukset.class, valittu.getId());
            if (poistettava != null) {
                s.delete(poistettava);
            } else {
                System.out.println("Ei löytynyt poistettavaa varausta");
            }
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return false;

        } finally {
            s.close();
        }
        return true;

    }

}
