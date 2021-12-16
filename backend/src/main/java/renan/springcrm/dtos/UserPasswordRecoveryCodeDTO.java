package renan.springcrm.dtos;

import java.io.Serializable;
import java.time.Instant;

import renan.springcrm.entities.UserPasswordRecoveryCode;

public class UserPasswordRecoveryCodeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private String code;
	private Instant createdAt;
	private Instant expiresAt;
	
	public UserPasswordRecoveryCodeDTO() {
		
	}

	public UserPasswordRecoveryCodeDTO(Long id, Long userId, String code, Instant createdAt, Instant expiresAt) {
		this.id = id;
		this.userId = userId;
		this.code = code;
		this.createdAt = createdAt;
		this.expiresAt = expiresAt;
	}
	
	public UserPasswordRecoveryCodeDTO(UserPasswordRecoveryCode entity) {
		id = entity.getId();
		userId = entity.getUserId();
		code = entity.getCode();
		createdAt = entity.getCreatedAt();
		expiresAt = entity.getExpiresAt();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}
}
