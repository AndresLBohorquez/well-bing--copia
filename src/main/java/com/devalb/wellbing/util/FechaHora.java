package com.devalb.wellbing.util;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class FechaHora {
    Date date = Calendar.getInstance().getTime();

    public String fecha() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-YYYY", new Locale("es", "ES"));
        String dateString = sdf.format(date);
        return dateString.substring(0, 3) + dateString.toUpperCase().charAt(3)
                + dateString.substring(4, dateString.length());
    }

    public String hora() {
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss");
        String horaString = sdfHora.format(date);
        return horaString;
    }

    public LocalDate obtenerUltimoDiaDelMes(LocalDate fecha) {
        // Obtener el último día del mes
        int ultimoDia = fecha.lengthOfMonth();
        return fecha.withDayOfMonth(ultimoDia);
    }

    public LocalDate obtenerUltimoDiaDelMesSiguiente(LocalDate fecha) {
        // Obtener el último día del mes
        int ultimoDia = fecha.plusMonths(1).lengthOfMonth();
        return fecha.plusMonths(1).withDayOfMonth(ultimoDia);
    }

    public String nombreMes() {
        // Obtener la fecha y hora actual
        LocalDate fechaHora = LocalDate.now();

        // Definir el formato para el nombre completo del mes
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM", new Locale("es", "ES"));

        // Formatear la fecha para obtener el nombre del mes
        String nombreMes = fechaHora.format(formatter);

        // Retornar el nombre del mes
        return nombreMes;
    }
}
