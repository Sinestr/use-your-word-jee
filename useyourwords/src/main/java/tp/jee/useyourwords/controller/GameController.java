package tp.jee.useyourwords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GameController {
	
	@GetMapping("/play")
	public String playClick() {
		return "play";
	}
}