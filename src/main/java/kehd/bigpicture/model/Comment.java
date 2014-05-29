package kehd.bigpicture.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private Date timestamp;
	
	@Column
	private String comment;
	
	@ManyToOne //Mehrere Comments können von einem User erstellt werden.
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getUserid() {
		return user;
	}

	public void setUserid(User userid) {
		this.user = user;
	}

}
