package kronsoft.internship.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kronsoft.internship.entities.Appointment;
import kronsoft.internship.entities.AppointmentStatus;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

	Appointment getByAppointmentStatus(AppointmentStatus appointment);

	Set<Appointment> getById(Long appointmentId);

	List<Appointment> findByPatientId(Long patientId);

	@Query(nativeQuery = true, value = "SELECT COUNT(*) FROM appointment WHERE appointment_date = :appointmentDate AND appointment_hour = :appointmentHour")
	long countByDateAndHour(@Param("appointmentDate")LocalDate appDate, @Param("appointmentHour")LocalTime appHour);
	


	
	

}
