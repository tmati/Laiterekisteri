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
 *
 * @author Tommi
 */
public class KayttajaAccessObject implements KayttajaDAOIF {

    SessionFactory sf = null;

    /**
     * Konstuktori Hakee sessionfacotoryn
     */
    public KayttajaAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Vie uuden käyttäjän tietokantaan
     *
     * @param kayttaja kayttaja joka viedään tietokantaan
     * @return palauttaa true jos käyttäjän vienti tietokantaan onnistui
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
            e.printStackTrace();
            return false;
        } finally {
            s.close();
        }
        return true;

    }

    /**
     * Hakee käyttäjän tietokannasta id:n avulla
     *
     * @param id käyttäjän id
     * @return palauttaa kayttaja -olion
     */
    @Override
    public Kayttaja readKayttaja(int id) {
        Session s = sf.openSession();
        s = sf.openSession();
        s.beginTransaction();
        Kayttaja haettu = new Kayttaja();
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
     * Lukee kaikki käyttäjät tietokannasta.
     *
     * @return palauttaa taulukon kaikista käyttäjistä
     */
    @Override
    public Kayttaja[] readKayttajat() {
        Session s = sf.openSession();
        Kayttaja[] kayttajat = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Kayttaja> result = s.createQuery("from Kayttaja").list();
            kayttajat = result.toArray(new Kayttaja[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            s.close();
        }
        return kayttajat;
    }

    /**
     * Päivittää käyttäjän tietokantaan
     *
     * @param kayttaja paivitettava käyttäjä
     * @return palauttaa true jos käyttäjän päivitys onnistui
     */
    @Override
    public boolean updateKayttaja(Kayttaja kayttaja) {
        Session s = sf.openSession();
        try {
            s = sf.openSession();
            s.beginTransaction();

            Kayttaja paivitettava = (Kayttaja) s.get(Kayttaja.class, kayttaja.getId());
            if (paivitettava != null) {
                paivitettava.setSalasana(kayttaja.getSalasana());
                paivitettava.setNimi(kayttaja.getNimi());
                paivitettava.setKayttajatunnus(kayttaja.getKayttajatunnus());
                paivitettava.setSahkoposti(kayttaja.getSahkoposti());
                paivitettava.setSalasana(kayttaja.getSalasana());
                paivitettava.setValtuudet(kayttaja.getValtuudet());
            } else {
                System.out.println("Ei löytynyt päivitettävää!");
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
     * Poistaa käytäjän tietokannasta id:n perusteella.
     *
     * @param id poistettavan käyttäjän id
     * @return palauttaa true jos käyttäjän poisto onnistui
     */
    @Override
    public boolean deleteKayttaja(int id) {

        Session s = sf.openSession();
        try {
            s = sf.openSession();
            s.beginTransaction();
            Kayttaja valittu = readKayttaja(id);
            Kayttaja poistettava = (Kayttaja) s.get(Kayttaja.class, valittu.getId());
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
