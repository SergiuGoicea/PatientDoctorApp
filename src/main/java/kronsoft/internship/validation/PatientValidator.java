package kronsoft.internship.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.PatientDto;
import kronsoft.internship.repository.PatientRepository;


@Component
public class PatientValidator implements ConstraintValidator<PatientValidation, PatientDto> {

	@Autowired
	private PatientRepository patientRepository;

	@Override
	public boolean isValid(PatientDto value, ConstraintValidatorContext context) {

		String phoneNumber = value.getPhoneNumber();
		String pin = value.getPin();
		Long id = value.getId();
		String email = value.getEmail();
		String birthDate = value.getBirthDate();

		// Check Phone number and PIN

		boolean isValid = id == null ? !patientRepository.existsByPhoneNumber(phoneNumber)
				: patientRepository.countByPhoneNumberNotId(phoneNumber, id) == 0;

		if (!isValid) {
			context.buildConstraintViolationWithTemplate(
					String.format("The phoneNumber=%s does not exist.", value.getPhoneNumber()))
					.addPropertyNode("phoneNumber").addConstraintViolation();
			return false;
		}

		isValid = id == null ? !patientRepository.existsByPin(pin) : patientRepository.countByPinNotId(pin, id) == 0;

		if (!isValid) {
			context.buildConstraintViolationWithTemplate(String.format("The pin=%s does not exist.", value.getPin()))
					.addPropertyNode("pin").addConstraintViolation();
			return false;
		}

		isValid = id == null ? !patientRepository.existsByEmail(email)
				: patientRepository.countByEmailNotId(email, id) == 0;

		if (!isValid) {
			context.buildConstraintViolationWithTemplate(
					String.format("The patient with email=%s does exist.", value.getEmail())).addPropertyNode("email")
					.addConstraintViolation();
			return false;
		}

		if (birthDate != null) {
			try {
				LocalDate.parse(birthDate);
			} catch (DateTimeParseException e) {
				context.buildConstraintViolationWithTemplate(
						String.format("The pacient birthDate=%s is invalid.", birthDate)).addPropertyNode("birthDate")
						.addConstraintViolation();
				return false;
			}
		}
		LocalDate appDate = LocalDate.parse(birthDate);
		LocalDate todaysDate = LocalDate.now();

		LocalDate start = LocalDate.of(appDate.getYear(), appDate.getMonth(), appDate.getDayOfMonth());
		LocalDate end = LocalDate.of(todaysDate.getYear(), todaysDate.getMonth(), todaysDate.getDayOfMonth()); // use
																												// for
																												// age-calculation:
																												// LocalDate.now()
		long years = ChronoUnit.YEARS.between(start, end);

		if (years < 14) {
			context.buildConstraintViolationWithTemplate(String.format("The pacient is too young."))
					.addConstraintViolation();
			return false;
		}

		return isValid;
	}

}
