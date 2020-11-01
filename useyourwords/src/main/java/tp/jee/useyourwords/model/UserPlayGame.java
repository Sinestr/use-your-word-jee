package tp.jee.useyourwords.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "play")
public class UserPlayGame {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	@Column(name = "PLAY_ID", precision = 12)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
 
    @ManyToOne
    @JoinColumn(name = "GAME_ID")
    private Game game;
 
    @Column(name = "PLAY_SCORE", precision = 8)
    private int score;
    
    @Column(name = "PLAY_HOST", columnDefinition = "boolean default false")
    private boolean host;
    
    @Column(name = "PLAY_TEAM", precision = 1, nullable = true)
    private int team;
    
    /*
	 * CONSTRUCTS
	 */
	public UserPlayGame() {}
    
	//constrcution d'un partie standard, chacun pour soi
	public UserPlayGame(User user, Game game, boolean host) {
		this.user = user;
		this.game = game;
		this.score = 0;
		this.host = host;
	}
	
	//constrcution d'un partie par équipe
	public UserPlayGame(User user, Game game, boolean host, int team) {
		this.user = user;
		this.game = game;
		this.score = 0;
		this.host = host;
		this.team = team;
	}

    /*
	 * GETTERs AND SETTERS
	 */
    public int getPlayId() { return id; }
 	public void setPlayId(int play_id) { this.id = play_id; }
 	
    public User getUser() { return user; }
    public void setUser(User user_id) { this.user = user_id; }
 	
    public Game getGame() { return game; }
 	public void setGame(Game game_id) { this.game = game_id; }
   
 	public int getScore() { return score; }
 	public void setScore(int score) { this.score = score; }

	public boolean isHost() { return host; }
	public void setHost(boolean host) { this.host = host; }

	public int getTeam() { return team; }
	public void setTeam(int team) { this.team = team; }
}
