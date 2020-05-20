package com.web.service.api.services.usuario;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.service.api.IApiWebServices;
import com.web.service.api.dto.UsuariosDAO;
import com.web.service.api.utils.SecurityPassword;

@Service
public class UserServiceImp implements UserService{
	
	private static final Logger LOG = LoggerFactory.getLogger(UserServiceImp.class);
	
	@Autowired
	private IApiWebServices crudService;
	private SecurityPassword securityPassword = new SecurityPassword();
	
	@Override
	public boolean insertUser(UsuariosDAO usuario) {
		LOG.debug("*** Init Internal Services 'InserUser' ***");
		
			Object insertResult = crudService.save(usuario);
			
		LOG.debug("*** End Internal Services 'InserUser' ***");
		return insertResult != null ? true: false;
	}

	@Override
	public Object getUserID(String userMail) {
		LOG.debug("*** Init Internal Services 'getUserID' ***");
		
			Object idProxy = crudService.getIdByUserMail(userMail);
			
		LOG.debug("*** End Internal Services 'getUserID' ***");	
		return idProxy;
		
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
	public UsuariosDAO getUserByMail(String userMail) {
		UsuariosDAO userData;
		String [] getUserData = crudService.findByMail(userMail);
		
		if(getUserData.length != 0) {
			userData = new UsuariosDAO(
					Long.parseLong(getUserData[0].split(",")[0]),
						getUserData[0].split(",")[1],
							getUserData[0].split(",")[2],
								getUserData[0].split(",")[3],
									getUserData[0].split(",")[4],
									 	Boolean.parseBoolean(getUserData[0].split(",")[5]));
			return userData;
		}else {
			return null;
		}
	}

	@Override
	public Object validateUser(String userName) {
		return crudService.getPassByUserName(userName);
	}
	
}
