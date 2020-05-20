package com.web.service.api.services.usuario;

import com.web.service.api.dto.UsuariosDAO;

public interface UserService {
	
	public boolean insertUser (UsuariosDAO usuario);
	public Object getUserID (String userMail);
	public boolean getUserByID (String userID);
	public String setEncryptPass (String userPass);
	public UsuariosDAO getUserByMail (String userMail);
	public Object validateUser (String userName);
}
