package kehd.bigpicture.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity //Gibt an, dass diese Klasse als Tabelle in die DB gespeichert werden soll
public class Notification {

	@Id //Definiert das Attribut als Primaerschluessel
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gibt an, dass & wie der Wert des Primaerschluessels automatisch erzeugt wird (IDENTITY verwendet fuer die ID eine Spalte in der Tabelle)
	private long id;
	
	@Column //ueber @Column koennen Eigenschaften der Spalte angegeben werden wie z.B. der Name, ob sie unique ist oder ob sie den Wert null haben darf
	private String message;
	
	@ManyToOne // Ein Event kann mehrere Notifications haben
	private Event event;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
}
