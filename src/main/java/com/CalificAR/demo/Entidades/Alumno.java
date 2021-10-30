package com.CalificAR.demo.Entidades;

import java.util.Date;
import java.util.List;
import javax.persistence.Entity;

import javax.persistence.OneToMany;
import javax.persistence.OneToOne;


@Entity
public class Alumno extends Usuario {
    
    @OneToMany
    private List<Materia> materia;
    
    @OneToMany
    private List<Asistencia> asistencia;
    
    @OneToOne
    private Certificado certificado;
    
        
    public Alumno(List<Materia> materia, List<Asistencia> asistencia, String dni, String nombre, String apellido, String mail, String clave, Date fechaNac, Foto foto) {
        super(dni, nombre, apellido, mail, clave, fechaNac, foto);
        this.materia = materia;
        this.asistencia = asistencia;
    }

    public Alumno() {
    }

    public List<Materia> getMateria() {
        return materia;
    }

    public void setMateria(List<Materia> materia) {
        this.materia = materia;
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Date getFechaNac() {
        return fechaNac;
    }

    public void setFechaNac(Date fechaNac) {
        this.fechaNac = fechaNac;
    }

    public Foto getFoto() {
        return foto;
    }

    public void setFoto(Foto foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Alumno{" + "materia=" + materia + ", asistencia=" + asistencia + ", certificado=" + certificado + '}';
    }

    /**
     * @return the certificado
     */
    public Certificado getCertificado() {
        return certificado;
    }

    /**
     * @param certificado the certificado to set
     */
    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
    
    
}
