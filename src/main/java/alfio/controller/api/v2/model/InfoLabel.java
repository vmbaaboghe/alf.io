package alfio.controller.api.v2.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class InfoLabel implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final Integer id;
	private final String label;

}
