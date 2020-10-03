package kronsoft.internship.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import kronsoft.internship.dto.UserDto;
import kronsoft.internship.service.SecurityUtils;
import kronsoft.internship.service.UserService;
import kronsoft.internship.transformer.UserTransformer;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserTransformer userTransformer;

	@Autowired
	private SecurityUtils securityUtils;

	@GetMapping(value = "/getUsers", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<UserDto> getUsers() {
		return userService.getUsers().stream().map(userTransformer::toDto).collect(Collectors.toList());
	}

	// All
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public UserDto createUser(@Validated @RequestBody UserDto user) {
		return userTransformer.toDto(userService.createUser(userTransformer.toEntity(user)));
	}

	@PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editUser(@Validated @RequestBody UserDto user, @RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermissionForUser(httpHeaders, user.getId());
		userService.saveUser(userTransformer.toEntity(user));
	}

	@DeleteMapping(value = "/delete/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable Long id) {
		userService.deleteUser(id);
	}

}
