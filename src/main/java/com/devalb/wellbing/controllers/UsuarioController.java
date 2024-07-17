package com.devalb.wellbing.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devalb.wellbing.entities.Activacion;
import com.devalb.wellbing.entities.Equipo;
import com.devalb.wellbing.entities.EstadoActivacion;
import com.devalb.wellbing.entities.EstadoEquipo;
import com.devalb.wellbing.entities.MedioPago;
import com.devalb.wellbing.entities.RedesUsuario;
import com.devalb.wellbing.entities.Rol;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.ActivacionService;
import com.devalb.wellbing.service.EquipoService;
import com.devalb.wellbing.service.EstadoEquipoService;
import com.devalb.wellbing.service.MedioPagoService;
import com.devalb.wellbing.service.RedesService;
import com.devalb.wellbing.service.RedesUsuarioService;
import com.devalb.wellbing.service.RolService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.CrearCodigo;
import com.devalb.wellbing.util.FechaHora;
import com.devalb.wellbing.util.FormatoTexto;

import lombok.extern.slf4j.Slf4j;

import org.springframework.ui.Model;

@Slf4j
@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ActivacionService activacionService;

    @Autowired
    private MedioPagoService medioPagoService;

    @Autowired
    private RedesUsuarioService redesUsuarioService;

    @Autowired
    private RedesService redesService;

    @Autowired
    private EquipoService equipoService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private EstadoEquipoService estadoEquipoService;

    @Autowired
    private FormatoTexto ft;

    @Autowired
    private FechaHora fh;

    @Autowired
    private final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(4);

    CrearCodigo crearCodigo = new CrearCodigo();

    @GetMapping("/")
    public String goToIndex(Model model, Authentication auth) {

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

        return "index";
    }

    @GetMapping("/login")
    public String goToLogin(Model model) {

        return "login";
    }

    @GetMapping("/signup")
    public String goToSingUp(Model model) {
        Usuario user = new Usuario();
        model.addAttribute("user", user);

        return "signup";
    }

    @PostMapping("/signup")
    public String userRegister(@ModelAttribute("user") Usuario user, RedirectAttributes redirectAttributes) {
        try {
            user.setCodigoUsuario(generarCodigo());
            user.setRoles(generarRol());
            user.setPassword(ENCODER.encode(user.getPassword()));
            user.setEnable(true);
            user.setVisible(true);
            user.setFechaRegistro(LocalDate.now());
            user.setInactivo(0);
            user.setTarea(false);

            usuarioService.addUsuario(user);
            log.info("Usuario registrado correctamente");
            redirectAttributes.addFlashAttribute("messageOK",
                    "Usuario registrado correctamente");
            return "redirect:/signup";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message",
                    "El usuario ya se encuentra registrado en el sistema.");
            log.info("Proceso de registro de usuario fallido");
            System.out.println("com.devalb.wellbing.controllers.userRegister()" + e.getMessage());
            return "redirect:/signup";
        }
    }

    public String generarCodigo() {
        var usu = usuarioService.getUsuarios();
        List<String> codigos = new ArrayList<String>();
        for (Usuario usuario : usu) {
            codigos.add(usuario.getCodigoUsuario());
        }
        return crearCodigo.generarCodigo(codigos);
    }

    public List<Rol> generarRol() {
        List<Rol> rol = new ArrayList<>();
        rol.add(rolService.getRolById(4L));
        return rol;
    }

    @GetMapping("/tesorero")
    public String goToTesorero(Model model, Authentication auth) {
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
        return "/tesorero/usuarios";
    }

    @GetMapping("/tesorero/agregar-usuario")
    public String goToAddUsuarioTesorero(Model model, Authentication auth) {
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
        return "/tesorero/add-usuario";
    }

    @GetMapping("/secretario")
    public String goToSecretario(Model model, Authentication auth) {
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
        return "/secretario/usuarios";
    }

    @GetMapping("/secretario/agregar-usuario")
    public String goToAddUsuariosecretario(Model model, Authentication auth) {
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
        return "/secretario/add-usuario";
    }

    @GetMapping("/usuario")
    public String goToUsuario(Model model, Authentication auth) {
        try {
            var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
            var ultimaActivacion = activacionService.getUltimActivacion(usuLog.getId());
            if (ultimaActivacion == null) {
                model.addAttribute("estado", "Inactivo");
            } else if (!validarActivacionUsuario(usuLog)) {
                model.addAttribute("estado", "Inactivo");
            } else {
                model.addAttribute("estado", ultimaActivacion.getEstadoActivacion().getNombre());
            }

            model.addAttribute("usuLog", usuLog);

            model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());
            model.addAttribute("mediosPago", medioPagoService.getMedioPagoByIdUsuario(usuLog.getId()));
            model.addAttribute("redesUsuario", redesUsuarioService.getAllByIdUsuario(usuLog.getId()));
            model.addAttribute("nombre",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
            model.addAttribute("apellido",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
            model.addAttribute("rsUsuario", new RedesUsuario());
            model.addAttribute("redes", redesService.getRedes());
            model.addAttribute("mpago", new MedioPago());

            model.addAttribute("ListaActivaciones", activacionService.getActivacionesByIdUsuario(usuLog.getId()));
            model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
            model.addAttribute("ultimaActivacion", ultimaActivacion);
            model.addAttribute("activacion", new Activacion());

        } catch (Exception e) {
            System.out.println("UsuarioController.goToUsuario()" + e.getMessage());
        }

        return "usuario/perfil";
    }

    @GetMapping("/usuario/editar-usuario/{id}")
    public String goToEditUser(Usuario user, Model model, Authentication auth) {
        try {
            var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
            user = usuarioService.getUsuarioById(usuLog.getId());
            model.addAttribute("user", user);
            model.addAttribute("usuLog", usuLog);
            model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
            model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());
            model.addAttribute("nombre",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
            model.addAttribute("apellido",
                    ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        } catch (Exception e) {
            System.out.println("UsuarioController.goToEditUser()" + e.getMessage());
        }

        return "usuario/editar-usuario";
    }

    @PostMapping("/usuario/editar-usuario")
    public String editarUsuario(@ModelAttribute("user") Usuario user, Authentication auth) {
        try {

            Usuario usu = usuarioService.getUsuarioById(user.getId());

            user.setCodigoUsuario(usu.getCodigoUsuario());
            user.setEmail(usu.getEmail());
            user.setEnable(usu.isEnable());
            user.setUsername(usu.getUsername());
            user.setVisible(usu.isVisible());
            user.setRoles(usu.getRoles());
            if (user.getPassword().equals(null)) {
                user.setPassword(usu.getPassword());
            }
            user.setPassword(ENCODER.encode(user.getPassword()));

            usuarioService.editUsuario(user);
        } catch (Exception e) {
            System.out.println("UsuarioController.postMethodName()" + e.getMessage());
        }

        return "redirect:/usuario";
    }

    @PostMapping("/usuario")
    public String editarDatosUsuario(@ModelAttribute("redesUsuario") RedesUsuario redesUsuario,
            @ModelAttribute("mediosPago") MedioPago medioPago,
            @ModelAttribute("activacion") Activacion activacion,
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            @RequestParam("action") String action, Authentication auth, RedirectAttributes redirectAttributes) {

        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        try {
            switch (action) {
                case "redesUsuario":
                    var listaRedesUsuario = redesUsuarioService.getAllByIdUsuario(usuLog.getId());
                    if (listaRedesUsuario.size() < 4) {
                        redesUsuario.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
                        redesUsuarioService.addRedesUsuario(redesUsuario);
                        redirectAttributes.addFlashAttribute("messageOK", "Red Social registrada correctamente");
                    } else {
                        redirectAttributes.addFlashAttribute("message",
                                "Tienes registrado el número máximo de redes sociales permitidas");
                    }
                    break;

                case "mediosPago":
                    var listaMediosPago = medioPagoService.getMedioPagoByIdUsuario(usuLog.getId());
                    if (listaMediosPago.size() < 2) {
                        medioPago.setNombre(ft.formatoTitulo(medioPago.getNombre()));
                        medioPago.setNumero(ft.soloNumeros(medioPago.getNumero()));
                        medioPago.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
                        medioPagoService.addMedioPago(medioPago);
                        redirectAttributes.addFlashAttribute("messageOK", "Medio de pago registrado correctamente");
                    } else {
                        redirectAttributes.addFlashAttribute("message",
                                "Tienes registrado el número máximo de medios de pago permitidos");
                    }
                    break;

                case "activacion":
                    int ultimaActivacion = 0;
                    if (activacionService.getActivacionesByIdUsuario(usuLog.getId()).size() > 0) {
                        ultimaActivacion = activacionService.getUltimActivacion(usuLog.getId()).getFechaFin()
                                .getMonthValue();
                    }

                    if (validarPermisoActivacionUsuario(usuLog, ultimaActivacion).equals("OK")) {
                        if (imagen != null && !imagen.isEmpty()) {
                            Path directiorioImagenes = Paths.get("src//main//resources//static/images/activaciones");
                            String rutaAbsoluta = directiorioImagenes.toFile().getAbsolutePath();
                            var nombreImg = "activacion" + usuLog.getId() + "_" + LocalDate.now().getMonthValue();
                            try {

                                byte[] bytesImg = imagen.getBytes();
                                Path rutaCompleta = Paths.get(rutaAbsoluta + "//" + nombreImg
                                        + ft.obtenerExtension(imagen.getOriginalFilename()));
                                Files.write(rutaCompleta, bytesImg);

                                activacion.setFecha(LocalDate.now());
                                activacion
                                        .setComprobante(nombreImg + ft.obtenerExtension(imagen.getOriginalFilename()));

                                if (activacionService.getActivacionesByIdUsuario(usuLog.getId()).size() > 0) {
                                    activacion.setFechaFin(fh.obtenerUltimoDiaDelMes(LocalDate.now()));
                                    activacion.setNueva(false);
                                } else {
                                    activacion.setFechaFin(fh.obtenerUltimoDiaDelMesSiguiente(LocalDate.now()));
                                    activacion.setNueva(true);
                                }

                                EstadoActivacion estadoActivacion = new EstadoActivacion();
                                estadoActivacion.setId(1L);

                                activacion.setEstadoActivacion(estadoActivacion);
                                activacion.setUsuario(usuLog);
                                activacionService.addActivacion(activacion);
                                redirectAttributes.addFlashAttribute("messageOK",
                                        "Activación registrada correctamente");
                            } catch (IOException e) {
                                System.out.println("UsuarioController.postMethodName()" + e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    } else {
                        redirectAttributes.addFlashAttribute("message",
                                validarPermisoActivacionUsuario(usuLog, ultimaActivacion));
                        System.out.println(validarPermisoActivacionUsuario(usuLog, ultimaActivacion));
                    }
                    break;

                default:
                    break;
            }

        } catch (Exception e) {
            System.out.println("UsuarioController.postMethodName()" + e.getMessage());
        }
        return "redirect:/usuario";
    }

    @GetMapping("/usuario/equipo")
    public String goToAddequipo(Model model, Authentication auth) {
        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        var ultimaActivacion = activacionService.getUltimActivacion(usuLog.getId());
        if (ultimaActivacion == null) {
            model.addAttribute("estado", "Inactivo");
        } else if (!validarActivacionUsuario(usuLog)) {
            model.addAttribute("estado", "Inactivo");
        } else {
            model.addAttribute("estado", ultimaActivacion.getEstadoActivacion().getNombre());
        }
        model.addAttribute("listaEquipo", equipoService.getEquipoByIdUsuario(usuLog.getId()));
        model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
        model.addAttribute("nombre",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
        model.addAttribute("apellido",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());
        model.addAttribute("user", new Usuario());
        return "usuario/equipo";

    }

    @PostMapping("/usuario/equipo")
    public String agregarHijo(@ModelAttribute("user") Usuario user, Authentication auth,
            RedirectAttributes redirectAttributes) {
        if (equipoService.getEquipoByIdUsuario(usuarioService.getUsuarioByUsername(auth.getName()).getId())
                .size() < 21) {
            try {
                Equipo equipo = new Equipo();
                Usuario usuario = usuarioService.getUsuarioByCodigUsuario(user.getCodigoUsuario());
                equipo.setIdHijo(usuario);
                equipo.setVisible(true);
                equipo.setUsuario(usuarioService.getUsuarioByUsername(auth.getName()));
                EstadoEquipo ee = estadoEquipoService.getByNombreEstado("Pendiente");
                equipo.setEstadoEquipo(ee);

                equipoService.addEquipo(equipo);
                redirectAttributes.addFlashAttribute("messageOK", "El usuario " + usuario.getNombre() + " "
                        + usuario.getApellido() + " ha sido agregado correctamente");
            } catch (Exception e) {
                if (e.getMessage().contains("Duplicate entry")) {
                    redirectAttributes.addFlashAttribute("message",
                            "Usuario ya registrado en un equipo de trabajo");
                } else {
                    System.out.println("EquipoController.agregarHijo()" + e.getMessage());

                    redirectAttributes.addFlashAttribute("message",
                            "No es posible registrar el usuario a su equipo de trabajo");
                }

            }
            return "redirect:/usuario/equipo";
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "No es posible agregar más miembros a su equipo de trabajo (máximo 21)");
            return "redirect:/usuario/equipo";
        }

    }

    @GetMapping("/usuario/tareas")
    public String goToTareas(Model model, Authentication auth) {
        var usuLog = usuarioService.getUsuarioByUsername(auth.getName());
        var ultimaActivacion = activacionService.getUltimActivacion(usuLog.getId());
        if (ultimaActivacion == null) {
            model.addAttribute("estado", "Inactivo");
        } else if (!validarActivacionUsuario(usuLog)) {
            model.addAttribute("estado", "Inactivo");
        } else {
            model.addAttribute("estado", ultimaActivacion.getEstadoActivacion().getNombre());
        }
        model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
        model.addAttribute("nombre",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
        model.addAttribute("apellido",
                ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
        model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());

        model.addAttribute("listaTareas", tareaService.getTareasByIdUsuario(usuLog.getId()));
        model.addAttribute("tareasNuevas", tareaService.getTareasByNuevaAndIdUsuario(1L, usuLog.getId()));
        model.addAttribute("user", new Usuario());
        return "usuario/tareas";
    }

    /*
     * +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
     */

    public String validarPermisoActivacionUsuario(Usuario user, int ultimaActivacion) {

        if (!user.isEnable()) {
            return "Usuario inactivo";

        } else if (ultimaActivacion != 0) {
            if (LocalDate.now().getMonthValue() <= ultimaActivacion) {
                return "Ya tiene un registro de activación en este mes";

            }
        } else if (LocalDate.now().getDayOfMonth() > 25) {
            return "El periodo de activación ha expirado, debe esperar al próximo mes";

        }
        return "OK";
    }

    public boolean validarActivacionUsuario(Usuario user) {
        if (activacionService.getUltimActivacion(user.getId()).getFechaFin().getMonthValue() >= LocalDate.now()
                .getMonthValue()) {
            return true;
        }
        return false;
    }
}
