package renan.springcrm.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import renan.springcrm.dtos.UserDTO;
import renan.springcrm.dtos.UserProfileUpdateDTO;
import renan.springcrm.dtos.UserProfileUpdatePasswordDTO;
import renan.springcrm.entities.User;
import renan.springcrm.repositories.UserRepository;
import renan.springcrm.services.exceptions.ResourceNotFoundException;
import renan.springcrm.services.exceptions.UnauthorizedException;

@Service
public class ProfileService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public UserDTO profile() {
		User user = authService.authenticated();
		Optional<User> obj = userRepository.findById(user.getId());
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}

	@Transactional(readOnly = false)
	public UserDTO updateProfile(UserProfileUpdateDTO dto) {
		try {
			User user = authService.authenticated();

			User entity = userRepository.getOne(user.getId());

			if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
				throw new UnauthorizedException("Senha incorreta");
			}

			entity.setFirstName(dto.getFirstName());
			entity.setLastName(dto.getLastName());
			entity = userRepository.save(entity);
			return new UserDTO(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}

	@Transactional(readOnly = false)
	public void updatePassword(UserProfileUpdatePasswordDTO dto) {
		try {
			User user = authService.authenticated();

			User entity = userRepository.getOne(user.getId());

			if (!passwordEncoder.matches(dto.getPassword(), entity.getPassword())) {
				throw new UnauthorizedException("Senha incorreta");
			}

			entity.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			entity = userRepository.save(entity);
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found");
		}
	}
}
