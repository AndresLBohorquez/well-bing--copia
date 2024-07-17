package com.devalb.wellbing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devalb.wellbing.service.ActivacionService;
import com.devalb.wellbing.service.EstadoActivacionService;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActivacionController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ActivacionService activacionService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private EstadoActivacionService estadoActivacionService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/admin/activaciones")
    public String goToActivaciones(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaActivaciones", activacionService.getActivaciones());
        return "/admin/activaciones";
    }

    @GetMapping("/tesorero/activaciones")
    public String goToTesoreroActivaciones(Model model, Authentication auth) {
        cargarVistas(model, auth);
        return "/tesorero/activaciones";
    }

    @GetMapping("/usuario/activaciones")
    public String goToUsuarioActivaciones(Model model, Authentication auth) {
        cargarVistas(model, auth);
        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        model.addAttribute("listaActivacionesUsuario", activacionService
                .getActivacionesByIdUsuario(usuLog.getId()));

        return "usuario/activaciones";
    }

    @RequestMapping("/admin/activacion/preactivar/{id}")
    public String preactivar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var activacion = activacionService.getActivacionById(id);
            var ea = estadoActivacionService.getEstadoActivacionById(1L);
            activacion.setEstadoActivacion(ea);
            activacionService.editActivacion(activacion);
            redirectAttributes.addFlashAttribute("messageOK", "Pre activación de usuario aceptada correctamente");
        } catch (Exception e) {
            System.out.println("ActivacionController.postMethodName()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible pre activar al usuario");
        }
        return "redirect:/admin/activaciones";
    }

    @RequestMapping("/admin/activacion/activar/{id}")
    public String activar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var activacion = activacionService.getActivacionById(id);
            var ea = estadoActivacionService.getEstadoActivacionById(2L);
            activacion.setEstadoActivacion(ea);
            activacionService.editActivacion(activacion);
            redirectAttributes.addFlashAttribute("messageOK", "Activación de usuario aceptada correctamente");
        } catch (Exception e) {
            System.out.println("ActivacionController.activar()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible aceptar activación del usuario");
        }
        return "redirect:/admin/activaciones";
    }

    @RequestMapping("/admin/activacion/desactivar/{id}")
    public String activardes(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var activacion = activacionService.getActivacionById(id);
            var ea = estadoActivacionService.getEstadoActivacionById(3L);
            activacion.setEstadoActivacion(ea);
            activacionService.editActivacion(activacion);
            redirectAttributes.addFlashAttribute("messageOK", "Activación de usuario desactivado correctamente");
        } catch (Exception e) {
            System.out.println("ActivacionController.desactivar()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible desactivar al usuario");
        }
        return "redirect:/admin/activaciones";
    }

    @RequestMapping("/admin/activacion/rechazar/{id}")
    public String rechazar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var activacion = activacionService.getActivacionById(id);
            var ea = estadoActivacionService.getEstadoActivacionById(4L);
            activacion.setEstadoActivacion(ea);
            activacionService.editActivacion(activacion);
            redirectAttributes.addFlashAttribute("messageOK", "Activación de usuario rechazada correctamente");
        } catch (Exception e) {
            System.out.println("ActivacionController.rechazar()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible rechazar la activación del usuario");
        }
        return "redirect:/admin/activaciones";
    }

    @RequestMapping("/admin/activacion/validar/{id}")
    public String validar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var activacion = activacionService.getActivacionById(id);
            var ea = estadoActivacionService.getEstadoActivacionById(5L);
            activacion.setEstadoActivacion(ea);
            activacionService.editActivacion(activacion);
            redirectAttributes.addFlashAttribute("messageOK", "Activación de usuario validada correctamente");
        } catch (Exception e) {
            System.out.println("ActivacionController.validar()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible validar la activación del usuario");
        }
        return "redirect:/admin/activaciones";
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
}
