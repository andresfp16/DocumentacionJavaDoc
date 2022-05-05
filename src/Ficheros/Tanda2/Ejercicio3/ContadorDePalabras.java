package Ficheros.Tanda2.Ejercicio3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *  Realiza un programa que diga cuántas ocurrencias de una palabra hay en un fichero. Tanto el nombre del fichero 
 *  como la palabra se deben pasar como argumentos en la línea de comandos.
 * 
 * @author Andres Rodriguez Machado
 *
 */
public class ContadorDePalabras {

	public static void main(String[] args) {
		try {
			int contador = 0;
			String linea;
			ArrayList<String> palabras = new ArrayList<String> ();

			BufferedReader br = new BufferedReader(new FileReader("POO\\Ficheros\\Tanda2\\Ejercicio3\\"+args[0]));



			while ((linea = br.readLine()) != null) {
				palabras.add(linea);
			}

			for(String i : palabras) {
				if(i.equals(args[1])) {
					contador++;
				}
			}

			System.out.printf("La palabra se repite %d veces", contador);
			br.close();
		} catch (IOException e) {
			System.err.println("No existe el fichero.");
		}

	}

}
