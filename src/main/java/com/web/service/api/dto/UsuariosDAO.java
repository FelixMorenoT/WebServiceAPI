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
public class UsuariosDAO {

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
	
	@Column(name = "userrole", nullable = true)
	@NotBlank
	@NotNull
	private String userRole;
	
	@Column(name = "userstatus", nullable = false)
	@NotNull
	private boolean userStatus;

	public UsuariosDAO() {
		
	}
	
	public UsuariosDAO(long id, @NotBlank @NotNull String userName, @NotBlank @NotNull String userPassword,
			@NotBlank @NotNull String userMail, @NotBlank @NotNull String userRole, @NotNull boolean userStatus) {
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMail = userMail;
		this.userRole = userRole;
		this.userStatus = userStatus;
	}

	public UsuariosDAO(long id, String userName, String userPassword, String userRole ,boolean userStatus) {
		this.id = id;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
		this.userRole = userRole;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuariosDTO [id=");
		builder.append(id);
		builder.append(", userName=");
		builder.append(userName);
		builder.append(", userMail=");
		builder.append(userMail);
		builder.append(", Role=");
		builder.append(userRole);
		builder.append(", userStatus=");
		builder.append(userStatus);
		builder.append("]");
		return builder.toString();
	}
}
