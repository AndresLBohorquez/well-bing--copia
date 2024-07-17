package com.devalb.wellbing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;

@Controller
public class PagoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/admin/pagos")
    public String goToPagos(Model model, Authentication auth) {
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
        return "/admin/pagos";
    }

    @GetMapping("/tesorero/pagos")
    public String goToTesoreroPagos(Model model, Authentication auth) {
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
        return "/tesorero/pagos";
    }
}
