package com.web.service.api.services.usuario;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.service.api.dto.UsuarioLogInDAO;
import com.web.service.api.dto.UsuarioRebootDAO;
import com.web.service.api.dto.UsuariosDAO;
import com.web.service.api.responses.UserReponse;

@RestController
public class UserAPI {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserAPI.class);
	
	@Autowired
	private UserServiceImp userService;

	@PostMapping("/insertUser")
	@ResponseBody
	public ResponseEntity<UserReponse> insertUser(@RequestBody @Valid UsuariosDAO usuario) {
		LOG.debug("--- Start insetUser Debug ---");
		LOG.debug("Data IN: {}", usuario.toString());
		try {
			Object userIdProxy = userService.getUserID(usuario.getUserMail());
			LOG.debug("Getting User ID: {}", userIdProxy);
			
			if(userIdProxy == null || !userService.getUserByID(userIdProxy.toString())){
				usuario.setUserPassword(userService.setEncryptPass(usuario.getUserPassword()));
				userService.insertUser(usuario);
				LOG.debug("User Created with ID: {}", usuario.getId());
				LOG.debug("--- Finish insetUser Debug ---");
				return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
			}else {
				LOG.debug("User Already Exists With ID: {}" , userIdProxy);
				LOG.debug("--- Finish insetUser Debug ---");
				return new ResponseEntity<UserReponse>(new UserReponse("ALREADY_REPORTED","208"), HttpStatus.ALREADY_REPORTED);
			}
		 
		} catch (Exception e) {
			LOG.debug("insertUser Exception {}", e.getMessage());
			LOG.debug("--- Finish insetUser Debug ---");
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("/updateUser")
	@ResponseBody
	public ResponseEntity<UserReponse> updateUser(final @RequestBody @Valid UsuarioRebootDAO usuario){
		LOG.debug("--- Start updateUser Debug ---");
		try {
			UsuariosDAO updateUser = userService.getUserByMail(usuario.getUserMail());
			if (updateUser != null) {
				if(updateUser.getUserMail().equals(usuario.getUserMail())) {
					updateUser.setUserPassword(userService.setEncryptPass(usuario.getUserPassword()));
					userService.insertUser(updateUser);
					LOG.debug("User Updated");
					LOG.debug("--- Finish updateUser Debug ---");
					return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
				}else {
					LOG.debug("User No Match With the Registered Mail");
					LOG.debug("--- Finish updateUser Debug ---");
					return new ResponseEntity<UserReponse>(new UserReponse("NOT_ACCEPTABLE","406"), HttpStatus.NOT_ACCEPTABLE);
				}
			}else {
				LOG.debug("User No Found");
				LOG.debug("--- Finish updateUser Debug ---");
				return new ResponseEntity<UserReponse>(new UserReponse("NOT_FOUND","404"), HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			LOG.debug("updateUser Exception {}", e.getMessage());
			LOG.debug("--- Finish updateUser Debug ---");
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/logIn")
	@ResponseBody
	public ResponseEntity<UserReponse> validateUser(final @RequestBody @Valid UsuarioLogInDAO usuario){
		LOG.debug("--- Start validateUser Debug ---");
		try {
			Object internalPass = userService.validateUser(usuario.getUserName());
			
			if(userService.setEncryptPass(usuario.getUserPassword()).equals(internalPass.toString())) {
				LOG.debug("Valid User");
				LOG.debug("--- Finish validateUser Debug ---");
				return new ResponseEntity<UserReponse>(new UserReponse("OK","200"), HttpStatus.OK);
			}else {
				LOG.debug("Invalid Password");
				LOG.debug("--- Finish validateUser Debug ---");
				return new ResponseEntity<UserReponse>(new UserReponse("CONFLICT","409"), HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			LOG.debug("validateUser Exception {}", e.getMessage());
			LOG.debug("--- Finish validateUser Debug ---");
			return new ResponseEntity<UserReponse>(new UserReponse("INTERNAL_SERVER_ERROR","500"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
