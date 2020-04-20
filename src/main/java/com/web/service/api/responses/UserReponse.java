package com.web.service.api.responses;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseBody;

@ResponseBody
public class UserReponse {

	private String status;
	private String codeStatus;
	
	public UserReponse(String status , String codeStatus) {
		super();
		this.status = status;
		this.codeStatus = codeStatus;
	}
	
	public UserReponse(HttpStatus ok, String codeStatus2) {
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCodeStatus() {
		return codeStatus;
	}
	public void setCodeStatus(String codeStatus) {
		this.codeStatus = codeStatus;
	}
}
