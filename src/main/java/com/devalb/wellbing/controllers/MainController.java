package com.devalb.wellbing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.CrearCodigo;
import com.devalb.wellbing.util.FormatoTexto;
import com.devalb.wellbing.util.PlantillasEmail;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MainController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CrearCodigo crearCodigo;

    @Autowired
    private EmailController emailController;

    @Autowired
    private final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(4);

    @Autowired
    private PlantillasEmail plantillasEmail;

    @Autowired
    private FormatoTexto ft;

  

    @GetMapping("/nosotros")
    public String goToNosotros(Model model, Authentication auth) {
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
        return "nosotros";
    }

    @GetMapping("/recuperar-pass")
    public String goToRecuperarPass(Model model) {
        model.addAttribute("user", new Usuario());
        return "recuperar-pass";
    }

    @PostMapping("/recuperar-pass")
    public String recuperarPass(@ModelAttribute("user") Usuario user) {
        try {
            Usuario usu = usuarioService.getUsuarioByEmail(user.getEmail());
            var newPass = crearCodigo.generarPass();
            usu.setPassword(ENCODER.encode(newPass));
            usuarioService.editUsuario(usu);
            emailController.sendHtmlEmailPassRecovery(user.getEmail(),
                    plantillasEmail.recuperarPassword(usu.getNombre(), usu.getApellido(), user.getEmail(),
                            usu.getUsername(), newPass),
                    usu.getNombre(), usu.getApellido(),
                    usu.getUsername(), newPass);

            return "redirect:/";
        } catch (Exception e) {
            return "redirect:/recuperar-pass?error=true";
        }

    }



}
