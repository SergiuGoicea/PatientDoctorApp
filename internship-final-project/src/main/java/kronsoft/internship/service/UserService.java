package kronsoft.internship.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kronsoft.internship.entities.User;
import kronsoft.internship.entities.UserAuthority;
import kronsoft.internship.repository.UserRepository;

@Service
@Transactional
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("The username does not exists!"));
		return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
				.password(user.getPassword()).authorities(user.getUserAuthority().toString()).build();
	}

	@PostConstruct
	private void createAdmin() {
		if (!userRepository.existsByUsername("admin")) {
			User user = new User();
			user.setUsername("admin");
			user.setEmail("admin@kronsoft.ro");
			user.setPassword(passwordEncoder.encode("admin"));
			user.setUserAuthority(UserAuthority.ADMIN);
			saveUser(user);
		}
	}

	public User saveUser(User entity) {
		return userRepository.save(entity);
	}

	public List<User> getUsers() {
		return userRepository.findAll();
	}

	public User createUser(User entity) {
		entity.setPassword(passwordEncoder.encode(entity.getPassword()));
		entity.setUserAuthority(UserAuthority.REGULAR);
		return saveUser(entity);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);

	}




}
