package com.CalificAR.demo.Controladores;

import com.CalificAR.demo.Entidades.Alumno;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PortalControlador {

    @GetMapping("/")
    public String index() {
//        if (error != null) {
//
//            model.put("error", "Usuario o clave incorrectos");
//        }
//        if (logout != null) {
//            model.put("logout", "Ha salido correctamente.");
//        }
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
    public String inicio() {
        return "inicio.html";
    }
    
//    
//    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
//    @GetMapping("/inicio")
//    public String inicio(HttpSession session) {
//        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
//        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
//            return "redirect:/index";
//        }
//        return "inicio.html";
//    }
    

    // PANTALLA DE INICIO, CUANDO EL USUARIO SE LOGUEA EXITOSAMENTE.
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @GetMapping("/inicioAlumno")
    public String inicioAlumno(HttpSession session) {
        Alumno loginUsuario = (Alumno) session.getAttribute("alumnosession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect/index";
        }
        return "perfil.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')")
    @GetMapping("/inicioProfesor")
    public String inicioProfesor(HttpSession session) {
        Alumno loginUsuario = (Alumno) session.getAttribute("profesorsession");
        if (loginUsuario == null || !loginUsuario.getId().equals(session.getId())) {
            return "redirect:/index";
        }
        return "perfil.html";
    }

    @GetMapping("/registroProfesor")
    public String registroProfesor(ModelMap modelo) {
        return "validarProfesor.html";
    }

    // RECUADRO DE LOGIN, ESTÁ EN EL INDEX(VER COMO LO PONEMOS EN EL INDEX)
    //@GetMapping("/login")
//    public String login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
//            ModelMap model) {
//        if (error != null) {
//            model.put("error", "Usuario o clave incorrectos");
//        }
//        if (logout != null) {
//            model.put("logout", "Ha salido correctamente de la plataforma.");
//        }
//        return "login.html";
//    }
}
