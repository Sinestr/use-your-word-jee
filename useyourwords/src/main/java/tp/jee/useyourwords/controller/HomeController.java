package tp.jee.useyourwords.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/home")
	public String displayHome(Model model, HttpSession session) {	
		
		if (session.getAttribute("CURRENT_USER_ID") != null) {
			int currentUserId = (int) session.getAttribute("CURRENT_USER_ID");
			User currentUser = this.userService.findById(currentUserId);
			model.addAttribute("currentUser", currentUser);
		} 
		
		return "home";
	}
}
