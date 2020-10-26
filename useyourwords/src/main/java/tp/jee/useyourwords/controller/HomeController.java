package tp.jee.useyourwords.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;
import tp.jee.useyourwords.service.UserService;
import tp.jee.useyourwords.service.UserPlayGameService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserPlayGameService playService;
	
	@GetMapping("/")
	public String displayHomeFromRoot(Model model, HttpSession session) {	
		
		if (session.getAttribute("CURRENT_USER_ID") != null) {
			int currentUserId = (int) session.getAttribute("CURRENT_USER_ID");
			User currentUser = this.userService.findById(currentUserId);
			
			List<UserPlayGame> currentUserGame = this.playService.findByUser(currentUser);
			if (currentUserGame.size() == 1) {
				model.addAttribute("gameUserCode", currentUserGame.get(0).getGame().getCode());
			}
		
			model.addAttribute("currentUser", currentUser);
		} 
		
		
		return "redirect:/home";
	}
	
	/**
	 * Controller qui vient afficher la page d'accueil
	 * l'affichage est différent en fonction de si un utilisateur est connecté ou non
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/home")
	public String displayHome(Model model, HttpSession session) {	
		
		if (session.getAttribute("CURRENT_USER_ID") != null) {
			int currentUserId = (int) session.getAttribute("CURRENT_USER_ID");
			User currentUser = this.userService.findById(currentUserId);
			
			List<UserPlayGame> currentUserGame = this.playService.findByUser(currentUser);
			if (currentUserGame.size() == 1) {
				model.addAttribute("gameUserCode", currentUserGame.get(0).getGame().getCode());
			}
		
			model.addAttribute("currentUser", currentUser);
		} 
		
		
		return "home";
	}
}
