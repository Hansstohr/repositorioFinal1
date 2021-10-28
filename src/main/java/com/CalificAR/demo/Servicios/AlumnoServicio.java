package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Foto;
import com.CalificAR.demo.Errores.ErrorServicio;
import java.util.Date;
import javax.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AlumnoServicio {

    @Transactional
    public void registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail, String clave, String clave2, Date fechaNacimiento) {

        //validar(nombre, apellido, mail, clave, clave2, zona);
        Alumno alumno = new Alumno();
        alumno.setDni(dni);
        alumno.setNombre(nombre);
        alumno.setApellido(apellido);
        alumno.setMail(mail);
        alumno.setFechaNac(fechaNacimiento);

        String encriptada = new BCryptPasswordEncoder().encode(clave);
        alumno.setClave(encriptada);

        Foto foto = fotoServicio.guardar(archivo);
        alumno.setFoto(foto);

        alumnoRepositorio.save(alumno);

        //notificacionServicio.enviar("Bienvenidos al Tinder de Mascotas", "Tinder de Mascotas", usuario.getMail());
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

}
