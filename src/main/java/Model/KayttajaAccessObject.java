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
public class KayttajaAccessObject implements KayttajaDAO_IF {

    SessionFactory sf = null;

    public KayttajaAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createKayttaja(Kayttaja kayttaja) {
        Session s = sf.openSession();
        Transaction tran = null;

        try {
            tran = s.beginTransaction();
            s.saveOrUpdate(kayttaja);
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
    public Kayttaja readKayttaja(String nimi) {
        Session s = sf.openSession();
        Transaction transaktio = null;

        s = sf.openSession();
        s.beginTransaction();
        Kayttaja haettu = new Kayttaja();
        try {

            s.load(haettu, nimi);
            System.out.println(haettu.getNimi());
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
    public Kayttaja[] readKayttajat() {
        Session s = sf.openSession();
        Transaction tran = null;
        Kayttaja[] kayttajat = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Kayttaja> result = s.createQuery("from kayttaja").list();
            kayttajat = result.toArray(new Kayttaja[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();

        } finally {
            s.close();
        }
        return kayttajat;
    }

    @Override
    public boolean updateKayttaja(Kayttaja kayttaja) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();

            Kayttaja päivitettävä = (Kayttaja) s.get(Kayttaja.class, kayttaja.getNimi());
            if (päivitettävä != null) {
                päivitettävä.setSalasana(kayttaja.getSalasana());
                päivitettävä.setNimi(kayttaja.getNimi());
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

    public boolean deleteKayttaja(String nimi) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            Kayttaja valittu = readKayttaja(nimi);
            Kayttaja poistettava = (Kayttaja) s.get(Kayttaja.class, valittu.getNimi());
            if (poistettava != null) {
                s.delete(poistettava);
            } else {
                System.out.println("Ei löytynyt poistettavaa käyttäjää!");
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
