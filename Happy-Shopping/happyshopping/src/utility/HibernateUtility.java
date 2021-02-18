package utility;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtility {
	private HibernateUtility() {
		// TODO Auto-generated constructor stub
	}
	private final static ThreadLocal<Session> tlocal=new ThreadLocal<Session>();
	private static SessionFactory sessionFactory; 
	private static Session session;
	private static Transaction tx;
	synchronized public static Session getSession() {
		try {
			session=tlocal.get();
			if(session==null) {
				Configuration cfg=new Configuration().configure();
				sessionFactory=cfg.buildSessionFactory();
				System.out.println("New Hiber session created");
				session=sessionFactory.openSession();
				tx=session.beginTransaction();
				tlocal.set(session);
			}else {
				System.out.println("Exisiting hiber session is used");
			}
			return session;
		}catch(Exception e) {e.printStackTrace();return null;}
	}
	synchronized public static void closeSession(Exception e) {
		System.out.println("Hiber session destroyed");
		session=tlocal.get();
		if(e==null) {
			if(session!=null) {
				tx.commit();
				session.close();
				tlocal.remove();
				
			}
		}
		else {
			if(session!=null) {
				tx.rollback();
				session.close();
				tlocal.remove();
			}
		}
	
}
}