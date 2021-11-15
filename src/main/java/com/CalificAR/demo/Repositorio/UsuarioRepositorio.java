package com.CalificAR.demo.Repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

// UsuarioRepositorio toma un tipo U que puede ser un Alumno o un Profesor
// Se agrega esta notación para que no intente buscar el repositorio de Usuario ya que no existe
@NoRepositoryBean
public interface UsuarioRepositorio<U> extends JpaRepository<U, String> {

    public U buscarPorMail(String mail);

    public U buscarPorDni(String dni);
}
