

import bigPicture.model.*;
//import bigPicture.query.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Query;



public class Main {

	private static final Logger log = Logger.getLogger(Main.class);
	
	static SimpleDateFormat dateForm = new SimpleDateFormat("dd.MM.yyyy");
	static SimpleDateFormat timeForm = new SimpleDateFormat("dd.MM.yyyy mm:hh");	
	
	private static final SessionFactory sessionFactory;

	
	static {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
	        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
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
//		//Benutzer
//		String[] fname={"Aiden","Jackson","Ethan","Liam","Jerome"};
//		String[] lname={"Smith","Miller","Ryan","Thomson","Black"};
//		String[] mail={"Aiden@Smith.com","Jackson@Miller.com","Ethan@Ryan.com","Liam@Thomson.com","Jerome@Black.com"};
//		String[] pass={"abc123","asdf1234","huan123","ghjk6789","bluemchen98"};
//		String[] nummer={"202-555-0141","202-555-0198","202-555-0104","202-555-0181","202-555-0149"};
//		Long[] vpraem={82L,78L,593L,23L,17L};
//		Long[] gpraem={182L,273L,652L,76L,32L};
//		
//		//Reservierung
//		Long[] id = {1L,2L,3L,4L,5L};
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//		String[] date={"13-03-2014 10:20:56","14-04-2014 10:20:44","22-04-2014 10:40:56","02-02-2014 12:20:56","01-01-2001 10:23:26"};
//		String[] date2={"15-03-2014 10:20:56","16-04-2014 10:20:44","24-04-2014 10:40:56","04-02-2014 12:20:56","03-01-2001 10:23:26"};
//		StatusInfo[] si={StatusInfo.DELAYED,StatusInfo.CANCELED,StatusInfo.ONTIME,StatusInfo.ONTIME,StatusInfo.DELAYED};
//		
//		//Bahnhof
//		String[] bname1={"Landstrasse","Znojmo","Wolkersdorf","Purkersdorf","Meidling"};
//		String[] bname2={"Tulln","Linz","Wiener Neustadt", "Bregenz", "Braunau"};
//		int[] absPreisEntfernung={5,10,2,4,20};
//		int[] absKmEntfernung={20,34,45,2,10};
//		int[] absZeitEntfernung={90,60,90,20,3};
//		boolean[] kopfBahnhof={true,true,false,true,false};
//		
//		for(int i=0;i<5;i++) {
//			Benutzer user = new Benutzer();
//			user.setVorName(fname[i]);
//			user.setNachName(lname[i]);
//			user.seteMail(mail[i]);
//			user.setPasswort(pass[i]);
//			user.setSmsNummer(nummer[i]);
//			user.setVerbuchtePraemienMeilen(vpraem[i]);
//			user.setGesamtPraemienMeilen(gpraem[i]);
//			
//			Bahnhof b1 = new Bahnhof();
//			b1.setName(bname1[i]);
//			b1.setAbsPreisEntfernung(absPreisEntfernung[i]+20);
//			b1.setAbsKmEntfernung(absKmEntfernung[i]+30);
//			b1.setAbsZeitEntfernung(absZeitEntfernung[i]+40);
//			b1.setKopfBahnhof(kopfBahnhof[i]);
//			
//			Bahnhof b2 = new Bahnhof();
//			b2.setName(bname2[i]);
//			b2.setAbsPreisEntfernung(absPreisEntfernung[i]);
//			b2.setAbsKmEntfernung(absKmEntfernung[i]);
//			b2.setAbsZeitEntfernung(absZeitEntfernung[i]);
//			b2.setKopfBahnhof(kopfBahnhof[i]);
//			
//			Strecke s = new Strecke();
//			s.setStart(b1);
//			s.setEnde(b2);
//			
//			Zug z = new Zug();
//			z.setFahrradStellplaetze(z.getFahrradStellplaetze());
//			z.setRollStuhlPlaetze(z.getRollStuhlPlaetze());
//			z.setSitzPlaetze(z.getSitzPlaetze());
//			z.setStart(b1);
//			z.setEnde(b2);
//			z.setStartZeit(sdf.parse(date2[i]));
//			
//			Reservierung r = new Reservierung();
//			r.setBenutzer(user);
//			r.setDatum(sdf.parse(date[i]));
//			r.setStatus(si[i]);
//			r.setStrecke(s);
//			r.setZug(z);
//			r.setPraemienMeilenBonus(r.getPraemienMeilenBonus());
//			r.setPreis(r.getPreis());
//			
//		
//			Session session = getSessionFactory().getCurrentSession();
//			Transaction tx = session.beginTransaction();
//			session.save(b1);
//			session.save(b2);
//			session.save(s);
//			session.save(z);
//			
//			
//// Auskommentiert, da wir uns irgendwo bei der Reihenfolge vertan haben, aber nicht darauf kommen, wo der Fehler liegt
////	
//			if(i==3) {	
//				Einzelticket et = new Einzelticket();
//				
//				et.setStrecke(s);
//				et.setTicketOption(TicketOption.FAHRRAD);
//				
//				Zeitkarte zk = new Zeitkarte();
//				zk.setStrecke(s);
//				zk.setTyp(ZeitkartenTyp.MONATSKARTE);
//				
//				Collection<Ticket> t = new ArrayList<>();
//				t.add(et);
//				t.add(zk);
//				user.setTickets(t);
//				session.save(et);
//				session.save(zk);
//			}
//			
//			if(i==4) {
//				Zeitkarte zk = new Zeitkarte();
//				zk.setID((long) i);
//				zk.setStrecke(s);
//				zk.setTyp(ZeitkartenTyp.JAHRESKARTE);
//				
//				Collection<Ticket> t = new ArrayList<>();
//				t.add(zk);
//				user.setTickets(t);
//				session.save(zk);
//			}
//			
//			if(i==2) {
//				Einzelticket et = new Einzelticket();
//				
//				et.setStrecke(s);
//				et.setTicketOption(TicketOption.GROSSGEPAECK);
//				
//				Zeitkarte zk = new Zeitkarte();
//				zk.setID((long) i);
//				zk.setStrecke(s);
//				zk.setTyp(ZeitkartenTyp.WOCHENKARTE);
//				
//				Collection<Ticket> t = new ArrayList<>();
//				t.add(et);
//				t.add(zk);
//				user.setTickets(t);
//				session.save(et);
//				session.save(zk);
//			}
//			
//			
//			session.save(user);
//			session.save(r);
//			tx.commit();
//		}
		
	}

