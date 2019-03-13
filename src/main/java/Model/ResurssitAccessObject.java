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
 * Resursien DAO
 * @author Tommi
 */
public class ResurssitAccessObject implements ResurssitDAO_IF {

    SessionFactory sf = null;

    /**
     * Konstuktori
     */
    public ResurssitAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Luo Resursin tietokantaan.
     * @param resurssi
     * @return
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
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            s.close();
        }
        return true;

    }

    /**
     * Lukee resursin tietokannasta id:n perusteella.
     * @param id
     * @return
     */
    @Override
    public Resurssit readResurssi(int id) {
        Session s = sf.openSession();
        Transaction transaktio = null;

        s = sf.openSession();
        s.beginTransaction();
        Resurssit haettu = new Resurssit();
        try {

            s.load(haettu, id);
            System.out.println(haettu.getId());
            s.getTransaction().commit();
        } catch (Exception e) {
            if (transaktio != null) {
                transaktio.rollback();
            }
            throw e;
        } finally {
            s.close();
        }
        return haettu;
    }

    /**
     * lukee resursit tietokannasta.
     * @return
     */
    @Override
    public Resurssit[] readResurssit() {
        Session s = sf.openSession();
        Transaction tran = null;
        Resurssit[] resurssit = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Resurssit> result = s.createQuery("from Resurssit").list();
            resurssit = result.toArray(new Resurssit[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();

        } finally {
            s.close();
        }
        return resurssit;
    }

    /**
     * Päivittää resurssin.
     * @param resurssi
     * @return
     */
    @Override
    public boolean updateResurssi(Resurssit resurssi) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Resurssit päivitettävä = (Resurssit) s.get(Resurssit.class, resurssi.getId());
            if (päivitettävä != null) {
                päivitettävä.setLuvanvaraisuus(resurssi.getLuvanvaraisuus());
                päivitettävä.setKuvaus(resurssi.getKuvaus());
                päivitettävä.setNimi(resurssi.getNimi());
                päivitettävä.setTyyppi(resurssi.getTyyppi());
                päivitettävä.setStatus(resurssi.isStatus());
                päivitettävä.setVarauksets(resurssi.getVarauksets());
                s.saveOrUpdate(päivitettävä);
            } else {
                System.out.println("Ei löytynyt päivitettävää!");
            }
            s.getTransaction().commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            s.close();
        }
        return true;
    }

    /**
     * Poistaa resursin id:n avulla tietokannasta.
     * @param id
     * @return
     */
    @Override
    public boolean deleteResurssi(int id) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Resurssit valittu = readResurssi(id);
            Resurssit poistettava = (Resurssit) s.get(Resurssit.class, valittu.getId());
            if (poistettava != null) {
                s.delete(poistettava);
            } else {
                System.out.println("Ei löytynyt poistettavaa resurssia");
            }
            s.getTransaction().commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();
            return false;

        } finally {
            s.close();
        }
        return true;

    }

}
