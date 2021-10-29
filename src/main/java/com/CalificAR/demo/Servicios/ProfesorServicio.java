/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Servicios;




import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfesorServicio implements UserDetailsService {


    @Autowired
    private ProfesorRepositorio repositorioProfesor;

    @Transactional
    public void registrar(String dni, String nombre, String apellido, String mail, String clave, String clave2, Date fechaNac) throws ErrorServicio {

        validar(nombre, apellido, mail, clave, clave2,fechaNac);

        Profesor profesor = new Profesor();

        profesor.setDni(dni);
        profesor.setNombre(nombre);
        profesor.setApellido(apellido);
        profesor.setMail(mail);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        profesor.setClave(encriptada);
        profesor.setFechaNac(fechaNac);

        repositorioProfesor.save(profesor);
    }

    @Transactional
    public void modificar(String dni, String nombre, String apellido, String mail, String clave, String clave2, Date fechaNac) throws ErrorServicio{
        
        validar(nombre, apellido, mail, clave, clave2,fechaNac);

        Optional<Profesor> respuesta = repositorioProfesor.findById(dni);
        if (respuesta.isPresent()) {
            Profesor profesor = respuesta.get();
            profesor.setNombre(nombre);
            profesor.setApellido(apellido);
            profesor.setMail(mail);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            profesor.setClave(encriptada);
            profesor.setFechaNac(fechaNac);
        }
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

    @Override
    public UserDetails loadUserByUsername(String string) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
