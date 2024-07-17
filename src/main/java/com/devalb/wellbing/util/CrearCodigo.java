package com.devalb.wellbing.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devalb.wellbing.entities.Tarea;
import com.devalb.wellbing.repository.TareaRepository;

@Service
public class CrearCodigo {

    @Autowired
    private FechaHora fh;

    @Autowired
    private TareaRepository tRepository;

    char[] mayusculas = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
    char[] minusculas = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r',
            's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
    char[] numeros = { '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };

    public String generarCodigo(List<String> codigos) {

        StringBuilder caracteres = new StringBuilder();
        caracteres.append(mayusculas);
        caracteres.append(minusculas);
        caracteres.append(numeros);

        StringBuilder password = new StringBuilder();
        do {
            for (int i = 0; i <= 6; i++) {
                int cantidadCaracteres = caracteres.length();
                int numeroRandom = (int) (Math.random() * cantidadCaracteres);

                password.append((caracteres.toString()).charAt(numeroRandom));
            }
        } while (codigos.contains(password.toString()));

        return password.toString();
    }

    public String generarPass() {

        StringBuilder caracteres = new StringBuilder();
        caracteres.append(mayusculas);
        caracteres.append(minusculas);
        caracteres.append(numeros);

        StringBuilder password = new StringBuilder();

        for (int i = 0; i <= 9; i++) {
            int cantidadCaracteres = caracteres.length();
            int numeroRandom = (int) (Math.random() * cantidadCaracteres);

            password.append((caracteres.toString()).charAt(numeroRandom));
        }

        return password.toString();
    }

    public String generarNombreTarea() {
        var listaTareas = tRepository.findAll();
        List<String> tareas = new ArrayList<>();

        if (listaTareas.size() > 0) {
            for (Tarea tarea : listaTareas) {
                tareas.add(tarea.getNombre());
            }
        }

        StringBuilder caracteres = new StringBuilder();
        caracteres.append(mayusculas);
        caracteres.append(minusculas);
        caracteres.append(numeros);

        StringBuilder nombre = new StringBuilder();
        nombre.append(fh.nombreMes());
        do {
            for (int i = 0; i <= 3; i++) {
                int cantidadCaracteres = caracteres.length();
                int numeroRandom = (int) (Math.random() * cantidadCaracteres);

                nombre.append((caracteres.toString()).charAt(numeroRandom));
            }
        } while (tareas.contains(nombre.toString()));

        return nombre.toString();
    }
}
