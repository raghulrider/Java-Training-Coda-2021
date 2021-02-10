package actionerrors;

public class InvalidCustomerEntryAction extends ActionError{
	private String errorMessage;
	public InvalidCustomerEntryAction(String errorMessage) {
		super(errorMessage);
		this.errorMessage=errorMessage;
	}
	@Override
	public String getError() {
		return this.errorMessage;
	}
}
