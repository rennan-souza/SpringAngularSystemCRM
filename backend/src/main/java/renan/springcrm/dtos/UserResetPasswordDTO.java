package renan.springcrm.dtos;

import java.io.Serializable;

public class UserResetPasswordDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String email;
	private String password;
	private String code;
	
	public UserResetPasswordDTO() {
	}

	public UserResetPasswordDTO(String email, String password, String code) {
		this.email = email;
		this.password = password;
		this.code = code;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
