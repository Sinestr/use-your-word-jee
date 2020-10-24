package tp.jee.useyourwords.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IUserPlayGameRepository;
import tp.jee.useyourwords.model.Game;
import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;

@Service
public class UserPlayGameService {
	
	@Autowired
	private IUserPlayGameRepository daoPlay;
	
	/**
	 * 
	 * @param userPlayGame
	 */
	public void saveUserPlayGame(UserPlayGame userPlayGame) {
		this.daoPlay.save(userPlayGame);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteUserPlayGame(int id) {
		this.daoPlay.deleteById(id);
	}
	
	
	/**
	 * 
	 * @param game
	 */
	public void deleteAllPlayersInGame(Game game) {
		List<UserPlayGame> usersInGame = this.findByGame(game);
		for (UserPlayGame userPlayGame : usersInGame) {
			this.deleteUserPlayGame(userPlayGame.getPlayId());
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserPlayGame> findAll() {
		return this.daoPlay.findAll();
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional
	public List<UserPlayGame> findByGame(Game game) {
		List<UserPlayGame> usersPlayGame = this.daoPlay.findByGame(game);
		for (UserPlayGame userPlayGame : usersPlayGame) {
			Hibernate.initialize(userPlayGame.getUser());
		}
				
		return usersPlayGame;
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserPlayGame> findByUser(User user) {
		return this.daoPlay.findByUser(user);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<UserPlayGame> findByUserAndGame(User user, Game game) {
		return this.daoPlay.findByUserAndGame(user, game);
	}
	
	/**
	 * 
	 * @return
	 */
	@Transactional
	public List<UserPlayGame> findTopScores() {
		List<UserPlayGame> plays = this.daoPlay.findTopScores();
		
		for (UserPlayGame play : plays) {
			Hibernate.initialize(play.getUser());
		}
		return plays;
	}
	
}
