package renan.springcrm.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import renan.springcrm.entities.User;

public class UserProfileUpdateDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank(message = "Campo requerido")
	private String firstName;

	@NotBlank(message = "Campo requerido")
	private String lastName;

	@NotBlank(message = "Campo requerido")
	private String password;

	public UserProfileUpdateDTO() {
	}

	public UserProfileUpdateDTO(Long id, String firstName, String lastName, String password) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
	}

	public UserProfileUpdateDTO(User entity) {
		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.password = entity.getPassword();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
