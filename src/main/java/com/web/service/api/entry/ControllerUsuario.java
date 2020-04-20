package com.web.service.api.entry;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.bind.annotation.RestController;

import com.web.service.api.IApiWebServices;
import com.web.service.api.dto.UsuarioLogInDTO;
import com.web.service.api.dto.UsuarioRebootDTO;
import com.web.service.api.dto.UsuariosDTO;
import com.web.service.api.responses.UserReponse;
import com.web.service.api.security.SecurityPassword;

@RestController
public class ControllerUsuario {
	
	@Autowired
	private IApiWebServices iApiWebServices;

	private SecurityPassword securityPassword = new SecurityPassword();
	
	@PostMapping("/insertUser")
	@ResponseBody
	public ResponseEntity<UserReponse> insertUser(@RequestBody @Valid UsuariosDTO usuario) {
		try {
			Object userIdProxy = iApiWebServices.getIdByUserMail(usuario.getUserMail());
			
			if(userIdProxy == null || !iApiWebServices.findById(Long.parseLong(userIdProxy.toString())).isPresent()) {
				usuario.setUserPassword(securityPassword.MD5(usuario.getUserPassword()));
				iApiWebServices.save(usuario);
				return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
			}else {
				return new ResponseEntity<UserReponse>(new UserReponse("ALREADY_REPORTED","208"), HttpStatus.ALREADY_REPORTED);
			}
		 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/updateUser")
	@ResponseBody
	public ResponseEntity<UserReponse> updateUser(final @RequestBody @Valid UsuarioRebootDTO usuario){
		try {
			String [] s = iApiWebServices.findByMail(usuario.getUserMail());
			
			if (s.length > 0 ) {
				if(s[0].split(",")[3].equals(usuario.getUserMail())) {
					UsuariosDTO updateUser= new  UsuariosDTO(Long.parseLong(s[0].split(",")[0]) , s[0].split(",")[1], securityPassword.MD5(usuario.getUserPassword()), s[0].split(",")[3], Boolean.parseBoolean(s[0].split(",")[4]));
					iApiWebServices.save(updateUser);
					return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
				}else {
					return new ResponseEntity<UserReponse>(new UserReponse("NOT_ACCEPTABLE","406"), HttpStatus.NOT_ACCEPTABLE);
				}
			}else {
				return new ResponseEntity<UserReponse>(new UserReponse("NOT_FOUND","404"), HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			System.out.println(e);
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/logIn")
	@ResponseBody
	public ResponseEntity<UserReponse> validateUser(final @RequestBody @Valid UsuarioLogInDTO usuario){
		try {
			Object internalPass = iApiWebServices.getPassByUserName(usuario.getUserName());
			
			if(securityPassword.MD5(usuario.getUserPassword()).equals(internalPass.toString())) {
				return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
			}else {
				return new ResponseEntity<UserReponse>(new UserReponse("CONFLICT","409"), HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
