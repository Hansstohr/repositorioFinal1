package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.LoginRepositorio;
import com.CalificAR.demo.Repositorio.UsuarioRepositorio;
import java.util.Iterator;

@Service
public class AlumnoServicio extends UsuarioServicio {

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

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

    @Transactional
    public void modificar(String Id, MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, LocalDate fechaNacimiento) throws ErrorServicio {
        super.modificar(alumnoRepositorio, Id, archivo, dni, nombre, apellido, mail, clave, fechaNacimiento);
    }

    @Transactional(readOnly = true)
    public List<Alumno> todos() {
        return alumnoRepositorio.findAll();
    }

    @Transactional(readOnly = true)
    public List<Alumno> alumnnosPorMateria(String idMateria) {
        List<Alumno> alumnos = alumnoRepositorio.findAll();

        Iterator<Alumno> it = alumnos.iterator();
        while (it.hasNext()) {
            Alumno alumno = it.next();
            if (alumno.getMaterias().stream().anyMatch(m -> !m.getIdMateria().equals(idMateria))) {
                it.remove();
            }
        }
        return alumnos;
    }

    @Transactional(readOnly = true)
    public Alumno buscarPorId(String id) throws ErrorServicio {

        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Alumno alumno = respuesta.get();
            return alumno;
        } else {

            throw new ErrorServicio("No se encontró el alumno solicitado");
        }

    }
    
    @Transactional(readOnly = true)
    public Optional<Alumno> buscarPorMail(String mail) {
        return super.buscarPorMail(alumnoRepositorio, mail);
    }

}
