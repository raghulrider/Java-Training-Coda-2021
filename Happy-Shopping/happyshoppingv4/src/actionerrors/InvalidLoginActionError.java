package actionerrors;

public class InvalidLoginActionError extends ActionError{
	private String errorMessage;
	public InvalidLoginActionError(String errorMessage) {
		super(errorMessage);
		this.errorMessage=errorMessage;
	}
	@Override
	public String getError() {
		return this.errorMessage;
	}
}
