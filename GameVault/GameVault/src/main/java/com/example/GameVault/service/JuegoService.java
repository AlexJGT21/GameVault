package com.example.GameVault.service;

import com.example.GameVault.model.Juego;
import com.example.GameVault.repository.JuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class JuegoService {

    @Autowired
    private JuegoRepository juegoRepository;

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";

    public List<Juego> listarTodos() {
        return juegoRepository.findAll();
    }

    public void guardarJuego(Juego juego, MultipartFile portada) {
        String nombreArchivo = "default.png"; // Imagen por defecto si no suben nada

        // Verificamos si el archivo no está vacío
        if (!portada.isEmpty()) {
            nombreArchivo = portada.getOriginalFilename();
        }


    }

    public String guardarImagenEnProyecto(MultipartFile portada) {
        try {
            // Tip para clase: Crear la carpeta si no existe
            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }


            // Generamos un nombre único para evitar sobreescribir archivos con el mismo nombre
            String nombreArchivo = UUID.randomUUID().toString() + "_" + portada.getOriginalFilename();
            Path filePath = uploadPath.resolve(nombreArchivo);


            // Guardamos el archivo físicamente en disco
            Files.copy(portada.getInputStream(), filePath);

            System.out.println("Archivo guardado en: " + filePath.toAbsolutePath());

            return nombreArchivo;
        } catch (IOException e) {
            e.printStackTrace();
            // En un proyecto real, manejaríamos esta excepción apropiadamente (ej. redirigir con error)
        }
        return "default.png";

    }

}