package com.web.service.api.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "Usuario")
public class UsuariosDTO {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@Column(name = "username", nullable = false)
	@NotBlank
	@NotNull
	private String userName;
	
	@Column(name = "userpassword", nullable = false)
	@NotBlank
	@NotNull
	private String userPassword;
	
	@Column(name = "usermail", nullable = false)
	@NotBlank
	@NotNull
	private String userMail;
	
	@Column(name = "userstatus", nullable = false)
	@NotNull
	private boolean userStatus;


	public UsuariosDTO(long id, @NotBlank @NotNull String userName, @NotBlank @NotNull String userPassword,
			@NotBlank @NotNull String userMail, @NotBlank @NotNull boolean userStatus) {

		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userStatus = userStatus;
	}

	public UsuariosDTO() {
		
	}
	
	public UsuariosDTO(long id, String userName, String userPassword, boolean userStatus) {
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public boolean isUserStatus() {
		return userStatus;
	}

	public void setUserStatus(boolean userStatus) {
		this.userStatus = userStatus;
	}
}
