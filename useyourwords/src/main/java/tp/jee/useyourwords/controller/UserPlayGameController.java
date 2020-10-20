package tp.jee.useyourwords.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.model.UserPlayGame;
import tp.jee.useyourwords.service.UserPlayGameService;
import tp.jee.useyourwords.service.UserService;

@Controller
public class UserPlayGameController {

	@Autowired
	private UserPlayGameService playService;
	
	@Autowired
	private UserService userService;
	
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
}
