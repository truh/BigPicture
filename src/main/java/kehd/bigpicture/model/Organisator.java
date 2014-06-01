package kehd.bigpicture.model;

import javax.persistence.*;
import java.util.Collection;

@Entity //Gibt an, dass diese Klasse als Tabelle in die DB gespeichert werden soll
public class Organisator {

	@Id //Definiert das Attribut als Primaerschluessel
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gibt an, dass & wie der Wert des Primaerschluessels automatisch erzeugt wird (IDENTITY verwendet fuer die ID eine Spalte in der Tabelle)
	private long id;
		
	@OneToOne //Ein User darf nur ein Organisator sein
	private User user;
	
	@OneToMany
	private Collection<Event> event;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Collection<Event> getEvent() {
		return event;
	}

	public void setEvent(Collection<Event> event) {
		this.event = event;
	}

	
}
