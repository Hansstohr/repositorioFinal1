package com.CalificAR.demo.Servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;

@Service
public class NotificacionServicio {
    @Autowired
    private JavaMailSender mailSender;
    
    @Async
    public void enviar(String cuerpo , String titulo , String mail) {
        System.out.println("Enviando Email....");
        System.out.println(mail);
        System.out.println(cuerpo);
        System.out.println(titulo);
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom("no-Reply@calificar.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
        System.out.println("Email enviado");
    }
}
