/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Alumno;
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class ProfesorServicio extends UsuarioServicio {

    @Autowired
    private ProfesorRepositorio profesorRepositorio;

    @Transactional
    public void registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail, String clave,
            String clave2, LocalDate fechaNacimiento) throws ErrorServicio {
        Usuario usuario = super.registrarUsuario(profesorRepositorio, archivo, dni, nombre, apellido, mail, clave, clave2, fechaNacimiento);
        Profesor profesor = usuario.crearProfesor();
        profesorRepositorio.save(profesor);

    }

    @Transactional
    public void modificar(String Id, MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, LocalDate fechaNacimiento) throws ErrorServicio {
        super.modificar(profesorRepositorio, Id, archivo, dni, nombre, apellido, mail, clave, fechaNacimiento);
    }

    public List<Profesor> todos() {
        return profesorRepositorio.findAll();
    }

    // Método para testeos con Postman
    public List<Profesor> todos(ProfesorRepositorio profesorRepositorio) {
        this.profesorRepositorio = profesorRepositorio;
        return todos();
    }

    // Método para testeos con Postman
    public void registrar(ProfesorRepositorio profesorRepositorio, MultipartFile archivo, String dni, String nombre,
            String apellido, String mail, String clave, String clave2, LocalDate fechaNac) throws ErrorServicio {
        this.profesorRepositorio = profesorRepositorio;
        registrar(archivo, dni, nombre, apellido, mail, clave, clave2, fechaNac);

    }

    // Método para testeos con Postman
    public void modificar(ProfesorRepositorio profesorRepositorio, MultipartFile archivo, String id, String dni, String nombre,
            String apellido, String mail, String clave, LocalDate fechaNac) throws ErrorServicio {
        this.profesorRepositorio = profesorRepositorio;
        modificar(id, archivo, dni, nombre, apellido, mail, clave, fechaNac);

    }
    
    public Boolean validarProfesor(String claveingresada) {
        String claveacomparar = "soyprofesor";
        if (claveacomparar.equals(claveingresada)) {
           return true; 
        }else{
            return false;
        }
        
    }
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Profesor profesor = profesorRepositorio.buscarPorMail(mail);
        if (profesor != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();

            GrantedAuthority p1 = new SimpleGrantedAuthority("ROLE_PROFESOR_REGISTRADO");
            permisos.add(p1);

            //Esto me permite guardar el OBJETO USUARIO LOG, para luego ser utilizado
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("profesorsession", profesor);

//
//            GrantedAuthority p1 = new SimpleGrantedAuthority("MODULO_FOTOS");
//            GrantedAuthority p2 = new SimpleGrantedAuthority("MODULO_MASCOTAS");
//            GrantedAuthority p3 = new SimpleGrantedAuthority("MODULO_VOTOS");
//
//            permisos.add(p1);
//            permisos.add(p2);
//            permisos.add(p3);
            User user = new User(profesor.getMail(), profesor.getClave(), permisos);
            return user;

        } else {
            return null;
        }

    }

    public Profesor buscarPorId(String id) throws ErrorServicio {

        Optional<Profesor> respuesta = profesorRepositorio.findById(id);
        if (respuesta.isPresent()) {

            Profesor profesor = respuesta.get();
            return profesor;
        } else {

            throw new ErrorServicio("No se encontró el profesor solicitado");
        }

    }

}
