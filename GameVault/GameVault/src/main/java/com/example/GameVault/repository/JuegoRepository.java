package com.example.GameVault.repository;

import com.example.GameVault.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//Esta clase se registra como repositorio, es decir, acceso a la base de datos
@Repository
public interface JuegoRepository extends JpaRepository<Juego,Long> {

    //SELECT * FROM juegos WHERE título LIKE "%titulo"
    //public List<Juego> findByTituloContainingIgnoreCase(String titulo);

    //Trabajamos sobre clases - JPQL (Java Persistence Query Language)
    //@Query("SELECT j FROM Juego WHERE LOWER(j.descripcion) LIKE CONTAC('%', :palabra, '%')")
    //public List<Juego> buscarJuegoPor(@Param("palabra") String palabra);

    /**
     * Conecta a la base de datos para buscar un juego por ID
     * @param id Argumento de búsqueda
     * @return Juego
     */
    public Juego obtenerJuegoPorId(long id);

    /**
     * Conecta a la base de datos para eliminar un juego
     * @param id Argumento de búsqueda
     */
    public void eliminarJuego(long id);

}

