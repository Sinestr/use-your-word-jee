package tp.jee.useyourwords.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	/**
	 * Controller vient rediriger vers une salle d'attente avant le lancement d'une partie
	 * Plusieurs conditions sont à respecter pour pouvoir avoir accès à une salle d'attente
	 * @param code
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/room/{code}")
	public String createroom(@PathVariable String code, Model model, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		
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
		
		List<UserPlayGame> currentUserInGame = this.userPlayGameService.findByUserAndGame(currentUser, currentGame);
		if (currentUserInGame.size() == 0) {
			return "redirect:/home/?gameaccessdenied";
		}
		UserPlayGame currentUserPlayGame = currentUserInGame.get(0);
		
		model.addAttribute("currentGame", currentGame);
		model.addAttribute("currentUser", currentUser);
		model.addAttribute("currentUserPlayGame", currentUserPlayGame);
		model.addAttribute("usersGame", this.userPlayGameService.findByGame(currentGame));
		
		return "room";
	}
	
	/**
	 * Exécution du formulaire qui permet de créer une partie 
	 * Partie en mode chacun pour soi
	 * @param session
	 * @return
	 */
	@PostMapping("/createroom")
	public String createroom(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home/?cannotplay";
		} 
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		
		Game newGame = this.gameService.createroom(currentUser, false);
			
		this.gameService.saveGame(newGame);
		this.userPlayGameService.saveUserPlayGame(new UserPlayGame(currentUser,newGame,true));
		
		return "redirect:/room/" + newGame.getCode();
	}
	
	/**
	 * Exécution du formulaire qui permet de créer une partie 
	 * Partie en mode par équipes
	 * @param session
	 * @return
	 */
	@PostMapping("/createroomteam")
	public String createRoomTeam(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home/?cannotplay";
		} 
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		
		Game newGame = this.gameService.createroom(currentUser, true);
			
		this.gameService.saveGame(newGame);
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
	public String leaveroom(@PathVariable int id, HttpSession session) {
		if(session.getAttribute("CURRENT_USER_ID") == null)  {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		Game game = this.gameService.findById(id);
		
		//suppression de la partie
		this.userPlayGameService.deleteUserPlayGame(this.userPlayGameService.findByUserAndGame(currentUser, game).get(0).getPlayId());
		
		//on décrémente le nombre de joueurs de la partie toujours existante
		game.setNbPlayers(game.getNbPlayers() - 1);
		this.gameService.edit(game);
		
		return "redirect:/home/?gameleft";
	}
	
	/**
	 * Exécution du formulaire pour lancer une partie créée
	 * @param code
	 * @param gameId
	 * @return
	 */
	@PostMapping("/startGame")
	public String startGame(@RequestParam String code, @RequestParam int gameId) {
		Game gameToStart = this.gameService.findById(gameId);
		
		if (gameToStart.getNbPlayers() < 2) {
			return "redirect:/room/" + code + "?lownbplayer";
		}
		
		
		
		
		//si un des joueurs n'a pas de d'équipe
		List<UserPlayGame> usersPlayGame = this.userPlayGameService.findByGame(gameToStart);
		List<Integer> usersTeam = new ArrayList<Integer>();
		
		for (UserPlayGame userPlayGame : usersPlayGame) {
			if (userPlayGame.getTeam() == 0 || userPlayGame.getTeam() > 3) {
				return "redirect:/room/" + code + "?hasnoteam";
			}
			usersTeam.add(userPlayGame.getTeam());
		}
		
		int team1 = this.gameService.countFrequencies(usersTeam, 1);
		int team2 = this.gameService.countFrequencies(usersTeam, 2);
		int team3 = this.gameService.countFrequencies(usersTeam, 3);
		
		if ((team1 == 0 && team2 == 0) || (team1 == 0 && team3 == 0) || (team2 == 0 && team3 == 0)) {
			return "redirect:/room/" + code + "?wrongplayersinteam";
		}
		
		//activer le statut de la partie
		gameToStart.setStatus(true);
		this.gameService.edit(gameToStart);
		
		return "game";
	}
}
