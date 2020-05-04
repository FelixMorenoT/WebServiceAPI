package com.web.service.api.services.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.service.api.IApiWebServices;
import com.web.service.api.dto.UsuariosDTO;
import com.web.service.api.utils.SecurityPassword;

@Service
public class UserServiceImp implements UserService{
	
	@Autowired
	private IApiWebServices crudService;
	private SecurityPassword securityPassword = new SecurityPassword();
	
	@Override
	public boolean insertUser(UsuariosDTO usuario) {
		try {
			crudService.save(usuario);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	@Override
	public Object getUserID(String userMail) {
		Object idProxy = null;
		try {
			idProxy = crudService.getIdByUserMail(userMail);
			return idProxy;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public boolean getUserByID(String userID) {
		try {
			if (crudService.findById(Long.parseLong(userID) ) != null){
				return true;
			}else {
				return false;
			}
				
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public String setEncryptPass(String userPass) {
		return securityPassword.MD5(userPass);
	}

	@Override
	public String[] findByMail(String userMail) {
		return crudService.findByMail(userMail);
	}

	@Override
	public Object validateUser(String userName) {
		return crudService.getPassByUserName(userName);
	}
	
}
