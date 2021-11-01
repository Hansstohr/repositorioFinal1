package com.CalificAR.demo.Entidades;

//Clase agregada para poder testear desde Postman (app que permite mandar mensajes web a muestra app)
//@Entity
public class AlumnoExtendido extends Alumno {

	private String clave2;

	private AlumnoExtendido() {
		super();
	}

	public String getClave2() {
		return clave2;
	}

	public void setClave2(String clave2) {
		this.clave2 = clave2;
	}

}
