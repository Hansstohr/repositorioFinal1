package com.CalificAR.demo.Repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.CalificAR.demo.Entidades.Profesor;

@Repository
public interface ProfesorRepositorio extends UsuarioRepositorio<Profesor> {

    @Query("SELECT c FROM Profesor c WHERE c.mail = :mail")
    @Override
    public Profesor buscarPorMail(@Param("mail") String mail);

    @Query("SELECT c FROM Profesor c WHERE c.login.dni = :dni")
    @Override
    public Profesor buscarPorDni(@Param("dni") String dni);

    @Query("SELECT id FROM Profesor c WHERE c.login.dni = :dni")
    public String buscarPorDniModificar(@Param("dni") String dni);

}
