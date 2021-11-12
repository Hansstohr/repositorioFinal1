package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "alumno")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Alumno extends Usuario {

    @OneToOne
    private Certificado certificado;

    public Alumno() {
    }

    Alumno(Login login, String nombre, String apellido, String mail, LocalDate fechaNac, Foto foto, List<Materia> materias) {
        super(login, nombre, apellido, mail, fechaNac, materias, foto);
    }

    public Certificado getCertificado() {
        return certificado;
    }

    public void setCertificado(Certificado certificado) {
        this.certificado = certificado;
    }
}
