package kehd.bigpicture.model;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(unique=true)
	private String name;
	
	@Column
	private String password;

    @ManyToMany
    private Collection<Appointment> appointments;

    @OneToMany(mappedBy = "author")
    private Collection<Comment> comments;

    @ManyToMany//(mappedBy = "users")
    private Collection<Event> events;

    @OneToMany(mappedBy = "organisator")
    private Collection<Event> organisedEvents;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public Collection<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Collection<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Collection<Comment> getComments() {
        return comments;
    }

    public void setComments(Collection<Comment> comments) {
        this.comments = comments;
    }

    public Collection<Event> getEvents() {
        return events;
    }

    public void setEvents(Collection<Event> events) {
        this.events = events;
    }

    public Collection<Event> getOrganisedEvents() {
        return organisedEvents;
    }

    public void setOrganisedEvents(Collection<Event> organisedEvents) {
        this.organisedEvents = organisedEvents;
    }
}
