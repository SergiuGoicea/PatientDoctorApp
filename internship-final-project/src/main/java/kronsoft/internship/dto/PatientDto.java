package kronsoft.internship.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import kronsoft.internship.entities.SexType;
import kronsoft.internship.validation.PatientValidation;

@PatientValidation
public class PatientDto extends BaseDto {

	private static final long serialVersionUID = -8380250393452732929L;

	@NotNull
	@Size(min = 0, max = 64)
	private String firstName;

	@NotNull
	@Size(min = 0, max = 64)
	private String lastName;

	@NotNull
	private String birthDate;

	@NotNull
	private String pin;

	@NotNull
	private SexType sexType;

	@NotNull
	@Size(min = 0, max = 58)
	private String city;

	@NotNull
	@Size(min = 0, max = 58)
	private String country;

	@NotNull
	private String phoneNumber;

	@NotNull
	@Email
	@Size(min = 0, max = 64)
	private String email;

	@Positive
	private Long appointmentId;
	
	@NotNull
	@Positive
	private Long userId;
	
	
	public Long getAppointmentId() {
		return appointmentId;
	}

	public void setAppointmentId(Long appointmentId) {
		this.appointmentId = appointmentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public SexType getSexType() {
		return sexType;
	}

	public void setSexType(SexType sexType) {
		this.sexType = sexType;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "PatientDto [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", pin="
				+ pin + ", sexType=" + sexType + ", city=" + city + ", country=" + country + ", phoneNumber="
				+ phoneNumber + ", email=" + email + "]";
	}

}
