package com.web.service.api.services.usuario;

import com.web.service.api.dto.UsuariosDTO;

public interface UserService {
	
	public boolean insertUser (UsuariosDTO usuario);
	public Object getUserID (String userMail);
	public boolean getUserByID (String userID);
	public String setEncryptPass (String userPass);
	public String [] findByMail (String userMail);
	public Object validateUser (String userName);
}
