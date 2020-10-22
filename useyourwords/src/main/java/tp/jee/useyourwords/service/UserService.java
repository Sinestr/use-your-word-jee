package tp.jee.useyourwords.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tp.jee.useyourwords.dao.IUserRepository;
import tp.jee.useyourwords.exception.UserNotFoundException;
import tp.jee.useyourwords.model.Game;
import tp.jee.useyourwords.model.User;

@Service
public class UserService {
	
	@Autowired
	private IUserRepository daoUser;
	
	/**
	 * 
	 * @param user
	 */
	public void register(User user) {
		this.daoUser.save(user);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public User findById(int id) {
		return this.daoUser.findById(id).orElseThrow(UserNotFoundException::new);
	}
	
	/**
	 * 
	 * @param login
	 * @return
	 */
	public List<User> findByLogin(String login) {
		return this.daoUser.findByLogin(login);
	}
	
	/**
	 * 
	 * @param login
	 * @param password
	 * @return
	 */
	public List<User> findByLoginAndPaswword(String login, String password) {
		return this.daoUser.findByLoginAndPassword(login, password);
	}
}
