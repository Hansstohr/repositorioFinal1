package com.CalificAR.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.CalificAR.demo.Entidades.Foto;

@Repository
public interface FotoRepositorio extends JpaRepository<Foto, String> {

}
