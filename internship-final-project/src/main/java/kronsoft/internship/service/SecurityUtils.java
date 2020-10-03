package kronsoft.internship.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import kronsoft.internship.entities.UserAuthority;
import kronsoft.internship.repository.PatientRepository;
import kronsoft.internship.repository.UserRepository;

@Service
public class SecurityUtils {

	@Autowired
	UserRepository userRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	UserService userService;

	public void checkIfHavePermission(HttpHeaders httpHeaders, Long id) {

		String authHeader = httpHeaders.getFirst("authorization").substring("Basic ".length());
		if (StringUtils.isEmpty(authHeader)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		String username = extractUsernameFromToken(authHeader);
		Long patientId = patientRepository.getIdByUserId(userRepository.getIdByUsername(username));

		if (!patientId.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);

		}
	}

	public void checkAuthority(HttpHeaders httpHeaders) {

		String authHeader = httpHeaders.getFirst("authorization").substring("Basic ".length());
		if (StringUtils.isEmpty(authHeader)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
		String username = extractUsernameFromToken(authHeader);
		UserAuthority userAuth = userRepository.checkRegularByUsername(username);
		if (userAuth == UserAuthority.REGULAR) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}
	}

	private String extractUsernameFromToken(String authHeader) {

		byte[] usernameAndPasswordByte = Base64.getDecoder().decode(authHeader);
		String usernameAndPassword = new String(usernameAndPasswordByte);
		String[] parts = usernameAndPassword.split(":");

		return parts[0];
	}

	public void checkIfHavePermissionForUser(HttpHeaders httpHeaders, Long id) {
		String authHeader = httpHeaders.getFirst("authorization").substring("Basic ".length());
		if (StringUtils.isEmpty(authHeader)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		String username = extractUsernameFromToken(authHeader);
		Long userId = userRepository.getIdByUsername(username);

		if (!userId.equals(id)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

	}
}
