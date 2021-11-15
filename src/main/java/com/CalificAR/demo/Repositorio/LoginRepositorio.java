package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepositorio extends JpaRepository<Login, String> {

    @Query("SELECT c FROM Login c WHERE c.dni = :dni")
    public Login buscarPorDni(@Param("dni") String dni);

}
