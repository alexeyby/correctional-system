package alexeyby.corsys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException
{
	private static final long serialVersionUID = -1307010159122432403L;
	
	public NotFoundException(String message) {
		super(message);
	}
}