package com.web.service.api.dto;

import javax.validation.constraints.NotNull;

public class UsuarioRebootDAO {
	
	@NotNull
	private String userMail;
	
	@NotNull
	private String userPassword;
	
	public UsuarioRebootDAO() {
		
	}
	
	public UsuarioRebootDAO(String userMail, String userPassword) {
		this.userMail = userMail;
		this.userPassword = userPassword;
	}

	public String getUserMail() {
		return userMail;
	}

	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
