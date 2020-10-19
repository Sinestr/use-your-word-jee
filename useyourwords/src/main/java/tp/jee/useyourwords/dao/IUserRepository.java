package tp.jee.useyourwords.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import tp.jee.useyourwords.model.User;

public interface IUserRepository extends JpaRepository<User, Integer> {
	public Optional<User> findByLogin(String login);
}
