package kehd.bigpicture;

import kehd.bigpicture.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main {

	private static final Logger log = Logger.getLogger(Main.class);

	static SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeForm = new SimpleDateFormat("dd.MM.yyyy mm:hh");

	private static final SessionFactory sessionFactory;

	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			sessionFactory = new AnnotationConfiguration().configure()
					.buildSessionFactory();
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	private Main() {
		super();
	}

	public static void main(String[] args) {
		log.setLevel(Level.ALL);
		try {
			log.info("Starting \"Mapping Perstistent Classes and Associations\" (task1)");
			task01();
			log.info("Starting \"Working with JPA-QL and the Hibernate Criteria API\" (task2)");
			task02a();
			task02b();
			task02c();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillDB(EntityManager em) throws ParseException {
		// dateForm.parse("01.01.1930")
	}

	/**
	 * @throws ParseException
	 * @throws InterruptedException
	 */
	public static void task01() throws ParseException, InterruptedException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");;

		
		long[] ids = { 1, 2, 3, 4, 5 };
		String[] name = { "Aiden", "Jackson", "Ethan", "Liam", "Jerome" };
		String[] pass = { "test1", "test2", "test3", "test4", "test5" };

		
		long[] ids2 = { 1, 2, 3, 4, 5 };
		String[] date = { "13-03-2014 22:00:11", "14-04-2014 12:03:00", "22-04-2014 17:13:00",
				"02-02-2014 10:00:40", "01-01-2001 01:03:00" };
		String[] com = { "meeting", "absagen", "irgendwas", "ka", "HH" };

		
		long[] ids3 = { 1, 3, 5, 22, 12 };
		String[] pass2 = { "test1", "test3", "test5", "test6", "test9" };
		String[] date2 = { "13-07-2014", "14-09-2014", "22-08-2014",
				"02-02-2014", "01-01-2011" };

		
		long[] ids4 = { 11, 23, 5, 22, 33 };
		String[] mes = { "igrndwas", "lknlkd", "testo", "hiv", "kidz" };

		
		long[] eids = { 1, 2, 3, 4, 5 };
		String[] titles = { "Seduction", "Raping Party", "HH", "Huan", "fitinn" };
		String[] title = { "Brutal", "vorsicht", "HH", "HH", "HUH" };

		
		long[] aids = { 1, 2, 3, 4, 5 };
		String[] date3 = { "13-02-2014 ", "14-10-2014", "22-09-2014",
				"02-12-2014", "01-11-2011" };
		Date[] ar = new Date[date2.length];
//		for (int j = 0; j < date2.length; j++) {
//			ar[j] = sdf.parse(date2[j]);
//		}
		
		char[] sd={'s','h'};
		for (int i = 0; i < 5; i++) {
			User n = new User();
			Comment c = new Comment();
			Organisator org = new Organisator();
			Notification nf = new Notification();
			Event e = new Event();
			Appointment ap = new Appointment();
			Session session = getSessionFactory().getCurrentSession();
			Transaction tx = session.beginTransaction();
			
			n.setName(name[i]);
			n.setPassword(pass[i].toCharArray());
			session.save(n);
			
			
			c.setTimestamp(sdf.parse(date[i]));
			c.setComment(com[i]);
			c.setUserid(n);
			session.save(c);
//			
			
//			org.setId(ids3[i]);
//			org.setPassword(pass[i].toCharArray());
//	    	org.setPassword(sd);
//			org.setTermine(ar);
		
//			nf.setId(ids4[i]);
//			nf.setMessage(mes[i]);
//
//			e.setId(eids[i]);
//			e.setTitle(titles[i]);
//			e.setDescription(title[i]);
//
//			ap.setId(aids[i]);
//			ap.setTimestamp(sdf.parse(date3[i]));
			
			
			
//			session.save(org);
			tx.commit();

		}

		
		
//		
//		session.save(nf);
//		session.save(e);
//		session.save(ap);
		
	}

	public static void task02a() throws ParseException {
		// System.out.println("\n\nQuery 2a:\n-------------");
		// Session s = sessionFactory.openSession();
		//
		// Query nq = s.getNamedQuery("ReservierungByMail");
		//
		// nq.setString("eMail", "Aiden@Smith.com");
		//
		// @SuppressWarnings("unchecked")
		// List<Reservierung> l = nq.list();
		//
		// System.out.println();
		// for(int i=0; i<l.size(); i++) {
		// System.out.println("Reservierung "+(i+1)+": ID:"+l.get(i).getID()+", Preis:"+l.get(i).getPreis()+", Benutzer-Mail:"+l.get(i).getBenutzer().geteMail());
		// }
	}

	public static void task02b() throws ParseException {
		// System.out.println("\n\nQuery 2b:\n-------------");
		// Session s = sessionFactory.openSession();
		//
		// Query nq = s.getNamedQuery("BenutzerWithMonatskarte");
		//
		// @SuppressWarnings("unchecked")
		// List<Benutzer> l = nq.list();
		//
		// System.out.println();
		// for(int i=0; i<l.size(); i++) {
		// System.out.println("Benutzer "+(i+1)+": ID:"+l.get(i).getID()+", Vorname:"+l.get(i).getVorName()+", Nachname:"+l.get(i).getNachName());
		// }
	}

	public static void task02c() throws ParseException {
		System.out.println("\n\nQuery 2c:\n-------------");
	}

}
