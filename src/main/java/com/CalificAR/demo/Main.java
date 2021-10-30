/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CalificAR.demo;

import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Servicios.AlumnoServicio;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author juanm
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Alumno a = new Alumno();
        AlumnoServicio aServ = new AlumnoServicio();
        String dni = "35703943";
        String nombre = "Juan";
        String apellido = "Oviedo";
        String mail = "jmo";
        String clave = "1234567";
        String clave2 = "1234567";
        Date fechaNacimiento = new GregorianCalendar(1991, Calendar.JUNE, 11).getTime();
        try {
            aServ.registrar(null, dni, nombre, apellido, mail, clave, clave2, fechaNacimiento);
        } catch (ErrorServicio ex) {
            Logger.getLogger(CalificArApplication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
