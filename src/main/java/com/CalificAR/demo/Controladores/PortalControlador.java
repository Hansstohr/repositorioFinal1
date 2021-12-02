package com.CalificAR.demo.Controladores;

import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.LoginServicio;
import com.CalificAR.demo.Servicios.MateriaServicio;
import com.CalificAR.demo.Servicios.ProfesorServicio;

@Controller
public class PortalControlador {

    @Autowired
    AlumnoServicio alumnoServicio;
    @Autowired
    ProfesorServicio profesorServicio;
    @Autowired
    LoginServicio loginServicio;
    @Autowired
    MateriaServicio materiaServicio;
    
    @GetMapping("/")
    public String index() {
        return "index.html";
    }
    
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
            ModelMap model, HttpSession session) {
        // Borrar sesiones y demás cosas agregadas al modelo.
        session.invalidate();
        model.clear();
        if (error != null) {
            model.put("error", "Usuario o clave incorrectos");
        }
        if (logout != null) {
            model.put("logout", "Su sesión se cerró correctamente.");
        }
        return "login.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @GetMapping("validarCertificado")
    public String validarCertificado(HttpSession session) {
        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null) {
            return "redirect:/index";
        }
        return "/certificado/validarCertificado";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        Login loginAdmin = (Login) session.getAttribute("adminsession");
        // Es un alumno
        if (loginAlumno != null) {
            Optional<Alumno> optAlumno = alumnoServicio.buscarPordDni(loginAlumno.getLogin().getDni());
            if (!optAlumno.isPresent()) {
                return "/";
            }
            List<Materia> materias = materiaServicio.materiasParaInscribirse(loginAlumno.getLogin().getDni());
            session.setAttribute("materias", materias);
            session.setAttribute("alumnosession", optAlumno.get());
        }
        // Es un profesor
        if (loginProfesor != null) {
            Optional<Profesor> optProfesor = profesorServicio.buscarPordDni(loginProfesor.getLogin().getDni());
            if (!optProfesor.isPresent()) {
                return "/";
            }
            session.setAttribute("profesorsession", optProfesor.get());
        }
        //Es el ADMIN
        if (loginAdmin != null) {
            Login optLogin = loginServicio.buscarPordDni(loginAdmin.getDni());
            if (optLogin == null) {
                return "/";
            }
            session.setAttribute("adminsession", optLogin);
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
                loginServicio.enviarContraseña(alumno.get());
                return "exitoContraseña.html";
            } else {
                loginServicio.enviarContraseña(profesor.get());
                return "exitoContraseña.html";
            }
        } else {
            model.put("error", "El mail ingresado es inexistente");
        }
        return "recuperarContraseña.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/modificarusuario")
    public String modificar(HttpSession session, ModelMap modelo) {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        if (loginAlumno == null && loginProfesor == null) {
            return "redirect:/index";
        }
        // Es un alumno
        if (loginAlumno != null) {
            Optional<Alumno> alumno = alumnoServicio.buscarPordDni(loginAlumno.getLogin().getDni());
            if (!alumno.isPresent()) {
                return "redirect:/index";
            }
            modelo.addAttribute("alumno", alumno.get());
            return "modificarUsuario";
        } else {
            Optional<Profesor> profesor = profesorServicio.buscarPordDni(loginProfesor.getLogin().getDni());
            if (!profesor.isPresent()) {
                return "redirect:/index";
            }
            modelo.addAttribute("profesor", profesor.get());
            return "modificarUsuario";
        }
    }
    
    @PreAuthorize("hasAnyRole('ROLE_USUARIO_REGISTRADO')")
    @GetMapping("/perfil")
    public String perfil(HttpSession session, ModelMap modelo) throws ErrorServicio {
        Alumno loginAlumno = (Alumno) session.getAttribute("alumnosession");
        Profesor loginProfesor = (Profesor) session.getAttribute("profesorsession");
        if (loginAlumno == null && loginProfesor == null) {
            return "redirect:/index";
        }
        return "perfil";
    }
}
