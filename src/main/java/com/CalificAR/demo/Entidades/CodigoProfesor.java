package com.CalificAR.demo.Entidades;

import java.math.BigInteger;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

/**
 * SE CREA ESTA ENTIDAD PARA LA GENERACIÓN DE CÓDIGOS PARA VALIDAR A LOS
 * PROFESORES
 */
@Entity
public class CodigoProfesor {

    @Id
    private String codigo;

    private Boolean alta;

    public CodigoProfesor(String codigo, Boolean alta) {
        this.codigo = codigo;
        this.alta = alta;
    }

    
    public CodigoProfesor() {
    }
    

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Boolean getAlta() {
        return alta;
    }

    public void setAlta(Boolean alta) {
        this.alta = alta;
    }

    

}
