package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import java.util.ArrayList;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class AlumnoServicio extends UsuarioServicio {

    @Autowired
    private AlumnoRepositorio alumnoRepositorio;

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

    public List<Alumno> todos() {
        return alumnoRepositorio.findAll();
    }

    // Método para testeos con Postman
    public List<Alumno> todos(AlumnoRepositorio alumnoRepositorio) {
        this.alumnoRepositorio = alumnoRepositorio;
        return todos();
    }

    // Método para testeos con Postman
////    public Alumno registrar(AlumnoRepositorio alumnoRepositorio, MultipartFile archivo, String dni, String nombre,
////            String apellido, String mail, String clave, String clave2, LocalDate fechaNac) throws ErrorServicio {
////        this.alumnoRepositorio = alumnoRepositorio;
////        return registrar(archivo, dni, nombre, apellido, mail, clave, clave2, fechaNac);
////
////    }
//
//    // Método para testeos con Postman
//    public void modificar(AlumnoRepositorio alumnoRepositorio, MultipartFile archivo, String id, String dni, String nombre,
//            String apellido, String mail, String clave, LocalDate fechaNac) throws ErrorServicio {
//        this.alumnoRepositorio = alumnoRepositorio;
//        modificar(id, archivo, dni, nombre, apellido, mail, clave, fechaNac);
//
//    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Alumno alumno = alumnoRepositorio.buscarPorMail(mail);
        if (alumno != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_ALUMNO_REGISTRADO");
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("alumnosession", alumno);

//
//            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
//
//            permisos.add(p1);
//            permisos.add(p2);
//            permisos.add(p3);
            User user = new User(alumno.getMail(), alumno.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }

    public Alumno buscarPorId(String id) throws ErrorServicio {

        Optional<Alumno> respuesta = alumnoRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Alumno alumno = respuesta.get();
            return alumno;
        } else {

            throw new ErrorServicio("No se encontró el alumno solicitado");
        }

    }

}
