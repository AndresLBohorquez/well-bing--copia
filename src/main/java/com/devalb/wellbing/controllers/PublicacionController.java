package com.devalb.wellbing.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.devalb.wellbing.entities.EstadoPublicacion;
import com.devalb.wellbing.entities.EstadoTarea;
import com.devalb.wellbing.entities.Publicacion;
import com.devalb.wellbing.entities.Tarea;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.ActivacionService;
import com.devalb.wellbing.service.EstadoPublicacionService;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PublicacionController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private ActivacionService activacionService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private EstadoPublicacionService ePublicacionService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/admin/publicaciones")
    public String goToPublicaciones(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaPublicaciones", publicacionService.getAllVisible());
        return "admin/publicaciones";
    }

    @GetMapping("/tesorero/publicaciones")
    public String goToTesoreroPublicaciones(Model model, Authentication auth) {
        if (auth == null) {
            model.addAttribute("usuLog", null);
        } else {
            model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
            model.addAttribute("nombre",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
            model.addAttribute("apellido",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
            model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());

        }
        return "tesorero/publicaciones";
    }

    @GetMapping("/usuario/publicaciones")
    public String goToUsuarioPublicaciones(Model model, Authentication auth) {
        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        var ultimaActivacion = activacionService.getUltimActivacion(usuLog.getId());
        if (ultimaActivacion == null) {
            model.addAttribute("estado", "Inactivo");
        } else if (!validarActivacionUsuario(usuLog)) {
            model.addAttribute("estado", "Inactivo");
        } else {
            model.addAttribute("estado", ultimaActivacion.getEstadoActivacion().getNombre());
        }

        model.addAttribute("listaTareas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        model.addAttribute("listaPublicaciones", publicacionService.getAllByIdUsuario(usuLog.getId()));
        model.addAttribute("publicacion", new Publicacion());

        model.addAttribute("usuLog", usuLog);
        model.addAttribute("nombre",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
        model.addAttribute("apellido",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());

        return "usuario/publicaciones";
    }

    @PostMapping("/usuario/publicaciones")
    public String crearPublicacion(@ModelAttribute("publicacion") Publicacion publicacion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen, Authentication auth,
            RedirectAttributes redirectAttributes) {

        try {
            if (imagen != null && !imagen.isEmpty()) {
                var usuLog = usuarioService.getUsuarioByUsername(auth.getName());

                publicacion.setFecha(LocalDateTime.now());
                publicacion.setEstadoPublicacion(ePublicacionService.getEstadoPublicacionByNombre("Pendiente"));
                publicacion.setUsuario(usuLog);
                publicacion.setVisible(true);
                publicacion.setSoporte("publicacion");

                Tarea tar = tareaService.getTareaById(publicacion.getTarea().getId());
                EstadoTarea et = new EstadoTarea();
                et.setId(3L);
                tar.setEstadoTarea(et);
                tareaService.editTarea(tar);
                publicacionService.addPublicacion(publicacion);

                Path directiorioImagenes = Paths.get("src//main//resources//static/images/publicaciones");
                String rutaAbsoluta = directiorioImagenes.toFile().getAbsolutePath();
                byte[] bytesImg = imagen.getBytes();

                var nombreImg = "publicacion_" + publicacion.getId()
                        + ft.obtenerExtension(imagen.getOriginalFilename());
                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreImg);

                Files.write(rutaCompleta, bytesImg);
                publicacion.setSoporte(nombreImg);
                publicacionService.editPublicacion(publicacion);
                redirectAttributes.addFlashAttribute("messageOK",
                        "Publicación registrada correctamente");
            }

        } catch (IOException e) {
            System.out.println("PublicacionController.postMethodName()" + e.getMessage());
            e.printStackTrace();
        }

        return "redirect:/usuario/publicaciones";
    }

    @RequestMapping("/admin/aprobarPublicacion/{id}")
    public String aprobarPublicacion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            var publicacion = publicacionService.getPublicacionById(id);
            EstadoPublicacion ep = ePublicacionService.getEstadoPublicacionByNombre("Aprobada");
            publicacion.setEstadoPublicacion(ep);
            publicacionService.editPublicacion(publicacion);
            redirectAttributes.addFlashAttribute("messageOK", "Se ha aprobado la publicación correctamente");
        } catch (Exception e) {
            System.out.println("PublicacionController.aprobarPublicacion()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible aprobar la publicación");
        }
        return "redirect:/admin/publicaciones";
    }

    @RequestMapping("/admin/pendientePublicacion/{id}")
    public String pendientePublicacion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            var publicacion = publicacionService.getPublicacionById(id);
            EstadoPublicacion ep = ePublicacionService.getEstadoPublicacionByNombre("Pendiente");
            publicacion.setEstadoPublicacion(ep);
            publicacionService.editPublicacion(publicacion);
            redirectAttributes.addFlashAttribute("messageOK", "La publicación sea modificado a pendiente correctamente");
        } catch (Exception e) {
            System.out.println("PublicacionController.pendientePublicacion()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible modificar la publicación");
        }
        return "redirect:/admin/publicaciones";
    }

    @RequestMapping("/admin/rechazarPublicacion/{id}")
    public String rechazarPublicacion(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            var publicacion = publicacionService.getPublicacionById(id);
            EstadoPublicacion ep = ePublicacionService.getEstadoPublicacionByNombre("Rechazada");
            publicacion.setEstadoPublicacion(ep);
            publicacionService.editPublicacion(publicacion);
            redirectAttributes.addFlashAttribute("messageOK", "La publicación ha sido rechazada correctamente");
        } catch (Exception e) {
            System.out.println("PublicacionController.rechazarPublicacion()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible rechazar la publicación");
        }
        return "redirect:/admin/publicaciones";
    }

    /*
     * ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    public void cargarVistas(Model model, Authentication auth) {
        try {
            model.addAttribute("cantidadUsuarios", usuarioService.getUsuarios().size());
            model.addAttribute("cantidadProductos", productoService.getProductosVisibles().size());
            model.addAttribute("cantidadPublicaciones", publicacionService.getPublicaciones().size());
            model.addAttribute("cantidadPqrs", pqrsService.getPqrs().size());

            model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
            model.addAttribute("nombre",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
            model.addAttribute("apellido",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
            model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());
        } catch (Exception e) {
            System.out.println("AdminController.cargarVistas()" + e.getMessage());
        }
    }

    public boolean validarActivacionUsuario(Usuario user) {
        if (activacionService.getUltimActivacion(user.getId()).getFechaFin().getMonthValue() == LocalDate.now()
                .getMonthValue()) {
            return true;
        }
        return false;
    }
}
