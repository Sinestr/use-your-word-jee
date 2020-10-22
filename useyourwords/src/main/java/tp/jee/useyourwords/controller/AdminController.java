package tp.jee.useyourwords.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.service.MediaService;
import tp.jee.useyourwords.service.MediaTypeService;

@Controller
public class AdminController {
	
	@Autowired
	private MediaService srvMedia;
	@Autowired
	private MediaTypeService srvMediaType;
	
	@GetMapping("/admin-interface")
	public String displayAdminInterface() {
		return "admin/admin-interface";

	}
	
	@GetMapping("/add-Pictures")
	public String displayAddPicturesInterface(Model model) {
		model.addAttribute("Medias", this.srvMedia.findAll());
		
		return "admin/add-Pictures";
	}
	
	@GetMapping("/add-Texts")
	public String displayAddTextInterface(Model model) {	
		List<Media> listMediaText = this.srvMedia.findByMediaType(this.srvMediaType.findById(2));
		model.addAttribute("listMediaText", listMediaText);
		return "admin/add-Texts";
	}
	
	@GetMapping("/add-Videos")
	public String displayAddVideoInterface() {				
		return "admin/add-Videos";

	}
}
