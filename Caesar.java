
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
        return null;
    }

    //Función para recalcular delta.
    static int recalcular_delta(int delta) {
        //Recalculamos delta para obtener un número inferior o igual a 26.
        while (delta > 26) {
            delta -= 26;
        }
        return delta;
    }
}