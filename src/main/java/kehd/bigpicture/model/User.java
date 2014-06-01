package kehd.bigpicture.model;

import javax.persistence.*;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String name;
	
	@Column
	@javax.validation.constraints.Size(min=2, max=150)
	@javax.validation.constraints.Pattern(regexp = "[a-zA-Z_0-9-]")
	private char[] password;

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

	public char[] getPassword() {
		return password;
	}

	public void setPassword(char[] password) {
		this.password = password;
	}
}
