package Ficheros.Tanda2.Ejercicio1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

/**
 * Realiza un programa que sea capaz de ordenar alfabéticamente las palabras contenidas en un fichero de texto. 
 * El nombre del fichero que contiene las palabras se debe pasar como argumento en la línea de comandos. 
 * El nombre del fichero resultado debe ser el mismo que el original añadiendo la coletilla sort, por ejemplo palabras_sort.txt . 
 * Suponemos que cada palabra ocupa una línea. 
 * 
 * @author Andres Rodriguez Machado
 *
 */
public class OrdenarPalabras {

	public static void main(String[] args) {
		try {
			List<String> palabras = Files.readAllLines(Paths.get(args[0]));;
			BufferedWriter bw = new BufferedWriter(new FileWriter("POO\\Ficheros\\Tanda2\\Ejercicio1\\"+nombreArchivo(args[0])+"_Sort"));
			palabras.remove(palabras.size() - 1);

			Collections.sort(palabras);
			for (String s : palabras) {
				System.out.println(s);
				bw.write(s);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			System.err.println("EL fichero no existe.");
		}

	}

	private static String nombreArchivo(String cadena) {
		String nombre = "";
		for (char c: cadena.toCharArray ()) { 
			if (Character.compare(c, '.') != 0) {
				nombre += c;
			}else {
				break;
			}
		}
		return nombre;

	}

}