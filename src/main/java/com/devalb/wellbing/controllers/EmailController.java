package com.devalb.wellbing.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.devalb.wellbing.entities.Email;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
public class EmailController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/email")
    public String goToEmail(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "email";
    }

    @GetMapping("/admin/email")
    public String goToEmail(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("email", new Email());
        return "admin/email";
    }

    @PostMapping("/admin/email")
    public String enviarEmailAdmin(@ModelAttribute("email") Email email, RedirectAttributes redirectAttributes,
            @RequestParam("action") String action) {
        try {
            List<String> listaCorreos = new ArrayList<>();
            System.out.println(listaCorreos.isEmpty());
            if (!email.getMensaje().isEmpty() && email.getMensaje() != null) {
                if (!email.getAsunto().isEmpty() && email.getAsunto() != null) {
                    if (action.equals("enviar")) {
                        listaCorreos = new ArrayList<>(Arrays.asList(email.getDestinatario().split(", ")));
                    }
                    if (action.equals("enviarTodos")) {
                        var usuarios = usuarioService.getUsuariosActivos();

                        for (Usuario usu : usuarios) {
                            listaCorreos.add(usu.getEmail());
                        }
                    }
                    if (!email.getDestinatario().isEmpty() && email.getDestinatario() != null
                            || listaCorreos.size() > 1) {

                        SimpleMailMessage mail = new SimpleMailMessage();
                        int cantidad = 0;
                        for (String correo : listaCorreos) {
                            mail.setTo(correo);
                            mail.setSubject(email.getAsunto());
                            mail.setText(email.getMensaje());
                            mailSender.send(mail);
                            cantidad ++;
                        }
                        if (listaCorreos.size() > 1) {
                            redirectAttributes.addFlashAttribute("messageOK",
                                    cantidad + " Correos electrónicos enviados correctamente");
                            return "redirect:admin/email";
                        } else {
                            redirectAttributes.addFlashAttribute("messageOK",
                                    "Correo electrónico enviado correctamente");
                            return "redirect:admin/email";
                        }

                    } else {
                        redirectAttributes.addFlashAttribute("message", "Debe existir al menos un destinatario");
                        return "redirect:admin/email";
                    }
                } else {
                    redirectAttributes.addFlashAttribute("message", "El asunto no debe ser vacío");
                    return "redirect:admin/email";
                }
            } else {
                redirectAttributes.addFlashAttribute("message", "El mensaje no debe ser vacío");
                return "redirect:admin/email";
            }

        } catch (Exception e) {
            System.out.println("EmailController.enviarEmailAdmin()" + e.getMessage());
        }

        return "redirect:admin/email";
    }

    @PostMapping("/email")
    public String sendEmail(@ModelAttribute("email") Email email) {
        try {
            SimpleMailMessage mail = new SimpleMailMessage();

            mail.setTo(email.getDestinatario());
            mail.setSubject(email.getAsunto());
            mail.setText(email.getMensaje());

            mailSender.send(mail);

            log.info("Correo enviado correctamente");
            return "redirect:email?success=true";
        } catch (Exception e) {
            log.info("Proceso de envio de email fallido");
            System.out.println("com.devalb.wellbing.controllers.sendEmail()" + e.getMessage());
            return "redirect:email?error=true";
        }
    }

    @GetMapping("/email1")
    public String goToEmail1(Model model) {
        Email email = new Email();
        model.addAttribute("email", email);
        return "email1";
    }

    @PostMapping("/email1")
    public String sendHtmlEmail(@ModelAttribute("email") Email email) throws MessagingException {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            message.setRecipients(MimeMessage.RecipientType.TO, "andresleonardo.2526@gmail.com");
            message.setSubject("Prueba con plantilla");

            String htmlContent = "<h1> Esto es un mensaje de prueba desde spring boot</h1>" +
                    "<p> esto puede contener contenido <strong>HTML</strong> </p>";

            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);

            return "redirect:email1?success=true";
        } catch (Exception e) {
            return "redirect:email1?error=true";
        }
    }

    public void sendHtmlEmailPassRecovery(String to, String htmlContent, String nombre, String apellido,
            String userName,
            String newPass)
            throws MessagingException {
        try {
            MimeMessage message = mailSender.createMimeMessage();

            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject("Nueva Contraseña");

            message.setContent(htmlContent, "text/html; charset=utf-8");

            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("EmailController.sendHtmlEmailPassRecovery()" + e.getMessage());
        }
    }

    @GetMapping("/tesorero/email")
    public String goToTesoreroEmail(Model model, Authentication auth) {
        cargarVistas(model, auth);
        return "tesorero/email";
    }

    @GetMapping("/secretario/email")
    public String goToSecretarioEmail(Model model, Authentication auth) {
        cargarVistas(model, auth);
        return "secretario/email";
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
