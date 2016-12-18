package lt.itakademija.model.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description="Registered user")
public final class User {
	
	@ApiModelProperty(value = "user id")
	private String username;

	@ApiModelProperty(value = "user first name")
	private String firstName;

	@ApiModelProperty(value = "user last name")
	private String lastName;

	@ApiModelProperty(value = "user email")
	private String email;
	
	public User() {}

	public User(String username, String firstName, String lastName, String email) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	@Override
	public String toString() {
		return "User [username=" + username 
				+ ", firstName=" + firstName 
				+ ", lastName=" + lastName 
				+ ", email=" + email
				+ "]";
	}

}
