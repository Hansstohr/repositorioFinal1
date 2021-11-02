/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;
import java.util.List;

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
}
