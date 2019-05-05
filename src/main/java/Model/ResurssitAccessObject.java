/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Resurssi DAO
 *
 * @author Tommi
 */
public class ResurssitAccessObject implements ResurssitDAOIF {

    SessionFactory sf = null;

    /**
     * Konstuktori hakee sessionfactoryn
     */
    public ResurssitAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vie resurssin tietokantaan
     *
     * @param resurssi resurssi joka viedään tietokantaan
     * @return true jos tietokantaan vienti onnistui
     */
    @Override
    public boolean createResurssi(Resurssit resurssi) {
        Session s = sf.openSession();
        Transaction tran = null;

        try {
            tran = s.beginTransaction();
            s.saveOrUpdate(resurssi);
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
     * Lukee resurssin tietokannasta id:n perusteella.
     *
     * @param id resurssin id
     * @return haettu resurssi -olio
     */
    @Override
    public Resurssit readResurssi(int id) {
        Session s = sf.openSession();
        s = sf.openSession();
        s.beginTransaction();
        Resurssit haettu = new Resurssit();
        try {
            s.load(haettu, id);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return haettu;
    }

    /**
     * Lukee kaikki resurssit tietokannasta
     *
     * @return taulukko kaikista resursseista
     */
    @Override
    public Resurssit[] readResurssit() {
        Session s = sf.openSession();
        Resurssit[] resurssit = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Resurssit> result = s.createQuery("from Resurssit").list();
            resurssit = result.toArray(new Resurssit[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {

            e.printStackTrace();

        } finally {
            s.close();
        }
        return resurssit;
    }

    /**
     * Päivittää resurssin tietokantaan
     *
     * @param resurssi paivitettava resurssi
     * @return true jos resurssin päivitys onnistui
     */
    @Override
    public boolean updateResurssi(Resurssit resurssi) {
        Session s = sf.openSession();
        try {
            s = sf.openSession();
            s.beginTransaction();
            Resurssit paivitettava = (Resurssit) s.get(Resurssit.class, resurssi.getId());
            if (paivitettava != null) {
                paivitettava.setLuvanvaraisuus(resurssi.getLuvanvaraisuus());
                paivitettava.setKuvaus(resurssi.getKuvaus());
                paivitettava.setNimi(resurssi.getNimi());
                paivitettava.setTyyppi(resurssi.getTyyppi());
                paivitettava.setStatus(resurssi.isStatus());
                paivitettava.setVarauksets(resurssi.getVarauksets());
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
     * Poistaa resurssin id:n avulla tietokannasta
     *
     * @param id poistettavan resurssin id
     * @return true jos resurssin poisto onnistui
     */
    @Override
    public boolean deleteResurssi(int id) {
        Session s = sf.openSession();
        try {
            s = sf.openSession();
            s.beginTransaction();
            Resurssit valittu = readResurssi(id);
            Resurssit poistettava = (Resurssit) s.get(Resurssit.class, valittu.getId());
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
