package tp.jee.useyourwords.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.Game;

public interface IGameRepository extends JpaRepository<Game, Integer> {
	
	public List<Game> findByCode(String code);
	
}
