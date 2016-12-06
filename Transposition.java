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

        char[] clave = key.toCharArray();
        //Enviamos a la función crear_plantilla el array matriz y el número de elementos que no utilizaremos para meter carácteres del mensaje (Ya que puede que el array tenga más elementos que carácteres el mensaje)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y las columnas es importante)
        asignar_valores(s, matriz, true, matriz.length, matriz[0].length);

        //Al pasarle el valor true a recuperar_clave, nos realizará los cambios de posición correspondientes para ordenar la clave alfabéticamente. Estos cambios los aplicaremos a las columnas de matriz.
        ordenar_clave(matriz, clave);

        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, true, el número de columnas y el número de filas porque queremos encriptar (El orden de las columnas y filas es importante)
        return convertir_mensaje(matriz, true, matriz[0].length, matriz.length);
    }

    static String decypher(String s, String key) {
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas es la longitud de key, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre length de key))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) key.length())][key.length()];

        char[] clave = key.toCharArray();

        //Enviamos a la función crear_plantilla el array matriz y el número de elementos que no utilizaremos para meter carácteres del mensaje (Ya que puede que el array tenga más elementos que carácteres el mensaje)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());

        //Al pasarle el valor true a recuperar_clave, nos realizará los cambios de posición correspondientes para ordenar la clave alfabéticamente. Estos cambios los aplicaremos a las columnas de matriz.
        ordenar_clave(matriz, clave);

        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y las columnas es importante)
        asignar_valores(s, matriz, false, matriz[0].length, matriz.length);

        //Al pasarle el valor false a recuperar_clave, nos realizará los cambios de posición correspondientes para recuperar, a partir de la clave ordenada, la clave original. Estos cambios los aplicaremos a las columnas de matriz.
        recuperar_clave(matriz, key, clave);

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

    static void ordenar_clave(char[][] matriz, char[] clave) {
        int valor;
        for (int i = 0; i < clave.length; i++) {
            for (int j = i + 1; j < clave.length; j++) {
                if (clave[j] < clave[i]) {
                    valor = clave[i];
                    clave[i] = clave[j];
                    clave[j] = (char) valor;
                    mover_valores_mensaje(matriz, i, j);
                }
            }
        }
    }

    static void recuperar_clave(char[][] resultado, String key, char[] clave) {

        for (int i = 0, contenedor_provisional; i < key.length(); i++) {
            for (int j = i + 1; j < key.length(); j++) {
                if (clave[j] == key.charAt(i)) {
                    contenedor_provisional = clave[i];
                    clave[i] = clave[j];
                    clave[j] = (char) contenedor_provisional;
                    mover_valores_mensaje(resultado, i, j);
                    break;
                }
            }
        }
    }
}