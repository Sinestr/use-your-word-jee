package tp.jee.useyourwords.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IGameRepository;
import tp.jee.useyourwords.exception.GameNotFoundException;
import tp.jee.useyourwords.model.Game;

@Service
public class GameService {
	/**
	 * Existing to generate a random game code access 
	 */
	public static final String VARCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	@Autowired
	private IGameRepository daoGame;
	
	/**
	 * 
	 * @param game
	 */
	public void createroom(Game game) {
		this.daoGame.save(game);
	}
	
	/**
	 * 
	 * @param id
	 */
	public void deleteGame(int id) {
		this.daoGame.deleteById(id);;
	}
	
	/**
	 * Generate random access code for a game
	 * @param lenght
	 * @return
	 */
	public String generateCode(int lenght) {
		StringBuilder code = new StringBuilder(lenght);
		
		for (int i = 0; i < lenght; i++) { 
            int index  = (int)(VARCHARS.length() * Math.random()); 
            code.append(VARCHARS.charAt(index)); 
        } 
		return code.toString();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public Game findById(int id) {
		return this.daoGame.findById(id).orElseThrow(GameNotFoundException::new);
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public List<Game> findByCode(String code) {
		return this.daoGame.findByCode(code);
	}
}
