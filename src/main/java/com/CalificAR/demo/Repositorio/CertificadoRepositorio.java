package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Certificado;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificadoRepositorio extends JpaRepository<Certificado, String> {

    //buscar codigo
    @Query("SELECT c FROM Certificado c WHERE c.codigo = :codigo")
    public Optional<Certificado> buscarCodigo(@Param("codigo") String codigo);
}
