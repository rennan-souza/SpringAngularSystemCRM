package renan.springcrm.dtos;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import renan.springcrm.entities.User;

public class UserProfileUpdatePasswordDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank(message = "Campo requerido")
	private String password;

	@Size(min = 6, message = "A senha deve conter no mínimo 6 caracteres")
	@NotBlank(message = "Campo requerido")
	private String newPassword;

	public UserProfileUpdatePasswordDTO() {
	}

	public UserProfileUpdatePasswordDTO(String password, String newPassword) {
		this.password = password;
		this.newPassword = newPassword;
	}

	public UserProfileUpdatePasswordDTO(User entity) {
		password = entity.getPassword();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
