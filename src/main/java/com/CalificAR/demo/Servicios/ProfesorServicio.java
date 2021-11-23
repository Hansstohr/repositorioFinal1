/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo.Servicios;
import java.time.LocalDate;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Profesor;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.ProfesorRepositorio;

@Service
public class ProfesorServicio extends UsuarioServicio {
	@Autowired
	private ProfesorRepositorio profesorRepositorio;

	@Transactional
	public Profesor registrar(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
			String clave, String clave2, LocalDate fechaNacimiento, String codigoValidacionProfesor)
			throws ErrorServicio {
		Usuario usuario = super.registrarUsuario(profesorRepositorio, archivo, dni, nombre, apellido, mail, clave,
				clave2, fechaNacimiento, codigoValidacionProfesor);
		Profesor profesor = usuario.crearProfesor();
		return profesorRepositorio.save(profesor);
	}

	@Transactional
	public Profesor modificar(MultipartFile archivo, String dni, String nombre, String apellido, String mail,
			String claveNueva, LocalDate fechaNacimiento, String claveAnterior) throws ErrorServicio {
		String id = profesorRepositorio.buscarPorDniModificar(dni);
		return super.modificar(profesorRepositorio, id, archivo, dni, nombre, apellido, mail, claveNueva,
				fechaNacimiento, claveAnterior);
	}

	@Transactional(readOnly = true)
	public Optional<Profesor> buscarPorMail(String mail) {
		return super.buscarPorMail(profesorRepositorio, mail);
	}

	@Transactional(readOnly = true)
	public Optional<Profesor> buscarPordDni(String dni) {
		return super.buscarPordDni(profesorRepositorio, dni);
	}

	public ResponseEntity<byte[]> obtenerFoto(String id) throws ErrorServicio {
		return super.obtenerFoto(id, profesorRepositorio);
	}
}
