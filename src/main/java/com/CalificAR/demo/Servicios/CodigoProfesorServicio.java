package com.CalificAR.demo.Servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CalificAR.demo.Entidades.CodigoProfesor;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.CodigoProfesorRepositorio;

@Service
public class CodigoProfesorServicio {

	@Autowired
	private CodigoProfesorRepositorio codigoProfesorRepositorio;

	public void validarProfesor(String codigo, boolean guardar) throws ErrorServicio {
		CodigoProfesor codigoProfesor = codigoProfesorRepositorio.buscarCodigo(codigo);
		if (codigoProfesor == null) {
			throw new ErrorServicio("Código incorrecto");
		} else {
			if (codigoProfesor.getAlta()) {
				throw new ErrorServicio("El código ya fue utilizado. Solicite otro código a la institución");
			}
		}
		if (guardar) {
			codigoProfesor.setAlta(Boolean.TRUE);
			codigoProfesorRepositorio.save(codigoProfesor);
		}
	}
}
