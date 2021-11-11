package com.CalificAR.demo.Repositorio;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CalificAR.demo.Entidades.Asistencia;

@Repository
public interface AsistenciaRepositorio extends JpaRepository<Asistencia, LocalDate>{
    @Query("SELECT c FROM Asistencia c WHERE c.fecha = :fecha")
    public Asistencia buscarAsistenciaFecha(@Param("fecha") LocalDate fecha);

    @Query("SELECT c FROM Asistencia c WHERE c.alumno.id = :alumno_id AND c.materia.idMateria = :materia_id")
	public List<Asistencia> buscarAsistenciaPorAlumnoYMateria(@Param("alumno_id") String alumno_id , @Param("materia_id") String materia_id);

    @Query("SELECT c FROM Asistencia c WHERE c.alumno.id = :alumno_id")
	public List<Asistencia> buscarAsistenciaPorAlumno(@Param("alumno_id") String alumno_id);
}