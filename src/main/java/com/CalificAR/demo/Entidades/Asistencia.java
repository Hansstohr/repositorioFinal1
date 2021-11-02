package com.CalificAR.demo.Entidades;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Asistencia {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String idAsistencia;

    @Temporal(TemporalType.DATE)
    private Date fecha;
    private Boolean estado;

    @OneToOne(cascade=CascadeType.ALL)
    private Materia materia;

    public Asistencia(Date fecha, Boolean estado, Materia materia) {
        this.fecha = fecha;
        this.estado = estado;
        this.materia = materia;
    }

    public Asistencia() {
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    @Override
    public String toString() {
        return "Asistencia{" + "fecha=" + fecha + ", estado=" + estado + ", materia=" + materia + '}';
    }

}
