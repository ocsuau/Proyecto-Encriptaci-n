
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
        //Creamos una matriz donde guardamos la clave.
        char[] clave = key.toCharArray();
        //Creamos matriz bidimensional donde meteremos el mensaje. El número de columnas es la longitud de key, y el de filas será igual a (redondear hacia arriba(length del mensaje dividido entre length de key))
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) key.length())][key.length()];
        //Enviamos a la función crear_plantilla el array matriz y el número de elementos que no utilizaremos para meter carácteres del mensaje (Ya que puede que el array tenga más elementos que carácteres el mensaje)
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());
        //Introducimos los carácteres del mensaje dentro del array llamando a la función asignar_valores. (Además, le pasamos los valores true, número de filas y número de columnas porque queremos encriptar) (El orden de las filas y las columnas es importante)
        asignar_valores(s, matriz, true, matriz.length, matriz[0].length);
        //En la función ordenar_clave ordenamos la clave. Según los cambios en las posiciones de la clave, cambiaremos de lugar las columnas de la matriz.
        ordenar_clave(clave, matriz);
        //Devolvemos el retorno de la función convertir_mensaje. Le pasamos matriz, true, el número de columnas y el número de filas porque queremos encriptar (El orden de las columnas y filas es importante)
        return convertir_mensaje(matriz, true, matriz[0].length, matriz.length);
    }

    static String decypher(String s, String key) {
        char[][] matriz = new char[(int) Math.ceil(s.length() / (double) key.length())][key.length()];
        char[] clave = key.toCharArray();
        crear_plantilla(matriz, (matriz.length * matriz[0].length) - s.length());
        char[] clave_ordenada = ordenar_clave(clave, matriz);
        asignar_valores(s, matriz, false, matriz[0].length, matriz.length);
        recuperar_clave(matriz, clave, clave_ordenada);
        return convertir_mensaje(matriz, false, matriz.length, matriz[0].length);
    }

    static void crear_plantilla(char[][] matriz, int residuo) {
        //En está función ponemos en los últimos residuo elementos de la última fila de la matriz el carácter comodín '*', donde nos ayudará a la hora de desencriptar para no introducir los datos erróneamente
        for (int j = matriz[0].length - residuo; j < matriz[0].length; j++) {
            matriz[matriz.length - 1][j] = '*';
        }
    }

    static void asignar_valores(String s, char[][] matriz, boolean b, int longitud1, int longitud2) {
        //En esta función asignamos los valores a la matriz. Dependiendo de si queremos encriptar o desencriptar, recorreremos la matriz por filas o por columnas. (Por eso los input longitud1 y longitud2)
        int cont = 0;
        //Asignaremos los valores de la forma correcta según el valor de b (Según de si queremos desencriptar o encriptar). En los elementos donde no encuentre el carácter '*'(comodín que hemos asignado en la función crear_plantilla para su correcta desencriptación) meterá un carácter del mensaje.
        for (int i = 0; i < longitud1; i++) {
            for (int j = 0; j < longitud2; j++) {
                if (b) {
                    if (matriz[i][j] != '*') {
                        matriz[i][j] = s.charAt(cont);
                        cont++;
                    }
                } else {
                    if (matriz[j][i] != '*') {
                        matriz[j][i] = s.charAt(cont);
                        cont++;
                    }
                }
            }
        }
    }

    static String convertir_mensaje(char[][] matriz, boolean b, int longitud1, int longitud2) {
        //En esta función recorremos la matriz (según el valor de b, por filas o por columnas) y, siempre que el valor del elemento sea distinto a '*', meterá el valor del elemento en sb.
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud1; i++) {
            for (int j = 0; j < longitud2; j++) {
                if (b) {
                    if (matriz[j][i] != '*') {
                        sb.append(matriz[j][i]);
                    }
                } else {
                    if (matriz[i][j] != '*') {
                        sb.append(matriz[i][j]);
                    }
                }
            }
        }
        return sb.toString();
    }

    static void mover_valores_mensaje(char[][] matriz, int i, int j) {
        //En esta función ordenamos sustituimos los valores de una columna a otra según los parámetros que nos pasan. (para su desencriptación o encriptación)
        int valor;
        //En el bucle sólo recorremos las filas, sustituyendo las posiciones de las columnas según los parámetros de entrada.
        for (int k = 0; k < matriz.length; k++) {
            valor = matriz[k][i];
            matriz[k][i] = matriz[k][j];
            matriz[k][j] = (char) valor;
        }
    }

    static char[] ordenar_clave(char[] clave, char[][] matriz) {
        //En está función ordenamos la clave con el método bubble sort de una pasada.
        //Creamos una matriz nueva, que será igual a la clave desordenada. (no quiero trabajar sobre la matriz original porque más adelante nos hará falta)
        char[] retorno = new char[clave.length];
        for (int k = 0; k < retorno.length; k++) {
            retorno[k] = clave[k];
        }
        int valor;
        for (int i = 0; i < retorno.length; i++) {
            for (int j = i + 1; j < retorno.length; j++) {
                if (retorno[j] < retorno[i]) {
                    valor = (int) retorno[i];
                    retorno[i] = retorno[j];
                    retorno[j] = (char) valor;
                    //En el momento que ordenamos un valor de la palabra clave en su posición correspondiente, vamos a mover_valores_mensaje para cambiar los valores de las posiciones correspondientes de la clave afectada a la matriz del mensaje.
                    mover_valores_mensaje(matriz, i, j);
                }
            }
        }
        return retorno;
    }

    static void recuperar_clave(char[][] resultado, char[] clave, char[] clave_ordenada) {
        //En está función desordenamos de nuevo la clave (recuperamos) para que coincida con la clave original y porder hacer los cambios respectivos a la matriz del mensaje para su posterior desencriptación.
        int valor;
        for (int i = 0; i < clave.length; i++) {
            for (int j = i + 1; j < clave.length; j++) {
                if (clave[i] == clave_ordenada[j]) {
                    valor = clave_ordenada[i];
                    clave_ordenada[i] = clave_ordenada[j];
                    clave_ordenada[j] = (char) valor;
                    //En esté punto es donde pasamos a la función mover_valores_mensaje los valores de las posiciones nuevas para las columnas afectadas de la matriz del mensaje.
                    mover_valores_mensaje(resultado, i, j);
                }
            }
        }
    }
}