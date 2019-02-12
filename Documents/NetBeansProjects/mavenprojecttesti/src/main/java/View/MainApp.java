package com.mycompany.mavenprojecttesti;


import org.hibernate.*;




public class MainApp{


    public static void main(String[] args) {
       SessionFactory istuntotehdas = HibernateUtil.getSessionFactory();
       Session istunto = istuntotehdas.openSession();
		Transaction transaktio = null;
		Kayttaja k = new Kayttaja("asd","asd",1);
		try{
			transaktio = istunto.beginTransaction();
			istunto.saveOrUpdate(k);
			transaktio.commit();
		}
		catch(Exception e){
			if (transaktio!=null) transaktio.rollback();
			                 System.out.println("ASD");
		}
		finally{
			istunto.close();
                        System.out.println("toimi");
		}

}
}
