package com.devalb.wellbing.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class ObtenerDato {

    public String obtenerDato(String texto) {

        String regex = "'([^']*)'";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(texto);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }
}