	public static void task02a() throws ParseException {
//		System.out.println("\n\nQuery 2a:\n-------------");
//		Session s = sessionFactory.openSession();
//		
//		Query nq = s.getNamedQuery("ReservierungByMail");
//		
//		nq.setString("eMail", "Aiden@Smith.com");
//		
//		@SuppressWarnings("unchecked")
//		List<Reservierung> l = nq.list();
//		
//		System.out.println();
//		for(int i=0; i<l.size(); i++) {
//			System.out.println("Reservierung "+(i+1)+": ID:"+l.get(i).getID()+", Preis:"+l.get(i).getPreis()+", Benutzer-Mail:"+l.get(i).getBenutzer().geteMail());
//		}
	}

	public static void task02b() throws ParseException {
//		System.out.println("\n\nQuery 2b:\n-------------");
//		Session s = sessionFactory.openSession();
//		
//		Query nq = s.getNamedQuery("BenutzerWithMonatskarte");
//		
//		@SuppressWarnings("unchecked")
//		List<Benutzer> l = nq.list();
//		
//		System.out.println();
//		for(int i=0; i<l.size(); i++) {
//			System.out.println("Benutzer "+(i+1)+": ID:"+l.get(i).getID()+", Vorname:"+l.get(i).getVorName()+", Nachname:"+l.get(i).getNachName());
//		}
	}

	public static void task02c() throws ParseException {
		System.out.println("\n\nQuery 2c:\n-------------");
	}

}
