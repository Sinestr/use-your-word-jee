package tp.jee.useyourwords.dao;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tp.jee.useyourwords.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	public List<User> findByLogin(String login);
	
	public List<User> findByLoginAndPassword(String login, String password);
	
	//@Query("SELECT u FROM User u WHERE u.login = ?1 and u.password = ?2")
	//public Optional<User> findByLoginAndPaswword(String login, String password);
}
