package tp.jee.useyourwords.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp.jee.useyourwords.model.UserPlayGame;

public interface IUserPlayGameRepository extends JpaRepository<UserPlayGame, Integer> {
	
	@Query(
	  value = "SELECT * FROM play p ORDER BY p.PLAY_SCORE DESC LIMIT 10", 
	  nativeQuery = true)
	List<UserPlayGame> findTopScores();
}
