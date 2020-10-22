package tp.jee.useyourwords.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/dashboard-scores")
	public String displayTableauScores(Model model, HttpSession session) {				
		List<UserPlayGame> plays = this.playService.findTopScores();
		
		model.addAttribute("scores", plays);
		model.addAttribute("countScores", plays.size());
		
		if (session.getAttribute("CURRENT_USER_ID") != null) {
			int currentUserId = (int) session.getAttribute("CURRENT_USER_ID");
			User currentUser = this.userService.findById(currentUserId);
			model.addAttribute("currentUser", currentUser);
		} 
		
		return "dashboard-scores";
	}
	
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
		this.playService.saveUserPlayGame(new UserPlayGame(currentUser, gameFound, false));
		
		return "redirect:/room/"+code;
	}
}
