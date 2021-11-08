package com.CalificAR.demo.Entidades;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "profesor")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Profesor extends Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    protected String id;

    public Profesor() {
    }

    public Profesor(String dni, String nombre, String apellido, String mail, String clave, LocalDate fechaNac, Foto foto, List<Materia> materias) {
        super(dni, nombre, apellido, mail, clave, fechaNac, materias, foto);
    }

    

}
