package renan.springcrm.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import renan.springcrm.dtos.UserDTO;
import renan.springcrm.dtos.UserProfileUpdateDTO;
import renan.springcrm.dtos.UserProfileUpdatePasswordDTO;
import renan.springcrm.services.ProfileService;

@RestController
@RequestMapping(value = "/profile")
public class ProfileController {

	@Autowired
	private ProfileService profileService;

	@GetMapping
	public ResponseEntity<UserDTO> profile() {
		UserDTO dto = profileService.profile();
		return ResponseEntity.ok().body(dto);
	}

	@PutMapping
	public ResponseEntity<UserDTO> updateProfile(@Valid @RequestBody UserProfileUpdateDTO dto) {
		UserDTO newDto = profileService.updateProfile(dto);
		return ResponseEntity.ok().body(newDto);
	}

	@PutMapping(value = "/change-password")
	public ResponseEntity<Void> updatePassword(@Valid @RequestBody UserProfileUpdatePasswordDTO dto) {
		profileService.updatePassword(dto);
		return ResponseEntity.noContent().build();
	}
}
