package com.CalificAR.demo.Controladores;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.NotaRepositorio;
import com.CalificAR.demo.Servicios.NotaServicio;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/notas")
public class NotaController {

    @Autowired
    NotaRepositorio notaRepositorio;
    NotaServicio notaServicio = new NotaServicio();
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/agregarNota")
    public String agregarNota() {
        return "agregarNota.html";
    }
//OJO que hay otro GET linea 46, ALGO MALO VA A PASAR !!!!!
    @PreAuthorize("hasAnyRole('ROLE_ALUMNO_REGISTRADO')" + " || hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @GetMapping("/obtenerNota")
    public String obtenerNota() {
        return "obtenerNota.html";
    }
    
    @PreAuthorize("hasAnyRole('ROLE_PROFESOR_REGISTRADO')")
    @RequestMapping(value = "/agregarNota", method = RequestMethod.POST)
    public Notas agregarNotas(@RequestBody Notas notas) throws ErrorServicio {
        return notaServicio.crearNotas(notas);
    }

//	@RequestMapping(value = "/obtenerTodasLasNotas", method = RequestMethod.GET)
//	public List<Nota> obtenerTodasNotas() {
//		List<Nota> notas = notaServicio.todos();
//		return notas;
//	}
    @RequestMapping(value = "/obtenerNotas", method = RequestMethod.GET)
    public List<Nota> obtenerNotas(@RequestParam String idAlumno, @RequestParam String idMateria) {
        List<Nota> notas = notaServicio.obtenerNotas(idAlumno, idMateria);
        return notas;
    }
}
/*
 * 1) Crear materia POST: http://localhost:8080/api/materias/crearMateria {
 * "nombre":"Fisica" }
 * 
 * 2) Crear alumno POST: http://localhost:8080/api/alumnos/crearAlumno { "dni":
 * "39504712", "nombre": "Chino", "apellido": "Vega", "mail":
 * "chinofirmat@gmail.com", "clave": "12345678", "clave2": "12345678",
 * "fechaNac": "1996-04-10" }
 * 
 * 3) Crear nota POST: http://localhost:8080/notas/agregarNota
{
    "materia":
        {
            "idMateria":"e6a2de25-237a-4970-b233-33edf3235be1"
        },
    "notas": [
        {
            "alumno": {
                "id":"21f52625-0c8a-4edb-9385-b1ddb1c46953"
            },
            "nota": "8.0"
        },
        {
            "alumno": {
                "id":"188bd81b-421d-41a1-a644-0d054ff9ce37"
            },
            "nota": "6.0"
        }
    ]
}
 */
