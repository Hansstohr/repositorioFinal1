package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Asistencia;
import java.time.LocalDate;
import java.util.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, LocalDate>{
    @Query("SELECT c FROM Asistencia c WHERE c.fecha = :fecha")
    public Asistencia buscarAsistenciaFecha(@Param("fecha") LocalDate fecha);
}
