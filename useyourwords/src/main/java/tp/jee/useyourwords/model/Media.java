package tp.jee.useyourwords.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
@Table(name="media")
public class Media {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	@Column(name="MEDIA_ID")
	private int id;
	
	@Column(name="MEDIA_PATH", length=500)
	private String path;
	
	@Column(name="MEDIA_CONTENT", length=2500)
	private String content;
	
	@ManyToOne
	@JoinColumn(name = "MEDIA_TYPE_ID")
	private MediaType mediaType;
	
	@ManyToMany
	@JoinTable(name = "constitute", 
		joinColumns = @JoinColumn(name = "MEDIA_ID"), 
		inverseJoinColumns = @JoinColumn(name = "GAME_ID"))
	private List<Game> mediaGame;
	
	/*
	 * GETTERs AND SETTERS
	 */
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getPath() { return path; }
	public void setPath(String path) { this.path = path; }
	
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	public MediaType getMediaType() { return mediaType; }
	
	public List<Game> getMediaGame() { return mediaGame; }
}
