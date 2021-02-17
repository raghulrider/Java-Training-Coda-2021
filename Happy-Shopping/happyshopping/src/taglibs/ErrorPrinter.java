package taglibs;


import javax.servlet.jsp.tagext.SimpleTagSupport;

import actionerrors.ActionErrors;

public class ErrorPrinter extends SimpleTagSupport{
	private ActionErrors errors;

	public ActionErrors getErrors() {
		return errors;
	}

	public void setErrors(ActionErrors errors) {
		this.errors = errors;
	}
}
