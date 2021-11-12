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
}
