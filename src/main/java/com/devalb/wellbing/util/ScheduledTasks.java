package com.devalb.wellbing.util;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.devalb.wellbing.entities.Activacion;
import com.devalb.wellbing.entities.EstadoActivacion;
import com.devalb.wellbing.entities.EstadoTarea;
import com.devalb.wellbing.entities.Producto;
import com.devalb.wellbing.entities.Tarea;
import com.devalb.wellbing.entities.Usuario;
import com.devalb.wellbing.service.ActivacionService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.TareaService;
import com.devalb.wellbing.service.UsuarioService;

@Component
public class ScheduledTasks {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private TareaService tareaService;

    @Autowired
    private ActivacionService activacionService;

    @Autowired
    private CrearCodigo cCodigo;

    // ejecutar todos los día 28 de cada mes a las 00:00:00
    @Scheduled(cron = "0 0 0 28 * ?", zone = "America/Bogota") // seg, min, horas, día del mes, mes y día de la semana
    public void desactivarUsuarios() {

        try {
            var usuariosActivos = usuarioService.getUsuariosActivos();
            List<Activacion> listaUltimaActivacion = new ArrayList<>();

            List<Usuario> listaInactivos = new ArrayList<>();

            for (Usuario user : usuariosActivos) {
                if (activacionService.getUltimActivacion(user.getId()) == null) {
                    Activacion nuevaAct = new Activacion();
                    nuevaAct.setFechaFin(LocalDate.of(1900, 1, 1));
                    nuevaAct.setUsuario(user);
                    listaUltimaActivacion.add(nuevaAct);
                } else {
                    listaUltimaActivacion.add(activacionService.getUltimActivacion(user.getId()));
                }
            }

            for (Activacion act : listaUltimaActivacion) {
                if (act.getFechaFin().isBefore(LocalDate.now()) || act.getFechaFin().equals(null)) {
                    listaInactivos.add(act.getUsuario());
                }
            }

            for (Usuario usu : listaInactivos) {

                if (usu.getInactivo() < 2) {
                    usu.setInactivo(usu.getInactivo() + 1);
                } else if (usu.getInactivo() == 2) {
                    usu.setInactivo(usu.getInactivo() + 1);
                    usu.setEnable(false);
                    usu.setEmail("ban-" + usu.getEmail());
                }
                usuarioService.editUsuario(usu);
            }
            System.out.println("Usuarios inactivos editados");
        } catch (Exception e) {
            System.out.println("UsuarioController.prueba()" + e.getMessage());
            e.printStackTrace();

        }
    }

    // @Scheduled(cron = "0 0 * * * ?") // Esto se ejecuta cada hora
    // public void asignarTareasAUsuarios() {
    // LocalDate fechaActual = LocalDate.now();

    // // Se ejecutará los primeros 25 días de cada mes
    // if (fechaActual.getDayOfMonth() >= 1 && fechaActual.getDayOfMonth() <= 25) {
    // List<Usuario> usuarios = usuarioService.getUsuariosActivosSinTarea();
    // List<Producto> productos = productoService.getProductosVisibles();

    // int numProductos = productos.size();
    // Random random = new Random();

    // for (Usuario usuario : usuarios) {
    // for (int i = 0; i < 4; i++) { // Crear 4 tareas por usuario
    // Tarea tarea = new Tarea();
    // tarea.setNombre("Tarea " + (i + 1) + " de " + cCodigo.generarNombreTarea());
    // tarea.setFecha(LocalDate.now());
    // tarea.setNueva(true);
    // tarea.setVisible(true);
    // tarea.setUsuario(usuario);

    // // Asignar un producto aleatorio
    // Producto producto = productos.get(random.nextInt(numProductos));
    // tarea.setProducto(producto);

    // // Guardar la tarea en el repositorio
    // tareaService.addTarea(tarea);
    // }
    // usuario.setTarea(true);
    // usuarioService.editUsuario(usuario);
    // }
    // }
    // }


    // ejecutar todos los día 1 de cada mes a las 00:00:00
    @Scheduled(cron = "0 0 0 1 * ?", zone = "America/Bogota") // seg, min, horas, día del mes, mes y día de la semana
    public void limpiarTareas() {
        List<Usuario> usuarios = usuarioService.getUsuariosActivos();
        for (Usuario usuario : usuarios) {
            usuario.setTarea(false);
            usuarioService.editUsuario(usuario);
        }
        List<Tarea> tareas = tareaService.getTareasVencidas();
        for (Tarea tarea : tareas) {
            EstadoTarea et = new EstadoTarea();
            et.setId(2L);
            tarea.setEstadoTarea(et);
            tareaService.editTarea(tarea);
        }
    }

    // ejecutar todos los dias 1 de cada mes a las 00:10:00
    @Scheduled(cron = "0 10 0 1 * ?", zone = "America/Bogota") // seg, min, horas, día del mes, mes y día de la semana
    public void activarUsuariosValidados() {
        var listaActivaciones = activacionService.getActivacionesByEstadoActivacion(5);
        EstadoActivacion ea = new EstadoActivacion();
        ea.setId(2L);
        if (listaActivaciones.size() > 0) {
            for (Activacion activacion : listaActivaciones) {
                activacion.setEstadoActivacion(ea);
                activacion.setNueva(false);
                activacionService.editActivacion(activacion);
            }
        }

    }

}
