package com.CalificAR.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.CalificAR.demo.Entidades.Materia;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia, String> {

    //buscar materia por nombre
    @Query("SELECT c FROM Materia c WHERE c.nombre = :nombre")
    public Materia buscarPorNombre(@Param("nombre") String nombre);

//    //BUSCAR MATERIAS POR ALUMNO
//     @Query("SELECT c FROM Materia c WHERE c.alumno_materias.alumno_id = :alumno_id")
//     public List<Materia> buscarMateriasporAlumno(@Param("alumno_id") String alumno_id);
    
//    //BUSCAR MATERIAS POR PROFESOR
//       @Query("SELECT c FROM Materia c WHERE c.nombre = :nombre")
//     public List<Materia> buscarMateriasporProfesor(@Param("profesor_id") String profesor_id);
}
