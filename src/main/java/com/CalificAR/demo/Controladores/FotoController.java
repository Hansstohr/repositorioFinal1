package com.CalificAR.demo.Controladores;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Foto;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import com.CalificAR.demo.Repositorio.FotoRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import com.CalificAR.demo.Repositorio.UsuarioRepositorio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import com.CalificAR.demo.Servicios.FotoServicio;
import javax.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequestMapping("/foto")
public class FotoController {

    @Autowired
    private ProfesorRepositorio profRepo;

    @Autowired
    private AlumnoRepositorio alumnoRepo;

    // Para testeo con Postman
    @Autowired
    private FotoRepositorio fotoRepo;

    private FotoServicio fotoServicio = new FotoServicio();

    // Para testeo con Postman
    private AlumnoServicio alumnoServicio = new AlumnoServicio();

    @GetMapping("/profesor/{id}")
    public ResponseEntity<byte[]> fotoProfesor(@PathVariable String id) throws ErrorServicio {

        return fotoUsuario(id, profRepo);
    }

    @GetMapping("/alumno/{id}")
    public ResponseEntity<byte[]> fotoAlumno(@PathVariable String id) throws ErrorServicio {

        return fotoUsuario(id, alumnoRepo);
    }

    private <U extends Usuario> ResponseEntity<byte[]> fotoUsuario(String id, UsuarioRepositorio<U> repo) throws ErrorServicio {
        try {
            Optional<U> respuesta = repo.findById(id);
            U usuario = respuesta.get();
            if (usuario.getFoto() == null) {
                throw new ErrorServicio("El usuario no tiene una foto");
            }
            byte[] foto = usuario.getFoto().getArchivo();
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(foto, headers, HttpStatus.OK);
        } catch (ErrorServicio e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //Metodo para testeo 
    @PostMapping("/agregar")
    public String agregarFoto(@RequestPart("dniAlumno") String dniAlumno, @RequestPart("archivo") MultipartFile archivo)
            throws ErrorServicio {
        Optional<Alumno> alumno = alumnoServicio.buscarPordDni(alumnoRepo, dniAlumno);
        Foto fotoAnterior = alumno.get().getFoto();
        String idFoto = null;
        if (fotoAnterior != null) {
            idFoto = fotoAnterior.getIdFoto();
        }
        Foto foto = fotoServicio.guardar(fotoRepo, idFoto, archivo);
        alumno.get().setFoto(foto);
        alumnoRepo.save(alumno.get());
        return "redirect:/foto/alumno/" + alumno.get().getId();
    }
}
