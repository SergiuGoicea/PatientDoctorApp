package kronsoft.internship.entities;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.Length;

@Entity
public class Patient extends BaseEntity {

	private static final long serialVersionUID = -5949888684903371306L;

	@Column(name = "FIRST_NAME", nullable = false, length = 64)
	@Length(min = 0, max = 64)
	private String firstName;

	@Column(name = "LAST_NAME", nullable = false, length = 64)
	@Length(min = 0, max = 64)
	private String lastName;

	@Column(name = "BIRTHDATE", nullable = false)
	private LocalDate birthDate;

	@Column(name = "PIN", nullable = false, length = 13)
//    @Pattern(regexp="\b[1-9]\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])(0[1-9]|[1-4]\\d|5[0-2]|99)\\d{4}\\b",message="pin format is invalid")  
	private String pin;

	@Column(name = "SEX_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private SexType sexType;

	@Column(name = "CITY", nullable = false, length = 64)
	@Length(min = 0, max = 64)
	private String city;

	@Column(name = "COUNTRY", nullable = false, length = 64)
	@Length(min = 0, max = 64)
	private String country;

	@Column(name = "PHONENUMBER", nullable = false, length = 15, unique = true)
	private String phoneNumber;

	@Column(name = "EMAIL", nullable = false, length = 64, unique = true)
	@Email
	@Length(min = 0, max = 64)
	private String email;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "patient", fetch = FetchType.EAGER)
//	@JoinColumn(name = "APPOINTMENT_ID", nullable = true)
	private Set<Appointment> appointment = new HashSet<>();

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Set<Appointment> getAppointment() {
		return appointment;
	}

	public void setAppointment(Set<Appointment> appointment) {
		this.appointment = appointment;
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

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
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
		return "Patient [firstName=" + firstName + ", lastName=" + lastName + ", birthDate=" + birthDate + ", pin="
				+ pin + ", sexType=" + sexType + ", city=" + city + ", country=" + country + ", phoneNumber="
				+ phoneNumber + ", email=" + email;
	}

	public Patient(@Length(min = 0, max = 64) String firstName, @Length(min = 0, max = 64) String lastName,
			LocalDate birthDate, @Length(min = 0, max = 13) @Length(min = 0, max = 13) String pin, SexType sexType,
			@Length(min = 0, max = 64) String city, @Length(min = 0, max = 64) String country,
			@Length(min = 0, max = 15) String phoneNumber, @Email @Length(min = 0, max = 64) String email) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.pin = pin;
		this.sexType = sexType;
		this.city = city;
		this.country = country;
		this.phoneNumber = phoneNumber;
		this.email = email;

	}

	public Patient() {

	}

}
