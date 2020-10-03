package kronsoft.internship.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kronsoft.internship.entities.Appointment;
import kronsoft.internship.repository.AppointmentRepository;

@Service
@Transactional
public class AppointmentService {

	@Autowired
	private AppointmentRepository appointmentRepository;

	public List<Appointment> findAppointment() {
		return appointmentRepository.findAll();
	}

	public Appointment saveAppointment(Appointment entity) {
		return appointmentRepository.save(entity);
	}

	public void deleteAppointment(Long id) {
		appointmentRepository.deleteById(id);
	}

	public List<Appointment> getAppointmentsByPatient(Long patientId) {
		return appointmentRepository.findByPatientId(patientId);
	}

}
