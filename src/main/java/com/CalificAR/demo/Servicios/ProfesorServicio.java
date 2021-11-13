/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Login;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.LoginRepositorio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;

@Service
public class ProfesorServicio extends UsuarioServicio {

    @Autowired
    private ProfesorRepositorio profesorRepositorio;

    @Transactional
    public Profesor registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, String clave2, LocalDate fechaNacimiento) throws ErrorServicio {
        Usuario usuario = super.registrarUsuario(profesorRepositorio, archivo, dni, nombre, apellido, mail, clave,
                clave2, fechaNacimiento);
        Profesor profesor = usuario.crearProfesor();
        return profesorRepositorio.save(profesor);
    }

    @Transactional
    public void modificar(String Id, MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, LocalDate fechaNacimiento) throws ErrorServicio {
        super.modificar(profesorRepositorio, Id, archivo, dni, nombre, apellido, mail, clave, fechaNacimiento);
    }

    public List<Profesor> todos() {
        return profesorRepositorio.findAll();
    }

    public void validarProfesor(String claveingresada) throws ErrorServicio {
        String claveacomparar = "soyprofesor";
        if (!claveacomparar.equals(claveingresada)) {
            throw new ErrorServicio("Clave incorrecta");
        }
    }

    @Transactional(readOnly = true)
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
