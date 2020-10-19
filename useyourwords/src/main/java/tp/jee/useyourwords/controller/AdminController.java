package tp.jee.useyourwords.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

	@GetMapping("/admin-interface")
	public String displayAdminInterface() {
		return "admin/memu";
	}
}
