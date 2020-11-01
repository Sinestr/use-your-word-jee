package tp.jee.useyourwords.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IMediaRepository;
import tp.jee.useyourwords.model.Media;

@Service
public class MediaService {
	
	@Autowired
	private IMediaRepository daoMedia;
	
	/**
	 * 
	 * @return
	 */
	public List<Media> findAll() {
		return this.daoMedia.findAll();
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public List<Media> findByName(String name) {
		return this.daoMedia.findByName(name);
	}
	
	/**
	 * 
	 * @param title
	 * @return
	 */
	public List<Media> findByTitle(String title) {
		return this.daoMedia.findByTitle(title);
	}
	
	/**
	 * 
	 * @param media
	 */
	public void add(Media media) {
		this.daoMedia.save(media);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteById(int id) {
		this.daoMedia.deleteById(id);
	}
}