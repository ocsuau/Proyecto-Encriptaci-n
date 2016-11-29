import java.text.Normalizer;

public class Vigenere {

    static String encode(String s, String password) {
        //Llamamos a la función convertir_mensaje y le pasamos el valor true para que sepa que debe encriptar. Devolvemos el retorno.
        return convertir_mensaje(s, password, true);
    }

    static String decode(String s, String password) {
        //Llamamos a la función convertir_mensaje y le pasamos el valor false para que sepa que debe desencriptar. Devolvemos el retorno.
        return convertir_mensaje(s, password, false);
    }

    static String convertir_mensaje(String s, String password, boolean b) {
        //Ponemos el mensaje y la password en mayúsculas.
        s = s.toUpperCase();
        password = password.toUpperCase();

        //Quitamos los acentos al mensaje original y a la password.
        s = quitar_acentos(s);
        password = quitar_acentos(password);

        //Creamos el contador que recorrerá los carácteres de password (-1 porque al inicio del bucle lo debemos incrementar)
        int cont_pass = -1;

        //Creamos la variable delta, donde almacenaremos el decalaje que le pasaremos a mini_caesar.
        int delta;

        //Creamos SB resultado para meter el mesaje resultado.
        StringBuilder resultado = new StringBuilder();

        //Iniciamos bucle recorriendo el mensaje.
        for (int i = 0; i < s.length(); i++) {
            //Comprobamos que el carácter en cuestión sea una letra.
            if ((s.charAt(i) >= 'A') && (s.charAt(i) <= 'Z')) {
                //Incrementamos el contador de password.
                cont_pass++;
                //Calculamos el decalaje que le pasaremos a mini_aesar y lo metemos en delta.
                delta = (int) (password.charAt(cont_pass) - 64);

                //Con la variable b sabemos si debemos encriptar o desencriptar. Añadimos el retorno en resultado directamente.
                if (b) {
                    //Enviamos a la función mini_caesar el carácter con el que estamos trabajando, delta true para que sepa que debe encriptar.
                    resultado.append(mini_caesar(s.charAt(i), delta, true));
                } else {
                    //Enviamos a la función mini_caesar el carácter con el que estamos trabajando, delta false para que sepa que debe desencriptar.
                    resultado.append(mini_caesar(s.charAt(i), delta, false));
                }
                //Comprobamos que el valor del contador de password no supere el número de carácteres de passsword -1. De esta forma lo reiniciamos a cero cada vez.
                if (cont_pass == (password.length() - 1)) {
                    cont_pass = -1;
                }
                continue;
            }
            //Si llegamos a este punto, el carácter no era una letra y la añadimos directamente a resultado.
            resultado.append(s.charAt(i));
        }
        //Devolvemos resultado en forma de String.
        return resultado.toString();
    }

    //En la función quitar_acentos recorremos el mensaje y si hay letras acentuadas, la sustituímos por la correspondiente sin acentuar.
    static String quitar_acentos(String s) {
        StringBuilder retorno = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'À':
                    retorno.append('A');
                    break;
                case 'È':
                case 'É':
                    retorno.append('E');
                    break;
                case 'Í':
                    retorno.append('I');
                    break;
                case 'Ò':
                case 'Ó':
                    retorno.append('O');
                    break;
                case 'Ú':
                    retorno.append('U');
                    break;
                default:
                    retorno.append(s.charAt(i));
            }
        }
        return retorno.toString();
    }

    //En la función mini_caesar convertiremos el carácter que nos pasan en otro carácter dependiendo del valor de delta y de b.
    static char mini_caesar(char c, int delta, boolean b) {
        //Si b es true, debemos encriptar, así que sumaremos delta a la posición del carácter en la tabla ASCII.
        if (b) {
            delta += (int) c;
            //Si delta es mayor que la posición de 'Z' en la tabla ASCII, le restamos 26 para que devuelva el carácter correcto.
            if (delta > 'Z') {
                delta -= 26;
            }
        }
        //Si b no es true, debemos desencriptar, así que restaremos delta a la posición del carácter en la tabla ASCII.
        else {
            delta = (int) c - delta;
            //Si delta es menor que la posición de 'A' en la tabla ASCII, le sumamos 26 para que devuelva el carácter correcto.
            if (delta < 'A') {
                delta += 26;
            }
        }
        //Devolvemos el carácter de la tabla ASCII en la posición delta.
        return (char) delta;
    }
}