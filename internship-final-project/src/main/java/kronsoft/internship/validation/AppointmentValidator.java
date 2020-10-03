package kronsoft.internship.validation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.AppointmentDto;
import kronsoft.internship.repository.AppointmentRepository;

@Component
public class AppointmentValidator implements ConstraintValidator<AppointmentValidation, AppointmentDto> {

	@Autowired
	private AppointmentRepository appointmentRepository;

	@Override
	public boolean isValid(AppointmentDto value, ConstraintValidatorContext context) {
		boolean isValid = true;
		String appointmentDate = value.getAppointmentDate();
		String appointmentHour = value.getAppointmentHour();

		if (appointmentDate != null) {
			try {
				LocalDate.parse(appointmentDate);
			} catch (DateTimeParseException e) {
				context.buildConstraintViolationWithTemplate(
						String.format("The pacient appointmentDate=%s is invalid.", appointmentDate))
						.addPropertyNode("appointmentDate").addConstraintViolation();
				return false;
			}
		}
		LocalDate appDate = LocalDate.parse(appointmentDate);

		if (!appDate.isAfter(LocalDate.now())) {
			context.buildConstraintViolationWithTemplate(String.format("The date is invalid."))
					.addConstraintViolation();
			return false;
		}

		if (appointmentHour != null) {
			try {
				LocalTime.parse(appointmentHour);
			} catch (DateTimeParseException e) {
				context.buildConstraintViolationWithTemplate(
						String.format("The appointmentHour=%s is invalid.", appointmentHour))
						.addPropertyNode("appointmentHour").addConstraintViolation();
				return false;
			}
		}
		LocalTime appHour = LocalTime.parse(appointmentHour);

		if ((appointmentRepository.countByDateAndHour(appDate,appHour)) != 0) {
			context.buildConstraintViolationWithTemplate(String.format("The date is unavailable."))
					.addConstraintViolation();
			return false;
		}

		return isValid;
	}

}
