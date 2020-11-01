package tp.jee.useyourwords.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name = "game")
public class Game {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	@Column(name = "GAME_ID", precision = 10)
	private int id;
	
	@Column(name="GAME_NB_PLAYERS", precision = 2)
	private int nbPlayers;
	
	@Column(name = "GAME_ACCESS_CODE", length = 6) //access code is max sized by 6 chars.
	private String code;
	
	@Column(name="GAME_CREATED_AT")
	private LocalDateTime created_at;
	
	/**
	 * partie lancée ou non
	 */
	@Column(name="GAME_STATUS")
	private boolean status;
	
	/**
	 * partie en mode équipe ou chacun pour soi
	 */
	@Column(name="GAME_TEAM")
	private boolean team;
	
	@OneToMany(mappedBy = "game")
    private List<UserPlayGame> plays;
	
	@ManyToMany
	@JoinTable(name = "constitute", 
		joinColumns = @JoinColumn(name = "GAME_ID"), 
		inverseJoinColumns = @JoinColumn(name = "MEDIA_ID"))
    private List<Media> gameMedia;
	
	/*
	 * CONSTRUCTS
	 */
	public Game() {}
	
	public Game(String code, boolean team) {
		this.nbPlayers = 1;
		this.code = code;
		this.created_at = LocalDateTime.now();
		this.status = false;
		this.team = team;
	}
	
	
	/*
	 * GETTERs AND SETTERS
	 */
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public int getNbPlayers() { return nbPlayers; }
	public void setNbPlayers(int nbPlayers) { this.nbPlayers = nbPlayers; }
	
	public String getCode() { return code; }
	public void setNbPlayers(String code) { this.code = code; }
	
	public LocalDateTime getCreatedAt() { return created_at; }
	public void setCreatedAt(LocalDateTime created_at) { this.created_at = created_at; }
	
	public List<UserPlayGame> getPlays() { return plays; }
	
	public List<Media> getGameMedia() { return gameMedia; }

	public boolean isStatus() { return status; }
	public void setStatus(boolean status) { this.status = status; }

	public boolean isTeam() { return team; }
	public void setTeam(boolean team) { this.team = team; }
	
}
