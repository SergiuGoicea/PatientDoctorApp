package kronsoft.internship.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.UserDto;
import kronsoft.internship.repository.UserRepository;

@Component
public class UserValidator implements ConstraintValidator<UserValidation, UserDto>{

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean isValid(UserDto value, ConstraintValidatorContext context) {
		Long id=value.getId();
		String username = value.getUsername();
		String email = value.getEmail();
		boolean isValid = !(id==null ? userRepository.existsByUsername(username) : userRepository.countByUsernameNotId(username,id));

		if (!isValid) {
			context.buildConstraintViolationWithTemplate(
					String.format("The username=%s is used.", value.getUsername())).addPropertyNode("username")
					.addConstraintViolation();
			return false;
//			System.out.println("ACESTA E REZULTATUL "+ userRepository.getUsername(value.getId()));
		}
		
		isValid = id == null ? !userRepository.existsByEmail(email)
				: userRepository.countByEmailNotId(email, id) == 0;
		
		if (!isValid) {
			context.buildConstraintViolationWithTemplate(
					String.format("The patient with email=%s does exist.", value.getEmail())).addPropertyNode("email")
					.addConstraintViolation();
			return false;
		}
		return isValid;
	}
	
	
}
