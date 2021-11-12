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
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Repositorio.LoginRepositorio;

@Service
public class LoginServicio implements UserDetailsService {

    @Autowired
    private LoginRepositorio loginRepositorio;
    
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
            //System.out.println("Llegó hasta acá2");
//
//            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
//
//            permisos.add(p1);
//            permisos.add(p2);
//            permisos.add(p3);
            User user = new User(login.getDni(), login.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }
    
}
