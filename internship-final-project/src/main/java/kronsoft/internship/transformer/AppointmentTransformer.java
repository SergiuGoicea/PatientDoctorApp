package kronsoft.internship.transformer;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.AppointmentDto;
import kronsoft.internship.entities.Appointment;
import kronsoft.internship.repository.AppointmentRepository;
import kronsoft.internship.repository.PatientRepository;

@Component
public class AppointmentTransformer extends AbstractTransformer<Appointment, AppointmentDto>{
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private AppointmentRepository appointmentRepository;

	
	@Override
	public AppointmentDto toDto(Appointment entity) {
		AppointmentDto dto=new AppointmentDto();
		
		BeanUtils.copyProperties(entity, dto);
		if (entity.getPatients() != null) {
			dto.setPatientId(entity.getPatients().getId());
		}
		
		if (entity.getAppointmentDate() != null) {
			dto.setAppointmentDate(entity.getAppointmentDate().toString());
		}
		if (entity.getAppointmentHour() != null) {
			dto.setAppointmentHour(entity.getAppointmentHour().toString());
		}
		return dto;
	}

	@Override
	public Appointment toEntity(AppointmentDto dto) {
		Appointment entity = findOrCreateNew(dto.getId());
		BeanUtils.copyProperties(dto, entity);
		if (dto.getPatientId() != null) {
			entity.setPatients(patientRepository.getById(dto.getPatientId()));
		}
		
		if (dto.getAppointmentDate() != null) {
			entity.setAppointmentDate(LocalDate.parse(dto.getAppointmentDate()));
		}
		if (dto.getAppointmentHour() != null) {
			entity.setAppointmentHour(LocalTime.parse(dto.getAppointmentHour()));
		}
		return entity;
	}
	
	

	@Override
	protected Appointment findOrCreateNew(Long id) {
		return id == null ? new Appointment() : appointmentRepository.findById(id).orElseGet(Appointment::new);

	}

}
