package com.CalificAR.demo.Servicios;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.CalificAR.demo.Entidades.Alumno;
import com.CalificAR.demo.Entidades.Profesor;

@Service
public class NotificacionServicio {

	@Value("${spring.mail.username}")
	private String mailFrom;
	@Value("${spring.mail.password}")
	private String mailPassword;

	@Async
	public void enviar(String cuerpo, String titulo, String mail) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
			message.setSubject(titulo);
			String msg = cuerpo;
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
//        System.out.println("Enviando Email....");
//        System.out.println(mail);
//        System.out.println(cuerpo);
//        System.out.println(titulo);
//        SimpleMailMessage mensaje = new SimpleMailMessage();
//        mensaje.setTo(mail);
//        mensaje.setFrom("no-Reply@calificar.com");
//        mensaje.setSubject(titulo);
//        mensaje.setText(cuerpo);
//        
//        mailSender.send(mensaje);
//        System.out.println("Email enviado");
//    }
	}

	public void enviarBienvenidaProfe(Profesor profesor, String usuario, String clave) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(profesor.getMail()));
			message.setSubject("Bienvenido a CalificAR");
			String msg = "<center><h2>Bienvenido <b>Profesor/a " + profesor.getNombre() + "</b></h2>"
					+ "<br><br><h3>Recorda que tu usuario es: <b>" + usuario + "</b> y tu contraseña es: <b>" + clave
					+ "</b>.</h3>"
					+ "<br><br><h4>Si necesita cambiar su DNI, solicitar asistencia al soporte técnico -> <b>Suporte@calificar.com.ar</b></h4>"
					+ "<br><br><p>Equipo CalificAR</p></center>";
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarBienvenidaAlumno(Alumno alumno, String dni) {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
		Session session = Session.getInstance(prop, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(alumno.getMail()));
			message.setSubject("Bienvenido a CalificAR");
			String msg = "<center><h2>¡Bienvenido <b>" + alumno.getNombre() + " " + alumno.getApellido() + "!</b></h2>"
					+ "<br><br><h3>Recorda que tu usuario es: <b>" + dni + "</b></h3>"
					+ "<br><br><h4>Si necesita cambiar su DNI, solicitar asistencia al soporte técnico -> <b>Soporte@calificar.com.ar</b></h4>"
					+ "<br><br><p>Equipo CalificAR</p></center>";
			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			message.setContent(multipart);
			Transport.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
