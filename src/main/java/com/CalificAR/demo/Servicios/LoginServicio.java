package com.CalificAR.demo.Servicios;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.LoginRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginServicio implements UserDetailsService {

    @Autowired
    private LoginRepositorio loginRepositorio;
    @Autowired
    private ProfesorRepositorio profesorRepositorio;
    @Autowired
    private AlumnoRepositorio alumnoRepositorio;
    @Autowired
    private NotificacionServicio notificacionServicio;
    @Autowired
    private MateriaServicio materiaServicio;

    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Login login = loginRepositorio.buscarPorDni(dni);
        // System.out.println(alumno);
        if (login != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            // Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", login);
            // Chequear si es un alumno o un profesor o admin
            Profesor profesor = profesorRepositorio.buscarPorDni(dni);
            if (profesor != null) {
                session.setAttribute("profesorsession", profesor);
                GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_PROFESOR_REGISTRADO");
                permisos.add(p2);
            } else {
                Alumno alumno = alumnoRepositorio.buscarPorDni(dni);
                if (alumno != null) {
                    session.setAttribute("alumnosession", alumno);
                    GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_ALUMNO_REGISTRADO");
                    permisos.add(p2);
                    List<Materia> materias = materiaServicio.materiasParaInscribirse(alumno.getLogin().getDni());
                    session.setAttribute("materias", materias);
                } else {
                    session.setAttribute("adminsession", login);
                    GrantedAuthority p2 = new SimpleGrantedAuthority("ROLE_ADMIN");
                    permisos.add(p2);
                }
            }
            User user = new User(login.getDni(), login.getClave(), permisos);
            return user;
        } else {
            return null;
        }
    }

    public <U extends Usuario> void enviarContraseña(U u) throws ErrorServicio {
        String claveNuevaDefault = generarContraseña();
        Login login = loginRepositorio.buscarPorDni(u.getLogin().getDni());
        try {
            String encriptada = new BCryptPasswordEncoder().encode(claveNuevaDefault);
            login.setClave(encriptada);
            loginRepositorio.save(login);
            String cuerpo = "¡Hola " + u.getNombre() + "!¡¿De nuevo aquí?! Su usuario es: " + login.getDni()
                    + " y su contraseña nueva es: " + claveNuevaDefault + ".";
            notificacionServicio.enviarContraseniaOlvidada(cuerpo, "Se restablecio la contraseña", u.getMail());
        } catch (Exception e) {
            throw new ErrorServicio(
                    "Ocurrio un error al reestablecer la contraseña. Intente de nuevamente." + e.getMessage());
        }
    }

    private String generarContraseña() {
        int num = (int) (Math.random() * 1000000);
        return String.valueOf(num);
    }

    @Transactional(readOnly = true)
    public Login buscarPordDni(String dni) {
        return loginRepositorio.buscarPorDni(dni);
    }
}
