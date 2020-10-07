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

import kronsoft.internship.dto.AppointmentDto;
import kronsoft.internship.entities.AppointmentStatus;
import kronsoft.internship.service.AppointmentService;
import kronsoft.internship.service.SecurityUtils;
import kronsoft.internship.transformer.AppointmentTransformer;

@RestController
@RequestMapping("/appointment")
public class AppointmentController {

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private AppointmentTransformer appointmentTransformer;

	@Autowired
	private SecurityUtils securityUtils;

	// Only Admin
	@GetMapping(value = "/getAppointments", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AppointmentDto> getAppointments() {
		return appointmentService.findAppointment().stream().map(appointmentTransformer::toDto)
				.collect(Collectors.toList());
	}

	// Only Admin
	@PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public AppointmentDto createAppointment(@Validated @RequestBody AppointmentDto appointment) {
		return appointmentTransformer
				.toDto(appointmentService.saveAppointment(appointmentTransformer.toEntity(appointment)));
	}

	// Only Patient
	@PostMapping(value = "/createByPatient", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	public AppointmentDto createAppointmentByPatient(@Validated @RequestBody AppointmentDto appointment,
			@RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermission(httpHeaders, appointment.getPatientId());
		if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
			securityUtils.checkAuthority(httpHeaders);
		}
		return appointmentTransformer
				.toDto(appointmentService.saveAppointment(appointmentTransformer.toEntity(appointment)));
	}

	// Only Admin
	@PutMapping(value = "/edit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editAppointment(@RequestBody AppointmentDto appointment, @RequestHeader HttpHeaders httpHeaders) {
		appointmentService.saveAppointment(appointmentTransformer.toEntity(appointment));
	}

	// Only Patient
	@PutMapping(value = "/editByPatient", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void editAppointmentByPatient(@RequestBody AppointmentDto appointment,
			@RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermission(httpHeaders, appointment.getPatientId());
		if (appointment.getAppointmentStatus() == AppointmentStatus.CONFIRMED) {
			securityUtils.checkAuthority(httpHeaders);
		}
		appointmentService.saveAppointment(appointmentTransformer.toEntity(appointment));
	}

	// Only Admin
	@DeleteMapping("/delete/{id}/")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteAppointment(@PathVariable Long id) {
		appointmentService.deleteAppointment(id);
	}

	// Only Patient
	@GetMapping(value = "/getAppointmentsbyPatient/{patientId}/", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<AppointmentDto> getAppointmentsByPatient(@RequestParam Long patientId,
			@RequestHeader HttpHeaders httpHeaders) {
		securityUtils.checkIfHavePermission(httpHeaders, patientId);
		return appointmentService.getAppointmentsByPatient(patientId).stream().map(appointmentTransformer::toDto)
				.collect(Collectors.toList());
	}
}
