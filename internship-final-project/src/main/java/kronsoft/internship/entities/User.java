package kronsoft.internship.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

	private static final long serialVersionUID = 2248746365756281078L;

	@Column(name = "USERNAME", nullable = false, length = 64, unique = true)
	private String username;

	@Column(name = "EMAIL", nullable = false, length = 64)
	@Email
	private String email;

	@Column(name = "PASSWORD", nullable = false, length = 128)
	private String password;

	@Column(name="USER_TYPE",nullable=false)
	@Enumerated(EnumType.STRING)
	private UserAuthority userAuthority;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "PATIENT_ID", nullable = false)
	private Patient patient;
	
	
	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserAuthority getUserAuthority() {
		return userAuthority;
	}

	public void setUserAuthority(UserAuthority userAuthority) {
		this.userAuthority = userAuthority;
	}
	
}
