package com.fauxwerd.web.valid;

import javax.validation.constraints.Pattern;

public class ForgotPasswordForm {

	private String email = null;

	@Pattern(regexp="^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$", flags={Pattern.Flag.CASE_INSENSITIVE})
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
		
}
