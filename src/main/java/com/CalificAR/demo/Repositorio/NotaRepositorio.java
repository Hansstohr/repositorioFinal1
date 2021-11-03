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
    
    @Query("SELECT c FROM Nota c WHERE c.alumno_id = :alumnoId and c.materia_id_materia = :materiaId")
    public List<Nota> obtenerNotas(@Param("alumno_id") String alumnoId , @Param("materia_id_materia")String materiaId);
    
}
