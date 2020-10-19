package tp.jee.useyourwords.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.MediaType;

public interface IMediaTypeRepository extends JpaRepository<MediaType, Integer> {

}
