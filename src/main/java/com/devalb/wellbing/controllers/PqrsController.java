package com.devalb.wellbing.controllers;

import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devalb.wellbing.entities.EstadoPqrs;
import com.devalb.wellbing.entities.Mensaje;
import com.devalb.wellbing.entities.Pqrs;
import com.devalb.wellbing.service.EstadoPqrsService;
import com.devalb.wellbing.service.MensajeService;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.TipoPqrsService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PqrsController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private MensajeService mensajeService;

    @Autowired
    private TipoPqrsService tipoPqrsService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private EstadoPqrsService estadoPqrsService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/admin/pqrs")
    public String goToPqrs(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaPqrs", pqrsService.getPqrs());
        return "/admin/pqrs";
    }

    @GetMapping("/admin/responder-pqrs/{id}")
    public String goToResponderPqrs(@PathVariable("id") Long id, Model model, Authentication auth) {
        cargarVistas(model, auth);

        var pqrsUsuario = pqrsService.getPqrsById(id);

        List<Map.Entry<String, Mensaje>> listaMensajes = new ArrayList<>();
        var mensajes = mensajeService.getByIdPqrs(pqrsUsuario.getId());

        for (Mensaje mens : mensajes) {
            String tipoUsuario = mens.getUsuario().getRoles().size() > 1 ? "Admin" : "User";
            listaMensajes.add(new AbstractMap.SimpleEntry<>(tipoUsuario, mens));
        }

        model.addAttribute("pqrsUsuario", pqrsUsuario);
        model.addAttribute("mensajeObj", new Mensaje());
        model.addAttribute("listaMensajes", listaMensajes);
        return "/admin/responder-pqrs";
    }

    @PostMapping("/admin/responder-pqrs")
    public String postMethodName(@RequestParam("pqrsId") Long pqrsId, @ModelAttribute("mensajeObj") Mensaje mensaje,
            Authentication auth,
            RedirectAttributes redirectAttributes) {
        try {
            if (mensaje.getContenido().isEmpty() || mensaje.getContenido() == null) {
                redirectAttributes.addFlashAttribute("message",
                        "No se ha podido responder la PQRS, el mensaje no puede estar vacío");
                return "redirect:/admin/pqrs";
            }
            var pqrs = pqrsService.getPqrsById(pqrsId);
            pqrs.setFechaActualizacion(LocalDateTime.now());
            var ep = estadoPqrsService.getEstadoPqrsById(2L);
            pqrs.setEstadoPqrs(ep);
            pqrsService.editPqrs(pqrs);

            mensaje.setFecha(LocalDateTime.now());
            mensaje.setPqrs(pqrs);
            mensaje.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
            mensajeService.addMensaje(mensaje);
            redirectAttributes.addFlashAttribute("messageOK", "Respuesta enviada correctamente");
        } catch (Exception e) {
            System.out.println("PqrsController.postMethodName()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No se ha podido responder la PQRS");
        }

        return "redirect:/admin/pqrs";
    }

    @RequestMapping("/admin/pqrs/abrir/{id}")
    public String abrirPqrs(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var pqrs = pqrsService.getPqrsById(id);
            var estadoPqrs = estadoPqrsService.getEstadoPqrsById(1L);
            pqrs.setEstadoPqrs(estadoPqrs);
            pqrs.setFechaActualizacion(LocalDateTime.now());
            pqrsService.editPqrs(pqrs);
            redirectAttributes.addFlashAttribute("messageOK", "PQRS abierta correctamente");
        } catch (Exception e) {
            System.out.println("PqrsController.abrirPqrs()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible abrir la PQRS");
        }
        return "redirect:/admin/pqrs";
    }

    @RequestMapping("/admin/pqrs/eliminar/{id}")
    public String eliminarPqrs(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var pqrs = pqrsService.getPqrsById(id);
            var estadoPqrs = estadoPqrsService.getEstadoPqrsById(3L);
            pqrs.setEstadoPqrs(estadoPqrs);
            pqrs.setFechaActualizacion(LocalDateTime.now());
            pqrsService.editPqrs(pqrs);
            redirectAttributes.addFlashAttribute("messageOK", "PQRS eliminada correctamente");
        } catch (Exception e) {
            System.out.println("PqrsController.eliminarPqrs()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No ha sido posible eliminar la PQRS");
        }
        return "redirect:/admin/pqrs";
    }

    @GetMapping("/secretario/pqrs")
    public String goToSecretarioPqrs(Model model, Authentication auth) {
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
        return "/secretario/pqrs";
    }

    @GetMapping("/secretario/responder-pqrs")
    public String goToSecretarioResponderPqrs(Model model, Authentication auth) {
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
        return "/secretario/responder-pqrs";
    }

    @GetMapping("/usuario/pqrs")
    public String goToUsuarioPqrs(Model model, Authentication auth) {
        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        var listaTipoPqrs = tipoPqrsService.getTiposPqrs();

        model.addAttribute("pqrs", new Pqrs());
        model.addAttribute("listaTipoPqrs", listaTipoPqrs);

        model.addAttribute("listaPqrs", pqrsService.getPqrsByIdUsuario(usuLog.getId()));
        model.addAttribute("usuLog", usuLog);
        model.addAttribute("nombre",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
        model.addAttribute("apellido",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());

        return "usuario/pqrs";
    }

    @GetMapping("/usuario/pqrs-detalle/{id}")
    public String goToUsuarioPqrsDetalle(@PathVariable("id") Long id, Model model, Authentication auth) {
        var pqrs = pqrsService.getPqrsById(id);

        List<Map.Entry<String, Mensaje>> listaMensajes = new ArrayList<>();
        var mensajes = mensajeService.getByIdPqrs(pqrs.getId());

        for (Mensaje mens : mensajes) {
            String tipoUsuario = mens.getUsuario().getRoles().size() > 1 ? "Admin" : "User";
            listaMensajes.add(new AbstractMap.SimpleEntry<>(tipoUsuario, mens));
        }

        model.addAttribute("mensajeObj", new Mensaje());
        model.addAttribute("mensajes", listaMensajes);
        model.addAttribute("pqrs", pqrs);
        model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
        model.addAttribute("nombre",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
        model.addAttribute("apellido",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());

        return "usuario/pqrs-detalle";
    }

    @PostMapping("/usuario/pqrs-detalle")
    public String crearMensaje(@RequestParam("pqrsId") Long pqrsId, @ModelAttribute("mensajeObj") Mensaje mensaje,
            Authentication auth,
            RedirectAttributes redirectAttributes) {

        try {
            mensaje.setFecha(LocalDateTime.now());
            var pqrs = pqrsService.getPqrsById(pqrsId);

            EstadoPqrs estadoPqrs = new EstadoPqrs();
            estadoPqrs.setId(1L);
            pqrs.setEstadoPqrs(estadoPqrs);

            pqrs.setFechaActualizacion(LocalDateTime.now());
            pqrsService.editPqrs(pqrs);
            mensaje.setPqrs(pqrs);
            mensaje.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
            mensajeService.addMensaje(mensaje);

            redirectAttributes.addFlashAttribute("messageOK", "Mensaje enviado correctamente");
        } catch (Exception e) {
            System.out.println("UsuarioController.crearMensaje()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No se ha podido enviar el mensaje");
        }

        return "redirect:/usuario/pqrs-detalle/" + pqrsId;
    }

    @PostMapping("/usuario/pqrs")
    public String crearPqrs(@ModelAttribute("pqrs") Pqrs pqrs, Authentication auth,
            RedirectAttributes redirectAttributes) {

        try {
            pqrs.setFechaRegistro(LocalDateTime.now());
            pqrs.setFechaActualizacion(LocalDateTime.now());

            EstadoPqrs estadoPqrs = new EstadoPqrs();
            estadoPqrs.setId(1L);
            pqrs.setEstadoPqrs(estadoPqrs);
            pqrs.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
            pqrsService.addPqrs(pqrs);
            Mensaje mensaje = new Mensaje();
            mensaje.setContenido(pqrs.getContenido());
            mensaje.setFecha(LocalDateTime.now());
            mensaje.setPqrs(pqrs);
            mensaje.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
            mensajeService.addMensaje(mensaje);
            redirectAttributes.addFlashAttribute("messageOK", "PQRS creada correctamente");
        } catch (Exception e) {
            System.out.println("UsuarioController.crearPqrs()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No se ha podido crear la PQRS");
        }

        return "redirect:/usuario/pqrs";
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