package tp.jee.useyourwords.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp.jee.useyourwords.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	public List<User> findByLogin(String login);
	
	public List<User> findByLoginAndPassword(String login, String password);
	
}
