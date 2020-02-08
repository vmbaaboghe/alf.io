package alfio.controller.api.v2.model;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ImageSize {

	private final String large;
	private final String medium;
	private final String small;

}