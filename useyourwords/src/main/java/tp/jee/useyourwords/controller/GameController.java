package tp.jee.useyourwords.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tp.jee.useyourwords.model.Game;
import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;
import tp.jee.useyourwords.service.GameService;
import tp.jee.useyourwords.service.UserPlayGameService;
import tp.jee.useyourwords.service.UserService;

@Controller
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPlayGameService userPlayGameService;
	
	@GetMapping("/room/{code}")
	public String createroom(@PathVariable String code, Model model, HttpSession session) {
		List<Game> gameFound = this.gameService.findByCode(code);
		//si la partie aucune partie n'est trouvée ou si on en trouve plus d'une, on redirige vers l'accueil
		if (gameFound.size() != 1) {
			return "redirect:/home/?gamenotexist";
		}
		
		Game currentGame = gameFound.get(0);
		//si la partie a déjà été lancée alors on redirige vers l'accueil
		if (currentGame.isStatus()) {
			return "redirect:/home/?gamealreadystarted";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		List<UserPlayGame> currentUserInGame = this.userPlayGameService.findByUserAndGame(currentUser, currentGame);
		if (currentUserInGame.size() == 0) {
			return "redirect:/home/?gameaccessdenied";
		}
		
		//liste tous les jours d'une même partie
		List<User> usersGame = new ArrayList<User>();
		List<UserPlayGame> usersInGame = this.userPlayGameService.findByGame(currentGame);
		for (UserPlayGame userPlayGame : usersInGame) {
			usersGame.add(userPlayGame.getUser());
		}
		
		model.addAttribute("currentGame", currentGame);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("currentUserPlayGame", currentUserInGame.get(0));
		model.addAttribute("usersGame", usersGame);
		
		return "createroom";
	}
	
	@PostMapping("/createroom")
	public String createroom(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home/?cannotplay";
		} 
		String code = this.gameService.generateCode(6);
		
		//évite d'avoir deux codes d'accès de partie identiques
		List<Game> gameExist = this.gameService.findByCode(code);
		while(gameExist.size() > 0) {
			code = this.gameService.generateCode(6);
			gameExist = this.gameService.findByCode(code);
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		
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
					this.gameService.deleteGame(gameConcerned.getId());
				}
			}
		}
		
		//génère une partie avec un code d'accès de 6 caractères UNIQUE	
		Game newGame = new Game(code);
		this.gameService.createroom(newGame);
		this.userPlayGameService.saveUserPlayGame(new UserPlayGame(currentUser,newGame,true));
		
		return "redirect:/room/" + newGame.getCode();
	}
	
	/**
	 * Quand l'hote de la partie décide d'annuler une partie
	 * @param id
	 * @return
	 */
	@GetMapping("/cancelgame/{id}")
	public String cancelgame(@PathVariable int id) {
		//on supprime dans un premier temps les joueurs inscrits dans la partie
		this.userPlayGameService.deleteAllPlayersInGame(this.gameService.findById(id));
		
		//suppression de la partie
		this.gameService.deleteGame(id);
		
		return "redirect:/home/?gamedeleted";
	}
	
	/**
	 * Quand un joueur qui n'est pas l'hote décide quitter une partie dans laquelle il se trouve
	 * @param id
	 * @return
	 */
	@GetMapping("/leaveroom/{id}")
	public String leaveroom(@PathVariable int id) {
		//on supprime dans un premier temps les joueurs inscrits dans la partie
		this.userPlayGameService.deleteAllPlayersInGame(this.gameService.findById(id));
		
		//suppression de la partie
		this.gameService.deleteGame(id);
		
		return "redirect:/home/?gamedeleted";
	}
}
