public class Caesar {
    static String cypher(String s, int delta) {
        //Llamo a la función convertir_mensaje y le enviamos el valor true para que sepa que debe realizar la codificación
        return convertir_mensaje(s, delta, true);
    }

    static String decypher(String s, int delta) {
        //Llamo a la función convertir_mensaje y le enviamos el valor false para que sepa que debe realizar la descodificación
        return convertir_mensaje(s, delta, false);
    }

    static String convertir_mensaje(String s, int delta, boolean b) {

        if (comprobar_existencia_mensaje(s) || delta == 0) {
            //Si delta es igual a 0 o el mensaje que nos pasan no tiene ningun carácter, directamente retornamos el mismo mensaje, ya que no se vería afectado realizando el código.
            return s;
        }
        //Si delta es mayor que el número de letras del abecedario, calculamos su residuo para obtener el delta correcto.
        else if (delta > 26 || delta < -26) {
            delta = delta % 26;
        }

        //Si delta es un valor negativo, lo pasaremos a positivo y cambiaremos el valor de b para que encripte en vez de desencriptar y viceversa. (Al haber números negativos, encriptar/desencriptar es equivalente a la acción contraria con el mismo número en positivo)
        if (delta < 0) {
            delta *= -1;
            if (b) {
                b = false;
            } else {
                b = true;
            }
        }

        //Convertimos letras en mayúsculas.
        s = s.toUpperCase();

        //Creamos int pos_caracter para guardar la posición del carácter en la tabla ASCII si hiciera falta.
        int pos_caracter;

        //Creamos StringBuilder sb para ir introduciendo el nuevo mensaje.
        StringBuilder resultado = new StringBuilder();

        //Creamos un bucle donde recorreremos cada carácter del String para calcular su conversión.
        for (int i = 0; i < s.length(); i++) {

            //Comprobamos que el carácter en cuestión es una letra mayúscula sin acentos.
            if (((int) s.charAt(i) >= 'A') && ((int) s.charAt(i) <= 'Z')) {
                //Almacenamos la posición en la tabla ASCII del carácter en cuestión.
                pos_caracter = (int) s.charAt(i);

                //Si b es true, debe codificar
                if (b) {
                    //A pos_caracter le incrementamos delta.
                    pos_caracter += delta;

                    //Si pos caracter es mayor que la posición de 'Z' en la tabla ASCII, le restamos 26
                    if (pos_caracter > 'Z') {
                        pos_caracter -= 26;
                    }
                } else {
                    //Si hemos entrado aquí, es porque b es false y debemos decodificar el mensaje.

                    //A pos_caracter le restamos delta.
                    pos_caracter -= delta;

                    //Si pos caracter es mayor que la posición de 'Z' en la tabla ASCII, le restamos 26.
                    if (pos_caracter < 'A') {
                        pos_caracter += 26;
                    }
                }
                //Metemos en resultado el carácter de la tabla ASCII con posición pos_caracter.
                resultado.append((char) pos_caracter);
            } else {
                //En este punto sabemos que el carácter en cuestión no es una letra (puediendo ser una letra acentuada), así que lo metemos directamente en sb.
                resultado.append(s.charAt(i));
            }
        }
        //Devolvemos el mensaje codificado en forma de String
        return resultado.toString();
    }

    static String magic(String s) {

        //Si el mensaje que nos pasan no contiene ningún carácter, devolvemos el mismo mensaje ya que no se vería afectado con el resto del algoritmo.
        if (comprobar_existencia_mensaje(s)) {
            return s;
        }
        //Pasamos las letras a mayúsculas.
        s = s.toUpperCase();

        //Vamos a la función encontrar_repetida (nos devolverá la posición de la letra más frecuente en el string), y meteremos el retorno en num_provisional.
        int num_provisional = encontrar_repetida(s);

        //Metemos en delta el retorno de calcular_delta_magic, que será el delta correspondiente de la comparación de la letra 'E' con num_provisional.
        int delta = calcular_delta_magic(num_provisional);

        //Retornamos el mensaje desencriptado llamando a Caesar.decypher.
        return Caesar.decypher(s, delta);
    }

    //En esta función comprobamos que el mensaje contenga algún carácter.
    static boolean comprobar_existencia_mensaje(String s) {
        boolean retorno = false;
        if (s.length() == 0) {
            retorno = true;
        }
        return retorno;
    }

    //En esta función buscamos cuál es el carácter con mayor frecuencia en el mensaje que nos han pasado.
    static int encontrar_repetida(String s) {

        //Creamos un array, donde cada elemento representa una letra del abecedario (de manera ordenada).
        int[] abc = new int[26];

        //Recorremos el string
        for (int i = 0; i < s.length(); i++) {

            //Si el carácter en cuestión es una letra entre la 'A' y la 'Z', incrementa el elemento correspondiente del array a esa letra.
            if (s.charAt(i) >= 'A' && s.charAt(i) <= 'Z') {
                abc[s.charAt(i) - 65]++;
            }
        }
        //Retornamos el resultado de sacar_repetida
        return sacar_repetida(abc);
    }

    //En esta función calcularemos el delta corresondiente a partir de la letra más frecuente en el mensaje.
    static int calcular_delta_magic(int num_provisional) {

        //Guardamos delta valor la resta de la posición de la letra del mensaje con mayor frecuencia menos la posición de la letra 'E' en la tabla ASCII
        int delta = num_provisional - 'E';

        //Si el valor de delta es negativo, le sumamos el número de letras del abecedario.
        if (delta < 0) {
            delta += 26;
        }
        return delta;
    }

    //Calcularemos cuál es la letra más frecuente en el string a partir del array abc. (Donde tenemos almacenados la frecuencia de cada letra)
    static int sacar_repetida(int[] abc) {

        //Guardamos en posicion_matriz la posición del primer elemento del array y en valor_provisional el valor del primer elemento del array.
        int posicion_matriz = 0, valor_provisional = abc[0];

        //Recorremos el array empezando por la segunda posición (Empezamos comparándola con la primera).
        for (int i = 1; i < abc.length; i++) {

            //Si el valor del elemento es mayor del que ya hemos almacenado, actualizaremos valor_provisional y la posición del elemento en cuestión.
            if (abc[i] > valor_provisional) {
                posicion_matriz = i;
                valor_provisional = abc[i];
            }
        }
        //Devolvemos la posición más 65 (Para devolverle la posición del carácter en la tabla ASCII.
        return posicion_matriz + 65;
    }
}