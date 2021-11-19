package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.LoginRepositorio;
import com.CalificAR.demo.Repositorio.MateriaRepositorio;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.security.access.prepost.PreAuthorize;

@Service
public class AlumnoServicio extends UsuarioServicio {

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    
    @Autowired
    private MateriaRepositorio materiaRepositorio;

    @Autowired
    private LoginRepositorio loginRepositorio;
    
    @Transactional
    public Alumno registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail, String clave, String clave2,
            LocalDate fechaNacimiento) throws ErrorServicio {
        // Valida los datos del usuario y devuelve una instancia de Usuario.
        Usuario usuario = super.registrarUsuario(alumnoRepositorio, archivo, dni, nombre, apellido, mail, clave, clave2, fechaNacimiento);
        Alumno alumno = usuario.crearAlumno();
        return alumnoRepositorio.save(alumno);
    }

//    @Transactional
//    public void modificar(String Id, MultipartFile archivo, String dni, String nombre, String apellido, String mail,
//            String clave, LocalDate fechaNacimiento) throws ErrorServicio {
//        super.modificar(alumnoRepositorio, Id, archivo, dni, nombre, apellido, mail, clave, fechaNacimiento);
//    }

    @Transactional
    public void modificar2(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, LocalDate fechaNacimiento) throws ErrorServicio {
        String id = alumnoRepositorio.buscarPorDniModificar(dni);
        super.modificar(alumnoRepositorio, id, archivo, dni, nombre, apellido, mail, clave, fechaNacimiento);
    }


    @Transactional(readOnly = true)
    public List<Alumno> todos() {
        return alumnoRepositorio.findAll();
    }
//ESTO
    @Transactional(readOnly = true)
    public List<Alumno> alumnosPorMateria(String idMateria) {
        List<Alumno> alumnos = alumnoRepositorio.findAll();
       
        Iterator<Alumno> it = alumnos.iterator();
        while (it.hasNext()) {
            Alumno alumno = it.next();
          
            if (alumno.getMaterias().stream().allMatch(m -> !m.getIdMateria().equals(idMateria))) {
                it.remove();
            }
        }
        return alumnos;
    }

    @Transactional(readOnly = true)
    public Materia buscarMateriasporAlumno(String idAlumno) throws ErrorServicio {

        Optional<Materia> respuesta = materiaRepositorio.findById(idAlumno);
        if (respuesta.isPresent()) {

            Materia materia = respuesta.get();
            return materia;
        } else {
            throw new ErrorServicio("El alumno no está inscripto a ninguna materia este año");
        }

    }

    @Transactional(readOnly = true)
    public Optional<Alumno> buscarPorMail(String mail) {
        return super.buscarPorMail(alumnoRepositorio, mail);
    }

}
