package com.CalificAR.demo.Servicios;

import com.CalificAR.demo.Entidades.Nota;
import com.CalificAR.demo.Entidades.Notas;
import com.CalificAR.demo.Repositorio.NotaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotaServicio {
    
    @Autowired
    private NotaRepositorio notaRepositorio;
    
    public Notas crearNotas(Notas notas){
        for (Nota nota : notas.getNotas()) {
            notaRepositorio.save(nota);
        }
        
        return notas;
    }
    
    public void consultarNota() {
        
    }
    
//    public void modificarNota() {
//        
//    }
    
    
    //Testeo Postman
     public Notas crearNotas(NotaRepositorio notaRepositorio , Notas notas){
         this.notaRepositorio = notaRepositorio;
         
         return this.crearNotas(notas);
    }
    
}
