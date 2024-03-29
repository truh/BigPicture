package kehd.bigpicture;

import kehd.bigpicture.model.*;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The Class Main.
 */
public class Main {

	private static final Logger log = Logger.getLogger(Main.class);

	static SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeForm = new SimpleDateFormat("dd.MM.yyyy mm:hh");

    private static final EntityManagerFactory entityManagerFactory;

	static {

        entityManagerFactory = Persistence.createEntityManagerFactory("BigPicture");
	}

    public static EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
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
		String[] date3 = { "13-02-2014 21:00:11", "14-10-2014 17:20:11", "22-09-2014 05:00:11",
				"02-12-2014 10:10:10", "01-11-2011 23:023:23" };
		Date[] ar = new Date[date2.length];
//		for (int j = 0; j < date2.length; j++) {
//			ar[j] = sdf.parse(date2[j]);
//		}
		
		char[] sd={'s','h'};
		for (int i = 0; i < 5; i++) {
			User org = new User();
			Comment c = new Comment();
			Notification nf = new Notification();
			Event e = new Event();
			Appointment ap = new Appointment();

            EntityManager manager = getEntityManagerFactory().createEntityManager();
            manager.getTransaction().begin();


			org.setName(name[i]);
			org.setPassword(pass[i]);
            manager.persist(org);

			c.setTimestamp(sdf.parse(date[i]));
			c.setComment(com[i]);

			c.setAuthor(org);
			manager.persist(c);
			
			ap.setTimestamp(sdf.parse(date3[i]));
			
			e.setTitle(titles[i]);
			e.setOrganisator(org);
			Collection<Appointment> a= new ArrayList<>();
			a.add(ap);
			e.setAppointments(a);
			
			Collection<Event> ee= new ArrayList<>();
			ee.add(e);

			ap.setEvent(e);

            manager.persist(e);
            manager.persist(org);
            manager.persist(ap);
				
			manager.getTransaction().commit();
        }

	}	

	public static void task02a() throws ParseException {
		EntityManager manager = getEntityManagerFactory().createEntityManager();
		System.out.println("HALLOOOOO");

		manager.getTransaction().begin();
        Query query = manager.createNativeQuery("SELECT * FROM User");

		//session.getTransaction().commit();
		List result = query.getResultList();
		Iterator iterator = result.iterator();
		
		while(iterator.hasNext()){
			Object[] row = (Object[])iterator.next();
			for(int col=0;col<row.length;col++){
				System.out.println(row[col]);
			}
		}

		Query query2= manager.createNativeQuery(
                "SELECT timestamp,comment " +
                        "FROM Comment c " +
                        "INNER JOIN User u on u.id=c.id " +
                        "WHERE u.name = :vname ").setParameter("vname", "Jerome");

		List result2= query2.getResultList();
		Iterator iterator1 = result2.iterator();
		
		while(iterator1.hasNext()){
			Object[] row = (Object[])iterator1.next();
			for(int col=0;col<row.length;col++){
				System.out.println(row[col]);
			}
		}
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
