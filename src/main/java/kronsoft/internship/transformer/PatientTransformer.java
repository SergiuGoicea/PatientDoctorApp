package kronsoft.internship.transformer;

import java.time.LocalDate;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.PatientDto;
import kronsoft.internship.entities.Patient;
import kronsoft.internship.repository.PatientRepository;
import kronsoft.internship.repository.UserRepository;

@Component
public class PatientTransformer extends AbstractTransformer<Patient, PatientDto> {

	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public PatientDto toDto(Patient entity) {
		PatientDto dto = new PatientDto();
		BeanUtils.copyProperties(entity, dto);

		if (entity.getBirthDate() != null) {
			dto.setBirthDate(entity.getBirthDate().toString());
		}
		dto.setUserId(entity.getUser().getId());
		return dto;
	}

	@Override
	public Patient toEntity(PatientDto dto) {
		Patient entity = findOrCreateNew(dto.getId());
		BeanUtils.copyProperties(dto, entity);

		if (dto.getBirthDate() != null) {
			entity.setBirthDate(LocalDate.parse(dto.getBirthDate()));
		}
		entity.setUser(userRepository.getById(dto.getUserId()));
		return entity;
	}

	@Override
	protected Patient findOrCreateNew(Long id) {
		return id == null ? new Patient() : patientRepository.findById(id).orElseGet(Patient::new);

	}

}
