package kronsoft.internship.entities;

import java.time.LocalDate;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.Length;

@Entity
public class Appointment extends BaseEntity {

	private static final long serialVersionUID = 7861424610865185156L;

	@Column(name = "APPOINTMENT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private AppointmentType appointmentType;

	@Column(name = "APPOINTMENT_STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private AppointmentStatus appointmentStatus;

	@Column(name = "APPOINTMENT_DATE", nullable = false)
	private LocalDate appointmentDate;

	@Column(name = "APPOINTMENT_HOUR", nullable = false)
	private LocalTime appointmentHour;

	@Column(name = "DESCRIPTION", length = 128)
	@Length(min = 0, max = 128)
	private String description;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "PATIENT_ID", nullable = true)
	private Patient patient;

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

	public LocalDate getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDate appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	public LocalTime getAppointmentHour() {
		return appointmentHour;
	}

	public void setAppointmentHour(LocalTime appointmentHour) {
		this.appointmentHour = appointmentHour;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Patient getPatients() {
		return patient;
	}

	public void setPatients(Patient patient) {
		this.patient = patient;
	}

	public Appointment(AppointmentType appointmentType, AppointmentStatus appointmentStatus, LocalDate appointmentDate,
			LocalTime appointmentHour, @Length(min = 0, max = 128) String description) {
		super();
		this.appointmentType = appointmentType;
		this.appointmentStatus = appointmentStatus;
		this.appointmentDate = appointmentDate;
		this.appointmentHour = appointmentHour;
		this.description = description;

	}

	@Override
	public String toString() {
		return "Appointment [appointmentType=" + appointmentType + ", appointmentStatus=" + appointmentStatus
				+ ", appointmentDate=" + appointmentDate + ", appointmentHour=" + appointmentHour + ", description="
				+ description;
	}

	public Appointment() {

	}

}
