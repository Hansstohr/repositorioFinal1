package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Nota;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotaRepositorio extends JpaRepository<Nota, Alumno> {

    @Query("SELECT c FROM Nota c WHERE c.alumno.id = :alumno_id and c.materia.idMateria = :materia_id_materia")
    public List<Nota> obtenerNotasAlumno(@Param("alumno_id") String alumno_id, @Param("materia_id_materia") String materia_id_materia);

    @Query("SELECT c FROM Nota c WHERE c.materia.idMateria = :materia_id_materia")
    public List<Nota> obtenerNotas(@Param("materia_id_materia") String materia_id_materia);

}
