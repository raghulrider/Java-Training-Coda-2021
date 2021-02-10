package actionerrors;

public class RegisterFailureAction extends ActionError{
	private String errorMesage;
	public RegisterFailureAction(String errorMessage) {
		super(errorMessage);
		this.errorMesage=errorMessage;
	}
	 @Override
	public String getError() {
		return this.errorMesage;
	}

}
