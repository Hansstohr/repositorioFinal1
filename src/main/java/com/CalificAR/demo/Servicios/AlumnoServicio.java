package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Foto;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.AlumnoRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlumnoServicio {
    
    @Autowired
    private AlumnoRepositorio alumnoRepositorio; 
    @Transactional
    public void registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail, String clave, String clave2, Date fechaNacimiento) throws ErrorServicio {

        validar(nombre,apellido,mail,clave,clave2,fechaNacimiento);
        Alumno alumno = new Alumno();
        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setMail(mail);
        alumno.setFechaNac(fechaNacimiento);

        String encriptada = new BCryptPasswordEncoder().encode(clave);
        alumno.setClave(encriptada);

        //Foto foto = fotoServicio.guardar(archivo);
        //alumno.setFoto(foto);

        alumnoRepositorio.save(alumno);

        //notificacionServicio.enviar("Bienvenidos a Calific-AR", " ", usuario.getMail());
    }

    public void validar(String nombre, String apellido, String mail, String clave, String clave2, Date fechaNacimiento) throws ErrorServicio {

        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }

        if (mail == null || mail.isEmpty()) {
            throw new ErrorServicio("El mail no puede ser nulo");
        }

        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de seis digitos");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }

        if (fechaNacimiento == null || (fechaNacimiento.getYear()<2003)) {
            throw new ErrorServicio("Ingrese una fecha válida");
        }
        
    }
    @Transactional
    public void modificar(String Id, MultipartFile archivo, String dni, String nombre, String apellido, String mail, String clave, Date fechaNacimiento) throws ErrorServicio {

        validar(nombre,apellido,mail,clave,clave,fechaNacimiento);
        
     Optional<Alumno> respuesta = alumnoRepositorio.findById(Id);
         if (respuesta.isPresent()){
          Alumno auxAlumno = respuesta.get();
          auxAlumno.setApellido(apellido);
          auxAlumno.setNombre(nombre);
          auxAlumno.setMail(mail);
          auxAlumno.setDni(dni);
          auxAlumno.setFechaNac(fechaNacimiento);
           
            String encriptada = new BCryptPasswordEncoder().encode(clave);
          auxAlumno.setClave(encriptada);
            
            String idFoto = null;
            if(auxAlumno.getFoto() != null) {
                idFoto=auxAlumno.getFoto().getIdFoto();
            }
            //Foto foto = fotoServicio.actualizar(idFoto, archivo); HACER SERVICIO FOTO
            //respuesta.setFoto(foto);

            
            alumnoRepositorio.save(auxAlumno);
            
            
            

        } else {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
        
        
    }
}

