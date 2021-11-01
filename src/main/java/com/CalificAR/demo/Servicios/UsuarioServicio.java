package com.CalificAR.demo.Servicios;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartFile;
import com.CalificAR.demo.Entidades.Foto;
import com.CalificAR.demo.Entidades.Usuario;
import com.CalificAR.demo.Errores.ErrorServicio;
import com.CalificAR.demo.Repositorio.UsuarioRepositorio;

// Se centralizaron los servicios de Profesor y Alumno en la clase Usuario Servicio ya que compartían todos los métodos.
public abstract class UsuarioServicio implements UserDetailsService {

    private static final int MIN_EDAD = 18;
    private FotoServicio fotoServicio = new FotoServicio();

    @Transactional
    public <U extends Usuario> Usuario registrarUsuario(UsuarioRepositorio<U> repo, MultipartFile archivo, String dni, String nombre, String apellido, String mail,
            String clave, String clave2, LocalDate fechaNacimiento) throws ErrorServicio {
        validar(repo, dni, nombre, apellido, mail, clave, clave2, fechaNacimiento, null, null);
        Usuario usuario = new Usuario();
        usuario.setDni(dni);
        usuario.setNombre(nombre);
        usuario.setApellido(apellido);
        usuario.setMail(mail);
        usuario.setFechaNac(fechaNacimiento);
        String encriptada = new BCryptPasswordEncoder().encode(clave);
        usuario.setClave(encriptada);
        Foto foto = fotoServicio.guardar(archivo);
        usuario.setFoto(foto);
        // notificacionServicio.enviar("Bienvenidos a Calific-AR", " ",
        // usuario.getMail());
        return usuario;
    }

    private <U extends Usuario> void validarModificacion(String id, UsuarioRepositorio<U> repo, String dni, String nombre, String apellido, String mail, String clave, LocalDate fechaNacimiento) throws ErrorServicio {
        Optional<U> usuario = repo.findById(id);
        if (usuario.isPresent()) {

            String dniAnterior = usuario.get().getDni();
            String mailAnterior = usuario.get().getMail();
            validar(repo, dni, nombre, apellido, mail, clave, clave, fechaNacimiento, dniAnterior, mailAnterior);
        } else {
            throw new ErrorServicio("El Id del usuario a modificar no existe. Esto no deberia ocurrir");
        }

    }

    private <U extends Usuario> void validar(UsuarioRepositorio<U> repo, String dni, String nombre, String apellido, String mail, String clave, String clave2, LocalDate fechaNacimiento, String dniAnterior, String mailAnterior)
            throws ErrorServicio {
        if (nombre == null || nombre.isEmpty()) {
            throw new ErrorServicio("El nombre del usuario no puede ser nulo");
        }

        if (apellido == null || apellido.isEmpty()) {
            throw new ErrorServicio("El apellido del usuario no puede ser nulo");
        }

        if (dni == null || dni.isEmpty()) {
            throw new ErrorServicio("El DNI del usuario no puede ser nulo");
        }
        if (dniAnterior != null) {

            if (!dniAnterior.equals(dni)) {
                // Chequear que el nuevo DNI no esté siendo usado por otro Usuario
                U nuevoDni = repo.buscarPorDni(dni);
                if (nuevoDni != null) {
                    throw new ErrorServicio("El DNI ingresado ya está siendo usado por otro Usuario");
                }
            }
            if (mailAnterior == null || mailAnterior.isEmpty()) {
                throw new ErrorServicio("El mail del usuario no puede ser nulo");
            }
            if (!mailAnterior.equals(mail)) {
                // Chequear que el nuevo DNI no esté siendo usado por otro Usuario
                U nuevoMail = repo.buscarPorMail(mail);
                if (nuevoMail != null) {
                    throw new ErrorServicio("El mail ingresado ya está siendo usado por otro Usuario");
                }
            }

        }

        if (clave == null || clave.isEmpty() || clave.length() <= 6) {
            throw new ErrorServicio("La clave del usuario no puede ser nula y tiene que tener mas de seis digitos");
        }
        if (!clave.equals(clave2)) {
            throw new ErrorServicio("Las claves deben ser iguales");
        }

        // Se quitó el hardcodeo de menor a 2003 ya que la aplicación debe ser dinámica.
        // Cada vez que transcurre un año habría que editar este valor
        // Devuelve el dia actual
        LocalDate hoy = LocalDate.now();
        if (fechaNacimiento == null || (Period.between(fechaNacimiento, hoy).getYears() < MIN_EDAD)) {
            throw new ErrorServicio("Ingrese una fecha válida");
        }

    }

    // Funcion auxiliar que deveulve un Calendar a partir de un Date
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    // U extiende de Usuario. Es decir puede ser un Profesor o un Alumno.
    @Transactional
    public <U extends Usuario> void modificar(UsuarioRepositorio<U> repo, String id, MultipartFile archivo,
            String dni, String nombre, String apellido, String mail, String clave, LocalDate fechaNacimiento)
            throws ErrorServicio {
        validarModificacion(id, repo, dni, nombre, apellido, mail, clave, fechaNacimiento);
        Optional<U> respuesta = repo.findById(id);
        if (respuesta.isPresent()) {
            U usuarioModificado = respuesta.get();
            usuarioModificado.setApellido(apellido);
            usuarioModificado.setNombre(nombre);
            usuarioModificado.setMail(mail);
            usuarioModificado.setDni(dni);
            usuarioModificado.setFechaNac(fechaNacimiento);
            String encriptada = new BCryptPasswordEncoder().encode(clave);
            usuarioModificado.setClave(encriptada);
            String idFoto = null;
            if (usuarioModificado.getFoto() != null) {
                idFoto = usuarioModificado.getFoto().getIdFoto();
                Foto foto = fotoServicio.actualizar(idFoto, archivo);
                usuarioModificado.setFoto(foto);
            }
            repo.save(usuarioModificado);
        } else {
            throw new ErrorServicio("No se encontró el usuario solicitado");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
        // Tools | Templates.
    }

    // Devuelve un Alumno o Profesor filtrando por Dni
    public <U extends Usuario> Optional<U> buscarPordDni(UsuarioRepositorio<U> repo, String dni) {
        U respuesta = repo.buscarPorDni(dni);
        if (respuesta != null) {
            return Optional.of(respuesta);
        } else {
            return Optional.empty();
        }

    }

    // Devuelve un Alumno o Profesor filtrando por Mail
    public <U extends Usuario> Optional<U> buscarPorMail(UsuarioRepositorio<U> repo, String mail) {
        U respuesta = repo.buscarPorMail(mail);
        if (respuesta != null) {
            return Optional.of(respuesta);
        } else {
            return Optional.empty();
        }

    }
}
