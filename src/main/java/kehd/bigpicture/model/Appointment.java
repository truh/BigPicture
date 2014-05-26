package kehd.bigpicture.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Appointment {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@ManyToOne
	private long id;
	@Id
	private Date timestamp;
}