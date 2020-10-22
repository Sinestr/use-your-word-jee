package tp.jee.useyourwords.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IMediaRepository;
import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.model.MediaType;

@Service
public class MediaService {
	private static int id = 1;
	private List<Media> Medias;
	
	@Autowired
	private IMediaRepository daoMedia;
	
	public List<Media> findAll() {
		return this.daoMedia.findAll();
	}
	
	public List<Media> findByMediaType(MediaType mediaType) {
		return this.daoMedia.findByMediaType(mediaType);
	}
	
	public void add(Media media) {
		if (this.Medias == null) {
			this.Medias = new ArrayList<>();
		}
		
		media.setId(id++);
		this.Medias.add(media);
	}
	
	public void clearMedias()
	{
		this.Medias = null;
	}
}