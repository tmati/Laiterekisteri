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
 * Käyttäjän DAO
 * @author Tommi
 */
public class KayttajaAccessObject implements KayttajaDAO_IF {

    SessionFactory sf = null;

    /**
     * konstuktori
     */
    public KayttajaAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Luo uuden käyttäjän.
     * @param kayttaja
     * @return
     */
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

    /**
     * Lukee käytäjän käytäjän id:n perusteella.
     * @param id
     * @return
     */
    @Override
    public Kayttaja readKayttaja(int id) {
        Session s = sf.openSession();
        Transaction transaktio = null;

        s = sf.openSession();
        s.beginTransaction();
        Kayttaja haettu = new Kayttaja();
        try {
            s.load(haettu, id);
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
     * Lukee kaikki käyttäjät tietokannasta.
     * @return
     */
    @Override
    public Kayttaja[] readKayttajat() {
        Session s = sf.openSession();
        Transaction tran = null;
        Kayttaja[] kayttajat = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Kayttaja> result = s.createQuery("from Kayttaja").list();
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

    /**
     * Päivittää käytäjää.
     * @param kayttaja
     * @return
     */
    @Override
    public boolean updateKayttaja(Kayttaja kayttaja) {
        Session s = sf.openSession();
        Transaction tran = null;
        try {
            s = sf.openSession();
            s.beginTransaction();

            Kayttaja päivitettävä = (Kayttaja)s.get(Kayttaja.class, kayttaja.getId());
            if (päivitettävä != null) {
                päivitettävä.setSalasana(kayttaja.getSalasana());
                päivitettävä.setNimi(kayttaja.getNimi());
                päivitettävä.setKayttajatunnus(kayttaja.getKayttajatunnus());
                päivitettävä.setSahkoposti(kayttaja.getSahkoposti());
                päivitettävä.setSalasana(kayttaja.getSalasana());
                päivitettävä.setValtuudet(kayttaja.getValtuudet());
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
     * Poistaa käytäjän tietokannasta id:n perusteella.
     * @param id
     * @return
     */
    @Override
    public boolean deleteKayttaja(int id) {

        Session s = sf.openSession();
        Transaction tran = null;

        try {
            s = sf.openSession();
            s.beginTransaction();
            Kayttaja valittu = readKayttaja(id);
            Kayttaja poistettava = (Kayttaja) s.get(Kayttaja.class, valittu.getId());
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
