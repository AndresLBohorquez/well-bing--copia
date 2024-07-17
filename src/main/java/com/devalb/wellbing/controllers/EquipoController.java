package com.devalb.wellbing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.devalb.wellbing.service.EquipoService;
import com.devalb.wellbing.service.EstadoEquipoService;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class EquipoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private EstadoEquipoService estadoEquipoService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/admin/equipo")
    public String goToAdminEquipo(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaEquipos", equipoService.getEquiposVisibles());
        return "admin/equipo";
    }

    @RequestMapping("/admin/equipo/aprobar/{id}")
    public String aprobarEquipo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var equipo = equipoService.getEquipoById(id);
            var estadoEquipo = estadoEquipoService.getByNombreEstado("Aprobado");
            equipo.setEstadoEquipo(estadoEquipo);
            equipoService.editEquipo(equipo);
            redirectAttributes.addFlashAttribute("messageOK", "El equipo ha sido aprobado correctamente");
        } catch (Exception e) {
            System.out.println("EquipoController.aprobarEquipo()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "El equipo no ha sido aprobado");
        }
        return "redirect:admin/equipo";
    }
    @RequestMapping("/admin/equipo/pendiente/{id}")
    public String pendineteEquipo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var equipo = equipoService.getEquipoById(id);
            var estadoEquipo = estadoEquipoService.getByNombreEstado("Pendiente");
            equipo.setEstadoEquipo(estadoEquipo);
            equipoService.editEquipo(equipo);
            redirectAttributes.addFlashAttribute("messageOK", "El equipo ha sido asignado a Pendiente correctamente");
        } catch (Exception e) {
            System.out.println("EquipoController.pendienteEquipo()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "El equipo no ha sido asiganado a Pendiente");
        }
        return "redirect:admin/equipo";
    }
    @RequestMapping("/admin/equipo/rechazar/{id}")
    public String rechazarEquipo(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var equipo = equipoService.getEquipoById(id);
            var estadoEquipo = estadoEquipoService.getByNombreEstado("Rechazado");
            equipo.setEstadoEquipo(estadoEquipo);
            equipoService.editEquipo(equipo);
            redirectAttributes.addFlashAttribute("messageOK", "El equipo ha sido rechazado correctamente");
        } catch (Exception e) {
            System.out.println("EquipoController.rechazarEquipo()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "El equipo no ha sido rechazado");
        }
        return "redirect:admin/equipo";
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
