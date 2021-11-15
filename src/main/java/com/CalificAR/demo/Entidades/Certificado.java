package com.CalificAR.demo.Entidades;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Certificado {

    @Id
    private String codigo = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
    // VER QUE LA LÍNEA ANTERIOR NO TIRE CÓDIGOS REPETIDOS.
    private LocalDate vencimiento;

    public Certificado() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public LocalDate getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(LocalDate vencimiento) {
        this.vencimiento = vencimiento;
    }
}
