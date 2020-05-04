package com.web.service.api.services.usuario;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.service.api.dto.UsuarioLogInDTO;
import com.web.service.api.dto.UsuarioRebootDTO;
import com.web.service.api.dto.UsuariosDTO;
import com.web.service.api.responses.UserReponse;

@RestController
public class UserAPI {
	
	@Autowired
	private UserServiceImp userService;

	@PostMapping("/insertUser")
	@ResponseBody
	public ResponseEntity<UserReponse> insertUser(@RequestBody @Valid UsuariosDTO usuario) {
		try {
			Object userIdProxy = userService.getUserID(usuario.getUserMail());
			
			if(userIdProxy == null || !userService.getUserByID(userIdProxy.toString())){
				usuario.setUserPassword(userService.setEncryptPass(usuario.getUserPassword()));
				userService.insertUser(usuario);
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
			String [] s = userService.findByMail(usuario.getUserMail());
			
			if (s.length > 0 ) {
				if(s[0].split(",")[3].equals(usuario.getUserMail())) {
					UsuariosDTO updateUser= new  UsuariosDTO(Long.parseLong(s[0].split(",")[0]) , s[0].split(",")[1], userService.setEncryptPass(usuario.getUserPassword()), s[0].split(",")[3], Boolean.parseBoolean(s[0].split(",")[4]));
					userService.insertUser(updateUser);
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
			Object internalPass = userService.validateUser(usuario.getUserName());
			
			if(userService.setEncryptPass(usuario.getUserPassword()).equals(internalPass.toString())) {
				return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
			}else {
				return new ResponseEntity<UserReponse>(new UserReponse("CONFLICT","409"), HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
