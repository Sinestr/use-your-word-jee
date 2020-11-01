package tp.jee.useyourwords.controller;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.model.User;
import tp.jee.useyourwords.service.MediaService;
import tp.jee.useyourwords.service.UserService;

@Controller
public class AdminController {
	
	@Autowired
	ServletContext context;
	
	@Value("${uploadDir}")
    public String uploadFolder;
	
	public static String[] imagesTypes = {"jpg", "JPG", "jpeg", "JPEG", "png", "PNG"};
	public static String[] videosTypes = {"mp4", "MP4"};
	
	@Autowired
	private MediaService srvMedia;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/admin-interface")
	public String displayAdminInterface(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		return "admin/admin-interface";

	}
	
	/**
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/deletePicture/{id}")
	public String deleteImage(@PathVariable int id, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		this.srvMedia.deleteById(id);
				
		return "redirect:/display-Pictures/?deletesuccess";
	} 
	
	/**
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/deleteText/{id}")
	public String deleteText(@PathVariable int id, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		this.srvMedia.deleteById(id);
				
		return "redirect:/display-Texts/?deletesuccess";
	} 
	
	/**
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@GetMapping("/deleteVideo/{id}")
	public String deleteVideo(@PathVariable int id, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		this.srvMedia.deleteById(id);
				
		return "redirect:/display-Videos/?deletesuccess";
	} 
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/display-Pictures")
	public String displayAddPicturesInterface(Model model, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		List<Media> listMediaImages = this.srvMedia.findByTitle("image");
		if (listMediaImages.size() > 0) {
			model.addAttribute("listMediaImages", listMediaImages);
		}
		
		return "admin/display-Pictures";
	}
	
	@GetMapping("/add-Picture")
	public String addPictureForm(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
				
		return "admin/add-Picture";
	} 
	
	/**
	 * 
	 * @param file
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/add-Picture")
	public String addPicture(@RequestParam("path") MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);

		if (Arrays.asList(imagesTypes).contains(fileType)) {
			String uploadDirectory = request.getServletContext().getRealPath(this.uploadFolder);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(this.uploadFolder, fileName).toString();

			try {
				/*ClassPathResource imgFile = new ClassPathResource("images/uploads/pictures/" + fileName);
				response.setContentType(MediaType.IMAGE_JPEG_VALUE);
				StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());*/
				
				/*File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();*/
				if (this.srvMedia.findByName(fileName).size() > 0) {
					return "redirect:./add-Picture/?mediaalreadyexist";
				}
				//constrcution et ajout de d'un media de type image
				Media newMedia = new Media("image", file.getBytes(), fileName, null);
				this.srvMedia.add(newMedia);
					
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			return "redirect:./add-Picture/?wrongFormat";
		}
		return "redirect:./add-Picture/?imageAdded";
	}
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/display-Texts")
	public String displayAddTextInterface(Model model, HttpSession session) {	
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		List<Media> listMediaText = this.srvMedia.findByTitle("text");
		if (listMediaText.size() > 0) {
			model.addAttribute("listMediaText", listMediaText);
		}
		
		return "admin/display-Texts";
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/add-Text")
	public String addTextForm(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
				
		return "admin/add-Text";
	} 
	
	/**
	 * 
	 * @param content
	 * @return
	 */
	@PostMapping("/add-Text")
	public String addText(@RequestParam String content) {
		Media newMedia = new Media("text", null, "", content);
		this.srvMedia.add(newMedia);
		
		return "redirect:./add-Text/?textAdded";
	} 
	
	
	/**
	 * 
	 * @param model
	 * @param session
	 * @return
	 */
	@GetMapping("/display-Videos")
	public String displayVideosInterface(Model model, HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
		
		List<Media> listMediaVideos = this.srvMedia.findByTitle("video");
		if (listMediaVideos.size() > 0) {
			model.addAttribute("listMediaVideos", listMediaVideos);
		}
		
		return "admin/display-Videos";
	}
	
	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping("/add-Video")
	public String addVideo(HttpSession session) {
		if (session.getAttribute("CURRENT_USER_ID") == null) {
			return "redirect:/home";
		}
		
		//recuperation de l'utilisateur courant connecté
		User currentUser = this.userService.findById((int) session.getAttribute("CURRENT_USER_ID"));
		if (currentUser.isAdmin() == false) {
			return "redirect:/home";
		}
				
		return "admin/add-Video";
	} 
	
	/**
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/add-Video")
	public String addVideo(@RequestParam("path") MultipartFile file) {

		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
		
		if (Arrays.asList(videosTypes).contains(fileType)) {
			try {
					
				if (this.srvMedia.findByName(file.getOriginalFilename()).size() > 0) {
					return "redirect:./add-Video/?mediaalreadyexist";
				}
				
				//null car toutes les vidéos sont trop lourdes
				Media newMedia = new Media("video", null, file.getOriginalFilename(), null);
				this.srvMedia.add(newMedia);
				//Path fileNameAndPath = Paths.get(this.context.getRealPath("resources/static/images/uploads/pictures"), file.getOriginalFilename());

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			return "redirect:./add-Video/?wrongFormat";
		}
			
		
		
		return "redirect:./add-Video/?videoAdded";
	}
}
