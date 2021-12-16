package renan.springcrm.services;

import java.time.Instant;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import renan.springcrm.dtos.RoleDTO;
import renan.springcrm.dtos.UserDTO;
import renan.springcrm.dtos.UserResetPasswordDTO;
import renan.springcrm.entities.Role;
import renan.springcrm.entities.User;
import renan.springcrm.entities.UserPasswordRecoveryCode;
import renan.springcrm.repositories.RoleRepository;
import renan.springcrm.repositories.UserPasswordRecoveryCodeRepository;
import renan.springcrm.repositories.UserRepository;
import renan.springcrm.services.exceptions.ActionNotAllowedException;
import renan.springcrm.services.exceptions.DatabaseException;
import renan.springcrm.services.exceptions.ResourceBadRequestException;
import renan.springcrm.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserPasswordRecoveryCodeRepository userPasswordRecoveryCodeRepository;

	@Transactional(readOnly = true)
	public Page<UserDTO> findAllPaged(Pageable pageable, String search) {
		
		if (search == null) {
			Page<User> list = userRepository.findAll(pageable);
			return list.map(x -> new UserDTO(x));
		}
		
		Page<User> list = userRepository.searchUser(pageable, search);
		return list.map(x -> new UserDTO(x));
	}

	@Transactional(readOnly = false)
	public UserDTO insert(UserDTO dto) {

		User verifyEmail = userRepository.findByEmail(dto.getEmail());

		if (verifyEmail != null) {
			throw new DatabaseException("Email já cadastrado");
		}

		String password = RandomStringUtils.randomAlphanumeric(10);
		String passwordHash = passwordEncoder.encode(password);

		User entity = new User();
		entity.setFirstName(dto.getFirstName());
		entity.setLastName(dto.getLastName());
		entity.setEmail(dto.getEmail());

		entity.setPassword(passwordHash);

		entity.getRoles().clear();
		for (RoleDTO roleDto : dto.getRoles()) {
			Role role = roleRepository.getOne(roleDto.getId());
			entity.getRoles().add(role);
		}

		entity = userRepository.save(entity);

		mailService.sendNewUser(dto.getFirstName(), dto.getLastName(), dto.getEmail(), password);

		return new UserDTO(entity);
	}

	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
		return new UserDTO(entity);
	}

	@Transactional(readOnly = false)
	public UserDTO update(Long id, UserDTO dto) {
		try {

			User user = authService.authenticated();

			if (user.getId().equals(id)) {
				throw new ActionNotAllowedException("Ação não permitida");
			}

			User entity = userRepository.getOne(id);

			User verifyEmail = userRepository.findByEmail(dto.getEmail());

			if (!dto.getEmail().equals(entity.getEmail()) && verifyEmail != null) {
				throw new DatabaseException("Email já possui cadastro");
			}

			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity.setEmail(dto.getEmail());

			entity.getRoles().clear();
			for (RoleDTO roleDto : dto.getRoles()) {
				Role role = roleRepository.getOne(roleDto.getId());
				entity.getRoles().add(role);
			}

			entity = userRepository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		}
	}

	@Transactional(readOnly = false)
	public void delete(Long id) {
		try {
			User user = authService.authenticated();
			if (user.getId().equals(id)) {
				throw new ActionNotAllowedException("Ação não permitida");
			}
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id não encontrado");
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
	@Transactional(readOnly = false)
	public String recoveryPassword(String email) {
		
		String response = "Se o email informado estiver correto, em poucos minutos você receberá o código para criar a nova senha";
		
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			return response;
		}
		
		String code = RandomStringUtils.randomNumeric(10);
		
		UserPasswordRecoveryCode userPasswordRecoveryCode = new UserPasswordRecoveryCode();
		userPasswordRecoveryCode.setUserId(user.getId());
		userPasswordRecoveryCode.setCode(code);
		userPasswordRecoveryCodeRepository.save(userPasswordRecoveryCode);
		
		mailService.sendRecoverPassword(user.getFirstName(), user.getLastName(), user.getEmail(), code);
		
		return response;
	}
	
	@Transactional(readOnly = false)
	public void resetPassword(UserResetPasswordDTO dto) {
		UserPasswordRecoveryCode codeExists = userPasswordRecoveryCodeRepository.findByCode(dto.getCode());
		
		if (codeExists == null) {
			throw new ResourceBadRequestException("Código não encontrado");
		}
		
		if (codeExists.getExpiresAt().isBefore(Instant.now())) {
			throw new ResourceBadRequestException("Código expirado");
		}
		
		User entity = userRepository.getOne(codeExists.getUserId());
		entity.setPassword(passwordEncoder.encode(dto.getPassword()));
		entity = userRepository.save(entity);
		
		userPasswordRecoveryCodeRepository.deleteByCode(dto.getCode());
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Email não encontrado");
		}
		return (UserDetails) user;
	}
}
