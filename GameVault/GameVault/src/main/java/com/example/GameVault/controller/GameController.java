
package com.example.GameVault.controller;

import com.example.GameVault.model.Juego;
import com.example.GameVault.model.Usuario;
import com.example.GameVault.service.JuegoService;
import com.example.GameVault.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class GameController {

    @Autowired
    private JuegoService juegoService;
    @Autowired
    private UsuarioService usuarioService;

    //Inicio de sesión
    //NOTA: PENDIENTE
    public String procesarLogin(@RequestParam String nombre,
                                @RequestParam String contrasenia,
                                HttpSession session,
                                Model model) {

        //1. Validar que el usuario exista
        //NOTA: ESTO NO SE DEBERIA HACER, ES POR NOMBRE
        if (usuarioService.obtenerUsuarioPorNombre(nombre) != null) {
            //2. Validar contrasenia
 //           String passOK =
        }



        return "login";
    }

    //REGISTRO DE USUARIO Y PROCESAMIENTO

    //Registro de usuario
    @GetMapping("/juegos/registro/usuario")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }


    //Guardado de usuario
    @PostMapping("/procesado/Usuario")
    public String procesarRegistro(Usuario usuario, Model model) {
        model.addAttribute("usuario", usuario);
        this.usuarioService.guardarUsuario(usuario);
        return "redirect:/juegos";
    }

    //PANTALLAS Y FORMULARIOS

    @GetMapping("/fragments-demo")
    public String fragments() {
        return "fragments-demo";
    }

    @GetMapping({"/", "/juegos"})
    public String juegos(Model model) {
        model.addAttribute("juegos", this.juegoService.listarTodos());
        return "juegos";
    }

    @GetMapping("/juegos/nuevo")
    public String mostrarFormulario() {
        return "formulario";
    }

    @PostMapping("/juegos")
    public String guardarJuego(@RequestParam("titulo") String titulo,
                               @RequestParam("descripcion") String descripcion,
                               @RequestParam("portada") MultipartFile portada) {

        Juego juego = new Juego();
        juego.setTitulo(titulo);
        juego.setDescripcion(descripcion);
        this.juegoService.guardarJuego(juego, portada);

        //Redirigimos a la lista de juegos (Patron PRG - Post/Redirect/Get)
        return "redirect:/juegos";
    }

    //Juegos de usuario
    @GetMapping("/juegos/usuario")
    public String mostrarJuegosUsuario(Model model) {
        model.addAttribute("juegos", this.juegoService.listarTodos());
        Usuario usuario = new Usuario();
        usuario.setNombre(usuario.getNombre());
        model.addAttribute("usuario", usuario);
        return "mis-juegos";
    }

    //EDITAR Y ELIMINAR JUEGOS

    /**
     * Obtiene el juego y redirige al formulario para editarlo
     * @param id Extrae el ID del juego
     * @param model Carga el modelo del juego (su ID)
     * @return Formulario para editar el juego
     */
    @GetMapping("/juegos/editar/{id}")
    public String editarJuego(@PathVariable("id") Long id, Model model) {
        //Primero buscamos el juego
        Juego juego = this.juegoService.obtenerJuegoPorId(id);
        //Se busca el usuario también
        //Usuario usuario = this.usuarioService.obtenerUsuarioPorId();

        //Si el juego existe y el usuario coincide, se muestra en el formulario
        if (juego != null /*&& juego.getUsuario().getId().equals(usuario.getId())*/) {
            model.addAttribute("juego", juego);
            return "formulario";
       }
        return "redirect:/mis-juegos";
    }

    /**
     * Guarda todos los cambos generados en el formulario
     * @param id Obtiene el ID del juego
     * @param titulo Cambia el título del juego
     * @param descripcion Cambia la descripción del juego
     * @param precio Cambia el precio del juego
     * @param categoria Cambia la categoria del juego
     * @param portada Cambia la portada del juego
     * @return Pantalla principal de juegos
     */
    @PostMapping("/juegos/editar/{id}")
    public String actualizarJuego(@PathVariable Long id,
                                  @RequestParam("titulo") String titulo,
                                  @RequestParam("descripcion") String descripcion,
                                  @RequestParam("precio") Double precio,
                                  @RequestParam("categoria") String categoria,
                                  @RequestParam(value = "portada", required = false) MultipartFile portada) {

        Juego juegoExistente = juegoService.obtenerJuegoPorId(id);
        //Usuario logueado = usuarioService.obtenerUsuarioPorId(id);

        // Verificamos propiedad nuevamente antes de modificar
        if (juegoExistente != null /*&& juegoExistente.getUsuario().getId().equals(logueado.getId())*/) {
            juegoExistente.setTitulo(titulo);
            juegoExistente.setDescripcion(descripcion);
            juegoExistente.setPrecio(precio);
            juegoExistente.setCategoria(categoria);

            juegoService.guardarJuego(juegoExistente, portada);
        }

        return "redirect:/juegos/usuario";
    }

    @PostMapping("/juegos/eliminar/{id}")
    public String eliminarJuego(@PathVariable Long id) {
        Juego juego = juegoService.obtenerJuegoPorId(id);
        //Usuario logueado = usuarioService.obtenerUsuarioPorId();

        //Solo procedemos a borrar si el usuario logueado es el dueño
        if (juego != null /*&& juego.getUsuario().getId().equals(logueado.getId())*/) {
            juegoService.eliminarJuego(id);
        }

        return "redirect:/juegos/usuario";
    }


}

