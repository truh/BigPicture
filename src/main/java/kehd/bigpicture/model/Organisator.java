package bigPicture.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.*;



@Entity
public class Organisator {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@OneToMany
	private long id;
	@Id
	private char[] password;
	@Column
	private Date[] termine;
}
