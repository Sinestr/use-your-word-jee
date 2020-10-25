package tp.jee.useyourwords.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
	
	@GetMapping("/display-Pictures")
	public String displayAddPicturesInterface(Model model) {
		model.addAttribute("Medias", this.srvMedia.findAll());
		
		return "admin/display-Pictures";
	}
	
	@GetMapping("/display-Texts")
	public String displayAddTextInterface(Model model) {	
		List<Media> listMediaText = this.srvMedia.findByMediaType(this.srvMediaType.findById(2));
		model.addAttribute("listMediaText", listMediaText);
		return "admin/display-Texts";
	}
	
	@GetMapping("/add-Text")
	public String displayAdd() {
		return "admin/add-Text";
	} 
	
	@PostMapping("/add-Text")
	public String add(@RequestParam String content) {
		Media media = new Media();
		media.setMediaType(this.srvMediaType.findById(2));
		media.setContent(content);
		srvMedia.add(media);
		return "display-Texts";
	}
	
	
	@GetMapping("/display-Videos")
	public String displayAddVideoInterface() {				
		return "admin/display-Videos";

	}
}
