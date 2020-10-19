package tp.jee.useyourwords.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="media_type")
public class MediaType {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	@Column(name = "MEDIA_TYPE_ID")
	private int id;
	
	@Column(name = "MEDIA_TYPE_LIBELLE", length = 100)
	private String libelle;
	
	/*
	 * GETTERs AND SETTERS
	 */
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	
	public String getLibelle() { return libelle; }
	public void setLibelle(String libelle) { this.libelle = libelle; }
}
