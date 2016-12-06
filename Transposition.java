import java.util.Arrays;

public class Transposition {
    static String cypher(String s, int dim) {
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas nos lo dicen, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre número de columnas))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) dim)][dim];

        //Enviamos a la función crear_plantilla la matriz y el número de elementos de la misma que no serán rellenados por los carácteres del mensaje. (Ya que el mensaje puede que tenga menos carácteres que número de elementos el array)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y columnas es importante)
        asignar_valores(s, matriz, true, matriz.length, matriz[0].length);

        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, true, el número de columnas y el número de filas porque queremos encriptar (El orden de las columnas y filas es importante)
        return convertir_mensaje(matriz, true, matriz[0].length, matriz.length);
    }

    static String decypher(String s, int dim) {
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas nos lo dicen, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre número de columnas))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) dim)][dim];

        //Enviamos a la función crear_plantilla la matriz y el número de elementos de la misma que no serán rellenados por los carácteres del mensaje. (Ya que el mensaje puede que tenga menos carácteres que número de elementos el array)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores false, número de columnas y número de filas porque queremos desencriptar) (El orden de las columnas y las filas es importante)
        asignar_valores(s, matriz, false, matriz[0].length, matriz.length);

        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, false, el número de filas y el número de columnas porque queremos desencriptar (El orden de las filas y las columnas es importante)
        return convertir_mensaje(matriz, false, matriz.length, matriz[0].length);
    }

    static String cypher(String s, String key) {
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas es la longitud de key, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre length de key))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) key.length())][key.length()];

        //Enviamos a la función crear_plantilla el array matriz y el número de elementos que no utilizaremos para meter carácteres del mensaje (Ya que puede que el array tenga más elementos que carácteres el mensaje)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y las columnas es importante)
        asignar_valores(s, matriz, true, matriz.length, matriz[0].length);

        //Al pasarle el valor true a recuperar_clave, nos realizará los cambios de posición correspondientes para ordenar la clave alfabéticamente. Estos cambios los aplicaremos a las columnas de matriz.
        recuperar_clave(matriz, key, true);

        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, true, el número de columnas y el número de filas porque queremos encriptar (El orden de las columnas y filas es importante)
        return convertir_mensaje(matriz, true, matriz[0].length, matriz.length);
    }

    static String decypher(String s, String key) {
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas es la longitud de key, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre length de key))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) key.length())][key.length()];

        //Enviamos a la función crear_plantilla el array matriz y el número de elementos que no utilizaremos para meter carácteres del mensaje (Ya que puede que el array tenga más elementos que carácteres el mensaje)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Al pasarle el valor true a recuperar_clave, nos realizará los cambios de posición correspondientes para ordenar la clave alfabéticamente. Estos cambios los aplicaremos a las columnas de matriz.
        recuperar_clave(matriz, key, true);

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y las columnas es importante)
        asignar_valores(s, matriz, false, matriz[0].length, matriz.length);

        //Al pasarle el valor false a recuperar_clave, nos realizará los cambios de posición correspondientes para recuperar, a partir de la clave ordenada, la clave original. Estos cambios los aplicaremos a las columnas de matriz.
        recuperar_clave(matriz, key, false);

        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, true, el número de columnas y el número de filas porque queremos encriptar (El orden de las columnas y filas es importante)
        return convertir_mensaje(matriz, false, matriz.length, matriz[0].length);
    }

    static void crear_plantilla(char[][] matriz, int residuo) {
        //En está función ponemos en los últimos residuo elementos de la última fila de la matriz el carácter comodín '~', donde nos ayudará a la hora de desencriptar para no introducir los datos erróneamente
        for (int j = matriz[0].length - residuo; j < matriz[0].length; j++) {
            matriz[matriz.length - 1][j] = '~';
        }
    }

    static void asignar_valores(String s, char[][] matriz, boolean b, int longitud1, int longitud2) {
        //En esta función asignamos los valores a la matriz. Dependiendo de si queremos encriptar o desencriptar, recorreremos la matriz por filas o por columnas. (Por eso los input longitud1 y longitud2)

        //Asignaremos los valores de la forma correcta según el valor de b (Según de si queremos desencriptar o encriptar). En los elementos donde no encuentre el carácter '~'(comodín que hemos asignado en la función crear_plantilla para su correcta desencriptación) meterá un carácter del mensaje.
        for (int i = 0, cont = 0; i < longitud1; i++) {
            for (int j = 0; j < longitud2; j++) {
                if ((b == true) && (matriz[i][j] != '~')) {
                        matriz[i][j] = s.charAt(cont);
                        cont++;
                } else if ((b == false) && (matriz[j][i] != '~')) {
                        matriz[j][i] = s.charAt(cont);
                        cont++;
                }
            }
        }
    }

    static String convertir_mensaje(char[][] matriz, boolean b, int longitud1, int longitud2) {
        //En esta función recorremos la matriz (según el valor de b, por filas o por columnas) y, siempre que el valor del elemento sea distinto a '~', meterá el valor del elemento en sb.

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < longitud1; i++) {
            for (int j = 0; j < longitud2; j++) {
                if ((b == true) && (matriz[j][i] != '~')) {
                    sb.append(matriz[j][i]);
                } else if ((b == false) && (matriz[i][j] != '~')) {
                    sb.append(matriz[i][j]);
                }
            }
        }
        return sb.toString();
    }


    static void mover_valores_mensaje(char[][] matriz, int i, int j) {
        //En esta función ordenamos sustituimos los valores de una columna a otra según los parámetros que nos pasan. (para su desencriptación o encriptación)

        //En el bucle sólo recorremos las filas, sustituyendo las posiciones de las columnas según los parámetros de entrada.
        for (int k = 0, contenedor_provisional; k < matriz.length; k++) {
            contenedor_provisional = matriz[k][i];
            matriz[k][i] = matriz[k][j];
            matriz[k][j] = (char) contenedor_provisional;
        }
    }

    static void recuperar_clave(char[][] resultado, String key, boolean b) {
        //En está función, dependiendo del valor de b, desordenamos la clave (recuperamos) o la ordenamos para porder hacer los cambios respectivos a la matriz del mensaje para su posterior desencriptación o encriptación.

        //Creamos dos arrays que representan la clave original y la ordenada.
        char[] clave_ordenada = key.toCharArray();
        char[] clave_desordenada = key.toCharArray();

        //Ordenamos clave_ordenada.
        Arrays.sort(clave_ordenada);

        //j es igual a i + 1 porque empezamos a comparar a partir del segundo elemento (si coincide con el primero, igualmente no realizaremos ningún cambio.
        for (int i = 0, contenedor_provisional; i < key.length(); i++) {
            for (int j = i + 1; j < key.length(); j++) {
                //Si b es true, ordenamos la clave desordenada y hacemos los cambios respectivos a la matriz.
                if (b) {
                    if (clave_ordenada[i] == clave_desordenada[j]) {
                        contenedor_provisional = clave_desordenada[i];
                        clave_desordenada[i] = clave_desordenada[j];
                        clave_desordenada[j] = (char) contenedor_provisional;
                        mover_valores_mensaje(resultado, i, j);
                        //Realizamos break porque sabemos que al encontrar una letra, no la volveremos a encontrar ya que las palabras clave que nos dan no tienen letras repetidas.
                        break;
                    }
                    //Si b no es true, desordenamos la clave ordenada y hacemos los cambios respectivos a la matriz.
                } else {
                    if (clave_desordenada[i] == clave_ordenada[j]) {
                        contenedor_provisional = clave_ordenada[i];
                        clave_ordenada[i] = clave_ordenada[j];
                        clave_ordenada[j] = (char) contenedor_provisional;
                        mover_valores_mensaje(resultado, i, j);
                        //Realizamos break porque sabemos que al encontrar una letra, no la volveremos a encontrar ya que las palabras clave que nos dan no tienen letras repetidas.
                        break;
                    }
                }
            }
        }
    }
}