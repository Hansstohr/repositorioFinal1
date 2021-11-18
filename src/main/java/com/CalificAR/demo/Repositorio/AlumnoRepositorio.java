package com.CalificAR.demo.Repositorio;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.CalificAR.demo.Entidades.Alumno;

@Repository
public interface AlumnoRepositorio extends UsuarioRepositorio<Alumno> {

    @Query("SELECT c FROM Alumno c WHERE c.mail = :mail")
    @Override
    public Alumno buscarPorMail(@Param("mail") String mail);

    @Query("SELECT c FROM Alumno c WHERE c.login.dni = :dni")
    @Override
    public Alumno buscarPorDni(@Param("dni") String dni);

    @Query("SELECT c FROM Alumno c WHERE c.certificado.codigo = :certificado_codigo")
    public Alumno buscarPorCertificado(@Param("certificado_codigo") String certificado_codigo);

    @Query("SELECT id FROM Alumno c WHERE c.login.dni = :dni")
    public String buscarPorDniModificar(@Param("dni") String dni);

}
