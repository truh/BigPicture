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

    @ManyToMany(mappedBy = "users")
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
}
