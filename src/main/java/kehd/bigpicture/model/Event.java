package kehd.bigpicture.model;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity //Gibt an, dass diese Klasse als Tabelle in die DB gespeichert werden soll
public class Event {

	@Id //Definiert das Attribut als Primaerschluessel
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Gibt an, dass & wie der Wert des Primaerschluessels automatisch erzeugt wird (IDENTITY verwendet fuer die ID eine Spalte in der Tabelle)
	private long id;
	
	@Column //ueber @Column koennen Eigenschaften der Spalte angegeben werden wie z.B. der Name, ob sie unique ist oder ob sie den Wert null haben darf
	private String title;
		
	@ManyToOne //Es können mehrere Events von einem Organisator erstellt werden.
	private Organisator organisator;
	
	@OneToMany //(mappedBy="Appointment")
	private Collection<Appointment> appointments;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
}
