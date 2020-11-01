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
import org.springframework.web.bind.annotation.RequestParam;

import tp.jee.useyourwords.model.Game;
import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;
import tp.jee.useyourwords.service.GameService;
import tp.jee.useyourwords.service.UserPlayGameService;
import tp.jee.useyourwords.service.UserService;

@Controller
public class UserPlayGameController {

	@Autowired
	private UserPlayGameService playService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GameService gameService;
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/dashboard-scores")
	public String displayTableauScores(Model model, HttpSession session) {		
		
		if (this.playService.findTopScores() == null || this.playService.findTopScores().size() == 0) {
			model.addAttribute("countScores", 0);
			model.addAttribute("scores", null);
		} else {
			model.addAttribute("countScores", this.playService.findTopScores().size());
			model.addAttribute("scores", this.playService.findTopScores());
		}

		if (session.getAttribute("CURRENT_USER_ID") != null) {
			int currentUserId = (int) session.getAttribute("CURRENT_USER_ID");
			User currentUser = this.userService.findById(currentUserId);
			model.addAttribute("currentUser", currentUser);
		} 
		
		return "dashboard-scores";
	}
	
	/**
	 * 
	 * @param code
	 * @param session
	 * @return
	 */
	@PostMapping("/joinGame")
	public String joinGame(@RequestParam String code, HttpSession session) {		
		List<Game> gameExist = this.gameService.findByCode(code);
		
		//test si on trouve bien une partie et si il y en a plusieurs avec le même code on redirige vers l'accueil
		if (gameExist.size() != 1) {
			return "redirect:/home/?gamenotexist";
		}	
		
		Game gameFound = gameExist.get(0);
		//si la partie a déjà été lancée ou terminée on redirige vers l'accueil
		if (gameFound.isStatus() == true) {
			return "redirect:/home/?gamealreadystarted";
		}
		//si la partie est complète max 6 joueurs, on redirige vers l'accueil
		if (gameFound.getNbPlayers() == 6) {
			return "redirect:/home/?gamefull";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		
		List<UserPlayGame> userAlreadyExistInGame = this.playService.findByUserAndGame(currentUser, gameFound);
		
		if (userAlreadyExistInGame.size() == 0) {
			this.playService.saveUserPlayGame(new UserPlayGame(currentUser, gameFound, false));
			//on augmente de 1 le nombre de joueurs
			gameFound.setNbPlayers(gameFound.getNbPlayers() + 1);
			this.gameService.edit(gameFound);
		}
		
		return "redirect:/room/"+code;
	}
	
	/**
	 * 
	 * @param code
	 * @param session
	 * @return
	 */
	@PostMapping("/changeTeam")
	public String changeTeam(@RequestParam("team") int team, @RequestParam("userId") int userId, HttpSession session) {	
		List<UserPlayGame> userPlayGame = this.playService.findByUser(this.userService.findById(userId));
		UserPlayGame currentUserPlayGame = userPlayGame.get(0);
		
		currentUserPlayGame.setTeam(team);
		this.playService.saveUserPlayGame(currentUserPlayGame);

		return "redirect:/room/" + currentUserPlayGame.getGame().getCode();
	}
	
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/kick/{gameId}/{userId}")
	public String kick(@PathVariable int gameId, @PathVariable int userId) {
		//suppression du joueur dans la partie
		List<UserPlayGame> userPlayGame = this.playService.findByUser(this.userService.findById(userId));
		this.playService.deleteUserPlayGame(userPlayGame.get(0).getPlayId());
		
		//décrémentation du nombre de joueurs dans la partie
		Game currentGame = this.gameService.findById(gameId);
		currentGame.setNbPlayers(currentGame.getNbPlayers() - 1);
		this.gameService.edit(currentGame);
		
		return "redirect:/room/" + currentGame.getCode();
	}
}
