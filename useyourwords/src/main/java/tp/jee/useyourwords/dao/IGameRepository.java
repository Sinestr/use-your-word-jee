package tp.jee.useyourwords.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.Game;

public interface IGameRepository extends JpaRepository<Game, Integer> {

}
