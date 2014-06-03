package kehd.bigpicture.model;

import javax.persistence.*;
import java.util.Collection;

@Entity //Gibt an, dass diese Klasse als Tabelle in die DB gespeichert werden soll
public class Event {

	@Id //Definiert das Attribut als Primaerschluessel
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gibt an, dass & wie der Wert des Primaerschluessels automatisch erzeugt wird (IDENTITY verwendet fuer die ID eine Spalte in der Tabelle)
	private long id;
	
	@Column //ueber @Column koennen Eigenschaften der Spalte angegeben werden wie z.B. der Name, ob sie unique ist oder ob sie den Wert null haben darf
	private String title;
		
	@ManyToOne //Es koennen mehrere Events von einem Organisator erstellt werden.
	private Organisator organisator;
	
	@OneToMany
	private Collection<Appointment> appointments;

    @OneToMany
    private Collection<Comment> comments;

    @Enumerated(value = EnumType.STRING)
    private EventType type;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Organisator getOrganisator() {
		return organisator;
	}

	public void setOrganisator(Organisator organisator) {
		this.organisator = organisator;
	}

	public Collection<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(Collection<Appointment> appointments) {
		this.appointments = appointments;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }
}
