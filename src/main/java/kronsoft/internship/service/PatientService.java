package kronsoft.internship.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kronsoft.internship.entities.Patient;
import kronsoft.internship.repository.PatientRepository;

@Service
@Transactional
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	public List<Patient> findPatients() {
		return patientRepository.findAll();
	}

	public Patient savePatient(Patient entity) {
		return patientRepository.save(entity);
	}

	public void deletePatient(Long id) {
		patientRepository.deleteById(id);

	}

	public Patient findPatientByPatientId(Long id) {
		return patientRepository.getById(id);
	}

}
