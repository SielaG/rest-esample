package lt.itakademija.dao;

import java.util.List;

import lt.itakademija.model.user.User;

/*
 * DAO - Data Access Object. Darbo su User objektais API.
 */
public interface UserDAO {

	List<User> getUsers();
	
	User getUser(String username);

	void createUser(User user);
	
	void deleteUser(String username);

}
