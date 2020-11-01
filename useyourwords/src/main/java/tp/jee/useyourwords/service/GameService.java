package tp.jee.useyourwords.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IGameRepository;
import tp.jee.useyourwords.exception.GameNotFoundException;
import tp.jee.useyourwords.model.Game;
import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;

@Service
public class GameService {
	/**
	 * Existing to generate a random game code access 
	 */
	public static final String VARCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	
	@Autowired
	private IGameRepository daoGame;
	

	@Autowired
	private UserPlayGameService userPlayGameService;
	
	/**
	 * 
	 * @param game
	 */
	public void edit(Game game) {
		this.daoGame.save(game);
	}
	
	/**
	 * 
	 * @param game
	 */
	public Game createroom(User currentUser, boolean type) {
		String code = this.generateUniqueCode();
		
		//si l'utilisateur est déjà hote d'une partie qui n'a pas démarré alors on supprime les autres salles d'attentes
		List<UserPlayGame> userAlreadyInGame = this.userPlayGameService.findByUser(currentUser);
		for (UserPlayGame userPlayGame : userAlreadyInGame) {
			if (userPlayGame.isHost() == true) {
				Game gameConcerned = userPlayGame.getGame();
				//si la partie que le joueur a hébergé n'a pas été lancée/finie
				if (gameConcerned.isStatus() == false) {
					//delete all players in game
					this.userPlayGameService.deleteAllPlayersInGame(gameConcerned);
					//delete game
					this.deleteGame(gameConcerned.getId());
				}
			}
		}
		
		//génère une partie avec un code d'accès de 6 caractères UNIQUE	
		Game newGame = new Game(code, type);		
		return newGame;
	}
	
	
	/**
	 * 
	 * @param game
	 */
	public void saveGame(Game game) {
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
	 * évite d'avoir deux codes d'accès de partie identiques
	 * @param lenght
	 * @return
	 */
	public String generateUniqueCode() {
		String code = this.generateCode(6);
		
		List<Game> gameExist = this.findByCode(code);
		while(gameExist.size() > 0) {
			code = this.generateCode(6);
			gameExist = this.findByCode(code);
		}
		
		return code;
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
	
	/**
	 * 
	 * @param list
	 */
	public int countFrequencies(List<Integer> usersTeam, int numberToFind) 
    { 
		int result = 0;
		
		for (Integer integer : usersTeam) {
			if (integer == numberToFind) {
				result++;
			}
		}
  
		return result;
    } 
}
