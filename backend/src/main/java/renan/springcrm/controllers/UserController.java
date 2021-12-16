package renan.springcrm.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import renan.springcrm.dtos.UserDTO;
import renan.springcrm.dtos.UserResetPasswordDTO;
import renan.springcrm.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<Page<UserDTO>> findAllPaged(Pageable pageable,
			@RequestParam(value = "search", defaultValue = "") String search) {
		Page<UserDTO> list = userService.findAllPaged(pageable, search);
		return ResponseEntity.ok(list);
	}

	@PostMapping
	public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserDTO dto) {
		dto = userService.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
		UserDTO dto = userService.findById(id);
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserDTO dto) {
		UserDTO newDto = userService.update(id, dto);
		return ResponseEntity.ok(newDto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping(value = "/recovery")
	public ResponseEntity<String> recoveryPassword(@RequestBody UserDTO dto) {
		String response = userService.recoveryPassword(dto.getEmail());
		return ResponseEntity.ok(response);
	}
	
	@PostMapping(value = "/reset")
	public ResponseEntity<Void> resetPassword(@RequestBody UserResetPasswordDTO dto) {
		userService.resetPassword(dto);
		return ResponseEntity.noContent().build();
	}
}
