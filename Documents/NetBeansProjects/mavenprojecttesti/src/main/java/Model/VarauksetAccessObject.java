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
public class VarauksetAccessObject implements VarauksetDAO_IF {

    SessionFactory sf = null;

    public VarauksetAccessObject() {
        try {
            sf = HibernateUtil.getSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean createVaraus(Varaukset varaus) {
        Session s = sf.openSession();
        Transaction tran = null;

        try {
            tran = s.beginTransaction();
            s.saveOrUpdate(varaus);
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
    public Varaukset readVaraus(int id) {
        Session s = sf.openSession();
        Transaction transaktio = null;

        s = sf.openSession();
        s.beginTransaction();
        Varaukset haettu = new Varaukset();
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
    public Varaukset[] readVaraukset() {
        Session s = sf.openSession();
        Transaction tran = null;
        Varaukset[] varaukset = null;
        try {
            s = sf.openSession();
            s.beginTransaction();
            @SuppressWarnings("unchecked")
            List<Varaukset> result = s.createQuery("from varaukset").list();
            varaukset = result.toArray(new Varaukset[result.size()]);
            s.getTransaction().commit();
        } catch (Exception e) {
            if (tran != null) {
                tran.rollback();
            }
            e.printStackTrace();

        } finally {
            s.close();
        }
        return varaukset;
    }

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
