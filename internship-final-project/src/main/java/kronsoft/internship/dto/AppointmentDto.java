package kronsoft.internship.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import kronsoft.internship.entities.AppointmentStatus;
import kronsoft.internship.entities.AppointmentType;
import kronsoft.internship.validation.AppointmentValidation;

@AppointmentValidation
public class AppointmentDto extends BaseDto {

	private static final long serialVersionUID = 633065296748603379L;


	private AppointmentType appointmentType;


	private AppointmentStatus appointmentStatus;

	@NotNull
	private String appointmentDate;

	@NotNull
	private String appointmentHour;

	@NotNull
	@Size(min = 0, max = 128)
	private String description;
	
	@Positive
	@NotNull
	private Long patientId;

	public AppointmentType getAppointmentType() {
		return appointmentType;
	}

	public void setAppointmentType(AppointmentType appointmentType) {
		this.appointmentType = appointmentType;
	}

	public AppointmentStatus getAppointmentStatus() {
		return appointmentStatus;
	}

	public void setAppointmentStatus(AppointmentStatus appointmentStatus) {
		this.appointmentStatus = appointmentStatus;
	}


	public String getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(String appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public String getAppointmentHour() {
		return appointmentHour;
	}

	public void setAppointmentHour(String appointmentHour) {
		this.appointmentHour = appointmentHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		this.patientId = patientId;
	}

	@Override
	public String toString() {
		return "AppointmentDto [appointmentType=" + appointmentType + ", appointmentStatus=" + appointmentStatus
				+ ", appointmentDate=" + appointmentDate + ", appointmentHour=" + appointmentHour + ", description="
				+ description + ", patientId=" + patientId + "]";
	}




	
}
