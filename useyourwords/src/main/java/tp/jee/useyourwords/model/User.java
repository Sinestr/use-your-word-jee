package tp.jee.useyourwords.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	@Column(name = "USER_ID", precision = 8)
	private int id;
	
	@Column(name = "USER_LOGIN", length = 100)
	private String login;
	
	@Column(name = "USER_PASSWORD", length = 100)
	private String password;
	
	@Column(name = "USER_PSEUDO", length = 100)
	private String pseudo;
	
	@Column(name = "USER_ADMIN", columnDefinition = "boolean default false")
	private boolean admin;
	
	@OneToMany(mappedBy = "user")
    private List<UserPlayGame> user;
	
	/*
	 * CONSTRUCTS
	 */
	public User() {}
	
	public User(String pseudo, String login, String password, boolean admin) {
		this.pseudo = pseudo;
		this.login = login;
		this.password = password;
		this.admin = admin;
	}
	
	/*
	 * GETTERs AND SETTERS
	 */
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getLogin() { return login; }
	public void setLogin(String login) { this.login = login; }
	
	public String getPassword() { return password; }
	public void setPassword(String password) { this.password = password; }
	
	public String getPseudo() { return pseudo; }
	public void setPseudo(String pseudo) { this.pseudo = pseudo; }
	
	public boolean isAdmin() { return admin; }
	public void setAdmin(boolean admin) { this.admin = admin; }
	
	public List<UserPlayGame> getContent() { return user; }
}
