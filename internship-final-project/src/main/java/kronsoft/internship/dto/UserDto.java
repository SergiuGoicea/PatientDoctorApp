package kronsoft.internship.dto;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import kronsoft.internship.validation.UserValidation;

@UserValidation
public class UserDto extends BaseDto{


	private static final long serialVersionUID = -559151222272941121L;

	@NotNull
	@Size(min=4,max=64)
	private String username;
	
	@NotNull
	@Size(min=0,max=64)
	private String email;
	
	@Size(min=4,max=128)
	private String password;
	
	@Positive
	private Long patientId;

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
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
	
	
}
