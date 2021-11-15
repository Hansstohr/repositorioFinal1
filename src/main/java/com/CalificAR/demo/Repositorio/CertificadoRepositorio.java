package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Certificado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CertificadoRepositorio extends JpaRepository<Certificado, String> {

}
