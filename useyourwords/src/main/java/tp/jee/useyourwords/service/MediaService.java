package tp.jee.useyourwords.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import tp.jee.useyourwords.dao.IMediaRepository;
import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.model.MediaType;

@Service
public class MediaService {
	
	@Autowired
	private IMediaRepository daoMedia;
	

    /*public void uploadFile(MultipartFile file, HttpServletRequest request) {
        try {
            Path copyLocation = Paths
                .get(uploadDir + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
            Files.copy(file.getInputStream(), copyLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
	
	public List<Media> findAll() {
		return this.daoMedia.findAll();
	}
	
	public List<Media> findByMediaType(MediaType mediaType) {
		return this.daoMedia.findByMediaType(mediaType);
	}
	
	public List<Media> findByName(String name) {
		return this.daoMedia.findByName(name);
	}
	
	public void add(Media media) {
		this.daoMedia.save(media);
	}
	
	public void deleteById(int id) {
		this.daoMedia.deleteById(id);
	}
}