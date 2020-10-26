package tp.jee.useyourwords.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	
	/**
	 * Page de connexion avec une vérificationde session
	 * pour savoir si un utilisateur est déjà connecté ou non
	 * @param session
	 * @return
	 */
	@GetMapping("/login")
	public String login(HttpSession session) {
		//test if a user is already connected or not
		if (session.getAttribute("CURRENT_USER_ID") != null) {
			return "redirect:/home";
		}
		return "login";
	}
	
	/**
	 * Exécution 
	 * @param login
	 * @param password
	 * @param request
	 * @return
	 */
	@PostMapping("/login")
	public String login(@RequestParam String login, @RequestParam String password, HttpServletRequest request) {
		String redirectionOption = "redirect:./home";
		List<User> userFound = this.userService.findByLoginAndPaswword(login, password);
		
		if (userFound != null && userFound.size() == 1) {
			//create new session which will store current user (logged)
			request.getSession().setAttribute("CURRENT_USER_ID", userFound.get(0).getId());
			//check if it's an admin or not (change redirection)
			if(userFound.get(0).isAdmin()) {
				redirectionOption = "redirect:/admin-interface/?loginsuccess";
			} else {
				redirectionOption = "redirect:/home/?loginsuccess";
			}
		} else {
			redirectionOption = "redirect:./login/?loginfail";
		}
		return redirectionOption;
	}
	
	@PostMapping("/logout")
	public String logout(HttpServletRequest request) {
		request.getSession().invalidate();
		return "redirect:/home";
	}
	
	@GetMapping("/registration")
	public String registration() {
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@RequestParam String pseudo, @RequestParam String login, @RequestParam String password) {
		String redirectionOption = "redirect:./registration";
		User user = new User(pseudo, login, password, false);

		List<User> userExit = this.userService.findByLogin(user.getLogin());
		
		if (userExit.size() > 0) {
			redirectionOption = "redirect:./registration/?loginexist?" + user.getLogin();
		} else {
			this.userService.register(user);
			redirectionOption = "redirect:./registration/?success";
		}
		
		return redirectionOption;
	}
}
