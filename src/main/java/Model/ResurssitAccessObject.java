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
 *
 * @author Tommi
 */
public class ResurssitAccessObject implements ResurssitDAO_IF {

    SessionFactory sf = null;

    public ResurssitAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    @Override
    public Resurssit[] readResurssit() {
        Session s = sf.openSession();
        Transaction tran = null;
        Resurssit[] resurssit = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Resurssit> result = s.createQuery("from resurssit").list();
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

    @Override
    public boolean updateResurssi(Resurssit resurssi) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Resurssit päivitettävä = (Resurssit) s.get(Resurssit.class, resurssi.getId());
            if (päivitettävä != null) {
                päivitettävä.setKuvaus(resurssi.getKuvaus());
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