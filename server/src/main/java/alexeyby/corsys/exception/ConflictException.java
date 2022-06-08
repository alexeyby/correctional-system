package alexeyby.corsys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException
{
	private static final long serialVersionUID = 2890218967473842981L;

	public ConflictException(String message) {
		super(message);
	}
}