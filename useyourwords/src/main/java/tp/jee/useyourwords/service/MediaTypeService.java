package tp.jee.useyourwords.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IMediaRepository;
import tp.jee.useyourwords.dao.IMediaTypeRepository;
import tp.jee.useyourwords.exception.MediaTypeNotFoundException;
import tp.jee.useyourwords.model.Media;
import tp.jee.useyourwords.model.MediaType;

@Service
public class MediaTypeService {
	@Autowired
	private IMediaTypeRepository daoMediaType;
	
	public MediaType findById(int id) {
		return this.daoMediaType.findById(id).orElseThrow(MediaTypeNotFoundException::new);
	}
}