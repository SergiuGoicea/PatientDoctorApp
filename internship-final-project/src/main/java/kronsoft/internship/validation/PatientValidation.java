package kronsoft.internship.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PatientValidator.class)
public @interface PatientValidation {

	String message() default "The patient is invalid!";
	Class<?>[] groups()default{};
	Class<? extends Payload>[] payload() default{};
	
}
