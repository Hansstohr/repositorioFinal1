/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Profesor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfesorRepositorio extends UsuarioRepositorio<Profesor>{
    
    @Query("SELECT c FROM Profesor c WHERE c.mail = :mail")
    @Override
    public Profesor buscarPorMail(@Param("mail") String mail);
    
    @Query("SELECT c FROM Profesor c WHERE c.dni = :dni")
    @Override
    public Profesor buscarPorDni(@Param("dni") String dni);
}
