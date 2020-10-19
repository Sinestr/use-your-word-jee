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
}
