package bigPicture.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Invitation {
	@Id
	private Date date;

}
