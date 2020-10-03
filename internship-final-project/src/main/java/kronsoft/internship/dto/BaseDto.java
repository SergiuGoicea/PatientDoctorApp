package kronsoft.internship.dto;

import java.io.Serializable;

public abstract class BaseDto implements Serializable {


	private static final long serialVersionUID = 4229706550746564128L;

	protected Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	
}
