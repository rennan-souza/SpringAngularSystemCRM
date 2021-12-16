package renan.springcrm.dtos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;

import renan.springcrm.entities.Customer;

public class CustomerDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank(message = "Campo nome obrigatório")
	private String firstName;

	@NotBlank(message = "Campo sobrenome obrigatório")
	private String lastName;

	@NotBlank(message = "Campo CPF obrigatório")
	private String cpf;

	@PastOrPresent(message = "Data de nascimento inválida")
	private LocalDate birthDate;

	@NotBlank(message = "Campo email obrigatório")
	@Email(message = "Digite um email válido")
	private String email;

	public CustomerDTO() {
	}

	public CustomerDTO(Long id, String firstName, String lastName, String cpf, LocalDate birthDate, String email) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.cpf = cpf;
		this.birthDate = birthDate;
		this.email = email;
	}

	public CustomerDTO(Customer entity) {
		this.id = entity.getId();
		this.firstName = entity.getFirstName();
		this.lastName = entity.getLastName();
		this.cpf = entity.getCpf();
		this.birthDate = entity.getBirthDate();
		this.email = entity.getEmail();
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

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
