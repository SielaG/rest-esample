package lt.itakademija;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import lt.itakademija.dao.InMemoryUserDAO;
import lt.itakademija.model.user.CreateUserCommand;
import lt.itakademija.model.user.User;

/* Paskaityti: http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-testing.html */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = { UserControllerIT.Config.class, Application.class })
public class UserControllerIT {

	private static final String URI = "/api/users";

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private ResetableInMemoryUserDao userDao;
	
	@Before
	public void setUp() {
		// Isvalysime dao objekta pries kiekviena testo paleidima
		userDao.clear();
	}
	
	private void createUser(final CreateUserCommand createUser) {
		// Exercise
		ResponseEntity<Void> response = restTemplate.postForEntity(URI, createUser, Void.class);

		// Verify
		Assert.assertThat(response.getStatusCode(), CoreMatchers.is(HttpStatus.CREATED));
	}

	private List<User> getUsers() {
		// Setup
		ParameterizedTypeReference<List<User>> users = new ParameterizedTypeReference<List<User>>() {
		};

		// Execute
		ResponseEntity<List<User>> response = restTemplate.exchange(URI, HttpMethod.GET, null, users);

		// Verify
		Assert.assertThat(response.getStatusCode(), is(HttpStatus.OK));

		return response.getBody();
	}

	private void deleteUser(final String username) {
		// Exercise
		ResponseEntity<Void> response = restTemplate.exchange(URI + "/" + username, HttpMethod.DELETE, null, Void.class);

		// Verify
		Assert.assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
	}

	@Test
	public void createsUserThenRetrievesUserListAndDeletesUser() {
		// Setup
		final String username = "mariusg";
		final CreateUserCommand createUser = new CreateUserCommand();
		createUser.setUsername(username);
		createUser.setEmail("marius.grybe@gmail.com");
		createUser.setFirstName("Marius");
		createUser.setLastName("Grybe");

		createUser(createUser);

		List<User> users = getUsers();
		Assert.assertThat(users.size(), is(1));

		deleteUser(username);
	}
	
	@TestConfiguration
	static class Config {
		/* Aplikacija naudos ResetableInMemoryUserDao vietoj InMemoryUserDAO */
		@Bean
		@Primary
		public ResetableInMemoryUserDao userDAO() {
			return new ResetableInMemoryUserDao();
		}
	}
	
	private static final class ResetableInMemoryUserDao extends InMemoryUserDAO {
		private void clear() {
			users.clear();
		}
	}

}
