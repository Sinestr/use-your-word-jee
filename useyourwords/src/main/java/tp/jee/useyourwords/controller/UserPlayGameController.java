package tp.jee.useyourwords.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tp.jee.useyourwords.model.UserPlayGame;
import tp.jee.useyourwords.service.UserPlayGameService;

@Controller
public class UserPlayGameController {

	@Autowired
	private UserPlayGameService playService;
	
	@GetMapping("/dashboard-scores")
	public String displayTableauScores(Model model) {				
		List<UserPlayGame> plays = this.playService.findTopScores();
		
		model.addAttribute("scores", plays);
		model.addAttribute("countScores", plays.size());
		
		return "dashboard-scores";
	}
}
