package tp.jee.useyourwords.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.Media;

public interface IMediaRepository extends JpaRepository<Media, Integer> {
	public List<Media> findByName(String name);
	public List<Media> findByTitle(String title);
}
