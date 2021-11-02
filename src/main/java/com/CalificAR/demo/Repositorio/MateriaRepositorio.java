package com.CalificAR.demo.Repositorio;

import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaRepositorio extends JpaRepository<Materia, String> {

    //buscar materia por nombre
    @Query("SELECT c FROM Materia c WHERE c.nombre = :nombre")
    public Materia buscarPorNombre(@Param("nombre") String nombre);
    
    
}
