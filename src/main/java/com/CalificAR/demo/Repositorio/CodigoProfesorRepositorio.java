/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.CodigoProfesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CodigoProfesorRepositorio extends JpaRepository<CodigoProfesor, String> {

    @Query("SELECT c FROM CodigoProfesor c WHERE c.codigo = :codigo")
    public CodigoProfesor buscarCodigo(@Param("codigo") String codigo);

}