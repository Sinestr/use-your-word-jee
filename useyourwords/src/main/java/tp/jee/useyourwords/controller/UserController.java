package tp.jee.useyourwords.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@RequestParam String pseudo, @RequestParam String login, @RequestParam String password) {
		String redirectionOption = "redirect:./registration";
		User user = new User(pseudo, login, password, false);
		
		if(user != null) {
			List<User> userExit = this.userService.findByLogin(user.getLogin());
			
			if(userExit.size() > 0) {
				redirectionOption = "redirect:./registration/?loginexist?" + user.getLogin();
			} else {
				this.userService.register(user);
				redirectionOption = "redirect:./registration/?success";
			}
		} else {
			redirectionOption = "redirect:./registration/?error";
		}
		
		return redirectionOption;
	}
}
