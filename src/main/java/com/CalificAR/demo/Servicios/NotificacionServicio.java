package com.CalificAR.demo.Servicios;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class NotificacionServicio {
    @Autowired
    private JavaMailSender mailSender;
    @Async
    public void enviar(String cuerpo , String titulo , String mail) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(mail);
        mensaje.setFrom("noreply@calific-ar.com");
        mensaje.setSubject(titulo);
        mensaje.setText(cuerpo);
        
        mailSender.send(mensaje);
    }
}
