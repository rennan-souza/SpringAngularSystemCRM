package renan.springcrm.services.exceptions;

public class ActionNotAllowedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ActionNotAllowedException(String msg) {
		super(msg);
	}
}
