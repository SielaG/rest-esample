package lt.itakademija.dao;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Repository;

import lt.itakademija.model.user.User;

@Repository
public class InMemoryUserDAO implements UserDAO {

	protected final List<User> users = new CopyOnWriteArrayList<>();
	
	@Override
	public List<User> getUsers() {
		return Collections.unmodifiableList(users);
	}
	
	@Override
	public User getUser(String username) {
		User foundUser = null;
		
		for (User user: users) {
			if (username.equals(user.getUsername())) {
				foundUser = user;
				break;
			}
		}
		
		return foundUser;
	}

	@Override
	public void createUser(User user) {
		users.add(user);
	}

	@Override
	public void deleteUser(String username) {
		User user = getUser(username);
		if (user != null) {
			users.remove(user);
		}
	}

}
