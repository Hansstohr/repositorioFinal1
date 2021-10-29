/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Hans
 */
@Repository
public interface ProfesorRepositorio extends JpaRepository<Profesor, String>{
    
    @Query("SELECT c FROM Profesor c WHERE c.mail = :mail")
    public Alumno buscarPorMail(@Param("mail") String mail);
    
}
