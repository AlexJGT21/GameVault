package com.example.GameVault.repository;

import com.example.GameVault.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Conecta a la base de datos para buscar un usuario
     * @param id Argumento de búsqueda
     * @return Usuario logueado
     */
    public Usuario findById(long id);

    /**
     * Conecta a la base de datos para buscar por nombre
     * @param nombre Argumento de búsqueda
     * @return Usuario
     */
    public Usuario findByNombre(String nombre);

    /**
     * Guardar usuario
     * @param usuario the entity to save, must not be {@literal null}.
     * @return Usuario guardado
     */
    public Usuario save(Usuario usuario);

}
