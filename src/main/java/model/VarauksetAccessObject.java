/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

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
public class VarauksetAccessObject implements VarauksetDAOIF {

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
        s = sf.openSession();
        s.beginTransaction();
        Varaukset haettu = new Varaukset();
        try {
            s.load(haettu, id);
            Hibernate.initialize(haettu.getKayttaja());
            Hibernate.initialize(haettu.getResurssit());
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
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
        try {
            s = sf.openSession();
            s.beginTransaction();
            Varaukset paivitettava = (Varaukset) s.get(Varaukset.class, varaus.getId());
            if (paivitettava != null) {
                paivitettava.setHyvaksytty(varaus.getHyvaksytty());
                paivitettava.setKayttaja(varaus.getKayttaja());
                paivitettava.setResurssit(varaus.getResurssit());
                paivitettava.setAlkupvm(varaus.getAlkupvm());
                paivitettava.setPaattymispvm(varaus.getPaattymispvm());
                paivitettava.setKuvaus(varaus.getKuvaus());
                paivitettava.setPalautettu(varaus.isPalautettu());
                paivitettava.setNimi(varaus.getNimi());
                s.saveOrUpdate(paivitettava);
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
        try {
            s = sf.openSession();
            s.beginTransaction();
            Varaukset valittu = readVaraus(id);
            Varaukset poistettava = (Varaukset) s.get(Varaukset.class, valittu.getId());
            if (poistettava != null) {
                s.delete(poistettava);
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
