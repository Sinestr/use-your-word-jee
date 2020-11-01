package tp.jee.useyourwords.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
	
	@Column(name="MEDIA_TITLE", length=255, nullable = true)
	private String title;
	
	@Lob
	@Column(name="MEDIA_PATH", length=500, nullable = true)
	private byte[] path;
	
	@Column(name="MEDIA_CONTENT", length=2500, nullable = true)
	private String content;
	
	@Column(name="MEDIA_NAME", length=255, nullable = true)
	private String name;
	
	@ManyToMany
	@JoinTable(name = "constitute", 
		joinColumns = @JoinColumn(name = "MEDIA_ID"), 
		inverseJoinColumns = @JoinColumn(name = "GAME_ID"))
	private List<Game> mediaGame;
	
	/*
	 * Constructs
	 */
	public Media() {}
	
	public Media(String title, byte[] path, String name, String content) {
		this.title = title;
		this.path = path;
		this.name = name;
		this.content = content;
	}
	
	/*
	 * GETTERs AND SETTERS
	 */
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	
	public byte[] getPath() { return path; }
	public void setPath(byte[] path) { this.path = path; }
	
	public String getContent() { return content; }
	public void setContent(String content) { this.content = content; }
	
	public List<Game> getMediaGame() { return mediaGame; }

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }

}
