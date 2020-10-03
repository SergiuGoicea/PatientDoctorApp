package kronsoft.internship.transformer;

import kronsoft.internship.dto.BaseDto;
import kronsoft.internship.entities.BaseEntity;

public abstract class AbstractTransformer<T extends BaseEntity, X extends BaseDto> {

	public abstract X toDto(T entity);

	public abstract T toEntity(X dto);

	protected abstract T findOrCreateNew(Long id);
}
