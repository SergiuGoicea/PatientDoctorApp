package kronsoft.internship.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import kronsoft.internship.dto.PatientDto;
import kronsoft.internship.service.PatientService;
import kronsoft.internship.service.SecurityUtils;
import kronsoft.internship.transformer.PatientTransformer;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private PatientTransformer patientTransformer;

	@Autowired
	private SecurityUtils securityUtils;

	// Only Admin
	@GetMapping(value = "/getPatients", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PatientDto> getPatients() {
		return patientService.findPatients().stream().map(patientTransformer::toDto).collect(Collectors.toList());

	}

	// Only Patient
	@GetMapping(value = "/getByPatientId/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public PatientDto getPatientsByPatientId(@RequestParam Long id, @RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermission(httpHeaders, id);
		return patientTransformer.toDto(patientService.findPatientByPatientId(id));
	}

	// Only Patient
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public PatientDto createPatient(@Validated @RequestBody PatientDto patient,
			@RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermissionForUser(httpHeaders, patient.getUserId());
		return patientTransformer.toDto(patientService.savePatient(patientTransformer.toEntity(patient)));
	}

	// Only Admin
	@PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editPatient(@Validated @RequestBody PatientDto patient) {
		Long oldUserId = patientService.findPatientByPatientId(patient.getId()).getUser().getId();
		Long newUserId = patient.getUserId();

		if (!oldUserId.equals(newUserId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		patientService.savePatient(patientTransformer.toEntity(patient));
	}

	// Only Patient
	@PutMapping(value = "/editByPatient", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editPatientByPatient(@Validated @RequestBody PatientDto patient,
			@RequestHeader HttpHeaders httpHeaders) {
		Long oldUserId = patientService.findPatientByPatientId(patient.getId()).getUser().getId();
		Long newUserId = patient.getUserId();

		if (!oldUserId.equals(newUserId)) {
			throw new ResponseStatusException(HttpStatus.FORBIDDEN);
		}

		securityUtils.checkIfHavePermission(httpHeaders, patient.getId());
		patientService.savePatient(patientTransformer.toEntity(patient));
	}

	// Only Admin
	@DeleteMapping(value = "/delete/{id}/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletePatient(@PathVariable Long id) {
		patientService.deletePatient(id);
	}
}
