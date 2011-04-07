package com.fauxwerd.web.valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.ScriptAssert;

@ScriptAssert(lang = "javascript", script = "_this.confirmPassword.equals(_this.password)")
public class ResetPasswordForm {
	
	private String password = null;	
	private String confirmPassword = null;

	@NotBlank
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@NotBlank
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
