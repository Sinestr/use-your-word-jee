package tp.jee.useyourwords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import tp.jee.useyourwords.model.User;

@Controller
public class UserController {
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(User user) {
		return "redirect:./home";
	}
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
}
