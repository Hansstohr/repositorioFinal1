package com.CalificAR.demo.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Materia;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Servicios.AsistenciaServicio;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaRepositorio asistenciaRepo;

    private final AsistenciaServicio asistenciaServicio = new AsistenciaServicio();

    // path - , consumes = MediaType.APPLICATION_JSON_VALUE
    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @PostMapping("/crearAsistencia")
    public String crearAsistencia(HttpSession session, ModelMap modelo, @RequestParam String idAlumno, @RequestParam Boolean estado, Materia materia) {
        Profesor login = (Profesor) session.getAttribute("usuariosession");
        if (login == null) {
            return "redirect:/login";
        }
        try {
            asistenciaServicio.crearAsistencia(idAlumno, estado, materia);
        } catch (ErrorServicio ex) {
            modelo.put("error", ex.getMessage());
            return "asistencia.html";
        }
        modelo.put("descripcion", "Asistencia creada!");
        return "asistencia.html";
    }

    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')" + " || hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/consultarAsistenciaAlumno")
    public String consultarAsistencia(ModelMap modelo, @RequestParam String idAlumno) {

        List<Asistencia> asistencias = asistenciaServicio.consultarAsistencia(idAlumno);

        modelo.put("asistencia", asistencias);
        return "asistencia.html";
    }
}

/*
Para testearlo:
1) Crear alumno. Esto nos va a devolver un json con el Id del alumno
POST: http://localhost:8080/api/alumnos/newAlumno
{
    "dni": "39504711",
    "nombre": "Chino",
    "apellido": "Vega",
    "mail": "chinofirmat@gmail.com",
    "clave": "12345678",
    "clave2": "12345678",
    "fechaNac": "1996-04-10"
}

2) Crear materia. Esto nos va a devolver un json con el Id de la materia


3) Crear asistencia. En el json se deben completar los ID de materia y los ID de alumnos
POST: http://localhost:8080/api/asistencia/crearAsistencia
{
    "materia" {
        "idMateria":"<ID_MATERIA>"
    },
    "asistencias": [
        {
            "estado":"true",
            "alumno": {
                "id":"<ID_ALUMNO_1>"
            }
        },
        {
            "estado":"false",
            "alumno": {
                "id":"<ID_ALUMNO_2>"
            }
        },
        {
            "estado":"true",
            "alumno": {
                "id":"<ID_ALUMNO_3>"
            }
        },
}

4) Obtener asistencias de un usuario. El último valor pasado en el GET es el Id del usuario del cual se quieren saber las asistencias
GET: http://localhost:8080/api/asistencia/consultarAsistenciaAlumno?idAlumno=6d1b476a-5210-4947-b8cc-443328d1859f
 */
