import java.util.Arrays;

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
        //Enviamos delta a recalcular_delta para obtener el nuevo valor equivalente al que nos han dado.
        delta = recalcular_delta(delta);

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
        //Pasamos las letras a mayúsculas.
        s = s.toUpperCase();
        //Vamos a la función encontrar_repetida (nos devolverá la posición de la letra más frecuente en el string), y meteremos el retorno en num_provisional.
        int num_provisional = encontrar_repetida(s);
        //Metemos en delta el retorno de calcular_delta_magic, que será el delta correspondiente de la comparación de la letra 'E' con num_provisional.
        int delta = calcular_delta_magic(num_provisional);
        //Retornamos el mensaje desencriptado llamando a Caesar.decypher.
        return Caesar.decypher(s, delta);
    }

    //Función para recalcular delta.
    static int recalcular_delta(int delta) {
        //Recalculamos delta para obtener un número inferior o igual a 26.
        while (delta > 26) {
            delta -= 26;
        }
        return delta;
    }

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

    static int calcular_delta_magic(int num_provisional) {
        //Si num_provisional (posición de la letra con mayor frecuencia en el string) es mayor que 'E', devolvemos el resultado de num_provisional - la posición en la tabla ASCII de 'E'
        if (num_provisional > 'E') {
            return num_provisional - 'E';
        }
        //Si no se cumple el anterior if, es porque num_provisional es menor que 'E'. Al resultado de la resta 'E'-num_provisional le debemos restar 26, para que devuelva el valor correspondiente entendible para Caesar.decypher.
        return 26 - ('E' - num_provisional);
    }

    static int sacar_repetida(int[] abc) {
        //Calcularemos cuál es la letra más frecuente en el string a partir del array abc. (Donde tenemos almacenados la frecuencia de cada letra)

        //Guardamos en valor la posición del primer elemento del array y en valor_provisional el valor del primer elemento del array.
        int valor = 0;
        int valor_provisional = abc[0];

        //Recorremos el array empezando por la segunda posición (Empezamos comparándola con la primera).
        for (int i = 1; i < (abc.length - 1); i++) {
            //Si el valor del elemento es mayor del que ya hemos almacenado, actualizaremos valor provisional y la posición del elemento en cuestión.
            if (abc[i] > valor_provisional) {
                valor = i + 65;
                valor_provisional = abc[i];
            }
        }
        //Devolvemos la posición más 65 (Para devolverle la posición del carácter en la tabla ASCII.
        return valor;
    }
}