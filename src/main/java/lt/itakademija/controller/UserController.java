package lt.itakademija.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lt.itakademija.dao.UserDAO;
import lt.itakademija.model.user.CreateUserCommand;
import lt.itakademija.model.user.User;

@RestController
@Api(value = "user")
@RequestMapping(value = "/api/users")
public class UserController {

	private final UserDAO userDao;

	@Autowired
	public UserController(UserDAO userDao) {
		this.userDao = userDao;
	}

	/* Apdoros užklausas: GET /api/users */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Get users", notes = "Returns registered users.")
	public List<User> getUsers() {
		return userDao.getUsers();
	}

	@RequestMapping(path = "/{username}", method = RequestMethod.GET)
	@ApiOperation(value = "Get user", notes = "Returns user by username.")
	public User getUser(@ApiParam(value = "User ID", required = true) @PathVariable final String username) {
		return userDao.getUser(username);
	}

	/*
	 * Sukurs vartotoją ir sėkmės atveju grąžinas atsakyma su HTTP statusu 201
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "Create user", notes = "Creates user.")
	public void createUser(@Valid @RequestBody final CreateUserCommand createUserCommand) {
		final String username = createUserCommand.getUsername();
		final String email = createUserCommand.getEmail();
		final String firstName = createUserCommand.getFirstName();
		final String lastName = createUserCommand.getLastName();

		final User user = new User(username, firstName, lastName, email);
		userDao.createUser(user);
	}

	/* Apdoros užklausas: DELETE /api/users/vartotojas */
	@RequestMapping(path = "/{username}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "Delete user", notes = "Deletes user.")
	public void deleteUser(@ApiParam(value = "User ID", required = true) @PathVariable final String username) {
		userDao.deleteUser(username);
	}
}
