package com.web.service.api;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.web.service.api.dto.UsuariosDAO;

@Repository
public interface IApiWebServices extends CrudRepository<UsuariosDAO, Long> {

    @Query("SELECT user.id, user.userName, user.userPassword, user.userMail, user.userRole ,user.userStatus FROM UsuariosDTO user where user.userMail = :usermail") 
    String [] findByMail(@Param("usermail") String usermail);
    
    @Query("SELECT userm.id FROM UsuariosDTO userm where userm.userMail = :mail")
    Object getIdByUserMail(@Param("mail") String mail);
    
    @Query("SELECT userm.userPassword FROM UsuariosDTO userm where userm.userName = :uname")
    Object getPassByUserName(@Param("uname") String uname);
}
