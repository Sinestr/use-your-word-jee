package tp.jee.useyourwords.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IUserRepository;
import tp.jee.useyourwords.model.User;

@Service
public class UserService {
	
	@Autowired
	private IUserRepository daoUser;
	
	public void register(User user) {
		this.daoUser.save(user);
	}
	
	public List<User> findByLogin(String login) {
		return this.daoUser.findByLogin(login);
	}
}
