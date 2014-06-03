package kehd.bigpicture.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

@Entity //Gibt an, dass diese Klasse als Tabelle in die DB gespeichert werden soll
public class Notification {

	@Id //Definiert das Attribut als Primaerschluessel
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gibt an, dass & wie der Wert des Primaerschluessels automatisch erzeugt wird (IDENTITY verwendet fuer die ID eine Spalte in der Tabelle)
	private long id;
	
	@Column //ueber @Column koennen Eigenschaften der Spalte angegeben werden wie z.B. der Name, ob sie unique ist oder ob sie den Wert null haben darf
	private String message;

    @Column
    private Date timestamp;

    @Enumerated(value = EnumType.STRING)
    NotificationType type;
	
	@ManyToOne // Ein Event kann mehrere Notifications haben
	private Event event;

    @ManyToMany
    private Collection<User> recipients;

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

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public Collection<User> getRecipients() {
        return recipients;
    }

    public void setRecipients(Collection<User> recipients) {
        this.recipients = recipients;
    }
}
