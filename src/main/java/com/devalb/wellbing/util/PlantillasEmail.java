package com.devalb.wellbing.util;

import org.springframework.stereotype.Service;

@Service
public class PlantillasEmail {

    public String recuperarPassword(String nombre, String apellido, String email, String userName, String newPass) {

        String plantilla = "<html xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\">\n"
                +
                "<head>\n" +
                "<title>Your Password change was successful!</title>\n" +
                "<!--[if !mso]><!-- -->\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "<!--<![endif]-->\n" +
                "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "<style type=\"text/css\"> span.productOldPrice { color: #A0131C; text-decoration: line-through;} #outlook a { padding: 0; } body { margin: 0; padding: 0; -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; } table, td { border-collapse: collapse; mso-table-lspace: 0pt; mso-table-rspace: 0pt; } img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; -ms-interpolation-mode: bicubic; } p { display: block; margin: 13px 0; } </style>\n"
                +
                "<link href=\"https://fonts.googleapis.com/css?family=Open+Sans:300,400,500,700\" rel=\"stylesheet\" type=\"text/css\">\n"
                +
                "<style type=\"text/css\"> @import url(https://fonts.googleapis.com/css?family=Open+Sans:300,400,500,700); </style>\n"
                +
                "<!--<![endif]-->\n" +
                "<style type=\"text/css\"> @media only screen and (min-width:480px) { .column-per-100 { width: 100% !important; max-width: 100%; } .column-per-25 { width: 25% !important; max-width: 25%; } .column-per-75 { width: 75% !important; max-width: 75%; } .column-per-48-4 { width: 48.4% !important; max-width: 48.4%; } .column-per-50 { width: 50% !important; max-width: 50%; } } </style>\n"
                +
                "<style type=\"text/css\"> @media only screen and (max-width:480px) { table.full-width-mobile { width: 100% !important; } td.full-width-mobile { width: auto !important; } } noinput.menu-checkbox { display: block !important; max-height: none !important; visibility: visible !important; } @media only screen and (max-width:480px) { .menu-checkbox[type=\"checkbox\"]~.inline-links { display: none !important; } .menu-checkbox[type=\"checkbox\"]:checked~.inline-links, .menu-checkbox[type=\"checkbox\"]~.menu-trigger { display: block !important; max-width: none !important; max-height: none !important; font-size: inherit !important; } .menu-checkbox[type=\"checkbox\"]~.inline-links>a { display: block !important; } .menu-checkbox[type=\"checkbox\"]:checked~.menu-trigger .menu-icon-close { display: block !important; } .menu-checkbox[type=\"checkbox\"]:checked~.menu-trigger .menu-icon-open { display: none !important; } } </style>\n"
                +
                "<style type=\"text/css\"> @media only screen and (min-width:481px) { .products-list-table img { width: 120px !important; display: block; } .products-list-table .image-column { width: 20% !important; } } a { color: #000; } .server-img img { width: 100% } .server-box-one a, .server-box-two a { text-decoration: underline; color: #2E9CC3; } .server-img img { width: 100% } .server-box-one a, .server-box-two a { text-decoration: underline; color: #2E9CC3; } .server-img img { width: 100% } .server-box-one a, .server-box-two a { text-decoration: underline; color: #2E9CC3; } </style>\n"
                +
                "</head>\n" +
                "<body style=\"background-color:#FFFFFF;\">\n" +
                "<div style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; background-color: #FFFFFF;\">\n"
                +
                "<div class=\"body-wrapper\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; padding-bottom: 20px; box-shadow: 0 4px 10px #ddd; background: #F2F2F2; background-color: #F2F2F2; margin: 0px auto; max-width: 600px; margin-bottom: 10px;\">\n"
                +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"background:#F2F2F2;background-color:#F2F2F2;width:100%;\">\n"
                +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; direction: ltr; font-size: 0px; padding: 10px 20px; text-align: center;\" align=\"center\">\n"
                +
                "<div class=\"pre-header\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; height: 1px; overflow: hidden; margin: 0px auto; max-width: 560px;\">\n"
                +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n"
                +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; direction: ltr; font-size: 0px; padding: 0px; text-align: center;\" align=\"center\">\n"
                +
                "<div class=\"column-per-100 outlook-group-fix\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"vertical-align:top;\" width=\"100%\">\n"
                +
                "<tr>\n" +
                "<td align=\"center\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; word-break: break-word;\">\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</div>\n" +
                "<div class=\"header\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; line-height: 22px; padding: 15px 0; margin: 0px auto; max-width: 560px;\">\n"
                +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n"
                +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; direction: ltr; font-size: 0px; padding: 0px; text-align: center;\" align=\"center\">\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</div>\n" +
                "<div class=\"notice-wrap margin-bottom\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; margin: 0px auto; max-width: 560px; margin-bottom: 15px;\">\n"
                +
                "<table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"width:100%;\">\n"
                +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; direction: ltr; font-size: 0px; padding: 0px; text-align: center;\" align=\"center\">\n"
                +
                "<div class=\"column-per-100 outlook-group-fix\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; text-align: left; direction: ltr; display: inline-block; vertical-align: top; width: 100%;\">\n"
                +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" width=\"100%\">\n" +
                "<tbody>\n" +
                "<tr>\n" +
                "<td style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; background-color: #ffffff; border-radius: 10px; vertical-align: top; padding: 30px 25px;\" bgcolor=\"#ffffff\" valign=\"top\">\n"
                +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style width=\"100%\">\n"
                +
                "<tr>\n" +
                "<td align=\"left\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; word-break: break-word;\">\n"
                +
                "<h1 style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 30px; font-weight: bold; line-height: 40px; text-align: left; color: #4F4F4F;\">"
                + nombre + " " + apellido + "</h1>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; word-break: break-word;\">\n"
                +
                "<h1 style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 22px; font-weight: bold; line-height: 27px; text-align: left; color: #4F4F4F;\">¡Tu contraseña se ha cambiado correctamente!</h1>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" class=\"link-wrap\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; padding-bottom: 20px; word-break: break-word;\">\n"
                +
                "<div style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 25px; text-align: left; color: #4F4F4F;\">Nuevas Credenciales: <br></div>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" class=\"link-wrap\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; padding-bottom: 20px; word-break: break-word;\">\n"
                +
                "<div style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 25px; text-align: left; color: #4F4F4F;\">Email: <strong>"
                + email + "</strong> <br></div>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" class=\"link-wrap\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; padding-bottom: 20px; word-break: break-word;\">\n"
                +
                "<div style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 25px; text-align: left; color: #4F4F4F;\">Nombre de usuario: <strong>"
                + userName + "</strong> <br></div>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" class=\"link-wrap\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; padding-bottom: 20px; word-break: break-word;\">\n"
                +
                "<div style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 16px; font-weight: 300; line-height: 25px; text-align: left; color: #4F4F4F;\">Contraseña: <strong>"
                + newPass + "</strong> <br></div>\n"
                +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td align=\"left\" vertical-align=\"middle\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 0px; padding: 0; word-break: break-word;\">\n"
                +
                "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\" style=\"border-collapse:separate;line-height:100%;\">\n"
                +
                "<tr>\n" +
                "<td align=\"center\" bgcolor=\"#dd2c00\" role=\"presentation\" style=\"font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; border: none; border-radius: 3px; cursor: auto; mso-padding-alt: 10px 25px; background: #dd2c00;\" valign=\"middle\"> <a href=\"http://localhost:8080/login\" style=\"display: inline-block; background: #dd2c00; color: #ffffff; font-family: Open Sans, Helvetica, Tahoma, Arial, sans-serif; font-size: 18px; font-weight: bold; line-height: 120%; margin: 0; text-decoration: none; text-transform: none; padding: 10px 25px; mso-padding-alt: 0px; border-radius: 3px;\" target=\"_blank\"> Iniciar Sesión</a> </td>\n"
                +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</div>\n" +
                "</td>\n" +
                "</tr>\n" +
                "</tbody>\n" +
                "</table>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";

        return plantilla;

    }

}
