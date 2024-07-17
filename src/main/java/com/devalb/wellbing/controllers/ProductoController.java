package com.devalb.wellbing.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.devalb.wellbing.entities.Producto;
import com.devalb.wellbing.service.PqrsService;
import com.devalb.wellbing.service.ProductoService;
import com.devalb.wellbing.service.PublicacionService;
import com.devalb.wellbing.service.UsuarioService;
import com.devalb.wellbing.util.FormatoTexto;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private PublicacionService publicacionService;

    @Autowired
    private PqrsService pqrsService;

    @Autowired
    private FormatoTexto ft;

    @GetMapping("/productos")
    public String goToProducts(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("listaProductos", productoService.getProductosVisibles());
        return "productos";
    }

    @GetMapping("/admin/productos")
    public String goToProductos(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("productos", productoService.getProductosVisibles());
        return "admin/productos";
    }

    @GetMapping("/admin/agregar-producto")
    public String goToAddproducto(Model model, Authentication auth) {
        cargarVistas(model, auth);
        model.addAttribute("producto", new Producto());
        return "admin/add-producto";
    }

    @PostMapping("/admin/agregar-producto")
    public String guardarProducto(@ModelAttribute Producto producto,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) throws IOException {
        if (!image.isEmpty()) {
            try {
                producto.setVisible(true);
                productoService.addProducto(producto);
                Path directiorioImagenes = Paths.get("src//main//resources//static/images/productos");
                String rutaAbsoluta = directiorioImagenes.toFile().getAbsolutePath();

                String nombreImg = "producto_" + producto.getId();
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths
                        .get(rutaAbsoluta + "//" + nombreImg + ft.obtenerExtension(image.getOriginalFilename()));
                Files.write(rutaCompleta, bytesImg);
                producto.setImagen(nombreImg + ft.obtenerExtension(image.getOriginalFilename()));
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("messageError", "Error al subir la imagen");
                e.printStackTrace();
            }
        }

        productoService.addProducto(producto);
        redirectAttributes.addFlashAttribute("messageOK", "Producto guardado correctamente");
        return "redirect:/admin/productos";
    }

    @GetMapping("/admin/editar-producto/{id}")
    public String goToDetalleproducto(@PathVariable("id") Long id, Model model, Authentication auth) {
        cargarVistas(model, auth);

        var producto = productoService.getProductoById(id);
        model.addAttribute("producto", producto);
        return "admin/edit-producto";
    }

    @PostMapping("/admin/editar-producto/{id}")
    public String editarProducto(@PathVariable("id") Long id,
            @ModelAttribute Producto producto,
            @RequestParam("image") MultipartFile image,
            RedirectAttributes redirectAttributes) {
        Producto productoExistente = productoService.getProductoById(id);

        if (productoExistente == null) {
            redirectAttributes.addFlashAttribute("message", "Producto no encontrado");
            return "redirect:/admin/productos";
        }

        if (producto.getNombre() != null && !producto.getNombre().isEmpty()) {
            productoExistente.setNombre(producto.getNombre());
        }
        if (producto.getDescripcion() != null && !producto.getDescripcion().isEmpty()) {
            productoExistente.setDescripcion(producto.getDescripcion());
        }
        if (producto.getPrecio() != null && !producto.getPrecio().toString().isEmpty()) {
            productoExistente.setPrecio(producto.getPrecio());
        }

        if (!image.isEmpty()) {
            Path directorioImagenes = Paths.get("src//main//resources//static/images/productos");
            String rutaAbsoluta = directorioImagenes.toFile().getAbsolutePath();
            String nombreImg = "producto_" + producto.getId();
            try {
                byte[] bytesImg = image.getBytes();
                Path rutaCompleta = Paths
                        .get(rutaAbsoluta + "//" + nombreImg + ft.obtenerExtension(image.getOriginalFilename()));
                Files.write(rutaCompleta, bytesImg);
                productoExistente.setImagen(nombreImg + ft.obtenerExtension(image.getOriginalFilename()));
            } catch (IOException e) {
                redirectAttributes.addFlashAttribute("message", "Error al subir la imagen");
                e.printStackTrace();
            }
        }

        productoService.editProducto(productoExistente);
        redirectAttributes.addFlashAttribute("messageOK", "Producto actualizado correctamente");
        return "redirect:/admin/productos";
    }

    @RequestMapping("/admin/eliminar-producto/{id}")
    public String eliminarProducto(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            productoService.deleteProducto(id);
            redirectAttributes.addFlashAttribute("messageOK", "Producto eliminado correctamente");
        } catch (Exception e) {
            System.out.println("ProductoController.eliminarProducto()" + e.getMessage());
            redirectAttributes.addFlashAttribute("message", "Ha ocurrido un error");
        }

        return "redirect:/admin/productos";
    }

    @GetMapping("/secretario/productos")
    public String goToSecretarioProductos(Model model, Authentication auth) {
        cargarVistas(model, auth);
        return "secretario/productos";
    }

    @GetMapping("/secretario/agregar-producto")
    public String goToSecretarioAddProducto(Model model, Authentication auth) {
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
        return "secretario/add-producto";
    }

    @GetMapping("/admin/detalle-producto/{id}")
    public String goToAdminProductDetail(@PathVariable("id") Long id, Model model, Authentication auth) {
        try {
            cargarVistas(model, auth);
            model.addAttribute("producto", productoService.getProductoById(id));
        } catch (Exception e) {
            System.out.println("ProductoController.goToAdminProductDetail()" + e.getMessage());
        }
        return "admin/detalle-producto";
    }

    @GetMapping("/productos/detalle-producto/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        model.addAttribute("producto", productoService.getProductoById(id));
        return "detalle-producto";
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

            if (auth != null) {
                model.addAttribute("usuLog", usuarioService.getUsuarioByUsername(auth.getName()));
                model.addAttribute("nombre",
                        ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getNombre()));
                model.addAttribute("apellido",
                        ft.primeraPalabra(usuarioService.getUsuarioByUsername(auth.getName()).getApellido()));
                model.addAttribute("roles", usuarioService.getUsuarioByUsername(auth.getName()).getRoles());
            }

        } catch (Exception e) {
            System.out.println("AdminController.cargarVistas()" + e.getMessage());
        }

    }
}
