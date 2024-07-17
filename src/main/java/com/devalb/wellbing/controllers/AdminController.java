package com.devalb.wellbing.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.devalb.wellbing.entities.Rol;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.ActivacionService;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.RolService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.CrearCodigo;
import com.devalb.wellbing.util.FormatoTexto;
import com.devalb.wellbing.util.ObtenerDato;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ActivacionService activacionService;

    @Autowired
    private ObtenerDato od;

    @Autowired
    private final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(4);

    @Autowired
    private FormatoTexto ft;

    CrearCodigo crearCodigo = new CrearCodigo();

    @GetMapping("/admin")
    public String goToAdmin(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaTareas", tareaService.getTareas());
        return "admin/index";
    }

    @RequestMapping("/admin/crear-tarea/{id}")
    public String activarTarea(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            var tarea = tareaService.getTareaById(id);
            tarea.setVisible(true);
            tareaService.editTarea(tarea);
            redirectAttributes.addFlashAttribute("messageOK",
                    "Se ha modificado la tarea correctamente. Ahora es visible");
        } catch (Exception e) {
            System.out.println("AdminController.activarTarea()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "No se ha podido modificar la tarea");
        }
        return "redirect:admin";
    }

    @RequestMapping("/admin/eliminar-tarea/{id}")
    public String eliminarTarea(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            var tarea = tareaService.getTareaById(id);
            tarea.setVisible(false);
            tareaService.editTarea(tarea);
            redirectAttributes.addFlashAttribute("messageOK",
                    "Se ha eliminado la tarea correctamente.");
        } catch (Exception e) {
            System.out.println("AdminController.activarTarea()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message",
                    "No se ha podido eliminar la tarea");
        }
        return "redirect:admin";
    }

    @GetMapping("/admin/usuarios")
    public String goToUsuarios(Model model, Authentication auth) {
        try {
            cargarVistas(model, auth);
            model.addAttribute("usuarios", usuarioService.getUsuarios());

        } catch (Exception e) {
            System.out.println("UsuarioController.goToUsuarios()" + e.getMessage());
        }

        return "admin/usuarios";
    }

    @GetMapping("/admin/agregar-usuario")
    public String goToAddUsuarios(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("user", new Usuario());
        List<Rol> lista = rolService.getRoles();
        model.addAttribute("listaRoles", lista);
        return "admin/add-usuario";
    }

    @PostMapping("/admin/agregar-usuario")
    public String postMethodName(@ModelAttribute("user") Usuario user,
            @RequestParam(value = "roles", required = false) List<Long> rolesSeleccionados,
            RedirectAttributes redirectAttributes) {
        try {
            List<Rol> roles = new ArrayList<>();
            roles.add(rolService.getRolById(4L));

            user.setCodigoUsuario(generarCodigo());
            user.setPassword(ENCODER.encode(user.getPassword()));
            user.setEnable(true);
            user.setFechaRegistro(LocalDate.now());
            user.setInactivo(0);
            user.setTarea(false);
            user.setVisible(true);

            if (rolesSeleccionados != null) {
                for (Long rolId : rolesSeleccionados) {
                    if (rolId == 1L) {
                        roles.add(rolService.getRolById(1L));
                        roles.add(rolService.getRolById(2L));
                        roles.add(rolService.getRolById(3L));

                        break;
                    }
                    if (rolId != 4L) {
                        roles.add(rolService.getRolById(rolId));

                    }
                }
            }

            user.setRoles(roles);
            usuarioService.addUsuario(user);
            redirectAttributes.addFlashAttribute("messageOK",
                    "Usuario registrado correctamente");

        } catch (Exception e) {
            System.out.println("AdminController.postMethodName()" + e.getMessage());
            if (e.getMessage().contains("Duplicate entry")) {
                redirectAttributes.addFlashAttribute("message",
                        "El campo: " + od.obtenerDato(e.getMessage())
                                + " ya se encuentra registrado en la base de datos");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "No se ha podido registrar el usuario");
            }

        }

        return "redirect:admin/usuarios";
    }

    @GetMapping("/admin/editar-usuario/{id}")
    public String goToAdminEditUser(@PathVariable("id") Long id, Model model, Authentication auth) {
        try {
            cargarVistas(model, auth);

            Usuario usuario = usuarioService.getUsuarioById(id);
            List<Rol> listaRoles = rolService.getRoles();

            model.addAttribute("user", usuario);
            model.addAttribute("listaRoles", listaRoles);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "admin/edit-usuario";
    }

    @PostMapping("/admin/editar-usuario/{id}")
    public String editarUsuario(@PathVariable("id") Long id, @ModelAttribute("user") Usuario user,
            @RequestParam(value = "roles", required = false) List<Long> rolesSeleccionados,
            RedirectAttributes redirectAttributes) {
        try {
            Usuario usuarioExistente = usuarioService.getUsuarioById(id);

            if (usuarioExistente != null) {
                if (user.getNombre() != null && !user.getNombre().isEmpty()) {
                    usuarioExistente.setNombre(user.getNombre());
                }
                if (user.getApellido() != null && !user.getApellido().isEmpty()) {
                    usuarioExistente.setApellido(user.getApellido());
                }
                if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                    usuarioExistente.setEmail(user.getEmail());
                }
                if (user.getCelular() != null && !user.getCelular().isEmpty()) {
                    usuarioExistente.setCelular(user.getCelular());
                }
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    usuarioExistente.setPassword(ENCODER.encode(user.getPassword()));
                }
                if (user.getUsername() != null && !user.getUsername().isEmpty()) {
                    usuarioExistente.setUsername(user.getUsername());
                }
                if (user.getDireccion() != null && !user.getDireccion().isEmpty()) {
                    usuarioExistente.setDireccion(user.getDireccion());
                }
                usuarioExistente.setEnable(user.isEnable());
                if (!user.isVisible()) {
                    usuarioExistente.setVisible(false);
                    usuarioExistente.setEnable(false);
                }

                usuarioExistente.setVisible(user.isVisible());

                List<Rol> roles = new ArrayList<>();
                roles.add(rolService.getRolById(4L));
                if (rolesSeleccionados != null) {
                    for (Long rolId : rolesSeleccionados) {
                        if (rolId == 1L) {
                            roles.add(rolService.getRolById(1L));
                            roles.add(rolService.getRolById(2L));
                            roles.add(rolService.getRolById(3L));
                            break;
                        } else if (rolId == 4L) {
                            continue;
                        }
                        roles.add(rolService.getRolById(rolId));
                    }
                }
                usuarioExistente.setRoles(roles);

                usuarioService.editUsuario(usuarioExistente);
                redirectAttributes.addFlashAttribute("messageOK", "Usuario actualizado correctamente");
            } else {
                redirectAttributes.addFlashAttribute("message", "Usuario no encontrado");
            }
        } catch (Exception e) {
            System.out.println("AdminController.editarUsuario()" + e.getMessage());
            if (e.getMessage().contains("Duplicate entry")) {
                redirectAttributes.addFlashAttribute("message",
                        "El campo: " + od.obtenerDato(e.getMessage())
                                + " ya se encuentra registrado en la base de datos");
            } else {
                redirectAttributes.addFlashAttribute("message",
                        "No se ha podido registrar el usuario");
            }
        }
        return "redirect:admin/usuarios";
    }

    @PostMapping("/admin/banear-usuario/{id}")
    public String banearUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var user = usuarioService.getUsuarioById(id);
            user.setEnable(false);
            usuarioService.editUsuario(user);
            redirectAttributes.addFlashAttribute("messageOK", "Usuario baneado correctamente");
        } catch (Exception e) {
            System.out.println("AdminController.banearUsuario()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No se ha podido banear al usuario");
        }
        return "redirect:admin/usuarios";
    }

    @PostMapping("/eliminar-usuario/{id}")
    public String eliminarUsuario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            var user = usuarioService.getUsuarioById(id);
            user.setVisible(false);
            user.setEnable(false);
            usuarioService.editUsuario(user);
            redirectAttributes.addFlashAttribute("messageOK", "Usuario eliminado correctamente");
        } catch (Exception e) {
            System.out.println("AdminController.eliminarUsuario()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "No se ha podido eliminar al usuario");
        }
        return "redirect:admin/usuarios";
    }

    @GetMapping("/admin/detalle-usuario/{id}")
    public String goToAdminUserDetail(@PathVariable("id") Long id, Model model, Authentication auth) {
        try {
            cargarVistas(model, auth);

            Usuario usuario = usuarioService.getUsuarioById(id);
            List<Rol> listaRoles = rolService.getRoles();

            var ultimaActivacion = activacionService.getUltimActivacion(usuario.getId());
            if (ultimaActivacion == null) {
                model.addAttribute("estado", "Inactivo");
            } else if (!validarActivacionUsuario(usuario)) {
                model.addAttribute("estado", "Inactivo");
            } else {
                model.addAttribute("estado", ultimaActivacion.getEstadoActivacion().getNombre());
            }
            model.addAttribute("tareasPendientes", tareaService.getTareasByNuevaAndIdUsuario(1L, id).size());
            model.addAttribute("hoy", LocalDate.now());
            model.addAttribute("ultimaActivacion", ultimaActivacion);
            model.addAttribute("user", usuario);
            model.addAttribute("listaRoles", listaRoles);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return "admin/detalle-usuario";
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

    public boolean validarActivacionUsuario(Usuario user) {
        if (activacionService.getUltimActivacion(user.getId()).getFechaFin().getMonthValue() >= LocalDate.now()
                .getMonthValue()) {
            return true;
        }
        return false;
    }
}
