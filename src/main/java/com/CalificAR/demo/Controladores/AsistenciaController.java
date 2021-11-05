package com.CalificAR.demo.Controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CalificAR.demo.Entidades.Asistencia;
import com.CalificAR.demo.Entidades.Asistencias;
import com.CalificAR.demo.Repositorio.AsistenciaRepositorio;
import com.CalificAR.demo.Servicios.AsistenciaServicio;

@RestController
@RequestMapping("/api/asistencia")
public class AsistenciaController {

    @Autowired
    private AsistenciaRepositorio asistenciaRepo;

    private final AsistenciaServicio asistenciaServicio = new AsistenciaServicio();

    // path - , consumes = MediaType.APPLICATION_JSON_VALUE
    @RequestMapping(value = "/crearAsistencia", method = RequestMethod.POST)
    public ResponseEntity<Asistencias> crearAsistencia(@RequestBody Asistencias asistencias) {
        asistencias = asistenciaServicio.crearAsistencia(asistenciaRepo, asistencias);
        return new ResponseEntity(asistencias, HttpStatus.CREATED);
    }

    @GetMapping("/consultarAsistenciaAlumno")
    public List<Asistencia> consultarAsistencia(@RequestParam String idAlumno) {
        List<Asistencia> asistencias = asistenciaServicio.consultarAsistencia(asistenciaRepo, idAlumno);
        return asistencias;
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
