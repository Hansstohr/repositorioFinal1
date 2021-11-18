package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.LoginServicio;

import com.CalificAR.demo.Servicios.ProfesorServicio;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class PortalControlador {

    @Autowired
    AlumnoServicio alumnoServicio;
    @Autowired
    ProfesorServicio profesorServicio;
    @Autowired
    LoginServicio loginServicio;

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, ModelMap model) {
        if (error != null) {

            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Ha salido correctamente.");
        }
        return "login.html";
    }

    //ESTO NO VA ACA
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @GetMapping("validarCertificado")
    public String validarCertificado(HttpSession session) {
        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        return "/certificado/validarCertificado";
    }

    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        // Es un alumno
        if (loginAlumno != null) {
            modelo.put("alumno", loginAlumno);
        }
        // Es un profesor
        if (loginProfesor != null) {
            modelo.put("profesor", loginProfesor);
        }

        return "inicio.html";
    }

    @GetMapping("/recuperarContraseña")
    public String recuperarContraseña() {
        return "recuperarContraseña.html";
    }

    @PostMapping("/validarMail")
    public String validarMail(String mail, ModelMap model) throws ErrorServicio {
        Optional<Alumno> alumno = alumnoServicio.buscarPorMail(mail);
        Optional<Profesor> profesor = profesorServicio.buscarPorMail(mail);
        if (alumno.isPresent() || profesor.isPresent()) {

            // Envia Mail
            if (alumno.isPresent()) {
                loginServicio.enviarContraseñaAlumno(alumno.get());

                return "exitoContraseña.html";
            } else {
                loginServicio.enviarContraseñaProfesor(profesor.get());
                return "exitoContraseña.html";
            }

        } else {
            model.put("error", "El mail ingresado es inexistente");
        }
        return "validarContraseña.html";
    }

    //TENDRÍA QUE SER EL MISMO PARA LOS DOS USUARIOS
    //TESTEADO
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificarusuario")
    public String modificar(HttpSession session, ModelMap modelo) {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        boolean alumnoNoLogueado = loginAlumno == null;
        boolean profesorNoLogueado = loginProfesor == null;
        if (alumnoNoLogueado && profesorNoLogueado) {
            return "redirect:/inicio";
        }

        // Es un alumno
        if (!alumnoNoLogueado) {
            modelo.addAttribute("alumno", new Alumno());
            modelo.addAttribute("login", new Login());
            return "modificarUsuario";
        } else {
            modelo.addAttribute("profesor", new Profesor());
            modelo.addAttribute("login", new Login());
            return "modificarUsuario";
        }
        // Es un profesor
//        if (!profesorNoLogueado) {
//            modelo.addAttribute("profesor", new Profesor());
//            modelo.addAttribute("login", new Login()); //POR QUE SE INSTANCIA UN NUEVO LOGIN?
//            return "modificarUsuario";
//        }

    }

    //TENDRÍA QUE SER EL MISMO PARA LOS DOS USUARIOS
    //TESTEADO
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelo) throws ErrorServicio {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        boolean alumnoNoLogueado = loginAlumno == null;
        boolean profesorNoLogueado = loginProfesor == null;
        if (alumnoNoLogueado && profesorNoLogueado) {
            return "redirect:/inicio";
        }

        // Es un alumno
        if (!alumnoNoLogueado) {
            modelo.put("nombre", loginAlumno.getNombre());
            modelo.put("apellido", loginAlumno.getApellido());
            modelo.put("fechaNac", loginAlumno.getFechaNac());
            modelo.put("mail", loginAlumno.getMail());
            modelo.put("dni", loginAlumno.getLogin().getDni());
            return "perfil";
        } else {
            modelo.put("nombre", loginProfesor.getNombre());
            modelo.put("apellido", loginProfesor.getApellido());
            modelo.put("fechaNac", loginProfesor.getFechaNac());
            modelo.put("mail", loginProfesor.getMail());
            modelo.put("dni", loginProfesor.getLogin().getDni());
            return "perfil";
        }

    }

}
