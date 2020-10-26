package tp.jee.useyourwords.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.model.MediaType;

public interface IMediaRepository extends JpaRepository<Media, Integer> {
	public List<Media> findByMediaType(MediaType mediaType);
	public List<Media> findByName(String name);
}
