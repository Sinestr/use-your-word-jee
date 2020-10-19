package tp.jee.useyourwords.service;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IUserPlayGameRepository;
import tp.jee.useyourwords.model.UserPlayGame;

@Service
public class UserPlayGameService {
	
	@Autowired
	private IUserPlayGameRepository daoPlay;
	
	public List<UserPlayGame> findAll() {
		return this.daoPlay.findAll();
	}
	
	@Transactional
	public List<UserPlayGame> findTopScores() {
		List<UserPlayGame> plays = this.daoPlay.findTopScores();
		
		for (UserPlayGame play : plays) {
			Hibernate.initialize(play.getUser());
		}
		
		return plays;
	}
	
}
