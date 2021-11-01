package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumno")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Alumno extends Usuario {

    @OneToMany
    private List<Asistencia> asistencia;

    @OneToOne
    private Certificado certificado;

    public Alumno(List<Materia> materia, List<Asistencia> asistencia, String dni, String nombre, String apellido,
            String mail, String clave, LocalDate fechaNac, Foto foto) {
        super(dni, nombre, apellido, mail, clave, fechaNac, foto, materia);
        this.asistencia = asistencia;
    }

    public Alumno() {
    }

    public List<Asistencia> getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(List<Asistencia> asistencia) {
        this.asistencia = asistencia;
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

    @Override
    public String toString() {
        return "Alumno [asistencia=" + asistencia + ", certificado=" + certificado + super.toString();
    }

}
