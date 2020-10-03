package kronsoft.internship.transformer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import kronsoft.internship.dto.UserDto;
import kronsoft.internship.entities.User;
import kronsoft.internship.repository.PatientRepository;
import kronsoft.internship.repository.UserRepository;

@Component
public class UserTransformer extends AbstractTransformer<User, UserDto> {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PatientRepository patientRepository;
	
	@Override
	public UserDto toDto(User entity) {
		UserDto dto = new UserDto();
		BeanUtils.copyProperties(entity, dto, "password");
		if(entity.getPatient()!=null) {
			dto.setPatientId(entity.getPatient().getId());
		}
		return dto;
	}

	@Override
	public User toEntity(UserDto dto) {
		User entity = findOrCreateNew(dto.getId());
		BeanUtils.copyProperties(dto, entity, "password");
		if (dto.getPassword() != null) {
			entity.setPassword(dto.getPassword());
		}
		if(dto.getPatientId()!=null) {
			entity.setPatient(patientRepository.getById(dto.getPatientId()));
		}
		return entity;
	}

	@Override
	protected User findOrCreateNew(Long id) {
		return id == null ? new User() : userRepository.findById(id).orElseGet(User::new);

	}

}
