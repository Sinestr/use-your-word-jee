package tp.jee.useyourwords.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.Media;

public interface IMediaRepository extends JpaRepository<Media, Integer> {

}
