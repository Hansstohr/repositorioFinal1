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
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.LoginRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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
    
    @Override
    public UserDetails loadUserByUsername(String dni) throws UsernameNotFoundException {
        Login login = loginRepositorio.buscarPorDni(dni);
        //System.out.println(alumno);
        if (login != null) {

            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_USUARIO_REGISTRADO");
            permisos.add(p1);
            //System.out.println("Llegó hasta acá");
            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("usuariosession", login);

            // Chequear si es un alumno o un profesor
            Profesor profesor = profesorRepositorio.buscarPorDni(dni);
            if (profesor != null) {
                session.setAttribute("profesorsession", profesor);
            } else {
                Alumno alumno = alumnoRepositorio.buscarPorDni(dni);
                if (alumno != null) {
                    session.setAttribute("alumnosession", alumno);
                } else {
                    // Esto no debería pasar
                    return null;
                }
            }
            User user = new User(login.getDni(), login.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }

    public void enviarContraseñaAlumno(Alumno alumno)throws ErrorServicio {
        String claveNuevaDefault = generarContraseña();
        Login login = loginRepositorio.buscarPorDni(alumno.getLogin().getDni());
        try {
            String encriptada = new BCryptPasswordEncoder().encode(claveNuevaDefault);
            login.setClave(encriptada);
            loginRepositorio.save(login);
            String cuerpo = "¡Hola "+ alumno.getNombre()+"!¡¿De nuevo aquí?! Su contraseña nueva es: "+ claveNuevaDefault+".";
            System.out.println(alumno.getMail());
            notificacionServicio.enviar(cuerpo, "Se restablecio la contraseña", alumno.getMail());
        } catch (Exception e) {
            throw new ErrorServicio("Ocurrio un error al reestablecer la contraseña. Intente de nuevamente."+e.getMessage());
        }
        
    }
    public void enviarContraseñaProfesor(Profesor profesor) throws ErrorServicio {
        String claveNuevaDefault = generarContraseña();
        Login login = loginRepositorio.buscarPorDni(profesor.getLogin().getDni());
        try {
            System.out.println("Lllego hasta aca 1!");
            String encriptada = new BCryptPasswordEncoder().encode(claveNuevaDefault);
            login.setClave(encriptada);
            loginRepositorio.save(login);
            String cuerpo = "¡Hola "+ profesor.getNombre()+"!¡¿De nuevo aquí?! Su contraseña nueva es: "+ claveNuevaDefault+".";
            notificacionServicio.enviar(cuerpo, "Se restablecio la contraseña", profesor.getMail());
        } catch (Exception e) {
            throw new ErrorServicio("Ocurrio un error al reestablecer la contraseña. Intente de nuevamente." + e.getMessage());
        }
    }
     public String generarContraseña(){
         int num = (int)(Math. random()*1000000);
         System.out.println(num);
        return String.valueOf(num);
     }
    

    }

